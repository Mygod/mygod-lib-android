package tk.mygod.app

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Build
import android.support.v7.widget.Toolbar
import android.view.View.OnLayoutChangeListener
import android.view.{View, ViewAnimationUtils}
import tk.mygod.view.LocationObserver

/**
 * Make sure to override onCreateSubView instead of onCreateView.
 * Based on: https://github.com/ferdy182/Android-CircularRevealFragment/blob/master/app/src/main/java/com/fernandofgallego/circularrevealfragment/sample/MainActivity.java
 * @author Mygod
 */
object CircularRevealFragment {
  private val navButtonField = classOf[Toolbar].getDeclaredField("mNavButtonView")
  navButtonField.setAccessible(true)
}

abstract class CircularRevealFragment extends ToolbarFragment {
  private var cached: FragmentStackActivity = _
  private def setStopping(value: Boolean) {
    _stopping = value
    var tmp = getActivity.asInstanceOf[FragmentStackActivity]
    if (tmp == null) tmp = cached else cached = tmp
    tmp.container.intercept = value
  }

  private var spawnLocation: (Float, Float) = _
  def setSpawnLocation(location: (Float, Float)) = spawnLocation = location
  private val stoppingAnimatorListener = new AnimatorListener {
    def onAnimationEnd(animation: Animator) = {
      CircularRevealFragment.super.stop()
      setStopping(false)
    }
    def onAnimationRepeat(animation: Animator) = ()
    def onAnimationStart(animation: Animator) = ()
    def onAnimationCancel(animation: Animator) = ()
  }

  private def getEnclosingCircleRadius(view: View, x: Float, y: Float) =
    math.hypot(math.max(x, view.getWidth - x), math.max(y, view.getHeight - y)).toFloat

  override protected def configureToolbar(view: View, title: CharSequence, navigationIcon: Int = -1) {
    super.configureToolbar(view, title, navigationIcon)
    if (navigationIcon == -1) return
    CircularRevealFragment.navButtonField.get(toolbar).asInstanceOf[View].setOnTouchListener(LocationObserver)
    //noinspection ConvertExpressionToSAM
    if (Build.VERSION.SDK_INT >= 21 && spawnLocation != null)
      view.addOnLayoutChangeListener(new OnLayoutChangeListener {
        def onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int,
                           oldRight: Int, oldBottom: Int) {
          v.removeOnLayoutChangeListener(this)
          val (x, y) = LocationObserver.getRelatedTo(spawnLocation, v)
          ViewAnimationUtils.createCircularReveal(v, x.toInt, y.toInt, 0, getEnclosingCircleRadius(v, x, y)).start
        }
      })
  }

  override def stop(sender: View = null) = {
    _stopping = true
    val view = getView
    if (Build.VERSION.SDK_INT >= 21 && view != null) {
      setStopping(true)
      val (x, y) = if (sender == null) (view.getWidth / 2F, view.getHeight.toFloat)
      else LocationObserver.getRelatedTo(sender, view)
      val animator = ViewAnimationUtils.createCircularReveal(view, x.toInt, y.toInt,
        getEnclosingCircleRadius(view, x, y), 0)
      animator.addListener(stoppingAnimatorListener)
      animator.start
    } else super.stop(sender)
  }
}

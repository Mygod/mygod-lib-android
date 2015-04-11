package tk.mygod.app

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Build
import android.support.v7.widget.Toolbar
import android.view.View.OnLayoutChangeListener
import android.view.{ViewAnimationUtils, View}
import tk.mygod.R
import tk.mygod.view.LocationObserver
import tk.mygod.util.MethodWrappers._

/**
 * Based on: https://github.com/ferdy182/Android-CircularRevealFragment/blob/master/app/src/main/java/com/fernandofgallego/circularrevealfragment/sample/MainActivity.java
 * @author Mygod
 */
object CircularRevealFragment {
  private val navButtonField = classOf[Toolbar].getDeclaredField("mNavButtonView")
  navButtonField.setAccessible(true)
}

abstract class CircularRevealFragment extends StoppableFragment {
  var toolbar: Toolbar = null
  private var spawnLocation: (Float, Float) = _
  def setSpawnLocation(location: (Float, Float)) = spawnLocation = location
  private val stoppingAnimatorListener = new AnimatorListener {
    def onAnimationEnd(animation: Animator) {
      getFragmentManager.beginTransaction.remove(CircularRevealFragment.this).commit
      getFragmentManager.executePendingTransactions
    }
    def onAnimationRepeat(animation: Animator) = ()
    def onAnimationStart(animation: Animator) = ()
    def onAnimationCancel(animation: Animator) = ()
  }

  private def getEnclosingCircleRadius(view: View, x: Float, y: Float) =
    math.hypot(math.max(x, view.getWidth - x), math.max(y, view.getHeight - y)).toFloat

  protected def configureToolbar(view: View, title: CharSequence, navigationIcon: Int = -1) {
    toolbar = view.findViewById(R.id.toolbar).asInstanceOf[Toolbar]
    toolbar.setTitle(title)
    if (navigationIcon != -1) {
      toolbar.setNavigationIcon(if (navigationIcon == 0) R.drawable.abc_ic_ab_back_mtrl_am_alpha else navigationIcon)
      toolbar.setNavigationOnClickListener((v: View) => exit(v))
      CircularRevealFragment.navButtonField.get(toolbar).asInstanceOf[View].setOnTouchListener(LocationObserver)
      if (Build.VERSION.SDK_INT >= 21) view.addOnLayoutChangeListener(new OnLayoutChangeListener {
        def onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int,
                           oldRight: Int, oldBottom: Int) {
          v.removeOnLayoutChangeListener(this)
          val (x, y) = LocationObserver.getRelatedTo(spawnLocation, v)
          ViewAnimationUtils.createCircularReveal(v, x.toInt, y.toInt, 0, getEnclosingCircleRadius(v, x, y)).start
        }
      })
    }
  }

  def stop(sender: View = null) {
    if (Build.VERSION.SDK_INT >= 21) {
      val view = getView
      val (x, y) = if (sender == null) (view.getWidth / 2F, view.getHeight.toFloat)
      else LocationObserver.getRelatedTo(sender, view)
      val animator = ViewAnimationUtils.createCircularReveal(view, x.toInt, y.toInt,
        getEnclosingCircleRadius(view, x, y), 0)
      animator.addListener(stoppingAnimatorListener)
      animator.start
    } else stoppingAnimatorListener.onAnimationEnd(null)
  }
}

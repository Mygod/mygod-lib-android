package tk.mygod.transition

import android.annotation.TargetApi
import android.content.Context
import android.transition.{TransitionValues, Visibility}
import android.util.{AttributeSet, DisplayMetrics}
import android.view.{View, ViewAnimationUtils, ViewGroup, WindowManager}
import tk.mygod.animation.NoPauseAnimator
import tk.mygod.view.LocationObserver

/**
  * @author Mygod
  */
@TargetApi(21)
class CircularReveal(context: Context, attrs: AttributeSet = null) extends Visibility(context, attrs) {
  var spawnLocation: (Float, Float) = _
  var stopper: View = _
  private val metrics = new DisplayMetrics
  private lazy val wm = context.getSystemService(Context.WINDOW_SERVICE).asInstanceOf[WindowManager]
  private def getEnclosingCircleRadius(x: Float, y: Float) =
    math.hypot(math.max(x, metrics.widthPixels - x), math.max(y, metrics.widthPixels - y)).toFloat

  override def onAppear(sceneRoot: ViewGroup, view: View,
                        startValues: TransitionValues, endValues: TransitionValues) = {
    wm.getDefaultDisplay.getMetrics(metrics)
    val (x, y) = LocationObserver.getRelatedTo(spawnLocation, view)
    new NoPauseAnimator(ViewAnimationUtils
      .createCircularReveal(view, x.toInt, y.toInt, 0, getEnclosingCircleRadius(x, y)))
  }
  override def onDisappear(sceneRoot: ViewGroup, view: View,
                           startValues: TransitionValues, endValues: TransitionValues) = {
    wm.getDefaultDisplay.getMetrics(metrics)
    val (x, y) = if (stopper == null)
      LocationObserver.getRelatedTo((metrics.widthPixels * .5F, metrics.heightPixels.toFloat), view)
    else LocationObserver.getRelatedTo(stopper, view)
    new NoPauseAnimator(ViewAnimationUtils
      .createCircularReveal(view, x.toInt, y.toInt, getEnclosingCircleRadius(x, y), 0))
  }
}

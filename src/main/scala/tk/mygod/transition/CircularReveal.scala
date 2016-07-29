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
  private def getEnclosingCircleRadius = math.hypot(math.max(spawnLocation._1, metrics.widthPixels - spawnLocation._1),
    math.max(spawnLocation._2, metrics.widthPixels - spawnLocation._2)).toFloat

  override def onAppear(sceneRoot: ViewGroup, view: View,
                        startValues: TransitionValues, endValues: TransitionValues) = {
    wm.getDefaultDisplay.getMetrics(metrics)
    val (x, y) = LocationObserver.getRelatedTo(spawnLocation, view)
    new NoPauseAnimator(ViewAnimationUtils
      .createCircularReveal(view, x.toInt, y.toInt, 0, getEnclosingCircleRadius))
  }
  override def onDisappear(sceneRoot: ViewGroup, view: View,
                           startValues: TransitionValues, endValues: TransitionValues) = {
    wm.getDefaultDisplay.getMetrics(metrics)
    val (x, y) = if (stopper == null)
      LocationObserver.getRelatedTo((metrics.widthPixels * .5F, metrics.heightPixels), view)
    else LocationObserver.getRelatedTo(stopper, view)
    new NoPauseAnimator(ViewAnimationUtils
      .createCircularReveal(view, x.toInt, y.toInt, getEnclosingCircleRadius, 0))
  }
}

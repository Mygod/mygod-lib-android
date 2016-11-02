package be.mygod.transition

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Point
import android.transition.{TransitionValues, Visibility}
import android.util.AttributeSet
import android.view.{View, ViewAnimationUtils, ViewGroup, WindowManager}
import be.mygod.animation.NoPauseAnimator
import be.mygod.view.LocationObserver

@TargetApi(21)
class CircularReveal(context: Context, attrs: AttributeSet = null) extends Visibility(context, attrs) {
  var spawnLocation: (Float, Float) = _
  var stopper: View = _
  private val size = new Point()
  private lazy val wm = context.getSystemService(Context.WINDOW_SERVICE).asInstanceOf[WindowManager]
  private def getEnclosingCircleRadius(spawnLocation: (Float, Float)) = math.hypot(
    math.max(spawnLocation._1, size.x - spawnLocation._1),
    math.max(spawnLocation._2, size.y - spawnLocation._2)).toFloat

  override def onAppear(sceneRoot: ViewGroup, view: View,
                        startValues: TransitionValues, endValues: TransitionValues) = {
    wm.getDefaultDisplay.getRealSize(size)
    val (x, y) = LocationObserver.getRelatedTo(spawnLocation, view)
    new NoPauseAnimator(ViewAnimationUtils
      .createCircularReveal(view, x.toInt, y.toInt, 0, getEnclosingCircleRadius(spawnLocation)))
  }
  override def onDisappear(sceneRoot: ViewGroup, view: View,
                           startValues: TransitionValues, endValues: TransitionValues) = {
    wm.getDefaultDisplay.getRealSize(size)
    val spawnLocation = if (stopper == null) (size.x * .5F, size.y.toFloat)
      else LocationObserver.getOnScreen(stopper)
    val (x, y) = LocationObserver.getRelatedTo(spawnLocation, view)
    new NoPauseAnimator(ViewAnimationUtils
      .createCircularReveal(view, x.toInt, y.toInt, getEnclosingCircleRadius(spawnLocation), 0))
  }
}

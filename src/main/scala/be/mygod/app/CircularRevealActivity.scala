package be.mygod.app

import android.annotation.{SuppressLint, TargetApi}
import android.content.Intent
import android.os.Bundle
import android.view.{View, ViewGroup}
import be.mygod.os.Build
import be.mygod.transition.CircularReveal

object CircularRevealActivity {
  final val EXTRA_SPAWN_LOCATION_X = "be.mygod.app.CircularRevealActivity.SPAWN_LOCATION_X"
  final val EXTRA_SPAWN_LOCATION_Y = "be.mygod.app.CircularRevealActivity.SPAWN_LOCATION_Y"

  def putLocation(intent: Intent, location: (Float, Float)) =
    intent.putExtra(EXTRA_SPAWN_LOCATION_X, location._1).putExtra(EXTRA_SPAWN_LOCATION_Y, location._2)
}

trait CircularRevealActivity extends LocationObservedActivity {
  import CircularRevealActivity._

  @TargetApi(21)
  private lazy val circularRevealTransition = new CircularReveal(this)

  @SuppressLint(Array("NewApi"))
  override protected def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    val window = getWindow
    val decor = window.getDecorView.asInstanceOf[ViewGroup]
    decor.setBackgroundColor(android.R.color.transparent) // prevent fading of background
    if (Build.version >= 21) {
      window.setEnterTransition(circularRevealTransition)
      window.setReturnTransition(circularRevealTransition)
      for (i <- 0 until decor.getChildCount) {            // decor.setTransitionGroup(true) won't work
        val child = decor.getChildAt(i).asInstanceOf[ViewGroup]
        if (child != null) child.setTransitionGroup(true)
      }
      if (savedInstanceState == null) {
        val intent = getIntent
        val x = intent.getFloatExtra(EXTRA_SPAWN_LOCATION_X, Float.NaN)
        if (!x.isNaN) {
          val y = intent.getFloatExtra(EXTRA_SPAWN_LOCATION_Y, Float.NaN)
          if (!y.isNaN) circularRevealTransition.spawnLocation = (x, y)
        }
      }
    }
  }

  def finish(stopper: View) {
    if (Build.version >= 21) circularRevealTransition.stopper = stopper
    supportFinishAfterTransition()
  }

  override def navigateUp(stopper: View) {
    if (Build.version >= 21) circularRevealTransition.stopper = stopper
    super.navigateUp(stopper)
  }
}

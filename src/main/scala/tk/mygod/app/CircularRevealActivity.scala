package tk.mygod.app

import android.annotation.TargetApi
import android.content.Intent
import android.os.Bundle
import android.view.{View, ViewGroup}
import tk.mygod.os.Build
import tk.mygod.transition.CircularReveal

/**
  * @author Mygod
  */
object CircularRevealActivity {
  final val EXTRA_SPAWN_LOCATION_X = "tk.mygod.app.CircularRevealActivity.SPAWN_LOCATION_X"
  final val EXTRA_SPAWN_LOCATION_Y = "tk.mygod.app.CircularRevealActivity.SPAWN_LOCATION_Y"

  def putLocation(intent: Intent, location: (Float, Float)) =
    intent.putExtra(EXTRA_SPAWN_LOCATION_X, location._1).putExtra(EXTRA_SPAWN_LOCATION_Y, location._2)
}

trait CircularRevealActivity extends ActivityPlus with LocationObservedActivity {
  import CircularRevealActivity._

  @TargetApi(21)
  lazy val circularRevealTransition = new CircularReveal(this)

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
}

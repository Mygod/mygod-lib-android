package be.mygod.app

import android.view.{View, MotionEvent}
import be.mygod.view.LocationObserver

/**
 * @author Mygod
 */
trait LocationObservedActivity extends ActivityPlus {
  override def dispatchTouchEvent(ev: MotionEvent): Boolean = {
    LocationObserver.onTouch(getWindow.getDecorView, ev)
    super.dispatchTouchEvent(ev)
  }

  def getLocation: (Float, Float) = LocationObserver.get(this)
  def getLocationOnScreen: (Float, Float) = LocationObserver.getOnScreen(this)
  def getLocationRelatedTo(target: View): (Float, Float) = LocationObserver.getRelatedTo(this, target)
}

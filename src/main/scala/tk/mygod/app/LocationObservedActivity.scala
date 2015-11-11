package tk.mygod.app

import android.view.{View, MotionEvent}
import tk.mygod.view.LocationObserver

/**
 * @author Mygod
 */
trait LocationObservedActivity extends ActivityPlus {
  override def dispatchTouchEvent(ev: MotionEvent) = {
    LocationObserver.onTouch(getWindow.getDecorView, ev)
    super.dispatchTouchEvent(ev)
  }

  def getLocation = LocationObserver.get(this)
  def getLocationOnScreen = LocationObserver.getOnScreen(this)
  def getLocationRelatedTo(target: View) = LocationObserver.getRelatedTo(this, target)
}

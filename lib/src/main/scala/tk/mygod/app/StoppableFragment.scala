package tk.mygod.app

import android.view.View

/**
 * A fragment that can be requested to stop.
 * @author Mygod
 */
trait StoppableFragment extends FragmentPlus {
  /**
   *
   * The fragment has been requested to be removed. However, it needs to remove itself with FragmentManager after its
   * animation, etc.
   * @param sender The View that user might have pressed, etc. which starts the stopping request. Can be null.
   */
  def stop(sender: View = null)
}

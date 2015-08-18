package tk.mygod.app

import android.view.View

/**
 * A fragment that can be requested to stop.
 * @author Mygod
 */
trait StoppableFragment extends FragmentPlus {
  protected var _stopping: Boolean = _
  def stopping = _stopping

  def exit(sender: View = null) = getActivity.asInstanceOf[FragmentStackActivity].pop(sender)

  /**
   * Only for internal use. Please use exit instead.
   * The fragment has been requested to be removed. However, it needs to remove itself with FragmentManager after its
   * animation, etc.
   * The super method shall be called no matter what or else shall hell released upon you. >:-D
   * @param sender The View that user might have pressed, etc. which starts the stopping request. Can be null.
   */
  def stop(sender: View = null) {
    _stopping = true
    val act = getActivity.asInstanceOf[FragmentStackActivity]
    if (act != null) act.popBackStack
    _stopping = false
  }
}

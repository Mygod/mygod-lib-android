package tk.mygod.app

import android.app.Fragment
import android.content.{Context, Intent}
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.view.{KeyEvent, View}
import tk.mygod.widget.InterceptableFrameLayout
import tk.mygod.R

/**
 * Use fragments as activities in one activity. StoppableFragments are acceptable for animations.
 * @author Mygod
 */
abstract class FragmentStackActivity extends ActivityPlus {
  private[app] var container: InterceptableFrameLayout = _
  private lazy val manager = getFragmentManager

  protected lazy val inputMethodManager =
    getSystemService(Context.INPUT_METHOD_SERVICE).asInstanceOf[InputMethodManager]
  protected def hideInput {
    val focus = getCurrentFocus
    if (focus != null) inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken, 0)
  }

  protected override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.activity_fragment_stack)
    container = findViewById(R.id.container).asInstanceOf[InterceptableFrameLayout]
  }

  protected override def onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    var count = getFragmentManager.getBackStackEntryCount
    while (count > 1) {
      pop()
      count = count - 1
    }
  }

  def push(fragment: Fragment): Boolean = {
    if (fragment.isAdded) return true
    hideInput
    val id = manager.getBackStackEntryCount.toString
    manager.beginTransaction.add(R.id.container, fragment, id).addToBackStack(id).commit
    false
  }

  private[app] def popBackStack = if (!getFragmentManager.popBackStackImmediate) super.onBackPressed
    // fix for support lib since I didn't really use those APIs :/
  def pop(sender: View = null): Fragment = {
    hideInput
    var i = manager.getBackStackEntryCount - 1
    while (i > 0) {
      val fragment = manager.findFragmentByTag(manager.getBackStackEntryAt(i).getName)
      fragment match {
        case stoppable: StoppableFragment => if (!stoppable.stopping) {
          stoppable.stop(sender)
          return stoppable
        }
        case _ =>
          popBackStack
          return fragment
      }
      i = i - 1
    }
    super.onBackPressed
    null
  }
  override def onBackPressed = pop()

  override def onKeyUp(keyCode: Int, event: KeyEvent): Boolean = {
    for (i <- manager.getBackStackEntryCount - 1 to 0) manager.findFragmentByTag(i.toString) match {
      case callback: KeyEvent.Callback => if (callback.onKeyUp(keyCode, event)) return true
      case _ =>
    }
    super.onKeyUp(keyCode, event)
  }
  override def onKeyLongPress(keyCode: Int, event: KeyEvent): Boolean = {
    for (i <- manager.getBackStackEntryCount - 1 to 0) manager.findFragmentByTag(i.toString) match {
      case callback: KeyEvent.Callback => if (callback.onKeyLongPress(keyCode, event)) return true
      case _ =>
    }
    super.onKeyLongPress(keyCode, event)
  }
  override def onKeyDown(keyCode: Int, event: KeyEvent): Boolean = {
    for (i <- manager.getBackStackEntryCount - 1 to 0) manager.findFragmentByTag(i.toString) match {
      case callback: KeyEvent.Callback => if (callback.onKeyDown(keyCode, event)) return true
      case _ =>
    }
    super.onKeyDown(keyCode, event)
  }
  override def onKeyMultiple(keyCode: Int, count: Int, event: KeyEvent): Boolean = {
    for (i <- manager.getBackStackEntryCount - 1 to 0) manager.findFragmentByTag(i.toString) match {
      case callback: KeyEvent.Callback => if (callback.onKeyMultiple(keyCode, count, event)) return true
      case _ =>
    }
    super.onKeyMultiple(keyCode, count, event)
  }
}

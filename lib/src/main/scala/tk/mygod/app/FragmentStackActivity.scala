package tk.mygod.app

import android.app.Fragment
import android.content.{Intent, Context}
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import tk.mygod.R

import scala.collection.mutable

/**
 * Use fragments as activities in one activity. StoppableFragments are acceptable for animations.
 * @author Mygod
 */
abstract class FragmentStackActivity extends ActivityPlus {
  private var container: FrameLayout = _
  private val stack = new mutable.Stack[Fragment]

  protected lazy val inputMethodManager =
    getSystemService(Context.INPUT_METHOD_SERVICE).asInstanceOf[InputMethodManager]
  protected def hideInput {
    val focus = getCurrentFocus
    if (focus != null) inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken, 0)
  }

  protected override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.activity_fragment_stack)
    container = findViewById(R.id.container).asInstanceOf[FrameLayout]
  }

  protected override def onNewIntent(intent: Intent) = while (stack.length > 1) pop()

  def push(fragment: Fragment): Boolean = {
    if (fragment.isAdded) return true
    hideInput
    getFragmentManager.beginTransaction.add(R.id.container, fragment).commit
    stack.push(fragment)
    false
  }
  def pop(sender: View = null) = if (stack.length <= 1) super.onBackPressed else {
    hideInput
    val fragment = stack.pop
    fragment match {
      case stoppable: StoppableFragment => stoppable.stop(sender)
      case _ =>
        getFragmentManager.beginTransaction.remove(fragment).commit
        getFragmentManager.executePendingTransactions
    }
    fragment
  }
  override def onBackPressed = pop()
}

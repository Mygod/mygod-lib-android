package tk.mygod.app

import android.app.Fragment
import android.os.Bundle
import android.view.View
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

  protected override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.activity_fragment_stack)
    container = findViewById(R.id.container).asInstanceOf[FrameLayout]
  }

  def push(fragment: Fragment) {
    getFragmentManager.beginTransaction.add(R.id.container, fragment).commit
    stack.push(fragment)
  }
  def pop(sender: View = null) = {
    val fragment = stack.pop
    fragment match {
      case stoppable: StoppableFragment => stoppable.stop(sender)
      case _ =>
        getFragmentManager.beginTransaction.remove(fragment).commit
        getFragmentManager.executePendingTransactions
    }
    fragment
  }

  override def onBackPressed = if (stack.length <= 1) super.onBackPressed else pop()
}

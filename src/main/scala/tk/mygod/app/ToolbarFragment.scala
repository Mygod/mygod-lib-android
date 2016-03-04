package tk.mygod.app

import android.support.annotation.DrawableRes
import android.view.KeyEvent
import tk.mygod.view.SimpleKeyEventCallback

/**
 * @author Mygod
 */
trait ToolbarFragment extends StoppableFragment with SimpleKeyEventCallback {
  final class ToolbarTypedView extends ToolbarTypedFindView {
    protected def findViewById(id: Int) = getView.findViewById(id)
  }
  private val toolbarView = new ToolbarTypedView
  def toolbar = toolbarView.toolbar

  protected def configureToolbar(title: CharSequence) = toolbarView.configureToolbar(title)

  protected def setNavigationIcon(@DrawableRes navigationIcon: Int) {
    toolbarView.setNavigationIcon(navigationIcon)
    toolbar.setNavigationOnClickListener(exit)
  }

  override def onKeyUp(keyCode: Int, event: KeyEvent) = toolbarView.onKeyUp(keyCode, event)
}

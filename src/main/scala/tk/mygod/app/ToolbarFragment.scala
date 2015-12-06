package tk.mygod.app

import android.support.v7.widget.Toolbar
import android.view.{KeyEvent, View}
import tk.mygod.R
import tk.mygod.view.SimpleKeyEventCallback

/**
 * @author Mygod
 */
trait ToolbarFragment extends StoppableFragment with SimpleKeyEventCallback {
  var toolbar: Toolbar = _

  protected def configureToolbar(view: View, title: CharSequence, navigationIcon: Int = -1) {
    toolbar = view.findViewById(R.id.toolbar).asInstanceOf[Toolbar]
    toolbar.setTitle(title)
    if (navigationIcon != -1) {
      toolbar.setNavigationIcon(if (navigationIcon == 0) R.drawable.abc_ic_ab_back_mtrl_am_alpha else navigationIcon)
      toolbar.setNavigationOnClickListener(exit)
    }
  }

  override def onKeyUp(keyCode: Int, event: KeyEvent) = keyCode match {
    case KeyEvent.KEYCODE_MENU =>
      if (toolbar.isOverflowMenuShowing) toolbar.hideOverflowMenu else toolbar.showOverflowMenu
    case _ => super.onKeyUp(keyCode, event)
  }
}

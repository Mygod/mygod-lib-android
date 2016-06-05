package tk.mygod.app

import android.support.annotation.DrawableRes
import android.view.KeyEvent

/**
 * @author Mygod
 */
trait ToolbarActivity extends ActivityPlus with ToolbarTypedFindView {
  protected def configureToolbar: Unit = configureToolbar(getTitle)

  override def setNavigationIcon(@DrawableRes navigationIcon: Int) {
    super.setNavigationIcon(navigationIcon)
    toolbar.setNavigationOnClickListener(_ => {
      val intent = getParentActivityIntent
      if (intent == null) finish else navigateUpTo(intent)
    })
  }

  override def onKeyUp(keyCode: Int, event: KeyEvent) = keyCode match {
    case KeyEvent.KEYCODE_MENU => toggleOverflowMenu
    case _ => super.onKeyUp(keyCode, event)
  }
}

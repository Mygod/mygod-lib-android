package tk.mygod.app

import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import tk.mygod.R

/**
 * @author Mygod
 */
trait ToolbarActivity extends ActivityPlus {
  var toolbar: Toolbar = null

  protected def configureToolbar(navigationIcon: Int = -1) {
    toolbar = findViewById(R.id.toolbar).asInstanceOf[Toolbar]
    toolbar.setTitle(getTitle)
    if (navigationIcon != -1) {
      toolbar.setNavigationIcon(if (navigationIcon == 0) R.drawable.abc_ic_ab_back_mtrl_am_alpha else navigationIcon)
      toolbar.setNavigationOnClickListener(_ => {
        val intent = getParentActivityIntent
        if (intent == null) finish else navigateUpTo(intent)
      })
    }
  }

  override def onKeyUp(keyCode: Int, event: KeyEvent) = keyCode match {
    case KeyEvent.KEYCODE_MENU =>
      if (toolbar.isOverflowMenuShowing) toolbar.hideOverflowMenu else toolbar.showOverflowMenu
    case _ => super.onKeyUp(keyCode, event)
  }
}

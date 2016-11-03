package be.mygod.app

import android.support.annotation.DrawableRes
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import be.mygod.R

/**
 * @author Mygod
 */
object ToolbarActivity {
  final val BACK = R.drawable.abc_ic_ab_back_material
}

trait ToolbarActivity extends LocationObservedActivity {
  var toolbar: Toolbar = _
  protected def configureToolbar(title: CharSequence = getTitle) {
    toolbar = findViewById(R.id.toolbar).asInstanceOf[Toolbar]
    toolbar.setTitle(title)
  }

  def setNavigationIcon(@DrawableRes navigationIcon: Int = ToolbarActivity.BACK) {
    toolbar.setNavigationIcon(navigationIcon)
    toolbar.setNavigationOnClickListener(navigateUp)
  }

  def toggleOverflowMenu = if (toolbar.isOverflowMenuShowing) toolbar.hideOverflowMenu else toolbar.showOverflowMenu

  override def onKeyUp(keyCode: Int, event: KeyEvent) = keyCode match {
    case KeyEvent.KEYCODE_MENU => toggleOverflowMenu
    case _ => super.onKeyUp(keyCode, event)
  }
}

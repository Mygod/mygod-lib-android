package tk.mygod.app

import android.support.annotation.DrawableRes
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import tk.mygod.view.SimpleKeyEventCallback
import tk.mygod.{R, TR, TypedFindView}

/**
  * @author Mygod
  */
object ToolbarTypedFindView {
  final val BACK = R.drawable.abc_ic_ab_back_material
}

trait ToolbarTypedFindView extends TypedFindView with SimpleKeyEventCallback {
  var toolbar: Toolbar = _

  def configureToolbar(title: CharSequence) {
    toolbar = findView(TR.toolbar)
    toolbar.setTitle(title)
  }

  def setNavigationIcon(@DrawableRes navigationIcon: Int) = toolbar.setNavigationIcon(navigationIcon)

  def toggleOverflowMenu = if (toolbar.isOverflowMenuShowing) toolbar.hideOverflowMenu else toolbar.showOverflowMenu

  override def onKeyUp(keyCode: Int, event: KeyEvent) = keyCode match {
    case KeyEvent.KEYCODE_MENU => toggleOverflowMenu
    case _ => super.onKeyUp(keyCode, event)
  }
}

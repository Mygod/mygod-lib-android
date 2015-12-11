package tk.mygod.app

import android.support.annotation.DrawableRes
import android.support.v7.widget.Toolbar
import android.view.{KeyEvent, View}
import tk.mygod.TypedResource._
import tk.mygod.view.SimpleKeyEventCallback
import tk.mygod.{R, TR}

/**
 * @author Mygod
 */
object ToolbarFragment {
  final val BACK = R.drawable.abc_ic_ab_back_mtrl_am_alpha
}

trait ToolbarFragment extends StoppableFragment with SimpleKeyEventCallback {
  var toolbar: Toolbar = _

  protected def configureToolbar(view: View, title: CharSequence) {
    toolbar = view.findView(TR.toolbar)
    toolbar.setTitle(title)
  }

  protected def setNavigationIcon(@DrawableRes navigationIcon: Int) = {
    toolbar.setNavigationIcon(navigationIcon)
    toolbar.setNavigationOnClickListener(exit)
  }

  override def onKeyUp(keyCode: Int, event: KeyEvent) = keyCode match {
    case KeyEvent.KEYCODE_MENU =>
      if (toolbar.isOverflowMenuShowing) toolbar.hideOverflowMenu else toolbar.showOverflowMenu
    case _ => super.onKeyUp(keyCode, event)
  }
}

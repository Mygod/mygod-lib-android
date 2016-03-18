package tk.mygod.app

import android.support.annotation.DrawableRes
import android.support.v7.widget.Toolbar
import tk.mygod.{R, TR, TypedFindView}

/**
  * @author Mygod
  */
object ToolbarTypedFindView {
  final val BACK = R.drawable.abc_ic_ab_back_mtrl_am_alpha
}

trait ToolbarTypedFindView extends TypedFindView {
  var toolbar: Toolbar = _

  def configureToolbar(title: CharSequence) {
    toolbar = findView(TR.toolbar)
    toolbar.setTitle(title)
  }

  def setNavigationIcon(@DrawableRes navigationIcon: Int) = toolbar.setNavigationIcon(navigationIcon)

  def toggleOverflowMenu = if (toolbar.isOverflowMenuShowing) toolbar.hideOverflowMenu else toolbar.showOverflowMenu
}

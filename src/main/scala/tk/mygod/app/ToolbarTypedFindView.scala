package tk.mygod.app

import android.support.annotation.DrawableRes
import android.support.v7.widget.Toolbar
import android.view.View
import tk.mygod.R

/**
  * @author Mygod
  */
object ToolbarTypedFindView {
  final val BACK = R.drawable.abc_ic_ab_back_material
}

trait ToolbarTypedFindView {
  var toolbar: Toolbar = _

  protected def findViewById(id: Int): View
  def configureToolbar(title: CharSequence) {
    toolbar = findViewById(R.id.toolbar).asInstanceOf[Toolbar]
    toolbar.setTitle(title)
  }

  def setNavigationIcon(@DrawableRes navigationIcon: Int) = toolbar.setNavigationIcon(navigationIcon)

  def toggleOverflowMenu = if (toolbar.isOverflowMenuShowing) toolbar.hideOverflowMenu else toolbar.showOverflowMenu
}

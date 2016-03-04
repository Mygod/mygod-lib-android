package tk.mygod.app

import android.support.annotation.DrawableRes

/**
 * @author Mygod
 */
trait ToolbarActivity extends ActivityPlus with ToolbarTypedFindView {
  override def setNavigationIcon(@DrawableRes navigationIcon: Int) {
    super.setNavigationIcon(navigationIcon)
    toolbar.setNavigationOnClickListener(_ => {
      val intent = getParentActivityIntent
      if (intent == null) finish else navigateUpTo(intent)
    })
  }
}

package tk.mygod.app

import android.support.v7.widget.Toolbar
import android.view.View
import tk.mygod.R
import tk.mygod.util.MethodWrappers._

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
      toolbar.setNavigationOnClickListener((v: View) => {
        val intent = getParentActivityIntent
        if (intent == null) finish else navigateUpTo(intent)
      })
    }
  }
}

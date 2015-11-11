package tk.mygod.app

import android.support.v7.widget.Toolbar
import android.view.View
import tk.mygod.R

/**
 * @author Mygod
 */
trait ToolbarFragment extends StoppableFragment {
  var toolbar: Toolbar = null

  protected def configureToolbar(view: View, title: CharSequence, navigationIcon: Int = -1) {
    toolbar = view.findViewById(R.id.toolbar).asInstanceOf[Toolbar]
    toolbar.setTitle(title)
    if (navigationIcon != -1) {
      toolbar.setNavigationIcon(if (navigationIcon == 0) R.drawable.abc_ic_ab_back_mtrl_am_alpha else navigationIcon)
      toolbar.setNavigationOnClickListener((v: View) => exit(v))
    }
  }
}

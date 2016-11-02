package be.mygod.app

import android.app.TaskStackBuilder
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
    toolbar.setNavigationOnClickListener(stopper => {
      val intent = getParentActivityIntent
      // BUG from Android: http://stackoverflow.com/a/31350642/2245107
      if (intent == null || !(isTaskRoot || shouldUpRecreateTask(intent))) this match {
        case cra: CircularRevealActivity => cra.finish(stopper)
        case _ => supportFinishAfterTransition()
      } else TaskStackBuilder.create(this).addNextIntentWithParentStack(intent).startActivities()
    })
  }

  def toggleOverflowMenu = if (toolbar.isOverflowMenuShowing) toolbar.hideOverflowMenu else toolbar.showOverflowMenu

  override def onKeyUp(keyCode: Int, event: KeyEvent) = keyCode match {
    case KeyEvent.KEYCODE_MENU => toggleOverflowMenu
    case _ => super.onKeyUp(keyCode, event)
  }
}

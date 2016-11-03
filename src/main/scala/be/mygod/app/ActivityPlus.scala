package be.mygod.app

import android.app.TaskStackBuilder
import android.graphics.Rect
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View.MeasureSpec
import android.view.{Gravity, View, Window}
import android.widget.Toast
import be.mygod.R
import be.mygod.content.ContextPlus

/**
 * @author Mygod
 */
trait ActivityPlus extends AppCompatActivity with ContextPlus {
  def makeSnackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_LONG, view: View =
    getWindow.getDecorView.findViewById(android.R.id.content)) = Snackbar.make(view, text, duration)

  def navigateUp() {
    val intent = getParentActivityIntent
    // BUG from Android: http://stackoverflow.com/a/31350642/2245107
    if (intent != null && (isTaskRoot || shouldUpRecreateTask(intent)))
      TaskStackBuilder.create(this).addNextIntentWithParentStack(intent).startActivities()
    else supportFinishAfterTransition()
  }

  def navigateUp(stopper: View) = navigateUp()

  private lazy val customTabsIntent = new CustomTabsIntent.Builder()
    .setToolbarColor(ContextCompat.getColor(this, R.color.material_primary_500)).build
  def launchUrl(uri: Uri) = customTabsIntent.launchUrl(this, uri)

  // Based on: http://stackoverflow.com/a/21026866/2245107
  def positionToast(toast: Toast, view: View, offsetX: Int = 0, offsetY: Int = 0, above: Boolean = false) = {
    val window: Window = getWindow
    val rect = new Rect
    window.getDecorView.getWindowVisibleDisplayFrame(rect)
    val viewLocation = new Array[Int](2)
    view.getLocationInWindow(viewLocation)
    val metrics = new DisplayMetrics
    window.getWindowManager.getDefaultDisplay.getMetrics(metrics)
    val toastView = toast.getView
    toastView.measure(MeasureSpec.makeMeasureSpec(metrics.widthPixels, MeasureSpec.UNSPECIFIED),
      MeasureSpec.makeMeasureSpec(metrics.heightPixels, MeasureSpec.UNSPECIFIED))
    val y = viewLocation(1) - rect.top + offsetY
    toast.setGravity(Gravity.LEFT | Gravity.TOP,
      viewLocation(0) - rect.left + (view.getWidth - toastView.getMeasuredWidth) / 2 + offsetX,
      if (above) y - view.getHeight else y + view.getHeight)
    toast
  }
}

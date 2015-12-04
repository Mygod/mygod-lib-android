package tk.mygod.app

import android.graphics.Rect
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View.MeasureSpec
import android.view.{Gravity, Window, View}
import android.widget.Toast
import tk.mygod.R
import tk.mygod.content.ContextPlus
import tk.mygod.os.Build

/**
 * @author Mygod
 */
trait ActivityPlus extends AppCompatActivity with ContextPlus {
  private var destroyed: Boolean = _

  def makeSnackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_LONG, view: View =
    getWindow.getDecorView.findViewById(android.R.id.content)) = Snackbar.make(view, text, duration)

  private lazy val customTabsIntent = new CustomTabsIntent.Builder()
    .setToolbarColor(ContextCompat.getColor(this, R.color.material_primary_500)).build
  def launchUrl(uri: Uri) = customTabsIntent.launchUrl(this, uri)

  override protected def onDestroy = {
    super.onDestroy
    destroyed = true
  }
  override def isDestroyed = if (Build.version >= 17) super.isDestroyed else destroyed

  // Based on: http://stackoverflow.com/a/21026866/2245107
  def positionToast(toast: Toast, view: View, offsetX: Int = 0, offsetY: Int = 0) = {
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
    toast.setGravity(Gravity.LEFT | Gravity.TOP,
      viewLocation(0) - rect.left + (view.getWidth - toast.getView.getMeasuredWidth) / 2 + offsetX,
      viewLocation(1) - rect.top + view.getHeight + offsetY)
    toast
  }
}

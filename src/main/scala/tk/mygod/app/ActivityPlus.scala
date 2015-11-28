package tk.mygod.app

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
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
    .setToolbarColor(ContextCompat.getColor(this, R.color.material_accent_500)).build
  def launchUrl(uri: Uri) = customTabsIntent.launchUrl(this, uri)

  override protected def onDestroy = {
    super.onDestroy
    destroyed = true
  }
  override def isDestroyed = if (Build.version >= 17) super.isDestroyed else destroyed

  def runOnUiThread[T](f: => T): Unit = super.runOnUiThread(() => f)
}

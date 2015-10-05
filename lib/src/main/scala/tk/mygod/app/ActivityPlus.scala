package tk.mygod.app

import android.os.Build
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import tk.mygod.content.ContextPlus

import scala.language.implicitConversions

/**
 * @author Mygod
 */
trait ActivityPlus extends AppCompatActivity with ContextPlus {
  private var destroyed: Boolean = _

  def makeSnackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_LONG, view: View =
    getWindow.getDecorView.findViewById(android.R.id.content)) = Snackbar.make(view, text, duration)

  override protected def onDestroy = {
    super.onDestroy
    destroyed = true
  }
  override def isDestroyed = if (Build.VERSION.SDK_INT >= 17) super.isDestroyed else destroyed

  def runOnUiThread[T](f: => T): Unit = super.runOnUiThread(() => f)
}

package tk.mygod.app

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import tk.mygod.content.ContextPlus
import tk.mygod.util.MethodWrappers._

/**
 * @author Mygod
 */
trait ActivityPlus extends AppCompatActivity with ContextPlus {
  def runOnUiThread(f: => Unit): Unit = runOnUiThread(new Runnable() { def run() = f })

  def makeSnackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_SHORT, view: View = null) = {
    val result = Snackbar.make(if (view == null) getWindow.getDecorView.findViewById(android.R.id.content) else view,
      text, duration)
    result.setAction(android.R.string.ok, (v: View) => result.dismiss)
    result
  }
}

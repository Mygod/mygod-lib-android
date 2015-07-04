package tk.mygod.app

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import tk.mygod.content.ContextPlus

import scala.language.implicitConversions

/**
 * @author Mygod
 */
trait ActivityPlus extends AppCompatActivity with ContextPlus {
  def runOnUiThread(f: => Unit): Unit = runOnUiThread(new Runnable() { def run() = f })

  def makeSnackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_LONG, view: View =
    getWindow.getDecorView.findViewById(android.R.id.content)) = Snackbar.make(view, text, duration)
}

package tk.mygod.app

import android.support.v7.app.AppCompatActivity
import tk.mygod.content.ContextPlus

/**
 * @author Mygod
 */
trait ActivityPlus extends AppCompatActivity with ContextPlus {
  def runOnUiThread(f: => Unit): Unit = runOnUiThread(new Runnable() { def run() = f })
}

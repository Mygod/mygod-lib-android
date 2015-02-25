package tk.mygod.content

import android.content.Context
import android.widget.Toast

import scala.language.implicitConversions

/**
 * @author Mygod
 */
trait ContextPlus extends Context {
  implicit def getStringImplicit(resourceId : Int): String = getString(resourceId)

  def showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, text, duration).show
}

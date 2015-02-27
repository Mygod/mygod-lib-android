package tk.mygod.content

import android.app.{PendingIntent, Activity}
import android.content.{Intent, Context}
import android.widget.Toast

import scala.language.implicitConversions
import scala.reflect.ClassTag

/**
 * @author Mygod
 */
trait ContextPlus extends Context {
  implicit def getStringImplicit(resourceId : Int): String = getString(resourceId)

  def intentActivity[A <: Activity](implicit ct: ClassTag[A]) = new Intent(this, ct.runtimeClass)
  def pendingIntent[A <: Activity](implicit ct: ClassTag[A]) =
    PendingIntent.getActivity(this, 0, intentActivity[A], PendingIntent.FLAG_UPDATE_CURRENT)
  def pendingIntentBroadcast(action: String) =
    PendingIntent.getBroadcast(this, 0, new Intent().setAction(action), PendingIntent.FLAG_UPDATE_CURRENT)
  
  def showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, text, duration).show
}

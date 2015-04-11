package tk.mygod.app

import android.app.{PendingIntent, Activity, Fragment}
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import tk.mygod.util.UriUtils._

import scala.language.implicitConversions
import scala.reflect.ClassTag

/**
 * @author Mygod
 */
trait FragmentPlus extends Fragment {
  implicit def getStringImplicit(resourceId : Int): String = getString(resourceId)
  implicit def getUri(resourceId : Int): Uri = getString(resourceId)

  def intentActivity[A <: Activity](implicit ct: ClassTag[A]) = new Intent(getActivity, ct.runtimeClass)
  def pendingIntent[A <: Activity](implicit ct: ClassTag[A]) =
    PendingIntent.getActivity(getActivity, 0, intentActivity[A], PendingIntent.FLAG_UPDATE_CURRENT)
  def pendingIntentBroadcast(action: String) =
    PendingIntent.getBroadcast(getActivity, 0, new Intent().setAction(action), PendingIntent.FLAG_UPDATE_CURRENT)

  def showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(getActivity, text, duration).show
}

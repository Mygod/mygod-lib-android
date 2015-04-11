package tk.mygod.app

import android.app.{PendingIntent, Activity, Fragment}
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.widget.Toast
import tk.mygod.util.UriUtils._

import scala.language.implicitConversions
import scala.reflect.ClassTag

/**
 * @author Mygod
 */
trait FragmentPlus extends Fragment {
  implicit def getStringImplicit(id : Int): String = getString(id)
  implicit def getDrawableImplicit(id : Int): Drawable = ContextCompat.getDrawable(getActivity, id)
  implicit def getUri(id : Int): Uri = getString(id)

  def intentActivity[A <: Activity](implicit ct: ClassTag[A]) = new Intent(getActivity, ct.runtimeClass)
  def pendingIntent[A <: Activity](implicit ct: ClassTag[A]) =
    PendingIntent.getActivity(getActivity, 0, intentActivity[A], PendingIntent.FLAG_UPDATE_CURRENT)
  def pendingIntentBroadcast(action: String) =
    PendingIntent.getBroadcast(getActivity, 0, new Intent().setAction(action), PendingIntent.FLAG_UPDATE_CURRENT)

  def showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(getActivity, text, duration).show

  def runOnUiThread(f: => Unit): Unit = getActivity.runOnUiThread(new Runnable() { def run() = f })
}

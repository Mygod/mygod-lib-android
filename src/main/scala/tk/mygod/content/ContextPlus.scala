package tk.mygod.content

import android.app.{Activity, PendingIntent}
import android.content.{ClipData, ClipboardManager, Context, Intent}
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.widget.Toast
import tk.mygod.R
import tk.mygod.os.Build
import tk.mygod.util.Logcat
import tk.mygod.util.UriUtils._

import scala.language.implicitConversions
import scala.reflect.ClassTag

/**
 * @author Mygod
 */
trait ContextPlus extends Context {
  implicit def getStringImplicit(id : Int): String = getString(id)
  implicit def getDrawableImplicit(id : Int): Drawable = ContextCompat.getDrawable(this, id)
  implicit def getUri(id : Int): Uri = getString(id)
  def systemService[T](implicit ct: ClassTag[T]) = getSystemService(ct.runtimeClass).asInstanceOf[T]

  def intentActivity[A <: Activity](implicit ct: ClassTag[A]) = new Intent(this, ct.runtimeClass)
  def pendingIntent[A <: Activity](implicit ct: ClassTag[A]) =
    PendingIntent.getActivity(this, 0, intentActivity[A], PendingIntent.FLAG_UPDATE_CURRENT)
  def pendingIntentBroadcast(action: String) =
    PendingIntent.getBroadcast(this, 0, new Intent().setAction(action), PendingIntent.FLAG_UPDATE_CURRENT)

  def share(text: String, subject: String = null) = if (Build.isChromeOS)
    systemService[ClipboardManager].setPrimaryClip(ClipData.newPlainText(subject, Logcat.fetch)) else {
    val intent = new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, text)
    if (subject != null) intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    startActivity(Intent.createChooser(intent, R.string.share_using))
  }
  
  def showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, text, duration).show
}

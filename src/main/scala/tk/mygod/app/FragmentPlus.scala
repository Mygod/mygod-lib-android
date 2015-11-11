package tk.mygod.app

import android.app.{Activity, Fragment, PendingIntent}
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import tk.mygod.util.UriUtils._

import scala.language.implicitConversions
import scala.reflect.ClassTag

/**
 * @author Mygod
 */
trait FragmentPlus extends Fragment {
  def isFullscreen = false
  override def onStart {
    super.onStart
    if (isFullscreen) {
      val view = getView
      if (!view.requestFocus) {
        view.setFocusable(true)
        view.setFocusableInTouchMode(true)
        view.requestFocus
      }
    }
  }

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
  def makeSnackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_LONG, view: View =
    getActivity.getWindow.getDecorView.findViewById(android.R.id.content)) = Snackbar.make(view, text, duration)

  def runOnUiThread[T](f: => T): Unit = getActivity.runOnUiThread(() => f)
}
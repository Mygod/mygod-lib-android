package tk.mygod.net

import java.net.URL

import android.app.ProgressDialog
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import tk.mygod.R
import tk.mygod.app.ActivityPlus
import tk.mygod.util.CloseUtils._
import tk.mygod.util.IOUtils
import tk.mygod.util.Conversions._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object UpdateManager {
  def check(activity: ActivityPlus, productUri: Uri, handler: Handler = null) {
    val dialog = ProgressDialog.show(activity, "", activity.getString(R.string.checking_for_updates), true, true)
    val h = if (handler == null) new Handler else handler
    Future(try {
      val uri = autoClose(new URL("https://mygod.tk/product/update/%d/".format(activity.getPackageManager
        .getPackageInfo(activity.getPackageName, 0).versionCode)).openStream())(stream => IOUtils.readAllText(stream))
      if (dialog.isShowing) {
        dialog.dismiss
        h.post(() => if (TextUtils.isEmpty(uri))
          activity.makeSnackbar(activity.getString(R.string.no_updates_available)).show
        else new AlertDialog.Builder(activity).setTitle(R.string.update_available)
          .setPositiveButton(R.string.download,
            ((dialog, which) => activity.startActivity(new Intent(Intent.ACTION_VIEW, uri))): OnClickListener)
          .setNegativeButton(R.string.learn_more,
            ((dialog, which) => activity.launchUrl(productUri)): OnClickListener)
          .show)
      }
    } catch {
      case e: Exception =>
        if (dialog.isShowing) {
          dialog.dismiss
          h.post(() => activity.makeToast(e.getMessage).show)
        }
    })
  }
}

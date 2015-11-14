package tk.mygod.net

import java.net.URL

import android.app.ProgressDialog
import android.content.DialogInterface.OnClickListener
import android.content.{Context, Intent}
import android.net.Uri
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.widget.Toast
import tk.mygod.R
import tk.mygod.util.CloseUtils._
import tk.mygod.util.IOUtils

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object UpdateManager {
  private def startUrl(context: Context, uri: String) =
    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
  def check(context: Context, productUri: String, handler: Handler = null) = {
    val dialog = ProgressDialog.show(context, "", context.getString(R.string.checking_for_updates), true, true)
    val h = if (handler == null) new Handler else handler
    Future(try {
      val uri = autoClose(new URL("https://mygod.tk/product/update/%d/".format(context.getPackageManager
        .getPackageInfo(context.getPackageName, 0).versionCode)).openStream())(stream => IOUtils.readAllText(stream))
      if (dialog.isShowing) {
        dialog.dismiss
        h.post(() => if (TextUtils.isEmpty(uri))
          Toast.makeText(context, context.getString(R.string.no_updates_available), Toast.LENGTH_SHORT).show
        else new AlertDialog.Builder(context).setTitle(R.string.update_available)
          .setPositiveButton(R.string.download, ((dialog, which) => startUrl(context, uri)): OnClickListener)
          .setNegativeButton(R.string.learn_more, ((dialog, which) => startUrl(context, productUri)): OnClickListener)
          .show)
      }
    } catch {
      case e: Exception =>
        if (dialog.isShowing) {
          dialog.dismiss
          h.post(() => Toast.makeText(context, e.getMessage, Toast.LENGTH_SHORT).show)
        }
    })
  }
}

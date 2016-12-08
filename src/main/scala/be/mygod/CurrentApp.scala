package be.mygod

import java.util.Date
import java.util.zip.ZipFile

import android.content.Context
import android.content.pm.PackageManager
import be.mygod.util.CloseUtils._

/**
 * @author Mygod
 */
object CurrentApp {
  /**
   * Get version name for the current package.
   * @param context Current context.
   * @return Version name if succeeded, null otherwise.
   */
  def getVersionName(context: Context): String = {
    try context.getPackageManager.getPackageInfo(context.getPackageName, 0).versionName catch {
      case e: PackageManager.NameNotFoundException =>
        e.printStackTrace()
        null
    }
  }

  def getBuildTime(context: Context): Date =
    autoClose(new ZipFile(context.getPackageManager.getApplicationInfo(context.getPackageName, 0).sourceDir))(
      file => new Date(file.getEntry("META-INF/MANIFEST.MF").getTime))
}

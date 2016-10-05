package be.mygod

import java.io.IOException
import java.util.Date
import java.util.zip.ZipFile

import android.content.Context
import android.content.pm.PackageManager

/**
 * @author Mygod
 */
object CurrentApp {
  /**
   * Get version name for the current package.
   * @param context Current context.
   * @return Version name if succeeded, null otherwise.
   */
  def getVersionName(context: Context) = {
    try context.getPackageManager.getPackageInfo(context.getPackageName, 0).versionName catch {
      case e: PackageManager.NameNotFoundException =>
        e.printStackTrace
        null
    }
  }

  def getBuildTime(context: Context) = {
    var file: ZipFile = null
    try {
      file = new ZipFile(context.getPackageManager.getApplicationInfo(context.getPackageName, 0).sourceDir)
      new Date(file.getEntry("META-INF/MANIFEST.MF").getTime)
    }
    catch {
      case e: Exception =>
        e.printStackTrace
        new Date
    } finally if (file != null) try file.close catch {
      case e: IOException => e.printStackTrace
    }
  }
}

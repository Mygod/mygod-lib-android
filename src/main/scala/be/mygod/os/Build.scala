package be.mygod.os

import android.os.{Build => osBuild}

object Build {
  lazy val version = osBuild.VERSION.SDK_INT
  lazy val isChromeOS: Boolean = osBuild.BRAND.contains("chromium")
}

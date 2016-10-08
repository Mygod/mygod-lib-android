package be.mygod.util

import java.util.Locale

import android.text.TextUtils

/**
 * @author Mygod
 */
object LocaleUtils {
  private val localeMatcher = "^([^_]*)(_([^_]*)(_#(.*))?)?$"r

  def parseLocale(value: String): Locale = {
    val o = localeMatcher.findFirstMatchIn(value.replace('-', '_'))
    if (o.nonEmpty) {
      val m = o.get
      val g1 = m.group(1)
      val g3 = m.group(3)
      val g5 = m.group(5)
      if (TextUtils.isEmpty(g5))
        if (TextUtils.isEmpty(g3))
          if (TextUtils.isEmpty(g1)) null
          else new Locale(g1)
        else new Locale(g1, g3)
      else new Locale(g1, g3, g5)
    }
    else null
  }
}

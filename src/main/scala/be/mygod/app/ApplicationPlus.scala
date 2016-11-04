package be.mygod.app

import java.util.Locale

import android.app.Application
import android.content.res.Configuration
import android.os.LocaleList
import be.mygod.content.ContextPlus
import be.mygod.os.Build

object ApplicationPlus {
  // The ones in Locale doesn't have script included
  private final lazy val SIMPLIFIED_CHINESE =
    if (Build.version >= 21) Locale.forLanguageTag("zh-Hans-CN") else Locale.SIMPLIFIED_CHINESE
}

class ApplicationPlus extends Application with ContextPlus {
  import ApplicationPlus._

  private def checkChineseLocale(locale: Locale): Locale =
    if (locale.getLanguage == "zh" && locale.getCountry != "CN") SIMPLIFIED_CHINESE else null

  private def checkChineseLocale(config: Configuration): Unit = if (Build.version >= 24) {
    val localeList = config.getLocales
    val newList = new Array[Locale](localeList.size())
    var changed = false
    for (i <- 0 until localeList.size()) {
      val locale = localeList.get(i)
      val newLocale = checkChineseLocale(locale)
      if (newLocale == null) newList(i) = locale else {
        newList(i) = newLocale
        changed = true
      }
    }
    if (changed) {
      val newConfig = new Configuration(config)
      newConfig.setLocales(new LocaleList(newList.distinct: _*))
      val res = getResources
      res.updateConfiguration(newConfig, res.getDisplayMetrics)
    }
  } else {
    val newLocale = checkChineseLocale(config.locale)
    if (config.locale != newLocale) {
      val newConfig = new Configuration(config)
      newConfig.locale = newLocale
      val res = getResources
      res.updateConfiguration(newConfig, res.getDisplayMetrics)
    }
  }

  override def onCreate() {
    super.onCreate()
    checkChineseLocale(getResources.getConfiguration)
  }

  override def onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    checkChineseLocale(newConfig)
  }
}

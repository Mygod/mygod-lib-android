package be.mygod.util

import android.net.Uri

import scala.language.implicitConversions

/**
 * @author Mygod
 */
object Conversions {
  implicit def parseUri(uri: CharSequence): Uri = Uri.parse(uri.toString)
  implicit def toRunnable[T](f: => T): Runnable = () => f
}

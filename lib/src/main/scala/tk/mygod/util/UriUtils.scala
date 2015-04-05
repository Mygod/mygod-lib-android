package tk.mygod.util

import android.net.Uri

import scala.language.implicitConversions

/**
 * @author Mygod
 */
object UriUtils {
  implicit def parseUri(uri: CharSequence): Uri = Uri.parse(uri.toString)
}

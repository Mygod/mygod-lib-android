package tk.mygod.util

import android.net.Uri

import scala.language.implicitConversions

/**
 * @author Mygod
 */
object UriUtils {
  implicit def parseUri(uriString: String): Uri = Uri.parse(uriString)
}

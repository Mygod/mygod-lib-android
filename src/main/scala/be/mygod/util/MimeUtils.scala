package be.mygod.util

import android.webkit.MimeTypeMap

/**
  * @author Mygod
  */
object MimeUtils {
  def getMimeType(url: String) = {
    val extension = MimeTypeMap.getFileExtensionFromUrl(url)
    if (extension == null) null else MimeTypeMap.getSingleton.getMimeTypeFromExtension(extension)
  }
  def getExtension(mimeType: String) = MimeTypeMap.getSingleton.getExtensionFromMimeType(mimeType)
}

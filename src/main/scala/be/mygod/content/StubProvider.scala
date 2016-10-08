package be.mygod.content

import android.content.{ContentProvider, ContentValues}
import android.net.Uri
import be.mygod.util.MimeUtils

/**
  * @author Mygod
  */
class StubProvider extends ContentProvider {
  def onCreate = true
  def getType(uri: Uri) =
    if (uri.getScheme == "file") MimeUtils.getMimeType(uri.toString) else getContext.getContentResolver.getType(uri)
  def query(uri: Uri, projection: Array[String], selection: String, selectionArgs: Array[String], sortOrder: String) =
    null
  def insert(uri: Uri, values: ContentValues) = null
  def delete(uri: Uri, selection: String, selectionArgs: Array[String]) = 0
  def update(uri: Uri, values: ContentValues, selection: String, selectionArgs: Array[String]) = 0
}

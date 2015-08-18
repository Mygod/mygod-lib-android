package tk.mygod.app

import java.io.File

import android.Manifest
import android.content.pm.PackageManager
import android.content.{Context, DialogInterface}
import android.os.{Bundle, Environment}
import android.support.v13.app.FragmentCompat
import android.support.v13.app.FragmentCompat.OnRequestPermissionsResultCallback
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.Toolbar.OnMenuItemClickListener
import android.view.{LayoutInflater, MenuItem, View, ViewGroup}
import android.webkit.MimeTypeMap
import android.widget._
import tk.mygod.R
import tk.mygod.app.SaveFileFragment._
import tk.mygod.util.MethodWrappers._
import tk.mygod.view.LocationObserver

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
 * @author Mygod
 */
object SaveFileFragment {
  private final val PERMISSION_REQUEST_STORAGE = 0
  private final val REQUEST_CODE = "RequestCode"
  private final val MIME_TYPE = "MimeType"
  private final val CURRENT_DIRECTORY = "CurrentDirectory"

  private final class DirectoryDisplay(context: Context, private val content: mutable.ArrayBuffer[File])
    extends ArrayAdapter[File](context, android.R.layout.activity_list_item, android.R.id.text1, content) {
    override def getView(position: Int, convertView: View, parent: ViewGroup) = {
      var result = convertView
      if (result == null) {
        result = super.getView(position, null, parent)
        result.setOnTouchListener(LocationObserver)
      }
      val file = content(position)
      if (file != null) {
        result.findViewById(android.R.id.icon).asInstanceOf[ImageView]
          .setImageResource(if (file.isFile) R.drawable.ic_doc_generic_am_alpha else R.drawable.ic_doc_folder_alpha)
        result.findViewById(android.R.id.text1).asInstanceOf[TextView].setText(file.getName)
      }
      result
    }
  }

  trait SaveFileCallback {
    def saveFilePicked(file: File, requestCode: Int)
  }
}

final class SaveFileFragment(private var requestCode: Int, private var mimeType: String, path: String = null,
                             private var defaultFileName: String = null) extends CircularRevealFragment
  with OnMenuItemClickListener with OnRequestPermissionsResultCallback {
  def this() = this(0, null, null, null)

  private var currentDirectory = if (path == null) Environment.getExternalStorageDirectory else new File(path)
  private var directoryDisplay: DirectoryDisplay = _
  private var fileName: AppCompatEditText = _
  private var directoryView: ListView = _
  private var storageGranted: Boolean = _

  private def setCurrentDirectory(directory: File = null) {
    if (directory != null) {
      currentDirectory = directory
      var path = currentDirectory.getAbsolutePath
      if (currentDirectory.getParent != null) path += "/"
      toolbar.setSubtitle(path)
    }
    directoryDisplay.clear
    if (!storageGranted) return
    if (currentDirectory.getParent != null) directoryDisplay.add(new File(".."))
    val list = currentDirectory.listFiles()
    if (list != null) directoryDisplay.addAll(list.toSeq.filter(file => file.isDirectory || file.isFile && mimeType
      .equals(MimeTypeMap.getSingleton.getMimeTypeFromExtension(MimeTypeMap
        .getFileExtensionFromUrl(file.getAbsolutePath)))).sortWith((lhs, rhs) => {
      var result = lhs.isFile.compareTo(rhs.isFile)
      if (result != 0) result < 0 else {
        result = lhs.getName.compareToIgnoreCase(rhs.getName)
        if (result == 0) lhs.getName < rhs.getName else result < 0
      }
    }))
  }

  private def submit(v: View) = if (new File(currentDirectory, fileName.getText.toString).exists) {
      var button: Button = null
      button = new AlertDialog.Builder(getActivity)
        .setTitle(R.string.dialog_overwrite_confirm_title)
        .setPositiveButton(android.R.string.yes, (dialog: DialogInterface, which: Int) => confirm(button))
        .setNegativeButton(android.R.string.no, null).show().getButton(DialogInterface.BUTTON_POSITIVE)
      button.setOnTouchListener(LocationObserver)
    } else confirm(v)

  private def confirm(v: View) {
    getActivity.asInstanceOf[SaveFileCallback]
      .saveFilePicked(new File(currentDirectory, fileName.getText.toString), requestCode)
    exit(v)
  }

  override def isFullscreen = true

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    storageGranted = ContextCompat.checkSelfPermission(getActivity, Manifest.permission.READ_EXTERNAL_STORAGE) ==
      PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity,
      Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    if (!storageGranted) FragmentCompat.requestPermissions(this, Array(Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_STORAGE)
    val result = inflater.inflate(R.layout.fragment_save_file, container, false)
    configureToolbar(result, R.string.fragment_save_file_title, 0)
    toolbar.inflateMenu(R.menu.save_file_actions)
    toolbar.setOnMenuItemClickListener(this)
    fileName = result.findViewById(R.id.file_name).asInstanceOf[AppCompatEditText]
    if (defaultFileName != null) fileName.setText(defaultFileName)
    directoryDisplay = new DirectoryDisplay(getActivity, new mutable.ArrayBuffer[File])
    directoryView = result.findViewById(R.id.directory_view).asInstanceOf[ListView]
    directoryView.setAdapter(directoryDisplay)
    directoryView.setOnItemClickListener((parent: AdapterView[_], view: View, position: Int, id: Long) =>
      if (position >= 0 && position < directoryDisplay.getCount) {
        val file = directoryDisplay.getItem(position)
        if (file.isFile) {
          fileName.setText(file.getName)
          submit(view)
        } else setCurrentDirectory(if (file.getName == "..") currentDirectory.getParentFile else file)
      })
    val ok = result.findViewById(R.id.ok)
    ok.setOnTouchListener(LocationObserver)
    ok.setOnClickListener((v: View) => submit(v))
    setCurrentDirectory(currentDirectory)
    result
  }

  override def onRequestPermissionsResult(requestCode: Int, permissions: Array[String], grantResults: Array[Int]) =
    requestCode match {
      case PERMISSION_REQUEST_STORAGE =>
        storageGranted = grantResults(0) == PackageManager.PERMISSION_GRANTED &&
          grantResults(1) == PackageManager.PERMISSION_GRANTED
        if (storageGranted) setCurrentDirectory() else {
          showToast(R.string.fragment_save_file_storage_denied, Toast.LENGTH_LONG)
          exit()
        }
      case _ => super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

  def onMenuItemClick(item: MenuItem): Boolean = {
    if (item.getItemId != R.id.action_create_dir) return super.onOptionsItemSelected(item)
    val text = new EditText(getActivity)
    new AlertDialog.Builder(getActivity).setTitle(R.string.dialog_create_dir_title).setView(text)
      .setPositiveButton(android.R.string.ok, (dialog: DialogInterface, which: Int) =>
        if (new File(currentDirectory, text.getText.toString).mkdirs) setCurrentDirectory(null))
      .setNegativeButton(android.R.string.cancel, null).show
    true
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    if (savedInstanceState == null) return
    requestCode = savedInstanceState.getInt(REQUEST_CODE)
    mimeType = savedInstanceState.getString(MIME_TYPE)
    setCurrentDirectory(new File(savedInstanceState.getString(CURRENT_DIRECTORY)))
  }

  override def onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putInt(REQUEST_CODE, requestCode)
    outState.putString(MIME_TYPE, mimeType)
    outState.putString(CURRENT_DIRECTORY, currentDirectory.getPath)
  }
}

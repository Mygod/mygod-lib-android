package tk.mygod.app

import java.io.File

import android.content.{Context, DialogInterface}
import android.os.{Bundle, Environment}
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.Toolbar.OnMenuItemClickListener
import android.view.{LayoutInflater, MenuItem, View, ViewGroup}
import android.webkit.MimeTypeMap
import android.widget._
import tk.mygod.R
import tk.mygod.app.SaveFileFragment.DirectoryDisplay
import tk.mygod.util.MethodWrappers._
import tk.mygod.view.LocationObserver

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
 * @author Mygod
 */
object SaveFileFragment {
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
}

final class SaveFileFragment(private val callback: (File) => Any, private var mimeType: String, path: String = null,
                             private val defaultFileName: String = null)
  extends CircularRevealFragment with OnMenuItemClickListener {
  private var currentDirectory = if (path == null) Environment.getExternalStorageDirectory else new File(path)
  private val directoryList = new mutable.ArrayBuffer[File]
  private var fileName: AppCompatEditText = null
  private var directoryView: ListView = null

  private def setCurrentDirectory(directory: File) {
    if (directory != null) {
      currentDirectory = directory
      var path = currentDirectory.getAbsolutePath
      if (currentDirectory.getParent != null) path += "/"
      toolbar.setSubtitle(path)
    }
    directoryList.clear
    val files = currentDirectory.listFiles().filter(file => file.isDirectory || file.isFile && mimeType
      .equals(MimeTypeMap.getSingleton.getMimeTypeFromExtension(MimeTypeMap
      .getFileExtensionFromUrl(file.getAbsolutePath)))).sortWith((lhs, rhs) => {
        var result = lhs.isFile.compareTo(rhs.isFile)
        if (result != 0) result < 0 else {
          result = lhs.getName.compareToIgnoreCase(rhs.getName)
          if (result == 0) lhs.getName < rhs.getName else result < 0
        }
      })
    if (currentDirectory.getParent != null) directoryList.append(new File(".."))
    directoryList.appendAll(files)
    directoryView.setAdapter(new DirectoryDisplay(getActivity, directoryList))
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
    callback(new File(currentDirectory, fileName.getText.toString))
    exit(v)
  }

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {
    val result = inflater.inflate(R.layout.fragment_save_file, container, false)
    configureToolbar(result, R.string.title_fragment_save_file, 0)
    toolbar.inflateMenu(R.menu.save_file_actions)
    toolbar.setOnMenuItemClickListener(this)
    fileName = result.findViewById(R.id.file_name).asInstanceOf[AppCompatEditText]
    if (defaultFileName != null) fileName.setText(defaultFileName)
    directoryView = result.findViewById(R.id.directory_view).asInstanceOf[ListView]
    directoryView.setOnItemClickListener((parent: AdapterView[_], view: View, position: Int, id: Long) =>
      if (position >= 0 && position < directoryList.size) {
        val file = directoryList.get(position)
        if (file.isFile) {
          fileName.setText(file.getName)
          submit(view)
        } else setCurrentDirectory(if ("..".equals(file.getName)) currentDirectory.getParentFile else file)
      })
    val ok = result.findViewById(R.id.ok)
    ok.setOnTouchListener(LocationObserver)
    ok.setOnClickListener((v: View) => submit(v))
    setCurrentDirectory(currentDirectory)
    result
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
}

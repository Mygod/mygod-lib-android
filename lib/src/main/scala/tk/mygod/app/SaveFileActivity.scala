package tk.mygod.app

import java.io.File

import android.app.{Activity, AlertDialog}
import android.content.{Context, DialogInterface, Intent}
import android.net.Uri
import android.os.{Bundle, Environment}
import android.view.{Menu, MenuItem, View, ViewGroup}
import android.webkit.MimeTypeMap
import android.widget._
import tk.mygod.R
import tk.mygod.app.SaveFileActivity.DirectoryDisplay
import tk.mygod.util.MethodWrappers._

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
 * @author Mygod
 */
object SaveFileActivity {
  val EXTRA_CURRENT_DIRECTORY = "tk.mygod.intent.extra.CurrentDirectory"

  private final class DirectoryDisplay(context: Context, private val content: mutable.ArrayBuffer[File])
    extends ArrayAdapter[File](context, android.R.layout.activity_list_item, android.R.id.text1, content) {
    override def getView(position: Int, convertView: View, parent: ViewGroup) = {
      val result = if (convertView == null) super.getView(position, null, parent) else convertView
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

final class SaveFileActivity extends ToolbarActivity {
  private var mimeType: String = null
  private var currentDirectory: File = null
  private val directoryList = new mutable.ArrayBuffer[File]
  private var fileName: EditText = null
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
    directoryView.setAdapter(new DirectoryDisplay(this, directoryList))
  }

  def submit(view: View) {
    if (new File(currentDirectory, fileName.getText.toString).exists) new AlertDialog.Builder(this)
      .setTitle(R.string.dialog_overwrite_confirm_title)
      .setPositiveButton(android.R.string.yes, (dialog: DialogInterface, which: Int) => confirm)
      .setNegativeButton(android.R.string.no, null).show()
    else confirm
  }

  private def confirm {
    setResult(Activity.RESULT_OK,
      new Intent().setData(Uri.fromFile(new File(currentDirectory, fileName.getText.toString))))
    finish
  }

  protected override def onCreate(icicle: Bundle) {
    super.onCreate(icicle)
    setContentView(R.layout.activity_save_file)
    configureToolbar(0)
    directoryView = findViewById(R.id.directory_view).asInstanceOf[ListView]
    directoryView.setOnItemClickListener((parent: AdapterView[_], view: View, position: Int, id: Long) => {
      if (position < 0 && position >= directoryList.size) return
      val file = directoryList.get(position)
      if (file.isFile) {
        fileName.setText(file.getName)
        submit(null)
      }
      else setCurrentDirectory(if ("..".equals(file.getName)) currentDirectory.getParentFile else file)
    })
    fileName = findViewById(R.id.file_name).asInstanceOf[EditText]
    val intent = getIntent
    mimeType = intent.getType
    val path = intent.getStringExtra(SaveFileActivity.EXTRA_CURRENT_DIRECTORY)
    val defaultFileName = intent.getStringExtra(Intent.EXTRA_TITLE)
    setCurrentDirectory(if (path == null) Environment.getExternalStorageDirectory else new File(path))
    if (defaultFileName != null) fileName.setText(defaultFileName)
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.save_file_actions, menu)
    true
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {
    if (item.getItemId != R.id.action_create_dir) return super.onOptionsItemSelected(item)
    val text = new EditText(this)
    new AlertDialog.Builder(this).setTitle(R.string.dialog_create_dir_title).setView(text)
      .setPositiveButton(android.R.string.ok, (dialog: DialogInterface, which: Int) =>
        if (new File(currentDirectory, text.getText.toString).mkdirs) setCurrentDirectory(null))
      .setNegativeButton(android.R.string.cancel, null).show
    true
  }
}

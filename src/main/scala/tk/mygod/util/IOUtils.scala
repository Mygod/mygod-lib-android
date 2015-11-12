package tk.mygod.util

import java.io.{OutputStreamWriter, InputStream, OutputStream}
import java.util.Scanner
import tk.mygod.util.CloseUtils._

/**
 * @author Mygod
 */
object IOUtils {
  private val BUF_SIZE: Int = 0x1000

  def copy(from: InputStream, to: OutputStream): Long = {
    val buf = new Array[Byte](BUF_SIZE)
    var total = 0
    var r = from.read(buf)
    while (r >= 0) {
      to.write(buf, 0, r)
      total += r
      r = from.read(buf)
    }
    total
  }

  def readAllText(stream: InputStream): String = {
    val scanner = new Scanner(stream).useDelimiter("\\a")
    if (scanner.hasNext) scanner.next else ""
  }

  def writeAllText(stream: OutputStream, text: String, charsetName: String = "UTF-8") =
    (() => new OutputStreamWriter(stream, charsetName)) closeAfter {
      case writer =>
        writer.write(text)
        writer.flush
    }
}

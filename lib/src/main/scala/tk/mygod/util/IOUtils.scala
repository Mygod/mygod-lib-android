package tk.mygod.util

import java.io.{InputStream, OutputStream}
import java.util.Scanner

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
    val scanner: Scanner = new Scanner(stream).useDelimiter("\\a")
    if (scanner.hasNext) scanner.next else ""
  }
}

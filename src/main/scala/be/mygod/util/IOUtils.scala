package be.mygod.util

import java.io._
import java.nio.charset.Charset

import be.mygod.util.CloseUtils._

/**
 * @author Mygod
 */
object IOUtils {
  private val BUFFER_SIZE: Int = 0x8000

  def copy(from: InputStream, to: OutputStream): Long = {
    val buf = new Array[Byte](BUFFER_SIZE)
    var total = 0L
    while (true) {
      val count = from.read(buf)
      if (count < 0) return total
      to.write(buf, 0, count)
      total += count
    }
    total
  }

  def readAllText(in: InputStream): String = {
    val builder = new StringBuilder()
    val buffer = new Array[Byte](BUFFER_SIZE)
    while (true) {
      val count = in.read(buffer)
      if (count >= 0) builder.append(new String(buffer, 0, count)) else return builder.toString()
    }
    null
  }
  def readAllText(in: InputStream, charset: Charset): String = {
    val builder = new StringBuilder()
    val buffer = new Array[Byte](BUFFER_SIZE)
    while (true) {
      val count = in.read(buffer)
      if (count >= 0) builder.append(new String(buffer, 0, count, charset)) else return builder.toString()
    }
    null
  }
  def readAllText(file: String): String = readAllText(new FileInputStream(file))
  def readAllText(file: String, charset: Charset): String = readAllText(new FileInputStream(file), charset)

  def writeAllText(stream: OutputStream, content: String) =
    autoClose(new OutputStreamWriter(stream))(writer => writer.write(content))
  def writeAllText(stream: OutputStream, content: String, charset: Charset) =
    autoClose(new OutputStreamWriter(stream, charset))(writer => writer.write(content))
  def writeAllText(file: String, text: String): Unit = writeAllText(new FileOutputStream(file), text)
  def writeAllText(file: String, text: String, charset: Charset): Unit =
    writeAllText(new FileOutputStream(file), text, charset)
}

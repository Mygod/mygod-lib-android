package tk.mygod.util

import java.text.SimpleDateFormat
import java.util.Date

/**
 * @author Mygod
 */
object FileUtils {
  def getTempFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date)
}

package tk.mygod.util

import tk.mygod.util.CloseUtils._

/**
  * @author Mygod
  */
object Logcat {
  //noinspection JavaAccessorMethodCalledAsEmptyParen
  def fetch = autoClose(Runtime.getRuntime.exec("logcat -d").getInputStream())(stream => IOUtils.readAllText(stream))
}

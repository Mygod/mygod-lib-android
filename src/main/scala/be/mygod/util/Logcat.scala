package be.mygod.util

import be.mygod.util.CloseUtils._

object Logcat {
  //noinspection JavaAccessorMethodCalledAsEmptyParen
  def fetch = autoClose(Runtime.getRuntime.exec("logcat -d").getInputStream())(IOUtils.readAllText)
}

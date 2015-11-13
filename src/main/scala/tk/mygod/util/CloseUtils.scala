package tk.mygod.util

object CloseUtils {
  type Disconnectable = {
    def disconnect()
  }

  def autoClose[A <: AutoCloseable, B](x: => A)(block: A => B): B = {
    var a: Option[A] = None
    try {
      a = Some(x)
      block(a.get)
    } finally if (a.nonEmpty) try a.get.close catch {
      case ignore: Exception =>
    }
  }
  def autoDisconnect[A <: Disconnectable, B](x: => A)(block: A => B): B = {
    var a: Option[A] = None
    try {
      a = Some(x)
      block(a.get)
    } finally if (a.nonEmpty) try a.get.disconnect catch {
      case ignore: Exception =>
    }
  }
}

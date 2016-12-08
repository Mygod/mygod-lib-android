package be.mygod.util

object CloseUtils {
  type Disconnectable = {
    def disconnect()
  }

  def autoClose[A <: AutoCloseable, B](x: => A)(block: A => B): B = {
    var a: Option[A] = None
    try {
      a = Some(x)
      block(a.get)
    } finally if (a.nonEmpty) try {
      val v = a.get
      if (v ne null) v.close()
    } catch {
      case _: Exception =>
    }
  }
  def autoDisconnect[A <: Disconnectable, B](x: => A)(block: A => B): B = {
    var a: Option[A] = None
    try {
      a = Some(x)
      block(a.get)
    } finally if (a.nonEmpty) try {
      val v = a.get
      if (v ne null) v.disconnect()
    } catch {
      case _: Exception =>
    }
  }
}

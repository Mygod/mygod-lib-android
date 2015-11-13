package tk.mygod.util

object CloseUtils {
  type Closeable = {
    def close()
  }
  type Disconnectable = {
    def disconnect()
  }

  def closeAfter[A <: Closeable, B](x: => A, block: A => B) = {
    var a: Option[A] = None
    try {
      a = Some(x)
      block(a.get)
    } finally if (a.nonEmpty) if (a.nonEmpty) a.get.close
  }
  def disconnectAfter[A <: Disconnectable, B](x: => A, block: A => B) = {
    var a: Option[A] = None
    try {
      a = Some(x)
      block(a.get)
    } finally if (a.nonEmpty) if (a.nonEmpty) a.get.disconnect
  }

  def closeAfter[A <: Product, B](x: => A, block: A => B) = {
    var a: Option[A] = None
    try {
      a = Some(x)
      block(a.get)
    } finally if (a.nonEmpty) for (i <- 0 until a.get.productArity)
      a.get.productElement(i) match {
        case c: Closeable => c.close
        case d: Disconnectable => d.disconnect
      }
  }
}

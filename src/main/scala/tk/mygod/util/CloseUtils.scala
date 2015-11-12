package tk.mygod.util

/**
  * Based on: http://stackoverflow.com/a/2400063/2245107
  */
object CloseUtils {
  type Closeable = {
    def close()
  }
  type Disconnectable = {
    def disconnect()
  }

  final class CloseAfterClosable[A <: Closeable](val x: () => A) {
    def closeAfter[B](block: A => B) = {
      var a: Option[A] = None
      try {
        a = Some(x())
        block(a.get)
      } finally if (a.nonEmpty) a.get.close
    }
  }
  final class CloseAfterDisconnectable[A <: Disconnectable](val x: () => A) {
    def closeAfter[B](block: A => B) = {
      var a: Option[A] = None
      try {
        a = Some(x())
        block(a.get)
      } finally if (a.nonEmpty) a.get.disconnect
    }
  }
  final class CloseAfterTuple[A <: Product](val x: () => A) {
    def closeAfter[B](block: A => B) = {
      var a: Option[A] = None
      try {
        a = Some(x())
        block(a.get)
      } finally if (a.nonEmpty) for (i <- 0 until a.get.productArity)
        a.get.productElement(i) match {
          case c: Closeable => c.close
          case d: Disconnectable => d.disconnect
        }
    }
  }

  implicit def any2CloseAfterClosable[A <: Closeable](x: () => A): CloseAfterClosable[A] = new CloseAfterClosable(x)
  implicit def any2CloseAfterDisconnectable[A <: Disconnectable](x: () => A): CloseAfterDisconnectable[A] =
    new CloseAfterDisconnectable(x)
  implicit def any2CloseAfterTuple[A <: Product](x: () => A): CloseAfterTuple[A] = new CloseAfterTuple(x)
}

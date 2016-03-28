package tk.mygod

import scala.util.{Failure, Try}

package object concurrent {
  val FailureHandler: Try[_] => Unit = {
    case Failure(throwable) => throw throwable
    case _ =>
  }
}

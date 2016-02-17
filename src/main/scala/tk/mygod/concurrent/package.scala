package tk.mygod

import scala.util.Failure

package object concurrent {
  val FailureHandler = {
    case Failure(throwable) => throw throwable
    case _ =>
  }
}

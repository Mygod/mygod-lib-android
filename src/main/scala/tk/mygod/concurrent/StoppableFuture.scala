package tk.mygod.concurrent

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Failure

abstract class StoppableFuture[T](finished: T => Unit = null) {
  private var stopped: Boolean = _
  def work: T
  def onFailure(exc: Exception) = throw exc
  def isStopped = stopped
  def stop = stopped = true

  Future {
    val result = work
    if (finished != null) finished(result)
  } onComplete {
    case Failure(exc: Exception) => onFailure(exc)
    case Failure(throwable) => throw throwable
    case _ =>
  }
}

package tk.mygod.concurrent

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

abstract class StoppableFuture[T](finished: T => Unit = null) {
  private var stopped: Boolean = _
  def work: T
  def onFailure(exc: Exception) = throw exc
  def isStopped = stopped
  def stop = stopped = true

  Future {
    val result = work
    if (finished != null) finished(result)
  } onFailure {
    case exc: Exception => onFailure(exc)
    case throwable => throw throwable
  }
}

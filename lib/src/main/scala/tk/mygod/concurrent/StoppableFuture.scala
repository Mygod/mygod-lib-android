package tk.mygod.concurrent

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

abstract class StoppableFuture(finished: () => Unit = null) {
  private var stopped: Boolean = _
  def work
  def onFailure(exc: Exception) = throw exc
  def isStopped = stopped
  def stop = stopped = true

  Future {
    work
    if (finished != null) finished()
  } onFailure {
    case exc: Exception => onFailure(exc)
    case throwable => throw throwable
  }
}

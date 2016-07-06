package tk.mygod.animation

import java.util.ArrayList

import android.animation.Animator.AnimatorListener
import android.animation.{Animator, TimeInterpolator}

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
  * Based on: https://halfthought.wordpress.com/2014/11/07/reveal-transition/
  *
  * @author Mygod
  */
final class NoPauseAnimator(val animator: Animator) extends Animator {
  private val listeners = new mutable.HashMap[AnimatorListener, AnimatorListenerWrapper]

  def getStartDelay = animator.getStartDelay
  def setStartDelay(startDelay: Long) = animator.setStartDelay(startDelay)
  def setInterpolator(value: TimeInterpolator) = animator.setInterpolator(value)
  def isRunning = animator.isRunning
  def getDuration = animator.getDuration
  def setDuration(duration: Long) = {
    animator.setDuration(duration)
    this
  }

  override def addListener(listener: AnimatorListener) = if (!listeners.contains(listener)) {
    val wrapper = new AnimatorListenerWrapper(this, listener)
    listeners(listener) = wrapper
    animator.addListener(wrapper)
  }
  override def removeListener(listener: AnimatorListener) = listeners.remove(listener) match {
    case Some(wrapper) => animator.removeListener(wrapper)
    case None =>
  }
  override def removeAllListeners {
    listeners.clear
    animator.removeAllListeners
  }
  override def cancel = animator.cancel
  override def end = animator.end
  override def getInterpolator = animator.getInterpolator()
  override def getListeners = new ArrayList[AnimatorListener](listeners.keys)
  override def isPaused = animator.isPaused
  override def isStarted = animator.isStarted
  override def setTarget(target: AnyRef) = animator.setTarget(target)
  override def setupEndValues = animator.setupEndValues
  override def setupStartValues = animator.setupStartValues
  override def start = animator.start
}

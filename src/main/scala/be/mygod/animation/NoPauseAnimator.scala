package be.mygod.animation

import java.util

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

  def getStartDelay: Long = animator.getStartDelay
  def setStartDelay(startDelay: Long): Unit = animator.setStartDelay(startDelay)
  def setInterpolator(value: TimeInterpolator): Unit = animator.setInterpolator(value)
  def isRunning: Boolean = animator.isRunning
  def getDuration: Long = animator.getDuration
  def setDuration(duration: Long): NoPauseAnimator = {
    animator.setDuration(duration)
    this
  }

  override def addListener(listener: AnimatorListener): Unit = if (!listeners.contains(listener)) {
    val wrapper = new AnimatorListenerWrapper(this, listener)
    listeners(listener) = wrapper
    animator.addListener(wrapper)
  }
  override def removeListener(listener: AnimatorListener): Unit = listeners.remove(listener) match {
    case Some(wrapper) => animator.removeListener(wrapper)
    case None =>
  }
  override def removeAllListeners() {
    listeners.clear
    animator.removeAllListeners()
  }
  override def cancel(): Unit = animator.cancel()
  override def end(): Unit = animator.end()
  //noinspection JavaAccessorMethodCalledAsEmptyParen
  override def getInterpolator: TimeInterpolator = animator.getInterpolator()
  override def getListeners = new util.ArrayList[AnimatorListener](listeners.keys)
  override def isPaused: Boolean = animator.isPaused
  override def isStarted: Boolean = animator.isStarted
  override def setTarget(target: AnyRef): Unit = animator.setTarget(target)
  override def setupEndValues(): Unit = animator.setupEndValues()
  override def setupStartValues(): Unit = animator.setupStartValues()
  override def start(): Unit = animator.start()
}

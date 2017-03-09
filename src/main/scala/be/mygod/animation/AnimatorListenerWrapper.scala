package be.mygod.animation

import android.animation.Animator

/**
  * Based on: https://halfthought.wordpress.com/2014/11/07/reveal-transition/
  *
  * @author Mygod
  */
final class AnimatorListenerWrapper(private val animator: Animator, private val listener: Animator.AnimatorListener)
  extends Animator.AnimatorListener {
  def onAnimationStart(animation: Animator): Unit = listener.onAnimationStart(animator)
  def onAnimationRepeat(animation: Animator): Unit = listener.onAnimationRepeat(animator)
  def onAnimationCancel(animation: Animator): Unit = listener.onAnimationCancel(animator)
  def onAnimationEnd(animation: Animator): Unit = listener.onAnimationEnd(animator)
}

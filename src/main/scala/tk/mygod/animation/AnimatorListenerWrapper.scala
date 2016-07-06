package tk.mygod.animation

import android.animation.Animator

/**
  * Based on: https://halfthought.wordpress.com/2014/11/07/reveal-transition/
  *
  * @author Mygod
  */
final class AnimatorListenerWrapper(private val animator: Animator, private val listener: Animator.AnimatorListener)
  extends Animator.AnimatorListener {
  def onAnimationStart(animation: Animator) = listener.onAnimationStart(animator)
  def onAnimationRepeat(animation: Animator) = listener.onAnimationRepeat(animator)
  def onAnimationCancel(animation: Animator) = listener.onAnimationCancel(animator)
  def onAnimationEnd(animation: Animator) = listener.onAnimationEnd(animator)
}

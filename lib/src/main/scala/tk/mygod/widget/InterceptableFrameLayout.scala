package tk.mygod.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * @author Mygod
 */
class InterceptableFrameLayout(context: Context, attrs: AttributeSet = null) extends FrameLayout(context, attrs) {
  var intercept = false

  override def onInterceptHoverEvent(event: MotionEvent) = intercept || super.onInterceptHoverEvent(event)
  override def onInterceptTouchEvent(event: MotionEvent) = intercept || super.onInterceptTouchEvent(event)
}

package tk.mygod.widget

import android.content.Context
import android.graphics.Canvas
import android.support.design.widget.{TextInputLayout => Buggy}
import android.support.v4.view.ViewCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.EditText

/**
 * A temporary fix for this stupid little bug: https://code.google.com/p/android/issues/detail?id=175228
 * TODO: Remove the useless code when the bug has been fixed some day.
 */
class TextInputLayout(context: Context, attrs: AttributeSet) extends Buggy(context, attrs) {
  private var hintSet: Boolean = _
  private var hint: CharSequence = _

  override def addView(child: View, index: Int, params: LayoutParams) {
    val text = child.asInstanceOf[EditText]
    if (text != null) hint = text.getHint
    super.addView(child, index, params)
  }

  override def setHint(hint: CharSequence) {
    super.setHint(hint)
    this.hint = hint
    hintSet = false
  }

  protected override def onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    if (!hintSet && ViewCompat.isLaidOut(this)) {
      super.setHint(null)
      val editText = getEditText
      val currentHint = editText.getHint
      if (!TextUtils.isEmpty(currentHint)) {
        hint = currentHint
        editText.setHint(null)
      }
      super.setHint(hint)
      hintSet = true
    }
  }
}

package tk.mygod.preference

import android.content.Context
import android.content.res.TypedArray
import android.preference.DialogPreference
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.NumberPicker
import tk.mygod.R

final class NumberPickerPreference(private val context: Context, attrs: AttributeSet = null)
  extends DialogPreference(context, attrs) with SummaryPreference {
  private val picker = new NumberPicker(context)
  private var value: Int = _

  {
    val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberPickerPreference)
    picker.setMinValue(a.getInt(R.styleable.NumberPickerPreference_min, 0))
    // TODO: https://code.google.com/p/android/issues/detail?id=183100
    picker.setMaxValue(a.getInt(R.styleable.NumberPickerPreference_max, Int.MaxValue - 1))
    a.recycle
  }
  initSummary(context, attrs)

  def getValue = value
  def getMin = if (picker == null) 0 else picker.getMinValue
  def getMax = picker.getMaxValue
  def setValue(i: Int) {
    picker.setValue(i)
    value = picker.getValue
    persistInt(value)
  }
  def setMin(value: Int) = picker.setMinValue(value)
  def setMax(value: Int) = picker.setMaxValue(value)

  override protected def onCreateDialogView = {
    val parent = picker.getParent.asInstanceOf[ViewGroup]
    if (parent != null) parent.removeView(picker)
    picker
  }
  override protected def onDialogClosed(positiveResult: Boolean) {
    super.onDialogClosed(positiveResult)  // forward compatibility
    if (!positiveResult) return
    val i = picker.getValue
    if (i == value || !callChangeListener(i)) return
    setValue(i)
    notifyChanged
  }
  override protected def onGetDefaultValue(a: TypedArray, index: Int): AnyRef =
    a.getInt(index, getMin).asInstanceOf[AnyRef]
  override protected def onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any) =
    setValue(if (restorePersistedValue) getPersistedInt(getMin) else defaultValue.asInstanceOf[Int])
  override protected def getSummaryValue: AnyRef = getValue.asInstanceOf[AnyRef]
}
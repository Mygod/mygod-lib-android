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
  private var picker: NumberPicker = _
  private var min, max, value: Int = _

  {
    val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberPickerPreference)
    min = a.getInt(R.styleable.NumberPickerPreference_min, 0)
    max = a.getInt(R.styleable.NumberPickerPreference_max, 0)
    a.recycle
  }
  initSummary(context, attrs)

  def getValue = value
  def getMin = min
  def getMax = max
  def setValue(n: Int) {
    value = n
    if (value < min) value = min
    else if (value > max) value = max
    persistInt(value)
  }
  def setMin(i: Int) {
    min = if (i <= max) i else max
    if (value < min) value = min
  }
  def setMax(i: Int) {
    max = if (i >= min) i else min
    if (value > max) value = max
  }

  override protected def onCreateDialogView = {
    picker = new NumberPicker(context)
    picker.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT))
    picker.setMinValue(min)
    picker.setMaxValue(max)
    picker.setValue(value)
    picker
  }
  override protected def onDialogClosed(positiveResult: Boolean) = if (positiveResult) persistInt(picker.getValue)
  override protected def onGetDefaultValue(a: TypedArray, index: Int): AnyRef =
    a.getInt(index, getMin).asInstanceOf[AnyRef]
  override protected def onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any) =
    setValue(if (restorePersistedValue) getPersistedInt(getMin) else defaultValue.asInstanceOf[Int])
  override protected def getSummaryValue: AnyRef = getValue.asInstanceOf[AnyRef]
}
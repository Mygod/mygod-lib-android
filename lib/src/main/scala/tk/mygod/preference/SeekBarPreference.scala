package tk.mygod.preference

import android.content.Context
import android.content.res.TypedArray
import android.support.v7.preference.DialogPreference
import android.util.AttributeSet
import android.widget.SeekBar
import tk.mygod.R

/**
 * @author mygod
 */
final class SeekBarPreference(private val context: Context, attrs: AttributeSet = null)
  extends DialogPreference(context, attrs) with SummaryPreference {
  private[preference] val seekBar = new SeekBar(context)
  private var min = 0F
  private var max = 1F
  private var value: Float = _
  private var reset: Float = _
  seekBar.setMax(Int.MaxValue)

  {
    val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SeekBarPreference)
    setMin(a.getFloat(R.styleable.SeekBarPreference_min, 0))
    setMax(a.getFloat(R.styleable.SeekBarPreference_max, 1))
    setReset(a.getFloat(R.styleable.SeekBarPreference_reset, Float.NaN))
    a.recycle
  }
  initSummary(context, attrs)

  private def updateProgress {
    val i = (value - min) / (max - min)
    seekBar.setProgress(if (i <= 0) 0 else if (i >= 1) Int.MaxValue else (i * Int.MaxValue).toInt)
  }

  def getValue = value
  def getMin = min
  def getMax = max
  def getReset = reset
  def setValue(i: Float) {
    if (value == i || !callChangeListener(i)) return
    value = i
    updateProgress
    persistFloat(value)
    notifyChanged
  }
  def setMin(value: Float) = {
    min = value
    updateProgress
  }
  def setMax(value: Float) = {
    max = value
    updateProgress
  }
  def setReset(value: Float) = reset = value

  override protected def onGetDefaultValue(a: TypedArray, index: Int): AnyRef =
    a.getFloat(index, getMin).asInstanceOf[AnyRef]
  override protected def onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any) =
    setValue(if (restorePersistedValue) getPersistedFloat(getMin) else defaultValue.asInstanceOf[Float])
  override protected def getSummaryValue: AnyRef = getValue.asInstanceOf[AnyRef]
}

package tk.mygod.preference

import android.app.AlertDialog.Builder
import android.content.{DialogInterface, Context}
import android.content.res.TypedArray
import android.preference.DialogPreference
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.SeekBar
import tk.mygod.R
import tk.mygod.util.MethodWrappers._

/**
 * @author mygod
 */
final class SeekBarPreference(private val context: Context, attrs: AttributeSet = null)
  extends DialogPreference(context, attrs) with SummaryPreference {
  private val seekBar = new SeekBar(context)
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

  override protected def onPrepareDialogBuilder(builder: Builder) {
    super.onPrepareDialogBuilder(builder) // forward compatibility
    if (!reset.isNaN) builder.setNeutralButton(R.string.reset, (dialog: DialogInterface, which: Int) => {
      setValue(reset)
    })
  }
  override protected def onCreateDialogView = {
    val parent = seekBar.getParent.asInstanceOf[ViewGroup]
    if (parent != null) parent.removeView(seekBar)
    seekBar
  }
  override protected def onDialogClosed(positiveResult: Boolean) {
    super.onDialogClosed(positiveResult)  // forward compatibility
    if (positiveResult) setValue(seekBar.getProgress * (max - min) / Int.MaxValue + min)
  }
  override protected def onGetDefaultValue(a: TypedArray, index: Int): AnyRef =
    a.getFloat(index, getMin).asInstanceOf[AnyRef]
  override protected def onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any) =
    setValue(if (restorePersistedValue) getPersistedFloat(getMin) else defaultValue.asInstanceOf[Float])
  override protected def getSummaryValue: AnyRef = getValue.asInstanceOf[AnyRef]
}

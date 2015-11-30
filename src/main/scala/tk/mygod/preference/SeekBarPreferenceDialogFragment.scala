package tk.mygod.preference

import android.app.AlertDialog.Builder
import android.content.Context
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.support.v14.preference.PreferenceDialogFragment
import android.view.ViewGroup
import tk.mygod.R
import tk.mygod.app.FragmentPlus

/**
 * @author mygod
 */
class SeekBarPreferenceDialogFragment(key: String) extends PreferenceDialogFragment with FragmentPlus {
  {
    val bundle = new Bundle(1)
    bundle.putString("key", key)
    setArguments(bundle)
  }
  private lazy val preference = getPreference.asInstanceOf[SeekBarPreference]
  private lazy val seekBar = preference.seekBar

  override protected def onPrepareDialogBuilder(builder: Builder) {
    super.onPrepareDialogBuilder(builder) // forward compatibility
    val reset = preference.getReset
    if (!reset.isNaN)
      builder.setNeutralButton(R.string.reset, ((dialog, which) => preference.setValue(reset)): OnClickListener)
  }
  override protected def onCreateDialogView(context: Context) = {
    val parent = seekBar.getParent.asInstanceOf[ViewGroup]
    if (parent != null) parent.removeView(seekBar)
    seekBar
  }
  protected def onDialogClosed(positiveResult: Boolean) {
    val min = preference.getMin
    if (positiveResult) preference.setValue(seekBar.getProgress * (preference.getMax - min) / Int.MaxValue + min)
    else preference.updateProgress  // reset the progress
  }
}

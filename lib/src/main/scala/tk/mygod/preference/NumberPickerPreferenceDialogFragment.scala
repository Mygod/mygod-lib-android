package tk.mygod.preference

import android.content.Context
import android.os.Bundle
import android.support.v14.preference.PreferenceDialogFragment
import android.view.ViewGroup

/**
 * @author mygod
 */
final class NumberPickerPreferenceDialogFragment(key: String) extends PreferenceDialogFragment {
  {
    val bundle = new Bundle(1)
    bundle.putString("key", key)
    setArguments(bundle)
  }
  private lazy val preference = getPreference.asInstanceOf[NumberPickerPreference]
  private lazy val picker = preference.picker

  override protected def onCreateDialogView(context: Context) = {
    val parent = picker.getParent.asInstanceOf[ViewGroup]
    if (parent != null) parent.removeView(picker)
    picker
  }

  def onDialogClosed(positiveResult: Boolean) = if (positiveResult) preference.setValue(picker.getValue)
}

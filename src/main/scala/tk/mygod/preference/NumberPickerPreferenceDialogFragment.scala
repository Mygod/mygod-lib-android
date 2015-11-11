package tk.mygod.preference

import android.content.Context
import android.os.Bundle
import android.support.v14.preference.PreferenceDialogFragment
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

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
  private lazy val inputMethodManager =
    getActivity.getSystemService(Context.INPUT_METHOD_SERVICE).asInstanceOf[InputMethodManager]

  override protected def onCreateDialogView(context: Context) = {
    val parent = picker.getParent.asInstanceOf[ViewGroup]
    if (parent != null) parent.removeView(picker)
    picker
  }

  def onDialogClosed(positiveResult: Boolean) {
    picker.clearFocus // commit changes
    if (positiveResult) preference.setValue(picker.getValue)
    inputMethodManager.hideSoftInputFromWindow(getActivity.getCurrentFocus.getWindowToken,
      InputMethodManager.HIDE_NOT_ALWAYS)
  }
}
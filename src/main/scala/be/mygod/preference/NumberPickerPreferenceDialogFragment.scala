package be.mygod.preference

import android.content.Context
import android.support.v14.preference.PreferenceDialogFragment
import android.view.inputmethod.InputMethodManager
import android.view.{View, ViewGroup}

/**
 * @author Mygod
 */
class NumberPickerPreferenceDialogFragment extends PreferenceDialogFragment {
  private lazy val preference = getPreference.asInstanceOf[NumberPickerPreference]
  private lazy val picker = preference.picker
  private lazy val inputMethodManager =
    getActivity.getSystemService(Context.INPUT_METHOD_SERVICE).asInstanceOf[InputMethodManager]

  override protected def onCreateDialogView(context: Context) = {
    val parent = picker.getParent.asInstanceOf[ViewGroup]
    if (parent != null) parent.removeView(picker)
    picker
  }

  override protected def onBindDialogView(view: View) {
    super.onBindDialogView(view)
    picker.setValue(preference.getValue)
  }

  override protected def needInputMethod = true

  def onDialogClosed(positiveResult: Boolean) {
    picker.clearFocus // commit changes
    if (positiveResult) {
      val value = picker.getValue
      if (preference.callChangeListener(value)) preference.setValue(value)
    }
    inputMethodManager.hideSoftInputFromWindow(getActivity.getCurrentFocus.getWindowToken,
      InputMethodManager.HIDE_NOT_ALWAYS)
  }
}

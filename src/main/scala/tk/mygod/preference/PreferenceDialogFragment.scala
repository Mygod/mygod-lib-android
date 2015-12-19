package tk.mygod.preference

import android.os.Bundle
import android.support.v14.preference.{PreferenceDialogFragment => Base}
import android.view.WindowManager

/**
  * Related issue: https://code.google.com/p/android/issues/detail?id=186940
  * Based on: https://github.com/Gericop/Android-Support-Preference-V7-Fix/commit/82d651fa4339d550ca6be7165a32be6dbdf4558b
  *
  * @author Mygod
  */
abstract class PreferenceDialogFragment extends Base {
  override def onCreateDialog(savedInstanceState: Bundle) = {
    val dialog = super.onCreateDialog(savedInstanceState)
    if (needInputMethod) dialog.getWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    dialog
  }
}

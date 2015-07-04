package tk.mygod.preference

import android.app.Activity
import android.content.Context
import android.preference.EditTextPreference
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager

/**
 * EditTextPreference + SummaryPreference.
 * @author Mygod
 */
class EditTextPlusPreference(context: Context, attrs: AttributeSet = null) extends EditTextPreference(context, attrs)
  with SummaryPreference {
  initSummary(context, attrs)

  protected override def getSummaryValue = getText

  protected lazy val inputMethodManager =
    getContext.getSystemService(Context.INPUT_METHOD_SERVICE).asInstanceOf[InputMethodManager]
  protected override def onDialogClosed(positiveResult: Boolean) {
    super.onDialogClosed(positiveResult)
    inputMethodManager.hideSoftInputFromWindow(getContext.asInstanceOf[Activity].getCurrentFocus.getWindowToken,
      InputMethodManager.HIDE_NOT_ALWAYS)
  }
}

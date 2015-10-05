package tk.mygod.preference

import android.content.Context
import android.support.v7.preference.EditTextPreference
import android.util.AttributeSet

/**
 * EditTextPreference + SummaryPreference.
 * @author Mygod
 */
class EditTextPlusPreference(context: Context, attrs: AttributeSet = null) extends EditTextPreference(context, attrs)
  with SummaryPreference {
  initSummary(context, attrs)

  protected override def getSummaryValue = getText

  /*TODO: protected lazy val inputMethodManager =
    getContext.getSystemService(Context.INPUT_METHOD_SERVICE).asInstanceOf[InputMethodManager]
  protected override def onDialogClosed(positiveResult: Boolean) {
    super.onDialogClosed(positiveResult)
    inputMethodManager.hideSoftInputFromWindow(getContext.asInstanceOf[Activity].getCurrentFocus.getWindowToken,
      InputMethodManager.HIDE_NOT_ALWAYS)
  }*/
}

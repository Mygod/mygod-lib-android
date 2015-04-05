package tk.mygod.preference

import android.content.Context
import android.preference.EditTextPreference
import android.util.AttributeSet

/**
 * EditTextPreference + SummaryPreference.
 * @author Mygod
 */
class EditTextPlusPreference(context: Context, attrs: AttributeSet = null) extends EditTextPreference(context, attrs)
  with SummaryPreference {
  initSummary(context, attrs)

  protected override def getSummaryValue: CharSequence = getText
}

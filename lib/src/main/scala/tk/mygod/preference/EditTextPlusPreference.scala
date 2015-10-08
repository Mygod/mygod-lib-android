package tk.mygod.preference

import android.content.Context
import android.support.v7.preference.EditTextPreference
import android.util.AttributeSet

/**
 * EditTextPreference + SummaryPreference.
 * TODO: This class is not tested.
 * @author Mygod
 */
class EditTextPlusPreference(context: Context, attrs: AttributeSet = null) extends EditTextPreference(context, attrs)
  with SummaryPreference {
  initSummary(context, attrs)

  protected override def getSummaryValue = getText
}

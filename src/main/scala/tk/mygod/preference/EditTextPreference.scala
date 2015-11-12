package tk.mygod.preference

import android.content.Context
import android.support.v7.preference.{EditTextPreference => Parent}
import android.support.v7.widget.AppCompatEditText
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet

/**
  * Fixed EditTextPreference + SummaryPreference with password support!
  * Based on: https://github.com/Gericop/Android-Support-Preference-V7-Fix/tree/master/app/src/main/java/android/support/v7/preference
  */
class EditTextPreference(context: Context, attrs: AttributeSet = null) extends Parent(context, attrs)
  with SummaryPreference {
  initSummary(context, attrs)

  val editText = new AppCompatEditText(context, attrs)
  editText.setId(android.R.id.edit)

  override protected def getSummaryValue = editText.getInputType match {
    case InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_NUMBER_VARIATION_PASSWORD |
         InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD =>
      PasswordTransformationMethod.getInstance.getTransformation(getText, null)
    case _ => getText
  }

  override def setText(text: String) = {
    super.setText(text)
    notifyChanged
  }
}

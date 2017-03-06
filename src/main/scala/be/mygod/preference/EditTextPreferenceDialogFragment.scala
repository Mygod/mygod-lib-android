package be.mygod.preference

import android.support.v14.preference.PreferenceDialogFragment
import android.support.v7.widget.AppCompatEditText
import android.view.{View, ViewGroup}

class EditTextPreferenceDialogFragment extends PreferenceDialogFragment {
  private lazy val preference = getPreference.asInstanceOf[EditTextPreference]
  protected lazy val editText: AppCompatEditText = preference.editText

  override protected def onBindDialogView(view: View) {
    super.onBindDialogView(view)
    editText.setText(preference.getText)
    val text = editText.getText
    if (text != null) editText.setSelection(0, text.length)
    val oldParent = editText.getParent.asInstanceOf[ViewGroup]
    if (oldParent eq view) return
    if (oldParent != null) oldParent.removeView(editText)
    val oldEdit = view.findViewById(android.R.id.edit)
    if (oldEdit == null) return
    val container = oldEdit.getParent.asInstanceOf[ViewGroup]
    if (container == null) return
    container.removeView(oldEdit)
    container.addView(editText, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
  }

  override protected def needInputMethod = true

  def onDialogClosed(positiveResult: Boolean): Unit = if (positiveResult) {
    val value = editText.getText.toString
    if (preference.callChangeListener(value)) preference.setText(value)
  }
}

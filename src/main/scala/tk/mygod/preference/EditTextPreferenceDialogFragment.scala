package tk.mygod.preference

import android.content.Context
import android.os.Bundle
import android.view.{View, ViewGroup}

class EditTextPreferenceDialogFragment(key: String) extends PreferenceDialogFragment {
  {
    val bundle = new Bundle(1)
    bundle.putString("key", key)
    setArguments(bundle)
  }
  private lazy val preference = getPreference.asInstanceOf[EditTextPreference]
  private lazy val editText = preference.editText

  override protected def onCreateDialogView(context: Context) = {
    val parent = editText.getParent.asInstanceOf[ViewGroup]
    if (parent != null) parent.removeView(editText)
    editText
  }

  override protected def onBindDialogView(view: View) {
    super.onBindDialogView(view)
    editText.setText(preference.getText)
    val text = editText.getText
    if (text != null) editText.setSelection(0, text.length)
  }

  override protected def needInputMethod = true

  def onDialogClosed(positiveResult: Boolean) = if (positiveResult) {
    val value = editText.getText.toString
    if (preference.callChangeListener(value)) preference.setText(value)
  }
}

package tk.mygod.preference

import android.os.Bundle
import android.support.v14.preference.{PreferenceDialogFragment}
import android.support.v7.widget.AppCompatEditText
import android.view.ViewGroup.LayoutParams
import android.view.{View, ViewGroup}

class EditTextPreferenceDialogFragment(key: String) extends PreferenceDialogFragment {
  {
    val bundle = new Bundle(1)
    bundle.putString("key", key)
    setArguments(bundle)
  }
  private lazy val preference = getPreference.asInstanceOf[EditTextPreference]
  private lazy val editText = preference.editText

  override protected def onBindDialogView(view: View) {
    super.onBindDialogView(view)
    editText.setText(preference.getText)
    val text = editText.getText
    if (text != null) editText.setSelection(text.length, text.length)
    val parent = editText.getParent.asInstanceOf[ViewGroup]
    val vg = view.asInstanceOf[ViewGroup]
    if (parent eq vg) return
    if (parent != null) parent.removeView(view)
    onAddEditTextToDialogView(vg, editText)
  }

  override protected def needInputMethod = true

  protected def onAddEditTextToDialogView(dialogView: ViewGroup, editText: AppCompatEditText): Unit = {
    if (dialogView == null) return
    val old = dialogView.findViewById(android.R.id.edit)
    if (old == null) return
    dialogView.removeView(old)
    dialogView.addView(editText, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
  }

  def onDialogClosed(positiveResult: Boolean) = if (positiveResult) {
    val value = editText.getText.toString
    if (preference.callChangeListener(value)) preference.setText(value)
  }
}

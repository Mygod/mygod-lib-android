package be.mygod.preference

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Resources
import android.support.annotation.NonNull
import android.support.v14.preference.ListPreferenceDialogFragment
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{BaseAdapter, CheckedTextView}
import be.mygod.os.Build

/**
 * @author Mygod
 */
class IconListPreferenceDialogFragment extends ListPreferenceDialogFragment {
  private lazy val preference = getPreference.asInstanceOf[IconListPreference]

  protected override def onPrepareDialogBuilder(@NonNull builder: AlertDialog.Builder) {
    var entries = preference.getEntries
    var entryValues = preference.getEntryValues
    if (entries == null) entries = new Array[CharSequence](0)
    if (entryValues == null) entryValues = new Array[CharSequence](0)
    if (entries.length != entryValues.length) throw new IllegalStateException(
      "ListPreference requires an entries array and an entryValues array which are both the same length")
    val entryIcons = preference.getEntryIcons
    if (entryIcons != null && entries.length != entryIcons.length) throw new IllegalStateException(
      "IconListPreference requires the icons entries array be the same length than entries or null")
    val adapter = new CheckedListAdapter
    builder.setSingleChoiceItems(null.asInstanceOf[Array[CharSequence]], preference.selectedEntry, null)
      .setAdapter(adapter, this).setPositiveButton(null, null)
  }

  protected override def onDialogClosed(positiveResult: Boolean) = ()
  override def onClick(dialog: DialogInterface, which: Int) {
    if (which >= 0) {
      val value = preference.getEntryValues()(which).toString
      if (preference.callChangeListener(value)) preference.setValue(value)
      super.onClick(dialog, DialogInterface.BUTTON_POSITIVE)
    }
    else super.onClick(dialog, which)
    dialog.dismiss
  }

  private class CheckedListAdapter extends BaseAdapter {
    private lazy val name = "select_dialog_singlechoice_" + (if (Build.version >= 21) "material" else "holo")

    def getCount = {
      val entries = preference.getEntries
      if (entries == null) 0 else entries.length
    }
    def getItem(position: Int) = preference.getEntries()(position)
    def getItemId(position: Int) = position

    def getView(position: Int, convertView: View, parent: ViewGroup): View = {
      val result = if (convertView == null) LayoutInflater.from(parent.getContext)
        .inflate(Resources.getSystem.getIdentifier(name, "layout", "android"), parent, false)
      else convertView
      val text = result.findViewById(android.R.id.text1).asInstanceOf[CheckedTextView]
      text.setText(getItem(position))
      val entryIcons = preference.getEntryIcons
      if (entryIcons != null) text.setCompoundDrawablesWithIntrinsicBounds(entryIcons(position), null, null, null)
      result
    }
  }
}

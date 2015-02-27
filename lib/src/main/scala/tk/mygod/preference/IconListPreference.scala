package tk.mygod.preference

import android.app.AlertDialog
import android.content.{Context, DialogInterface}
import android.content.res.{Resources, TypedArray}
import android.graphics.drawable.Drawable
import android.os.Build
import android.preference.{Preference, ListPreference}
import android.support.annotation.NonNull
import android.util.AttributeSet
import android.view.{ViewGroup, LayoutInflater, View}
import android.widget.{CheckedTextView, BaseAdapter}
import tk.mygod.R
import tk.mygod.util.MethodWrappers._

/**
 * @author Mygod
 */
class IconListPreference(context: Context, attrs: AttributeSet = null) extends ListPreference(context, attrs) {
  private var mEntryIcons: Array[Drawable] = null
  private var selectedEntry: Int = -1

  private var listener: Preference.OnPreferenceChangeListener = null
  override def getOnPreferenceChangeListener: Preference.OnPreferenceChangeListener = listener
  override def setOnPreferenceChangeListener(listener: Preference.OnPreferenceChangeListener) = this.listener = listener
  super.setOnPreferenceChangeListener((preference: Preference, newValue: Any) => {
    if (listener != null && !listener.onPreferenceChange(preference, newValue)) false else {
      setValue(newValue.toString)
      if (mEntryIcons != null) setIcon(getEntryIcon)
      true
    }
  })

  val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.IconListPreference)
  val entryIconsResId: Int = a.getResourceId(R.styleable.IconListPreference_entryIcons, -1)
  if (entryIconsResId != -1) setEntryIcons(entryIconsResId)
  a.recycle

  def getEntryIcon = mEntryIcons(selectedEntry)
  def getEntryIcons = mEntryIcons
  def setEntryIcons(entryIcons: Array[Drawable]) = mEntryIcons = entryIcons

  def setEntryIcons(entryIconsResId: Int) {
    val icons_array = getContext.getResources.obtainTypedArray(entryIconsResId)
    val icon_ids_array = new Array[Drawable](icons_array.length)
    var i = 0
    while (i < icons_array.length) {
      icon_ids_array(i) = icons_array.getDrawable(i)
      i += 1
    }
    setEntryIcons(icon_ids_array)
    icons_array.recycle
  }

  def init {
    val entryValues = getEntryValues
    if (entryValues == null) return
    selectedEntry = entryValues.indexOf(getValue)
    if (mEntryIcons != null) setIcon(getEntryIcon)
  }

  protected override def onSetInitialValue(restoreValue: Boolean, defaultValue: AnyRef) {
    super.onSetInitialValue(restoreValue, defaultValue)
    init
  }

  protected override def onPrepareDialogBuilder(@NonNull builder: AlertDialog.Builder) {
    var entries = getEntries
    var entryValues = getEntryValues
    if (entries == null) entries = new Array[CharSequence](0)
    if (entryValues == null) entryValues = new Array[CharSequence](0)
    if (entries.length != entryValues.length) throw new IllegalStateException(
      "ListPreference requires an entries array and an entryValues array which are both the same length")
    if (mEntryIcons != null && entries.length != mEntryIcons.length) throw new IllegalStateException(
      "IconListPreference requires the icons entries array be the same length than entries or null")
    val adapter = new CheckedListAdapter
    builder.setSingleChoiceItems(null.asInstanceOf[Array[CharSequence]], selectedEntry, null).setAdapter(adapter, this)
           .setPositiveButton(null, null)
  }

  protected override def onDialogClosed(positiveResult: Boolean) {
    if (!positiveResult || selectedEntry < 0) return
    val value = getEntryValues()(selectedEntry).toString
    if (callChangeListener(value)) setValue(value)
  }

  override def onClick(dialog: DialogInterface, which: Int) {
    if (which >= 0) {
      selectedEntry = which
      super.onClick(dialog, DialogInterface.BUTTON_POSITIVE)
    }
    else super.onClick(dialog, which)
    dialog.dismiss
  }

  private class CheckedListAdapter extends BaseAdapter {
    lazy val name = "select_dialog_singlechoice_" + (if (Build.VERSION.SDK_INT >= 21) "material" else "holo")

    def getCount = getEntries.length
    def getItem(position: Int) = getEntries()(position)
    def getItemId(position: Int) = position

    def getView(position: Int, convertView: View, parent: ViewGroup): View = {
      val result = if (convertView == null) LayoutInflater.from(parent.getContext)
        .inflate(Resources.getSystem.getIdentifier(name, "layout", "android"), parent, false)
        else convertView
      val text = result.findViewById(android.R.id.text1).asInstanceOf[CheckedTextView]
      text.setText(getItem(position))
      if (mEntryIcons != null) text.setCompoundDrawablesWithIntrinsicBounds(mEntryIcons(position), null, null, null)
      result
    }
  }
}

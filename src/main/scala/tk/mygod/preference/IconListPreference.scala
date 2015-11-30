package tk.mygod.preference

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference.OnPreferenceChangeListener
import android.util.AttributeSet
import tk.mygod.R

/**
 * @author Mygod
 */
class IconListPreference(context: Context, attrs: AttributeSet = null) extends ListPreference(context, attrs) {
  private var mEntryIcons: Array[Drawable] = null
  private[preference] var selectedEntry: Int = -1

  private var listener: OnPreferenceChangeListener = null
  override def getOnPreferenceChangeListener = listener
  override def setOnPreferenceChangeListener(listener: OnPreferenceChangeListener) = this.listener = listener
  super.setOnPreferenceChangeListener((preference, newValue) => {
    if (listener != null && !listener.onPreferenceChange(preference, newValue)) false else {
      setValue(newValue.toString)
      if (mEntryIcons != null) setIcon(getEntryIcon)
      true
    }
  })

  {
    val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.IconListPreference)
    val entryIconsResId: Int = a.getResourceId(R.styleable.IconListPreference_entryIcons, -1)
    if (entryIconsResId != -1) setEntryIcons(entryIconsResId)
    a.recycle
  }

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
}

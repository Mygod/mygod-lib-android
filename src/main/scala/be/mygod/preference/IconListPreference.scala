package be.mygod.preference

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.annotation.ArrayRes
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference.OnPreferenceChangeListener
import android.util.AttributeSet
import be.mygod.R

/**
 * @author Mygod
 */
class IconListPreference(context: Context, attrs: AttributeSet = null) extends ListPreference(context, attrs) {
  private var mEntryIcons: Array[Drawable] = _
  def selectedEntry = getEntryValues.indexOf(getValue)

  private var listener: OnPreferenceChangeListener = _
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

  def setEntryIcons(@ArrayRes entryIconsResId: Int) {
    val icons_array = getContext.getResources.obtainTypedArray(entryIconsResId)
    val icon_ids_array = new Array[Drawable](icons_array.length)
    for (i <- 0 until icons_array.length) icon_ids_array(i) = icons_array.getDrawable(i)
    setEntryIcons(icon_ids_array)
    icons_array.recycle
  }

  def init {
    val entryValues = getEntryValues
    if (entryValues == null) return
    if (mEntryIcons != null) setIcon(getEntryIcon)
  }

  protected override def onSetInitialValue(restoreValue: Boolean, defaultValue: AnyRef) {
    super.onSetInitialValue(restoreValue, defaultValue)
    init
  }
}

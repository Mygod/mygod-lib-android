package tk.mygod.preference

import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.preference.{PreferenceGroup, PreferenceViewHolder, PreferenceGroupAdapter => Old}
import android.view.{LayoutInflater, View, ViewGroup}
import tk.mygod.R
import tk.mygod.os.Build

/**
  * Fix by: https://github.com/Gericop/Android-Support-Preference-V7-Fix/commit/7de016b007e28a264001a8bb353f110a7f64bb69
  *
  * @author Mygod
  */
object PreferenceGroupAdapter {
  private val oldClass = classOf[Old]
  private val preferenceLayoutsField = oldClass.getDeclaredField("mPreferenceLayouts")
  preferenceLayoutsField.setAccessible(true)
  private val (fieldResId, fieldWidgetResId) = {
    val c = oldClass.getDeclaredClasses.filter(c => c.getSimpleName == "PreferenceLayout").head
    (c.getDeclaredField("resId"), c.getDeclaredField("widgetResId"))
  }
  fieldResId.setAccessible(true)
  fieldWidgetResId.setAccessible(true)
}

class PreferenceGroupAdapter(group: PreferenceGroup) extends Old(group) {
  import PreferenceGroupAdapter._

  protected val preferenceLayouts = preferenceLayoutsField.get(this).asInstanceOf[List]

  override def onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (Build.version < 21) {
    val context = parent.getContext
    val inflater = LayoutInflater.from(context)
    val pl = preferenceLayouts(viewType)
    val view = inflater.inflate(fieldResId.get(pl).asInstanceOf[Int], parent, false)
    val array = context.obtainStyledAttributes(null, R.styleable.BackgroundStyle)
    var background = array.getDrawable(R.styleable.BackgroundStyle_android_selectableItemBackground)
    if (background == null) background = ContextCompat.getDrawable(context, android.R.drawable.list_selector_background)
    array.recycle
    view.setBackground(background)
    ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), view.getPaddingTop,
      ViewCompat.getPaddingEnd(view), view.getPaddingBottom)
    val widgetFrame = view.findViewById(android.R.id.widget_frame).asInstanceOf[ViewGroup]
    if (widgetFrame != null) {
      val widgetResId = fieldWidgetResId.get(pl).asInstanceOf[Int]
      if (widgetResId != 0) inflater.inflate(widgetResId, widgetFrame) else widgetFrame.setVisibility(View.GONE)
    }
    new PreferenceViewHolder(view)
  } else super.onCreateViewHolder(parent, viewType)
}

package tk.mygod.util

import android.content.DialogInterface
import android.preference.Preference
import android.preference.Preference.{OnPreferenceChangeListener, OnPreferenceClickListener}
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener

import scala.language.implicitConversions

/**
 * @author Mygod
 */
object MethodWrappers {
  implicit def viewOnClickListener(func: View => Any): View.OnClickListener =
    new View.OnClickListener { override def onClick(v: View) = func(v) }

  implicit def dialogInterfaceOnClickListener(func: (DialogInterface, Int) => Any): DialogInterface.OnClickListener =
    new DialogInterface.OnClickListener {
      override def onClick(dialog: DialogInterface, which: Int) = func(dialog, which)
    }

  implicit def onItemClickListener(func: (AdapterView[_], View, Int, Long) => Any): OnItemClickListener =
    new OnItemClickListener {
      override def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) =
        func(parent, view, position, id)
    }

  implicit def onPreferenceClickListener(func: Preference => Boolean): OnPreferenceClickListener =
    new OnPreferenceClickListener { override def onPreferenceClick(preference: Preference) = func(preference) }

  implicit def onPreferenceChangeListener(func: (Preference, Any) => Boolean): OnPreferenceChangeListener =
    new OnPreferenceChangeListener {
      override def onPreferenceChange(preference: Preference, newValue: scala.Any): Boolean = func(preference, newValue)
    }
}

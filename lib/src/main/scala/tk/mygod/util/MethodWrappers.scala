package tk.mygod.util

import android.content.DialogInterface
import android.support.v7.preference.Preference
import android.support.v7.preference.Preference.{OnPreferenceChangeListener, OnPreferenceClickListener}
import android.text.{InputFilter, Spanned}
import android.view.View.{OnLongClickListener, OnTouchListener}
import android.view.{MotionEvent, KeyEvent, View}
import android.widget.TextView.OnEditorActionListener
import android.widget.{TextView, AdapterView}
import android.widget.AdapterView.OnItemClickListener

import scala.language.implicitConversions

/**
 * @author Mygod
 */
object MethodWrappers {
  implicit def viewOnClickListener(func: View => Any): View.OnClickListener =
    new View.OnClickListener { def onClick(v: View) = func(v) }
  implicit def dialogInterfaceOnClickListener(func: (DialogInterface, Int) => Any): DialogInterface.OnClickListener =
    new DialogInterface.OnClickListener { def onClick(dialog: DialogInterface, which: Int) = func(dialog, which) }

  implicit def onEditorAction(func: (TextView, Int, KeyEvent) => Boolean): OnEditorActionListener =
    new OnEditorActionListener {
      override def onEditorAction(v: TextView, actionId: Int, event: KeyEvent) = func(v, actionId, event)
    }

  implicit def onItemClickListener(func: (AdapterView[_], View, Int, Long) => Any): OnItemClickListener =
    new OnItemClickListener {
      def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) = func(parent, view, position, id)
    }

  implicit def onLongClickListener(func: View => Boolean): OnLongClickListener = new OnLongClickListener {
    override def onLongClick(v: View) = func(v)
  }

  implicit def onPreferenceClickListener(func: Preference => Boolean): OnPreferenceClickListener =
    new OnPreferenceClickListener { def onPreferenceClick(preference: Preference) = func(preference) }

  implicit def onPreferenceChangeListener(func: (Preference, Any) => Boolean): OnPreferenceChangeListener =
    new OnPreferenceChangeListener {
      def onPreferenceChange(preference: Preference, newValue: Any) = func(preference, newValue)
    }

  implicit def onTouchListener(func: (View, MotionEvent) => Boolean): OnTouchListener = new OnTouchListener {
    override def onTouch(v: View, event: MotionEvent): Boolean = func(v, event)
  }

  implicit def inputFilter(func: (CharSequence, Int, Int, Spanned, Int, Int) => CharSequence): InputFilter =
    new InputFilter {
      def filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int) =
        func(source, start, end, dest, dstart, dend)
    }
}

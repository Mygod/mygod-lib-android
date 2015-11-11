package tk.mygod.preference

import android.support.v14.preference.{PreferenceDialogFragment, PreferenceFragment}
import tk.mygod.app.FragmentPlus

/**
 * @author mygod
 */
trait PreferenceFragmentPlus extends PreferenceFragment with FragmentPlus {
  protected def displayPreferenceDialog(fragment: PreferenceDialogFragment) {
    fragment.setTargetFragment(this, 0)
    fragment.show(getFragmentManager, "android.support.v14.preference.PreferenceFragment.DIALOG")
  }
}

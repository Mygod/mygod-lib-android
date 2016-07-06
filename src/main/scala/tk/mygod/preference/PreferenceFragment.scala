package tk.mygod.preference

import android.support.v14.preference.{PreferenceDialogFragment, PreferenceFragment => Base}
import android.support.v7.preference.PreferenceScreen

/**
  * @author Mygod
  */
abstract class PreferenceFragment extends Base {
  protected final def displayPreferenceDialog(fragment: PreferenceDialogFragment) {
    fragment.setTargetFragment(this, 0)
    fragment.show(getFragmentManager, "android.support.v14.preference.PreferenceFragment.DIALOG")
  }

  override protected def onCreateAdapter(screen: PreferenceScreen) = new PreferenceGroupAdapter(screen)

  override def onResume {
    super.onResume
    getListView.scrollBy(0, 0)
  }
}

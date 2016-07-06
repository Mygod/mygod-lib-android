package tk.mygod.preference

import android.os.Bundle
import android.support.v14.preference.{PreferenceDialogFragment, PreferenceFragment}
import android.support.v7.preference.PreferenceScreen
import android.view.{LayoutInflater, ViewGroup}
import tk.mygod.app.FragmentPlus

/**
  * @author Mygod
  */
abstract class PreferenceFragmentPlus extends PreferenceFragment with FragmentPlus {
  override def layout = 0  // not applicable
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) =
    super[PreferenceFragment].onCreateView(inflater, container, savedInstanceState)

  protected final def displayPreferenceDialog(key: String, fragment: PreferenceDialogFragment) {
    val bundle = new Bundle(1)
    bundle.putString("key", key)
    fragment.setArguments(bundle)
    fragment.setTargetFragment(this, 0)
    fragment.show(getFragmentManager, "android.support.v14.preference.PreferenceFragment.DIALOG")
  }

  override protected def onCreateAdapter(screen: PreferenceScreen) = new PreferenceGroupAdapter(screen)

  override def onResume {
    super.onResume
    getListView.scrollBy(0, 0)
  }
}

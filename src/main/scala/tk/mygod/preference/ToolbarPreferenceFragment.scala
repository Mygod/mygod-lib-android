package tk.mygod.preference

import android.os.Bundle
import android.support.v14.preference.{PreferenceDialogFragment => Base, PreferenceFragment}
import android.view.{LayoutInflater, ViewGroup}
import tk.mygod.R
import tk.mygod.app.CircularRevealFragment

/**
  * @author Mygod
  */
abstract class ToolbarPreferenceFragment extends PreferenceFragment with CircularRevealFragment {
  override def layout = R.layout.fragment_preference_toolbar
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {
    val result = super[CircularRevealFragment].onCreateView(inflater, container, savedInstanceState)
    result.findViewById(R.id.preference_holder).asInstanceOf[ViewGroup]
      .addView(super[PreferenceFragment].onCreateView(inflater, container, savedInstanceState))
    result
  }

  protected final def displayPreferenceDialog(fragment: Base) {
    fragment.setTargetFragment(this, 0)
    fragment.show(getFragmentManager, "android.support.v14.preference.PreferenceFragment.DIALOG")
  }

  override def onResume {
    super.onResume
    getListView.scrollBy(0, 0)
  }
}

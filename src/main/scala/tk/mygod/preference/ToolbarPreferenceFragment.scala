package tk.mygod.preference

import android.graphics.{Canvas, Paint, Rect}
import android.os.Bundle
import android.support.v14.preference.PreferenceFragment
import android.support.v7.widget.{RecyclerView, LinearLayoutManager}
import android.view.{LayoutInflater, View, ViewGroup}
import tk.mygod.R
import tk.mygod.app.CircularRevealFragment

/**
  * @author Mygod
  */
object ToolbarPreferenceFragment {
  private val awakenScrollBars = classOf[View].getDeclaredMethod("awakenScrollBars")
  awakenScrollBars.setAccessible(true)
}

abstract class ToolbarPreferenceFragment extends PreferenceFragment with CircularRevealFragment {
  import ToolbarPreferenceFragment._

  override def layout = R.layout.fragment_preference_toolbar
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {
    val result = super[CircularRevealFragment].onCreateView(inflater, container, savedInstanceState)
    result.findViewById(R.id.preference_holder).asInstanceOf[ViewGroup]
      .addView(super[PreferenceFragment].onCreateView(inflater, container, savedInstanceState))
    result
  }

  protected final def displayPreferenceDialog(fragment: PreferenceDialogFragment) {
    fragment.setTargetFragment(this, 0)
    fragment.show(getFragmentManager, "android.support.v14.preference.PreferenceFragment.DIALOG")
  }

  // Dividers from: https://github.com/Gericop/Android-Support-Preference-V7-Fix/commit/9f3595d054f070b1b0b41255c019414f308d02d2
  protected val dividersEnabled = true
  private lazy val dividers = new RecyclerView.ItemDecoration {
    val paint = new Paint
    paint.setStyle(Paint.Style.FILL_AND_STROKE)
    paint.setColor(0x66CCCCCC)
    val divider = {
      val ta = getActivity.obtainStyledAttributes(Array(android.R.attr.listDivider))
      try ta.getDrawable(0) finally ta.recycle
    }
    val dividerHeight = if (divider == null) 2 else divider.getIntrinsicHeight

    override def onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) = {
      val lm = parent.getLayoutManager.asInstanceOf[LinearLayoutManager]
      val adapter = parent.getAdapter
      val left = parent.getPaddingLeft
      val right = parent.getWidth - parent.getPaddingRight
      var i = lm.findFirstVisibleItemPosition
      val end = Math.min(lm.findLastVisibleItemPosition, adapter.getItemCount - 2)
      while (i <= end) if (adapter.getItemViewType(i + 1) == 0) i += 2 else {
        if (adapter.getItemViewType(i) != 0) {
          val view = lm.findViewByPosition(i)
          val top = view.getBottom + view.getPaddingBottom
          if (divider == null) c.drawRect(left, top, right, top + dividerHeight, paint) else {
            divider.setBounds(left, top, right, top + dividerHeight)
            divider.draw(c)
          }
        }
        i += 1
      }
    }
    override def getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) =
      outRect.set(0, 0, 0, dividerHeight)
  }

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    if (dividersEnabled) getListView.addItemDecoration(dividers)
  }

  override def onResume {
    super.onResume
    awakenScrollBars.invoke(getListView)
  }
}

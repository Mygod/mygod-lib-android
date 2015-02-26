package tk.mygod.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * @author Mygod
 */
class ObservableScrollView(context: Context, attrs: AttributeSet = null) extends ScrollView(context, attrs) {
  private var scrollViewListener: (ObservableScrollView, Int, Int, Int, Int) => Any = null

  def setScrollViewListener(scrollViewListener: (ObservableScrollView, Int, Int, Int, Int) => Any) {
    this.scrollViewListener = scrollViewListener
  }

  protected override def onScrollChanged(x: Int, y: Int, oldx: Int, oldy: Int) {
    super.onScrollChanged(x, y, oldx, oldy)
    if (scrollViewListener != null) scrollViewListener(this, x, y, oldx, oldy)
  }
}

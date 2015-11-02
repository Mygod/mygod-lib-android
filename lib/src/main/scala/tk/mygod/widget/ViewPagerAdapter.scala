package tk.mygod.widget

import android.support.v4.view.{ViewPager, PagerAdapter}
import android.view.{ViewGroup, View}

/**
  * A useful PageAdapter for Views.
  * Based on: http://stackoverflow.com/a/18710626/2245107
  * @author mygod
  */
class ViewPagerAdapter(private val pager: ViewPager) extends PagerAdapter {
  def isViewFromObject(view: View, obj: scala.Any) = view == obj.asInstanceOf[View]
  def getCount = pager.getChildCount

  override def instantiateItem(collection: ViewGroup, position: Int) = collection.getChildAt(position)
  override def destroyItem(collection: ViewGroup, position: Int, view: Any) =
    collection.removeView(view.asInstanceOf[View])
}

package be.mygod.view

import android.content.Context
import android.support.v4.view.{PagerAdapter, ViewPager => Base}
import android.util.AttributeSet
import android.view.{ViewGroup, MotionEvent, View}
import android.view.ViewGroup.LayoutParams

/**
  * A useful ViewPager for Views.
  *
  * @author mygod
  */
class ViewPager(context: Context, attrs: AttributeSet) extends Base(context, attrs) {
  private class Adapter extends PagerAdapter {
    def isViewFromObject(view: View, obj: scala.Any): Boolean = view == obj.asInstanceOf[View]
    def getCount: Int = getChildCount

    override def instantiateItem(collection: ViewGroup, position: Int): View = collection.getChildAt(position)
    override def destroyItem(collection: ViewGroup, position: Int, view: Any): Unit =
      collection.removeView(view.asInstanceOf[View])
  }
  private val adapter = new Adapter
  setAdapter(adapter)

  override def addView(child: View, index: Int, params: LayoutParams) {
    super.addView(child, index, params)
    adapter.notifyDataSetChanged()
  }
  override def removeView(child: View) {
    super.removeView(child)
    adapter.notifyDataSetChanged()
  }

  override def onInterceptTouchEvent(event: MotionEvent) = false
  override def onTouchEvent(event: MotionEvent) = false
}

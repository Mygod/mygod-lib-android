package tk.mygod.view

import android.view.View.OnTouchListener
import android.view.{MotionEvent, View}
import tk.mygod.app.LocationObservedActivity

import scala.collection.mutable

/**
 * Usage; Set it as OnTouchListener and invoke get! No memory leaks!
 * @author Mygod
 */
object LocationObserver extends OnTouchListener {
  private val coordinates = new mutable.WeakHashMap[View, (Float, Float)]

  override def onTouch(v: View, event: MotionEvent) = {
    coordinates(v) = (event.getX, event.getY)
    false
  }

  def get(v: View) = {
    val option = coordinates.get(v)
    if (option.isEmpty) (v.getWidth / 2F, v.getHeight / 2F) else option.get
  }
  def get(a: LocationObservedActivity) = get(a.getWindow.getDecorView)
  def getOnScreen(v: View) = {
    val (x, y) = get(v)
    val location = new Array[Int](2)
    v.getLocationOnScreen(location)
    (x + location(0), y + location(1))
  }
  def getOnScreen(a: LocationObservedActivity): (Float, Float) = getOnScreen(a.getWindow.getDecorView)
  def getRelatedTo(source: (Float, Float), target: View) = {
    val location = new Array[Int](2)
    target.getLocationOnScreen(location)
    (source._1 - location(0), source._2 - location(1))
  }
  def getRelatedTo(source: View, target: View): (Float, Float) = getRelatedTo(getOnScreen(source), target)
  def getRelatedTo(source: LocationObservedActivity, target: View): (Float, Float) =
    getRelatedTo(getOnScreen(source), target)
}

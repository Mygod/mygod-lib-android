package tk.mygod.view

import android.view.KeyEvent

/**
  * @author Mygod
  */
trait SimpleKeyEventCallback extends KeyEvent.Callback {
  override def onKeyUp(keyCode: Int, event: KeyEvent) = false
  override def onKeyLongPress(keyCode: Int, event: KeyEvent) = false
  override def onKeyDown(keyCode: Int, event: KeyEvent) = false
  override def onKeyMultiple(keyCode: Int, count: Int, event: KeyEvent) = false
}

package tk.mygod.app

import android.app.Service
import android.content.Intent
import tk.mygod.content.ContextPlus
import tk.mygod.os.BinderPlus

/**
  * @author Mygod
  */
trait ServicePlus extends Service with ContextPlus {
  override def onBind(intent: Intent) = new BinderPlus(this)
}

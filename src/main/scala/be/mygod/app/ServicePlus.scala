package be.mygod.app

import android.app.Service
import android.content.Intent
import be.mygod.content.ContextPlus
import be.mygod.os.BinderPlus

/**
  * @author Mygod
  */
trait ServicePlus extends Service with ContextPlus {
  override def onBind(intent: Intent) = new BinderPlus(this)
}

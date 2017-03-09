package be.mygod.content

import android.content.{ServiceConnection, ComponentName}
import android.os.IBinder
import be.mygod.app.ServicePlus
import be.mygod.os.BinderPlus

/**
  * @author Mygod
  */
class ServicePlusConnection[S <: ServicePlus] extends ServiceConnection {
  var service: Option[S] = _
  def onServiceDisconnected(name: ComponentName): Unit = service = None
  def onServiceConnected(name: ComponentName, binder: IBinder): Unit =
    service = Some(binder.asInstanceOf[BinderPlus].service.asInstanceOf[S])
}

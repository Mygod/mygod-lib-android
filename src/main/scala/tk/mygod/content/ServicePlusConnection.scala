package tk.mygod.content

import android.content.{ServiceConnection, ComponentName}
import android.os.IBinder
import tk.mygod.app.ServicePlus
import tk.mygod.os.BinderPlus

/**
  * @author Mygod
  */
class ServicePlusConnection[S <: ServicePlus] extends ServiceConnection {
  var service: Option[S] = _
  def onServiceDisconnected(name: ComponentName) = service = None
  def onServiceConnected(name: ComponentName, binder: IBinder) =
    service = Some(binder.asInstanceOf[BinderPlus].service.asInstanceOf[S])
}

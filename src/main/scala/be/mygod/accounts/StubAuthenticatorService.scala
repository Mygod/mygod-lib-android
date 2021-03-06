package be.mygod.accounts

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
  * @author Mygod
  */
class StubAuthenticatorService extends Service {
  private var authenticator: StubAuthenticator = _

  override def onCreate() {
    super.onCreate()
    authenticator = new StubAuthenticator(this)
  }

  def onBind(intent: Intent): IBinder = authenticator.getIBinder
}

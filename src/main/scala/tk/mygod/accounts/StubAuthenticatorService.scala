package tk.mygod.accounts

import android.app.Service
import android.content.Intent

/**
  * @author Mygod
  */
class StubAuthenticatorService extends Service {
  private var authenticator: StubAuthenticator = _

  override def onCreate {
    super.onCreate
    authenticator = new StubAuthenticator(this)
  }

  def onBind(intent: Intent) = authenticator.getIBinder
}

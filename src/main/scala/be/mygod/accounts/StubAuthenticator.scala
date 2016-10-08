package be.mygod.accounts

import android.accounts.{Account, AccountAuthenticatorResponse, AbstractAccountAuthenticator}
import android.content.Context
import android.os.Bundle

/**
  * @author Mygod
  */
class StubAuthenticator(context: Context) extends AbstractAccountAuthenticator(context) {
  override def editProperties(response: AccountAuthenticatorResponse, accountType: String) =
    throw new UnsupportedOperationException
  override def addAccount(response: AccountAuthenticatorResponse, accountType: String, authTokenType: String,
                          requiredFeatures: Array[String], options: Bundle) = null
  override def confirmCredentials(response: AccountAuthenticatorResponse, account: Account, options: Bundle) = null
  override def getAuthToken(response: AccountAuthenticatorResponse, account: Account, authTokenType: String,
                            options: Bundle) = throw new UnsupportedOperationException
  override def getAuthTokenLabel(authTokenType: String) = throw new UnsupportedOperationException
  override def updateCredentials(response: AccountAuthenticatorResponse, account: Account, authTokenType: String,
                                 options: Bundle) = throw new UnsupportedOperationException
  override def hasFeatures(response: AccountAuthenticatorResponse, account: Account, features: Array[String]) =
    throw new UnsupportedOperationException
}

package tk.mygod.app

import android.app.Fragment
import android.net.Uri
import tk.mygod.util.UriUtils._

import scala.language.implicitConversions

/**
 * @author Mygod
 */
trait FragmentPlus extends Fragment {
  implicit def getStringImplicit(resourceId : Int): String = getString(resourceId)
  implicit def getUri(resourceId : Int): Uri = getString(resourceId)
}

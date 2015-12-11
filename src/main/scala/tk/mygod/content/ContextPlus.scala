package tk.mygod.content

import android.app.{Activity, PendingIntent}
import android.content.{ClipData, ClipboardManager, Context, Intent}
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.widget.Toast
import tk.mygod.R
import tk.mygod.os.Build
import tk.mygod.util.Conversions._
import tk.mygod.util.Logcat

import scala.language.implicitConversions
import scala.reflect.ClassTag

/**
  * @author Mygod
  */
trait ContextPlus extends Context {
  implicit def getStringImplicit(id : Int): String = getString(id)
  implicit def getDrawableImplicit(id : Int): Drawable = ContextCompat.getDrawable(this, id)
  implicit def getUri(id : Int): Uri = getString(id)
  def systemService[T](implicit ct: ClassTag[T]) = {
    val name = if (Build.version >= 23) getSystemServiceName(ct.runtimeClass) else ct.runtimeClass.getSimpleName match {
      case "AccessibilityManager" => Context.ACCESSIBILITY_SERVICE
      case "CaptioningManager" => Context.CAPTIONING_SERVICE
      case "AccountManager" => Context.ACCOUNT_SERVICE
      case "ActivityManager" => Context.ACTIVITY_SERVICE
      case "AlarmManager" => Context.ALARM_SERVICE
      case "AudioManager" => Context.AUDIO_SERVICE
      case "MediaRouter" => Context.MEDIA_ROUTER_SERVICE
      case "BluetoothManager" => Context.BLUETOOTH_SERVICE
      case "ClipboardManager" => Context.CLIPBOARD_SERVICE
      case "ConnectivityManager" => Context.CONNECTIVITY_SERVICE
      case "DevicePolicyManager" => Context.DEVICE_POLICY_SERVICE
      case "DownloadManager" => Context.DOWNLOAD_SERVICE
      case "BatteryManager" => Context.BATTERY_SERVICE
      case "NfcManager" => Context.NFC_SERVICE
      case "DropBoxManager" => Context.DROPBOX_SERVICE
      case "InputManager" => Context.INPUT_SERVICE
      case "DisplayManager" => Context.DISPLAY_SERVICE
      case "InputMethodManager" => Context.INPUT_METHOD_SERVICE
      case "TextServicesManager" => Context.TEXT_SERVICES_MANAGER_SERVICE
      case "KeyguardManager" => Context.KEYGUARD_SERVICE
      case "LayoutInflater" => Context.LAYOUT_INFLATER_SERVICE
      case "LocationManager" => Context.LOCATION_SERVICE
      case "NotificationManager" => Context.NOTIFICATION_SERVICE
      case "PowerManager" => Context.POWER_SERVICE
      case "SearchManager" => Context.SEARCH_SERVICE
      case "SensorManager" => Context.SENSOR_SERVICE
      case "StorageManager" => Context.STORAGE_SERVICE
      case "SubscriptionManager" => Context.TELEPHONY_SUBSCRIPTION_SERVICE
      case "TelecomManager" => Context.TELECOM_SERVICE
      case "UiModeManager" => Context.UI_MODE_SERVICE
      case "UsbManager" => Context.USB_SERVICE
      case "Vibrator" => Context.VIBRATOR_SERVICE
      case "WallpaperManager" => Context.WALLPAPER_SERVICE
      case "WifiManager" => Context.WIFI_SERVICE
      case "WifiP2pManager" => Context.WIFI_P2P_SERVICE
      case "WindowManager" => Context.WINDOW_SERVICE
      case "UserManager" => Context.USER_SERVICE
      case "AppOpsManager" => Context.APP_OPS_SERVICE
      case "CameraManager" => Context.CAMERA_SERVICE
      case "LauncherApps" => Context.LAUNCHER_APPS_SERVICE
      case "RestrictionsManager" => Context.RESTRICTIONS_SERVICE
      case "PrintManager" => Context.PRINT_SERVICE
      case "ConsumerIrManager" => Context.CONSUMER_IR_SERVICE
      case "MediaSessionManager" => Context.MEDIA_SESSION_SERVICE
      case "TvInputManager" => Context.TV_INPUT_SERVICE
      case "UsageStatsManager" => Context.USAGE_STATS_SERVICE
      case "JobScheduler" => Context.JOB_SCHEDULER_SERVICE
      case "MediaProjectionManager" => Context.MEDIA_PROJECTION_SERVICE
      case "AppWidgetManager" => Context.APPWIDGET_SERVICE
      case _ => throw new IllegalArgumentException
    }
    getSystemService(name).asInstanceOf[T]
  }

  def intent[T](implicit ct: ClassTag[T]) = new Intent(this, ct.runtimeClass)
  def pendingIntent[A <: Activity](implicit ct: ClassTag[A]) =
    PendingIntent.getActivity(this, 0, intent[A], PendingIntent.FLAG_UPDATE_CURRENT)
  def pendingBroadcast(intent: Intent) = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
  def pendingBroadcast(action: String): Unit = pendingBroadcast(new Intent(action))

  def share(text: String, subject: String = null) = if (Build.isChromeOS)
    systemService[ClipboardManager].setPrimaryClip(ClipData.newPlainText(subject, Logcat.fetch)) else {
    val intent = new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, text)
    if (subject != null) intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    startActivity(Intent.createChooser(intent, R.string.abc_shareactionprovider_share_with))
  }

  def makeToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, text, duration)
}

package tk.mygod.util

import android.content.Context

/**
 * @author mygod
 */
object MetricsUtils {
  def dp2px(context: Context, dp: Int) = (dp * context.getResources.getDisplayMetrics.density).toInt
  def sp2px(context: Context, sp: Int) = (sp * context.getResources.getDisplayMetrics.scaledDensity).toInt
}

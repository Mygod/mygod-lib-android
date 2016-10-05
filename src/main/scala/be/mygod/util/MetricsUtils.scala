package be.mygod.util

import android.content.Context

/**
 * @author mygod
 */
object MetricsUtils {
  def dp2px(context: Context, dp: Int) = (dp * context.getResources.getDisplayMetrics.density + .5).toInt
  def sp2px(context: Context, sp: Int) = (sp * context.getResources.getDisplayMetrics.scaledDensity + .5).toInt
}

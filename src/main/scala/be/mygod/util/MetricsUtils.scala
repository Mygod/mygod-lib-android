package be.mygod.util

import android.content.Context

object MetricsUtils {
  def dp2px(context: Context, dp: Int) = Math.round(dp * context.getResources.getDisplayMetrics.density + .5)
  def sp2px(context: Context, sp: Int) = Math.round(sp * context.getResources.getDisplayMetrics.scaledDensity + .5)
}

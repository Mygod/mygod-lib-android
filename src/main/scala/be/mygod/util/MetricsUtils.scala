package be.mygod.util

import android.content.Context

object MetricsUtils {
  def dp2px(context: Context, dp: Int): Int = Math.round(dp * context.getResources.getDisplayMetrics.density)
  def sp2px(context: Context, sp: Int): Int = Math.round(sp * context.getResources.getDisplayMetrics.scaledDensity)
}

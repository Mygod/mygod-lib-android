package tk.mygod.preference

import android.support.v7.preference.Preference

/**
 * Make your preference support %s in summary. Override getSummaryValue to customize what to put in.
 * Based on:
 * https://github.com/android/platform_frameworks_base/blob/master/core/java/android/preference/ListPreference.java
 * @author Mygod
 */
trait SummaryPreference extends Preference {
  protected def getSummaryValue: AnyRef

  /**
   * Returns the summary of this SummaryPreference. If the summary has a String formatting marker in it
   * (i.e. "%s" or "%1$s"), then the current entry value will be substituted in its place.
   *
   * @return the summary with appropriate string substitution
   */
  override def getSummary = String.format(super.getSummary.toString, getSummaryValue)
}

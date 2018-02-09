package org.lds.ldssa.util

import android.app.Activity
import org.lds.ldssa.R
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.prefs.type.DisplayThemeType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeUtil @Inject constructor(private val prefs: Prefs) {

    fun applyTheme(activity: Activity) {
        when (prefs.generalDisplayTheme) {
            DisplayThemeType.LIGHT, null -> activity.setTheme(R.style.AppTheme_Light)
            DisplayThemeType.DARK -> activity.setTheme(R.style.AppTheme_Dark)
            DisplayThemeType.DARK_BLUE -> activity.setTheme(R.style.AppTheme_DarkBlue)
            DisplayThemeType.MAGENTA -> activity.setTheme(R.style.AppTheme_Magenta)
            DisplayThemeType.SEPIA -> activity.setTheme(R.style.AppTheme_Sepia)
        }
    }
}

package org.lds.ldssa.util

import org.lds.ldssa.BuildConfig
import org.lds.ldssa.model.database.tips.tip.TipManager
import org.lds.ldssa.model.database.types.TipType
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.webservice.tips.TipsService
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TipsUtil @Inject
constructor(private val prefs: Prefs,
            private val tipsService: TipsService,
            private val tipManager: TipManager) {

    fun getTipsDownloadUri(version: Int) = """${TipsService.BASE_URL}bundles/$version.zip"""

    /**
     * Call webservice to determine the latest version of the tips
     * @return latest version of the tips or -1 on failure
     */
    fun fetchTipsVersion(): Int {
        try {
            val call = tipsService.tipsConfig

            val response = call.execute()

            if (response.isSuccessful) {
                val availableVersion = response.body()

                if (availableVersion == null) {
                    Timber.e("Tips Version missing")
                    return -1
                }
                return availableVersion.catalogVersion
            } else {
                Timber.e("Tips Version missing - responseCode: [%d]", response.code())
                return -1
            }
        } catch (error: Exception) {
            Timber.d(error)
        }

        return -1
    }

    fun getWelcomeTipIds() = tipManager.findAllWelcomeTipIds()

    fun getTipIds(languageId: Long, tipType: TipType) = tipManager.findAllIdsByLanguageIdAndType(languageId, tipType)

    fun shouldShowWelcomeScreen(): Boolean {
        val shouldShowTips = prefs.lastViewedWelcomeTipsAppVersion != BuildConfig.WELCOME_TIPS_VERSION
        return shouldShowTips && tipManager.findWelcomeTipCount() > 0
    }
}

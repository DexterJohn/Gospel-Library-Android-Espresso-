package org.lds.ldssa

import android.app.Activity
import android.app.Application
import android.os.Bundle
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.lds.ldsaccount.LDSAccountUtil
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.sync.AnnotationSync
import org.lds.ldssa.task.TipsUpdateTask
import org.lds.ldssa.ui.activity.StartupActivity
import org.lds.ldssa.util.AppUpdateUtil
import org.lds.ldssa.util.CatalogUpdateUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class GLUpdateLifecycle @Inject
constructor(private val prefs: Prefs,
            private val cc: CoroutineContextProvider,
            private val ldsAccountUtil: LDSAccountUtil,
            private val appUpdateUtil: AppUpdateUtil,
            private val catalogUpdateUtil: CatalogUpdateUtil,
            private val tipsUpdateTaskProvider: Provider<TipsUpdateTask>,
            private val annotationSync: AnnotationSync,
            private val internalIntents: InternalIntents) : Application.ActivityLifecycleCallbacks {

    override fun onActivityStarted(activity: Activity) {
        if (activity is StartupActivity) {
            return
        }

        launch(cc.commonPool) {
            val now = LocalDateTime.now()

            // App Version
            val daysSinceLastAppVersionCheck = ChronoUnit.DAYS.between(prefs.lastAppUpdateCheck, now)
            Timber.d("App Version Update last check: %d days", daysSinceLastAppVersionCheck)
            if (daysSinceLastAppVersionCheck >= APP_UPDATE_DAY_INTERVAL) {
                Timber.i("Checking for app update")
                async(cc.commonPool) {
                    appUpdateUtil.verifyAppVersion()
                }
            }

            // Catalog - Check online if there is any new updates to the catalog
            val hoursSinceLastCatalogCheck = ChronoUnit.HOURS.between(prefs.lastCatalogUpdateTime, now)
            Timber.d("Catalog Update last check: %d hours", hoursSinceLastCatalogCheck)
            if (hoursSinceLastCatalogCheck >= CATALOG_UPDATE_HOUR_CHECK_INTERVAL) {
                Timber.i("Checking for catalog update")
                // wait and apply any catalog updates later... (see code below for applying catalog updates later)
                async(cc.commonPool) {
                    catalogUpdateUtil.updateCatalog(false)
                }
            }

            // Catalog - Check to see if we have a pending catalog zip/diff file to install
            // (if enough time has passed... force restart/update catalog)
            prefs.lastCatalogUpdateDownloadTime?.let { lastCatalogUpdateDownloadTime ->
                // time lapsed allowed without force restart/update catalog
                val downloadTimeoutInterval = if (prefs.isCatalogUpdateDownloadTimeoutOverride) CATALOG_DEV_UPDATE_MINUTE_DOWNLOAD_INTERVAL else CATALOG_UPDATE_MINUTE_DOWNLOAD_INTERVAL

                val minutesSinceLastCatalogDownload = ChronoUnit.MINUTES.between(lastCatalogUpdateDownloadTime, now)
                Timber.d("Catalog Update last download: %d minutes", minutesSinceLastCatalogDownload)
                if (minutesSinceLastCatalogDownload >= downloadTimeoutInterval) {
                    Timber.i("Restarting for catalog update")
                    internalIntents.restart()
                }
            }

            // Tips
            val hoursSinceLastTipsCheck = ChronoUnit.HOURS.between(prefs.lastTipsUpdateTime, now)
            Timber.d("Tips Update last check: %d hours", hoursSinceLastTipsCheck)
            if (hoursSinceLastTipsCheck >= TIPS_UPDATE_HOUR_INTERVAL) {
                Timber.i("Checking for tips update")
                tipsUpdateTaskProvider.get().execute()
            }

            // Annotation Sync
            if (ldsAccountUtil.hasCredentials()) {
                val minutesSinceLastAnnotationSync = ChronoUnit.MINUTES.between(prefs.annotationsLastSyncTs, now)
                Timber.d("Annotations Sync last check: %d minutes", minutesSinceLastAnnotationSync)
                if (minutesSinceLastAnnotationSync >= ANNOTATION_SYNC_MINUTES_INTERVAL) {
                    Timber.i("Syncing Annotations")
                    async(cc.commonPool) {
                        annotationSync.sync()
                    }
                }
            }
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}
    override fun onActivityDestroyed(activity: Activity) {}

    companion object {
        private const val APP_UPDATE_DAY_INTERVAL = 5
        private const val CATALOG_UPDATE_HOUR_CHECK_INTERVAL = 12
        private const val CATALOG_UPDATE_MINUTE_DOWNLOAD_INTERVAL = 120
        private const val CATALOG_DEV_UPDATE_MINUTE_DOWNLOAD_INTERVAL = 1
        private const val TIPS_UPDATE_HOUR_INTERVAL = 12
        private const val ANNOTATION_SYNC_MINUTES_INTERVAL = 30
    }
}

package org.lds.ldssa.util

import android.app.Activity
import android.app.Application
import android.support.v4.app.FragmentActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.download.GLDownloadManager
import org.lds.ldssa.intent.ContentIntentParams
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemManager
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasAnnotationView
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasAnnotations
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasCatalogDirectory
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasContent
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasContentDirectory
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasCustomCollectionDirectory
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasNotes
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasSearch
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasStudyItems
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasStudyPlans
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasTips
import timber.log.Timber
import javax.inject.Inject

class ScreenLauncherUtil @Inject
constructor(private val application: Application, private val screenHistoryItemManager: ScreenHistoryItemManager, private val internalIntents: InternalIntents,
            private val contentItemUtil: ContentItemUtil, private val screenUtil: ScreenUtil, private val downloadManager: GLDownloadManager, private val gson: Gson) {

    fun showStartupScreen(activity: FragmentActivity) {
        if (screenUtil.lastVisibleScreenExists()) {
            val lastVisibleScreenId = screenUtil.lastVisibleScreenId

            // Get the contentItemId
            val screenHistoryItem = screenHistoryItemManager.findCurrentScreenHistoryItemByScreenId(lastVisibleScreenId)

            if (screenHistoryItem == null) {
                // create a new screen and show root catalog
                // NOTE: this should ONLY be a fallback call, because this will create a new screen every time
                internalIntents.showCatalogRoot(activity, ScreenUtil.STARTUP_SCREEN_ID, false)
                return
            }

            if (screenHistoryItem.sourceType.contentPackageRequired) {
                // Check to make sure the content is downloaded and available
                val contentItemId = getContentItemIdForScreen(screenHistoryItem)
                if (contentItemId > 0 && contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
                    // show last visible screen
                    onScreenClick(activity, lastVisibleScreenId, false, true)
                } else {
                    // create a new screen and show root catalog
                    // NOTE: this should ONLY be a fallback call, because this will create a new screen every time
                    internalIntents.showCatalogRoot(activity, ScreenUtil.STARTUP_SCREEN_ID, false)
                }
            } else {
                // show last visible screen
                onScreenClick(activity, lastVisibleScreenId, false, true)
            }
        } else {
            // create a new screen and show root catalog
            // Use Case: When there are NO screens (initial launch)
            internalIntents.showCatalogRoot(activity, ScreenUtil.STARTUP_SCREEN_ID, false)
        }
    }

    /**
     * Switch screens in current screen
     */
    fun onScreenClick(activity: FragmentActivity, clickedScreenId: Long, fromClickOnScreen: Boolean, showRootCatalogOnFail: Boolean) {
        // if screens are in the overview... try to bring screen to the front
        if (screenUtil.showScreenFromOverview(clickedScreenId)) {
            if (fromClickOnScreen) {
                // leave the screens
                activity.finish()
                return
            }
        }

        val screenHistoryItem = screenHistoryItemManager.findCurrentScreenHistoryItemByScreenId(clickedScreenId)
        if (screenHistoryItem == null) {
            internalIntents.showCatalogRoot(activity, clickedScreenId, fromClickOnScreen)
            return
        }

        // if this is a content item (directory or actual content), make sure that the content is downloaded and available
        val sourceType = screenHistoryItem.sourceType
        if (sourceType === ScreenSourceType.CONTENT_DIRECTORY || sourceType === ScreenSourceType.CONTENT) {
            val contentItemId = getContentItemIdForScreen(screenHistoryItem)

            val isContentAvailable = contentItemId > 0 && contentItemUtil.isItemDownloadedAndOpen(contentItemId)

            if (!isContentAvailable) {
                showMissingContentDialog(activity, screenHistoryItem.title, contentItemId)
                return
            }
        }

        showScreenContent(activity, clickedScreenId, fromClickOnScreen, showRootCatalogOnFail, Analytics.Referrer.SWITCH_SCREENS)
    }

    private fun getContentItemIdForScreen(screenHistoryItem: ScreenHistoryItem?): Long {
        screenHistoryItem ?: return 0

        return when (screenHistoryItem.sourceType) {
            ScreenSourceType.CONTENT -> {
                val extrasContent = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasContent::class.java)
                extrasContent.contentItemId
            }
            ScreenSourceType.CONTENT_DIRECTORY -> {
                val extrasContentDirectory = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasContentDirectory::class.java)
                extrasContentDirectory.contentItemId
            }
            else -> 0
        }
    }

    private fun showMissingContentDialog(activity: FragmentActivity, title: String, contentItemId: Long) {
        MaterialDialog.Builder(activity)
                .title(R.string.screens)
                .content(application.getString(R.string.screen_missing_content, title))
                .positiveText(R.string.download)
                .negativeText(R.string.cancel)
                .onPositive { materialDialog, dialogAction -> downloadManager.downloadContent(contentItemId) }
                .show()
    }

    /**
     * Internal use to open content for screen
     *
     * NOTE: This function MUST guarantee to finish the previous activity AND open a new activity...
     * if NOT, then the StartupActivity may hang
     */
    private fun showScreenContent(activity: FragmentActivity, screenId: Long, fromClickOnScreen: Boolean, showRootCatalogOnFail: Boolean, referrer: Analytics.Referrer) {
        val screenHistoryItem = screenHistoryItemManager.findCurrentScreenHistoryItemByScreenId(screenId)

        if (screenHistoryItem == null) {
            Timber.e("Failed to find screen history item for screenId: [%d]  Starting Root Catalog...", screenId)
            internalIntents.showCatalogRoot(activity, screenId, fromClickOnScreen)
            return
        }

        screenHistoryItemManager.log(screenHistoryItem, "show")

        when (screenHistoryItem.sourceType) {
            ScreenSourceType.CATALOG_DIRECTORY -> {
                val extrasCatalogDirectory = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasCatalogDirectory::class.java)
                internalIntents.showCatalog(activity, screenId, extrasCatalogDirectory.collectionId, extrasCatalogDirectory.scrollPosition, fromClickOnScreen = fromClickOnScreen)
            }
            ScreenSourceType.CUSTOM_CATALOG_DIRECTORY -> {
                val extrasCustomCatalogDirectory = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasCustomCollectionDirectory::class.java)
                internalIntents.showCustomCollection(activity, screenId, extrasCustomCatalogDirectory.customCollectionId, extrasCustomCatalogDirectory.scrollPosition, fromClickOnScreen)
            }
            ScreenSourceType.CONTENT_DIRECTORY -> {
                val extrasContentDirectory = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasContentDirectory::class.java)
                if (!contentItemUtil.isItemDownloadedAndOpen(extrasContentDirectory.contentItemId)) {
                    openPreviousScreenHistoryItem(activity, screenId, fromClickOnScreen, referrer)
                } else {
                    internalIntents.showContentDirectory(activity, screenId, extrasContentDirectory.contentItemId, extrasContentDirectory.navCollectionUri, true, extrasContentDirectory.scrollPosition, fromClickOnScreen, referrer)
                }
            }
            ScreenSourceType.CONTENT -> {
                val extrasContent = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasContent::class.java)
                if (!contentItemUtil.isItemDownloadedAndOpen(extrasContent.contentItemId)) {
                    openPreviousScreenHistoryItem(activity, screenId, fromClickOnScreen, referrer)
                } else {
                    val params = ContentIntentParams(screenId, extrasContent.contentItemId, extrasContent.subItemUri, referrer)
                    params.scrollPosition = extrasContent.scrollPosition
                    params.fromClickOnScreen = fromClickOnScreen
                    params.showRootCatalogOnFail = showRootCatalogOnFail
                    internalIntents.showContent(activity, params)
                }
            }
            ScreenSourceType.ANNOTATION_VIEW -> {
                val extrasAnnotationView = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasAnnotationView::class.java)
                internalIntents.showAnnotationView(activity, screenId, extrasAnnotationView.annotationId.toInt().toLong(), fromClickOnScreen)
            }
            ScreenSourceType.NOTES -> {
                val extrasNotes = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasNotes::class.java)
                internalIntents.showNotes(activity, screenId, extrasNotes.selectedTabId.toInt(), fromClickOnScreen)
            }
            ScreenSourceType.NOTEBOOK -> {
                val extrasAnnotations = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasAnnotations::class.java)
                internalIntents.showAnnotations(activity, screenId, extrasAnnotations.tag, extrasAnnotations.notebookId, fromClickOnScreen)
            }
            ScreenSourceType.TIPS -> {
                val extrasTips = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasTips::class.java)
                internalIntents.showTipList(activity, screenId, extrasTips.selectedTabId, fromClickOnScreen)
            }
            ScreenSourceType.STUDY_PLANS -> {
                val extrasStudyPlans = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasStudyPlans::class.java)
                internalIntents.showStudyPlans(activity, screenId, extrasStudyPlans.selectedTabId, fromClickOnScreen)
            }
            ScreenSourceType.STUDY_PLAN_ITEMS -> {
                val extrasStudyPlans = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasStudyItems::class.java)
                internalIntents.showStudyItems(activity, screenId, extrasStudyPlans.studyPlanId, extrasStudyPlans.scrollPosition, fromClickOnScreen)
            }
            ScreenSourceType.SETTINGS ->
                // todo: should settings be a screen history item???  (It may be needed for theme and font size changes)
                internalIntents.showSettings(activity, screenId)
            ScreenSourceType.SEARCH -> {
                val extrasSearch = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasSearch::class.java)

                internalIntents.showSearchActivity(activity, screenId,
                        extrasSearch.searchText,
                        extrasSearch.mode,
                        extrasSearch.modeCollectionId,
                        extrasSearch.modeContentItemId,
                        extrasSearch.glContentContext,
                        extrasSearch.scrollPosition,
                        fromClickOnScreen)
            }
            else -> {
                Timber.e(" *** FAILED to showScreenContent: %s", screenHistoryItem.toString())
                internalIntents.showCatalogRoot(activity, screenId, fromClickOnScreen) // fallback to root
            }
        }
    }

    fun onBackPressed(activity: FragmentActivity, screenActivity: ScreenActivity) {
        val screenId = screenActivity.getScreenId()

        // Will be null on the first item of the only screen
        val currentScreenHistoryItem = screenHistoryItemManager.findCurrentScreenHistoryItemByScreenId(screenId)
        if (currentScreenHistoryItem == null) {
            finishFinalScreenItem(activity, screenId)
            return
        }

        // Since the screen history is saved in onPause(), the current screen history will usually be the previous page
        if (!screenActivity.isScreenHistoryItemEqual(currentScreenHistoryItem)) {
            showScreenContent(activity, screenId, false, false, Analytics.Referrer.BACK)
            finishActivity(activity)
            return
        }

        // Moves to the previous screen history item, or finishes the activity when no history exists
        if (screenUtil.popScreenTopHistoryItem(screenId) == null) {
            finishFinalScreenItem(activity, screenId)
        } else {
            showScreenContent(activity, screenId, false, false, Analytics.Referrer.BACK)
        }
    }

    private fun finishFinalScreenItem(activity: FragmentActivity, screenId: Long) {
        screenUtil.removeScreen(activity, screenId)
        finishActivity(activity)
    }

    private fun finishActivity(activity: Activity) = with(activity) {
        finish()
        overridePendingTransition(0, 0)
    }

    private fun openPreviousScreenHistoryItem(activity: FragmentActivity, screenId: Long, fromClickOnScreen: Boolean, referrer: Analytics.Referrer) {
        screenUtil.popScreenTopHistoryItem(screenId)
        showScreenContent(activity, screenId, fromClickOnScreen, false, referrer)
    }

    interface ScreenActivity {
        /**
         * @return screenId for this Activity
         */
        fun getScreenId(): Long

        /**
         * Determine if the screen history item is represented by the given activity
         *
         * @param screenHistoryItem The screen history item for the current screen
         * @return true if the activity is the screenHistoryItem
         */
        fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem): Boolean

        /**
         * Save current activity data to the given screenHistoryItem
         */
        fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem)
    }
}

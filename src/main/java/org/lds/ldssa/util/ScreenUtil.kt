package org.lds.ldssa.util

import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityManager
import android.os.Build
import com.google.gson.Gson
import org.lds.ldssa.media.exomedia.manager.PlaylistManager
import org.lds.ldssa.model.database.gl.recentlanguage.RecentLanguageManager
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.screen.Screen
import org.lds.ldssa.model.database.userdata.screen.ScreenManager
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemManager
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasContent
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasContentDirectory
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.search.SearchUtil
import org.lds.ldssa.ui.activity.BaseActivity
import timber.log.Timber
import java.util.ArrayList
import java.util.HashSet
import javax.inject.Inject

class ScreenUtil @Inject
constructor(private val screenManager: ScreenManager, private val prefs: Prefs, private val screenHistoryItemManager: ScreenHistoryItemManager,
            private val playlistManager: PlaylistManager, private val imageUtil: ImageUtil, private val activityManager: ActivityManager,
            private val languageUtil: LanguageUtil, private val recentLanguageManager: RecentLanguageManager, private val searchUtil: SearchUtil,
            private val gson: Gson) {

    val lastVisibleScreenId: Long
        get() {
            val lastVisibleScreenId = prefs.lastVisibleScreenId
            return if (lastVisibleScreenId > 0) {
                lastVisibleScreenId
            } else {
                newScreenForLanguageId(prefs.getLastSelectedLanguageId(languageUtil))
            }
        }

    val lastVisibleScreenHistoryItem: ScreenHistoryItem?
        get() = screenHistoryItemManager.findCurrentScreenHistoryItemByScreenId(prefs.lastVisibleScreenId)

    private val allTaskIds: Set<Int>
        get() {
            val taskIdSet = HashSet<Int>()
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                return taskIdSet
            }

            val appTasks = activityManager.appTasks
            appTasks.map { it.taskInfo }
                    .mapTo(taskIdSet) { it.persistentId }

            return taskIdSet
        }

    fun lastVisibleScreenExists(): Boolean {
        val lastSavedScreenId = prefs.lastVisibleScreenId
        return lastSavedScreenId > 0 && screenManager.screenIdExists(lastSavedScreenId)
    }

    fun verifyScreenId(screenId: Long): Long {
        return if (screenManager.screenIdExists(screenId)) {
            screenId
        } else {
            val screen = newScreen(prefs.getLastSelectedLanguageId(languageUtil))
            screen.id
        }
    }

    /**
     * Create a new screen based on the language for the given screenId
     */
    fun newScreenFromScreenId(screenId: Long) = newScreen(getLanguageIdForScreen(screenId)).id

    /**
     * Create a new screen based on the languageId
     */
    fun newScreenForLanguageId(languageId: Long) = newScreen(languageId).id

    fun newScreen(languageId: Long): Screen {
        return Screen().also {
            it.displayOrder = 0
            it.languageId = languageId
            screenManager.save(it)
        }
    }

    fun hasPreviousScreen(screenActivity: ScreenLauncherUtil.ScreenActivity) = getPreviousScreenHistoryItem(screenActivity) != null

    private fun getPreviousScreenHistoryItem(screenActivity: ScreenLauncherUtil.ScreenActivity): ScreenHistoryItem? {
        val currentScreenHistoryItem = screenHistoryItemManager.findCurrentScreenHistoryItemByScreenId(screenActivity.getScreenId()) ?: return null

        return if (screenActivity.isScreenHistoryItemEqual(currentScreenHistoryItem)) {
            screenHistoryItemManager.findPreviousScreenHistoryItem(currentScreenHistoryItem)
        } else {
            currentScreenHistoryItem
        }
    }

    fun saveScreenState(activity: Activity, screenActivity: ScreenLauncherUtil.ScreenActivity) {
        var screenId = screenActivity.getScreenId()

        // get the screen from the database
        // if the screen is not found... recreate a new screen and assign the new screenId
        val screen = screenManager.findByRowId(screenId) ?: newScreen(prefs.getLastSelectedLanguageId(languageUtil)).also {
            screenId = it.id
        }

        // save a thumbnail of the current view
        imageUtil.createBitmapThumb(screen.id, activity)
        screenManager.save(screen)

        // Save the current screen history item (create a new one if necessary)
        var screenHistoryItem = screenHistoryItemManager.findCurrentScreenHistoryItemByScreenId(screenId)

        // If no history item or this item is different than the current screen item, then create a new screen history item
        var cleanHistory = false
        if (screenHistoryItem == null || !screenActivity.isScreenHistoryItemEqual(screenHistoryItem)) {
            screenHistoryItem = ScreenHistoryItem()
            screenHistoryItem.historyPosition = screenHistoryItemManager.findNextHistoryPositionByScreenId(screenId)
            cleanHistory = true
        }

        screenHistoryItem.screenId = screenId
        screenActivity.setCurrentScreenHistoryData(screenHistoryItem)
        screenHistoryItemManager.save(screenHistoryItem)

        if (cleanHistory) {
            screenHistoryItemManager.cleanupScreenHistory(screenHistoryItem.screenId)
            screenHistoryItemManager.log(screenHistoryItem, "new")
        } else {
            screenHistoryItemManager.log(screenHistoryItem, "reuse")
        }
    }

    fun renameScreen(screenId: Long, name: String) = screenManager.updateName(screenId, name)


    fun popScreenTopHistoryItem(screenId: Long): ScreenHistoryItem? {
        screenHistoryItemManager.deleteTopItemByScreenId(screenId)
        return screenHistoryItemManager.findCurrentScreenHistoryItemByScreenId(screenId)
    }

    fun updateScreenTaskId(activity: Activity, screenId: Long) {
        if (screenManager.updateScreenTaskId(screenId, activity.taskId.toLong()) <= 0) {
            Timber.d("Unable to assign taskId to screen.  Invalid screenId?: %d", screenId)
        }
    }

    private fun getTask(taskId: Int): ActivityManager.AppTask? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null
        }

        val appTasks = activityManager.appTasks
        for (task in appTasks) {
            val taskInfo = task.taskInfo
            if (taskInfo.persistentId == taskId) {
                return task
            }
        }

        return null
    }

    private fun closeAllOtherOverviewScreens(currentActivity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }

        val currentTaskId = currentActivity.taskId
        activityManager.appTasks
                .filter { currentTaskId != it.taskInfo.persistentId }
                .forEach { it.finishAndRemoveTask() }
    }

    /**
     * Handle changes when settings changes for screens
     */
    fun onScreenInOverviewChanged(activity: Activity) {
        if (!prefs.isScreensInOverview) {
            // prefs changed to use STANDARD screens
            // 1st... make sure any orphan screens get cleaned up
            cleanUpScreens()

            // 2nd close remaining overview screens
            closeAllOtherOverviewScreens(activity)
        }
    }

    private fun cleanUpScreens() {
        val taskIdSet = allTaskIds
        screenManager.findAll()
                .filterNot { taskIdSet.contains(it.androidTaskId) }
                .forEach { screenManager.delete(it.id) }
    }

    fun showScreenFromOverview(screenId: Long): Boolean {
        // this if statement is in isolation so that IntelliJ does not show an error on task.moveToFront()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false
        }

        if (!prefs.isScreensInOverview) {
            return false
        }

        val taskId = screenManager.findTaskIdById(screenId)
        if (taskId == 0) {
            return false
        }

        // if there are multiple screens with same taskId (because screensInOver was disabled)... then force a new task to be created (by returning false)
        if (screenManager.findCountByTaskId(taskId.toLong()) > 1) {
            return false
        }

        val task = getTask(taskId)
        if (task != null) {
            task.moveToFront()
            return true
        }

        return false
    }

    /**
     * Debugging util
     */
    fun logScreenState(sourceActivity: BaseActivity) {
        Timber.d("++++ SCREENS  screenId: %d ScreenHistoryCount: %d Screens Count: %d", sourceActivity.getScreenId(), screenHistoryItemManager.findCountByScreenId(sourceActivity.getScreenId()), screenManager.findCount())
        Timber.d("    lastViewedScreenId: %d", prefs.lastVisibleScreenId)
        for (screen in screenManager.findAll()) {
            Timber.d("    screenId: %d  TaskId: %d  Title: %s", screen.id, screen.androidTaskId, screenManager.findTitleById(screen.id))
        }
    }

    /**
     * Debugging util
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun logTaskInfo(source: String, sourceActivity: Activity) {
        Timber.e("++++ TASKS  source: [%s] THIS ACTIVITY: %s", source, sourceActivity.taskId)
        for (task in activityManager.appTasks) {
            val taskInfo = task.taskInfo
            Timber.d("    TaskId: %d  %s", taskInfo.persistentId, if (taskInfo.id != -1) "(alive) " else "(dead)")
        }
    }

    fun getLanguageIdForScreen(screenId: Long): Long {
        var languageId = screenManager.findLanguageById(screenId)
        if (languageId <= 0) {
            // fallback to prefs and/or device language
            languageId = prefs.getLastSelectedLanguageId(languageUtil)
        }

        return languageId
    }

    fun updateLanguage(screenId: Long, languageId: Long) {
        if (getLanguageIdForScreen(screenId) != languageId) {
            // save current languageId as a "recent"
            recentLanguageManager.saveRecentLanguage(getLanguageIdForScreen(screenId))

            // stop any media playback
            playlistManager.invokeStop()

            // change the language for the screenId
            screenManager.updateLanguageId(screenId, languageId)
        }
    }

    fun removeScreen(activity: Activity?, screenId: Long): Boolean {
        if (prefs.isScreensInOverview) {
            val screenAndroidTaskId = screenManager.findAndroidTaskId(screenId)
            val currentAndroidTaskId = activity?.taskId ?: -1

            val screenTaskIsCurrentTask = screenAndroidTaskId == currentAndroidTaskId

            if (screenManager.delete(screenId) > 0) { // this call will delete screen history items
                if (!screenTaskIsCurrentTask) {
                    closeScreenTask(screenAndroidTaskId)
                }

                // clear any search history for screen
                searchUtil.clearSearch(screenId)

                return true
            }
        } else {
            if (screenManager.delete(screenId) > 0) { // this call will delete screen history items
                // clear any search history for screen
                searchUtil.clearSearch(screenId)

                return true
            }
        }

        return false
    }

    fun closeScreenTasks(contentItemId: Long) {
        if (!prefs.isScreensInOverview) {
            // No need to close tasks if we are not showing screens in the overview
            return
        }

        val openTaskIds = ArrayList(allTaskIds)
        val screens = screenManager.findAllByTaskIds(openTaskIds)
        for (screen in screens) {
            val screenHistoryItem = screenHistoryItemManager.findCurrentScreenHistoryItemByScreenId(screen.id) ?: continue

            val sourceType = screenHistoryItem.sourceType
            val shouldCloseTask = when (sourceType) {
                ScreenSourceType.CONTENT -> screenHistoryItem.getExtras(gson, ScreenHistoryExtrasContent::class.java).contentItemId == contentItemId
                ScreenSourceType.CONTENT_DIRECTORY -> screenHistoryItem.getExtras(gson, ScreenHistoryExtrasContentDirectory::class.java).contentItemId == contentItemId
                else -> false
            }

            if (shouldCloseTask) {
                closeScreenTask(screen.androidTaskId)
            }
        }
    }

    private fun closeScreenTask(taskId: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }

        val task = getTask(taskId)
        task?.finishAndRemoveTask()
    }

    companion object {
        const val NEW_SCREEN_ID: Long = -1
        const val STARTUP_SCREEN_ID: Long = -2
    }
}

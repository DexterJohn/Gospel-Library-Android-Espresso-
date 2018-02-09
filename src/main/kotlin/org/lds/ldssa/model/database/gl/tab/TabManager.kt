/*
 * TabManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.gl.tab

import org.apache.commons.io.FileUtils
import org.dbtools.query.shared.filter.InFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.gl.tabhistoryitem.TabHistoryItemManager
import org.lds.ldssa.model.database.userdata.screen.Screen
import org.lds.ldssa.model.database.userdata.screen.ScreenManager
import org.lds.ldssa.util.GLFileUtil
import java.util.ArrayList
import javax.inject.Inject

@Deprecated("Use ScreenManager")
@javax.inject.Singleton
class TabManager @Inject constructor(databaseManager: DatabaseManager, val fileUtil: GLFileUtil, val tabHistoryItemManager: TabHistoryItemManager) : TabBaseManager(databaseManager) {

    private val FIND_ALL_BY_TIMESTAMP = SQLQueryBuilder()
            .table(TabConst.TABLE)
            .orderBy(TabConst.C_DISPLAY_ORDER, true).buildQuery()


    @Deprecated("Use Screen instead of Tab")
    override fun delete(rowId: Long, databaseName: String): Int {
        // delete associated backstack
        tabHistoryItemManager.deleteAllByTabId(rowId)

        // delete the tab
        val count = super.delete(rowId, databaseName)

        // cleanup bitmap file
        if (count > 0) {
            FileUtils.deleteQuietly(fileUtil.getThumbsFile(rowId))
        }
        return count
    }

    @Deprecated("Use Screen instead of Tab")
    fun findAllOrderedByTimestamp(): List<Tab> {
        return findAllByRawQuery(FIND_ALL_BY_TIMESTAMP)
    }

    @Deprecated("Use Screen instead of Tab")
    fun findAllByTaskIds(taskIds: List<Int>): List<Tab> {
        if (taskIds.isEmpty()) {
            return ArrayList()
        }

        val builder = SQLQueryBuilder()
                .table(TabConst.TABLE)
                .filter(InFilter.create(TabConst.C_ANDROID_TASK_ID, true, taskIds))
        return findAllByRawQuery(builder.buildQuery(), null)
    }

    @Deprecated("Use Screen instead of Tab")
    fun findTitleById(id: Long): String {
        return tabHistoryItemManager.findStackTopTitleByTabId(id)
    }

    @Deprecated("Use Screen instead of Tab")
    fun updateName(id: Long, name: String) {
        val values = createNewDBToolsContentValues()
        values.put(TabConst.C_NAME, name)
        update(values, id)
    }

    @Deprecated("Use Screen instead of Tab")
    fun updateLanguageId(id: Long, languageId: Long) {
        val values = createNewDBToolsContentValues()
        values.put(TabConst.C_LANGUAGE_ID, languageId)
        update(values, id)
    }

    @Deprecated("Use Screen instead of Tab")
    fun tabIdExists(id: Long): Boolean {
        return findCountBySelection(TabConst.C_ID + " = " + id, null) > 0
    }

    @Deprecated("Use Screen instead of Tab")
    fun updateTabTaskId(id: Long, taskId: Long): Int {
        val values = createNewDBToolsContentValues()
        values.put(TabConst.C_ANDROID_TASK_ID, taskId)
        return update(values, id)
    }

    @Deprecated("Use Screen instead of Tab")
    fun findLanguageById(id: Long): Long {
        return findValueByRowId(Long::class.java, TabConst.C_LANGUAGE_ID, id, 0L)
    }

    @Deprecated("Use Screen instead of Tab")
    fun findTaskIdById(tabId: Long): Int {
        return findValueByRowId(Int::class.java, TabConst.C_ANDROID_TASK_ID, tabId, 0)
    }

    @Deprecated("Use Screen instead of Tab")
    fun findCountByTaskId(taskId: Long): Long {
        return findCountBySelection(TabConst.C_ANDROID_TASK_ID + " = " + taskId, null)
    }

    @Deprecated("Use Screen instead of Tab")
    fun findAndroidTaskId(id: Long): Int {
        return findValueByRowId(Int::class.java, TabConst.C_ANDROID_TASK_ID, id, 0)
    }

    fun convertTabsToScreens(screenManager: ScreenManager) {
        val tabs = findAll()
        return tabs.forEach {
            val screen = Screen().apply {
                androidTaskId = it.androidTaskId
                languageId = it.languageId
                name = it.name
                displayOrder = it.displayOrder
            }
            val screenId = screenManager.insert(screen)

            val items = tabHistoryItemManager.findAndConvertToScreenItems(it.id, screenId)
            for (item in items) {
                screenManager.screenHistoryItemManager.insert(item)
            }
        }
    }
}
/*
 * ScreenManager.kt
 *
 * Generated on: 10/16/2017 12:03:55
 *
 */



package org.lds.ldssa.model.database.userdata.screen

import org.apache.commons.io.FileUtils
import org.dbtools.query.shared.filter.InFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemManager
import org.lds.ldssa.util.GLFileUtil
import org.lds.ldssa.util.UserdataDbUtil
import java.util.ArrayList
import javax.inject.Inject


@javax.inject.Singleton
class ScreenManager @Inject constructor(databaseManager: DatabaseManager, val fileUtil: GLFileUtil, val screenHistoryItemManager: ScreenHistoryItemManager, val userdataDbUtil: UserdataDbUtil) : ScreenBaseManager(databaseManager) {

    private val FIND_ALL_BY_TIMESTAMP = SQLQueryBuilder()
            .table(ScreenConst.TABLE)
            .orderBy(ScreenConst.C_DISPLAY_ORDER, true).buildQuery()

    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    override fun delete(rowId: Long, databaseName: String): Int {
        // delete associated backstack
        screenHistoryItemManager.deleteAllByScreenId(rowId)

        // delete the screen
        val count = super.delete(rowId, databaseName)

        // cleanup bitmap file
        if (count > 0) {
            FileUtils.deleteQuietly(fileUtil.getThumbsFile(rowId))
        }
        return count
    }

    fun findAllOrderedByTimestamp(): List<Screen> {
        return findAllByRawQuery(FIND_ALL_BY_TIMESTAMP)
    }

    fun findAllByTaskIds(taskIds: List<Int>): List<Screen> {
        if (taskIds.isEmpty()) {
            return ArrayList()
        }

        val builder = SQLQueryBuilder()
                .table(ScreenConst.TABLE)
                .filter(InFilter.create(ScreenConst.C_ANDROID_TASK_ID, true, taskIds))
        return findAllByRawQuery(builder.buildQuery(), null)
    }

    fun findTitleById(id: Long): String {
        return screenHistoryItemManager.findStackTopTitleByScreenId(id)
    }

    fun updateName(id: Long, name: String) {
        val values = createNewDBToolsContentValues()
        values.put(ScreenConst.C_NAME, name)
        update(values, id)
    }

    fun updateLanguageId(id: Long, languageId: Long) {
        val values = createNewDBToolsContentValues()
        values.put(ScreenConst.C_LANGUAGE_ID, languageId)
        update(values, id)
    }

    fun screenIdExists(id: Long): Boolean {
        return findCountBySelection(ScreenConst.C_ID + " = " + id, null) > 0
    }

    fun updateScreenTaskId(id: Long, taskId: Long): Int {
        val values = createNewDBToolsContentValues()
        values.put(ScreenConst.C_ANDROID_TASK_ID, taskId)
        return update(values, id)
    }

    fun findLanguageById(id: Long): Long {
        return findValueByRowId(Long::class.java, ScreenConst.C_LANGUAGE_ID, id, 0L)
    }

    fun findTaskIdById(screenId: Long): Int {
        return findValueByRowId(Int::class.java, ScreenConst.C_ANDROID_TASK_ID, screenId, 0)
    }

    fun findCountByTaskId(taskId: Long): Long {
        return findCountBySelection(ScreenConst.C_ANDROID_TASK_ID + " = " + taskId, null)
    }

    fun findAndroidTaskId(id: Long): Int {
        return findValueByRowId(Int::class.java, ScreenConst.C_ANDROID_TASK_ID, id, 0)
    }
}
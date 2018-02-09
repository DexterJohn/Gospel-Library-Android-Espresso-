/*
 * ScreenHistoryItemManager.kt
 *
 * Generated on: 10/16/2017 12:03:55
 *
 */



package org.lds.ldssa.model.database.userdata.screenhistoryitem

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.UserdataDbUtil
import timber.log.Timber
import javax.inject.Inject


@javax.inject.Singleton
class ScreenHistoryItemManager @Inject constructor(databaseManager: DatabaseManager, val userdataDbUtil: UserdataDbUtil) : ScreenHistoryItemBaseManager(databaseManager) {

    private val STACK_TOP_QUERY: String
    private val STACK_TOP_ID_QUERY: String
    private val STACK_TOP_TITLE_QUERY: String
    private val CLEAN_HISTORY_QUERY: String

    init {
        val topPositionQuery = SQLQueryBuilder()
                .table(ScreenHistoryItemConst.TABLE)
                .filter(ScreenHistoryItemConst.C_SCREEN_ID, "?")
                .orderBy(ScreenHistoryItemConst.C_HISTORY_POSITION, false)

        STACK_TOP_QUERY = topPositionQuery.buildQuery() + " LIMIT 1"

        STACK_TOP_ID_QUERY = topPositionQuery
                .clone()
                .field(ScreenHistoryItemConst.C_ID)
                .buildQuery() + " LIMIT 1"

        STACK_TOP_TITLE_QUERY = topPositionQuery
                .clone()
                .field(ScreenHistoryItemConst.C_TITLE)
                .buildQuery() + " LIMIT 1"


        CLEAN_HISTORY_QUERY = ScreenHistoryItemConst.C_SCREEN_ID + "=? " +
                "AND " + ScreenHistoryItemConst.C_HISTORY_POSITION + "!=0 " + //0 is the root

                "AND " + ScreenHistoryItemConst.C_HISTORY_POSITION + "<= " +
                "(SELECT MAX(" + ScreenHistoryItemConst.C_HISTORY_POSITION + ") " +
                "FROM " + ScreenHistoryItemConst.TABLE +
                " WHERE " + ScreenHistoryItemConst.C_SCREEN_ID + "=?)-" + ALLOWED_HISTORY_LENGTH
    }

    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    fun findCurrentScreenHistoryItemIdByScreenId(screenId: Long): Long {
        return findValueByRawQuery(valueType = Long::class.java, rawQuery = STACK_TOP_ID_QUERY, selectionArgs = SQLQueryBuilder.toSelectionArgs(screenId), defaultValue = 0L)
    }

    fun findCurrentScreenHistoryItemByScreenId(screenId: Long): ScreenHistoryItem? {
        return findByRawQuery(STACK_TOP_QUERY, SQLQueryBuilder.toSelectionArgs(screenId))
    }

    fun findStackTopTitleByScreenId(screenId: Long): String {
        return findValueByRawQuery(valueType = String::class.java, rawQuery = STACK_TOP_TITLE_QUERY, selectionArgs = SQLQueryBuilder.toSelectionArgs(screenId), defaultValue = "")
    }

    fun deleteAllByScreenId(screenId: Long) {
        delete(ScreenHistoryItemConst.C_SCREEN_ID + " = ?", SQLQueryBuilder.toSelectionArgs(screenId))
    }

    fun findTitleById(id: Long): String {
        return findValueByRowId(String::class.java,
                column = ScreenHistoryItemConst.C_TITLE,
                rowId = id,
                defaultValue = "")
    }

    fun findNextHistoryPositionByScreenId(screenId: Long): Int {
        return findValueBySelection(
                valueType = Int::class.java,
                column = "MAX(${ScreenHistoryItemConst.C_HISTORY_POSITION})",
                selection = "${ScreenHistoryItemConst.C_SCREEN_ID} = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(screenId),
                defaultValue = 0) + 1
    }

    fun deleteTopItemByScreenId(screenId: Long) {
        val topId = findCurrentScreenHistoryItemIdByScreenId(screenId)
        if (topId > 0) {
            delete(topId)
            logCurrent(screenId,"delete")
        }
    }

    fun findPreviousScreenHistoryItem(ScreenHistoryItem: ScreenHistoryItem): ScreenHistoryItem? {
        val previousPosition = ScreenHistoryItem.historyPosition - 1
        return findBySelection(
                selection = "${ScreenHistoryItemConst.C_SCREEN_ID} = ? AND ${ScreenHistoryItemConst.C_HISTORY_POSITION} = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(ScreenHistoryItem.screenId, previousPosition))
    }

    fun cleanupScreenHistory(screenId: Long) {
        val deleted = delete(CLEAN_HISTORY_QUERY, SQLQueryBuilder.toSelectionArgs(screenId, screenId))
        if (deleted > 0) Timber.d("$deleted ScreenHistoryItem(s) cleaned")
    }

    fun findCountByScreenId(screenId: Long) = findCountBySelection(ScreenHistoryItemConst.C_SCREEN_ID + " = " + screenId, null)

    fun log(screenHistoryItem: ScreenHistoryItem?, prefix: String) {
        Timber.d("$prefix ScreenHistoryItem: position ${screenHistoryItem?.historyPosition}, name ${screenHistoryItem?.title}, desc ${screenHistoryItem?.description}")
    }

    fun logCurrent(screenId: Long, prefix: String) {
        log(findCurrentScreenHistoryItemByScreenId(screenId), prefix)
    }

    companion object {
        private const val ALLOWED_HISTORY_LENGTH: Long = 50
    }
}
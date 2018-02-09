/*
 * TabHistoryItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.gl.tabhistoryitem

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import javax.inject.Inject

@Deprecated("Use ScreenHistoryItemManager")
@javax.inject.Singleton
class TabHistoryItemManager @Inject constructor(databaseManager: DatabaseManager) : TabHistoryItemBaseManager(databaseManager) {
    private val ALLOWED_HISTORY_LENGTH: Long = 50

    val STACK_TOP_QUERY: String
    val STACK_TOP_ID_QUERY: String
    val STACK_TOP_TITLE_QUERY: String
    val CLEAN_HISTORY_QUERY: String

    init {
        val topPositionQuery = SQLQueryBuilder()
                .table(TabHistoryItemConst.TABLE)
                .filter(TabHistoryItemConst.C_TAB_ID, "?")
                .orderBy(TabHistoryItemConst.C_HISTORY_POSITION, false)

        STACK_TOP_QUERY = topPositionQuery.buildQuery() + " LIMIT 1"

        STACK_TOP_ID_QUERY = topPositionQuery
                .clone()
                .field(TabHistoryItemConst.C_ID)
                .buildQuery() + " LIMIT 1"

        STACK_TOP_TITLE_QUERY = topPositionQuery
                .clone()
                .field(TabHistoryItemConst.C_TITLE)
                .buildQuery() + " LIMIT 1"


        CLEAN_HISTORY_QUERY = TabHistoryItemConst.C_TAB_ID + "=? " +
                "AND " + TabHistoryItemConst.C_HISTORY_POSITION + "!=0 " + //0 is the root

                "AND " + TabHistoryItemConst.C_HISTORY_POSITION + "<= " +
                "(SELECT MAX(" + TabHistoryItemConst.C_HISTORY_POSITION + ") " +
                "FROM " + TabHistoryItemConst.TABLE +
                " WHERE " + TabHistoryItemConst.C_TAB_ID + "=?)-" + ALLOWED_HISTORY_LENGTH
    }

    @Deprecated("Use ScreenHistoryItemManager")
    fun findCurrentTabHistoryItemIdByTabId(screenId: Long): Long {
        return findValueByRawQuery(valueType = Long::class.java, rawQuery = STACK_TOP_ID_QUERY, selectionArgs = SQLQueryBuilder.toSelectionArgs(screenId), defaultValue = 0L)
    }

    @Deprecated("Use ScreenHistoryItemManager")
    fun findCurrentTabHistoryItemByTabId(screenId: Long): TabHistoryItem? {
        return findByRawQuery(STACK_TOP_QUERY, SQLQueryBuilder.toSelectionArgs(screenId))
    }

    @Deprecated("Use ScreenHistoryItemManager")
    fun findStackTopTitleByTabId(screenId: Long): String {
        return findValueByRawQuery(valueType = String::class.java, rawQuery = STACK_TOP_TITLE_QUERY, selectionArgs = SQLQueryBuilder.toSelectionArgs(screenId), defaultValue = "")
    }

    @Deprecated("Use ScreenHistoryItemManager")
    fun deleteAllByTabId(screenId: Long) {
        delete(TabHistoryItemConst.C_TAB_ID + " = ?", SQLQueryBuilder.toSelectionArgs(screenId))
    }

    @Deprecated("Use ScreenHistoryItemManager")
    fun findTitleById(id: Long): String {
        return findValueByRowId(String::class.java,
                column = TabHistoryItemConst.C_TITLE,
                rowId = id,
                defaultValue = "")
    }

    @Deprecated("Use ScreenHistoryItemManager")
    fun findNextHistoryPositionByTabId(tabId: Long): Int {
        return findCountBySelection(TabHistoryItemConst.C_TAB_ID + " = ?", SQLQueryBuilder.toSelectionArgs(tabId)).toInt()
    }

    @Deprecated("Use ScreenHistoryItemManager")
    fun deleteTopItemByTabId(tabId: Long) {
        val topId = findCurrentTabHistoryItemIdByTabId(tabId)
        if (topId > 0) {
            delete(topId)
        }
    }

    @Deprecated("Use ScreenHistoryItemManager")
    fun findPreviousTabHistoryItem(tabHistoryItem: TabHistoryItem): TabHistoryItem? {
        val previousPosition = tabHistoryItem.historyPosition - 1
        return findBySelection(
                selection = "${TabHistoryItemConst.C_TAB_ID} = ? AND ${TabHistoryItemConst.C_HISTORY_POSITION} = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(tabHistoryItem.tabId, previousPosition))
    }

    @Deprecated("Use ScreenHistoryItemManager")
    fun cleanupTabHistory(tabId: Long) {
        delete(CLEAN_HISTORY_QUERY, SQLQueryBuilder.toSelectionArgs(tabId, tabId))
    }

    @Deprecated("Use ScreenHistoryItemManager")
    fun findCountByTabId(tabId: Long): Long {
        return findCountBySelection(TabHistoryItemConst.C_TAB_ID + " = " + tabId, null)
    }

    fun findAndConvertToScreenItems(tabId: Long, newScreenId: Long): List<ScreenHistoryItem> {
        val tabItems = findAllBySelection("${TabHistoryItemConst.C_TAB_ID} = $tabId")
        return tabItems.map {
            ScreenHistoryItem().apply {
                screenId = newScreenId
                historyPosition = it.historyPosition
                sourceType = it.sourceType
                title = it.title
                description = it.description
                extrasJson = it.extrasJson
            }
        }
    }
}
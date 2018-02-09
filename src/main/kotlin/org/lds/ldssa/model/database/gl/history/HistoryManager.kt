/*
 * HistoryManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.gl.history

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.threeten.bp.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@javax.inject.Singleton
class HistoryManager @Inject constructor(databaseManager: DatabaseManager) : HistoryBaseManager(databaseManager) {

    private val THREE_MONTHS_MILLISECONDS = TimeUnit.DAYS.toMillis(93) //Approximately 3 months is fine
    private val ALLOWED_ITEM_COUNT: Long = 1000

    private val TRIM_QUERY = "SELECT ${HistoryConst.C_TIME} FROM ${HistoryConst.TABLE} ORDER BY ${HistoryConst.C_TIME} DESC LIMIT 1 OFFSET ?"
    private val HISTORY_IN_DAY_SELECTION = "${HistoryConst.C_TIME} >= ? AND ${HistoryConst.C_CONTENT_ITEM_ID} = ? AND ${HistoryConst.C_ITEM_URI} = ?"

    fun findAllSorted(): List<History> {
        return findAllBySelection(orderBy = "${HistoryConst.C_TIME} DESC")
    }

    fun add(contentItemId: Long, itemUri: String?, title: String, description: String, scrollPosition: Int) {
        val currentTime = System.currentTimeMillis()
        val dayStart = currentTime - currentTime % TimeUnit.DAYS.toMillis(1)

        //If a history item for the current contentItemId / itemUri already exists for the day, update the timestamp
        val cursor = findCursorBySelection(HISTORY_IN_DAY_SELECTION, SQLQueryBuilder.toSelectionArgs(dayStart, contentItemId, itemUri))
        if (cursor != null && cursor.moveToFirst()) {
            updateLastModified(HistoryConst.getId(cursor))
            return
        }

        //Adds a new History item
        val history = History()
        history.time = LocalDateTime.now()
        history.contentItemId = contentItemId
        history.itemUri = itemUri
        history.title = title
        history.description = description
        history.scrollPosition =scrollPosition

        save(history)
    }

    fun updateLastModified(id: Long) {
        val values = createNewDBToolsContentValues()
        values.put(HistoryConst.C_TIME, System.currentTimeMillis())
        update(values, id)
    }

    fun cleanupHistory() {
        deleteOlder(THREE_MONTHS_MILLISECONDS)
        trimItems(ALLOWED_ITEM_COUNT)
    }

    fun deleteOlder(ageOffset: Long) {
        val currentTime = System.currentTimeMillis()
        val cutoffTime = currentTime - currentTime % TimeUnit.DAYS.toMillis(1) - ageOffset

        delete(HistoryConst.C_TIME + " < ?", SQLQueryBuilder.toSelectionArgs(cutoffTime))
    }

    fun trimItems(allowedItemCount: Long) {
        val lastAllowedDate = findValueByRawQuery(Long::class.java,
                rawQuery = TRIM_QUERY,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(allowedItemCount),
                defaultValue = null)

        if (lastAllowedDate != null) {
            delete("${HistoryConst.C_TIME} <= ?", SQLQueryBuilder.toSelectionArgs(lastAllowedDate))
        }
    }
}
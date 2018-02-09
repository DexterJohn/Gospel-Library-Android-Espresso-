/*
 * SidebarHistoryItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.gl.sidebarhistoryitem

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.types.SideBarSourceType
import javax.inject.Inject


@javax.inject.Singleton
class SidebarHistoryItemManager @Inject constructor(databaseManager: DatabaseManager) : SidebarHistoryItemBaseManager(databaseManager) {

    val STACK_TOP_QUERY: String
    val STACK_TOP_ID_QUERY: String
    val STACK_TOP_TYPE_QUERY: String

    init {
        val topPositionQuery = SQLQueryBuilder()
                .table(SidebarHistoryItemConst.TABLE)
                .orderBy(SidebarHistoryItemConst.C_HISTORY_POSITION, false)

        STACK_TOP_QUERY = topPositionQuery.buildQuery() + " LIMIT 1"

        STACK_TOP_ID_QUERY = topPositionQuery
                .clone()
                .field(SidebarHistoryItemConst.C_ID)
                .buildQuery() + " LIMIT 1"

        STACK_TOP_TYPE_QUERY = topPositionQuery
                .clone()
                .field(SidebarHistoryItemConst.C_SOURCE_TYPE)
                .buildQuery() + " LIMIT 1"
    }

    fun findCurrentHistoryItemId(): Long {
        return findValueByRawQuery(Long::class.java, rawQuery = STACK_TOP_ID_QUERY, defaultValue = 0L)
    }

    fun findCurrentHistoryItemType(): SideBarSourceType {
        val typeOrdinal = findValueByRawQuery(Int::class.java, rawQuery = STACK_TOP_TYPE_QUERY, defaultValue = -1)

        if (typeOrdinal >= 0) {
            return SideBarSourceType.values()[typeOrdinal]
        } else {
            return SideBarSourceType.UNKNOWN
        }
    }


    fun findCurrentHistoryItem(): SidebarHistoryItem? {
        return findByRawQuery(STACK_TOP_QUERY, null)
    }

    fun deleteTopItem() {
        val topId = findCurrentHistoryItemId()
        if (topId > 0) {
            delete(topId)
        }
    }

    fun findNextHistoryPosition(): Int {
        return findCount().toInt()
    }
}
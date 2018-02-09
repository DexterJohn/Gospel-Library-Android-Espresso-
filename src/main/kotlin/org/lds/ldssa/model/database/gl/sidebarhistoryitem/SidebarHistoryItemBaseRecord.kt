/*
 * SidebarHistoryItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.sidebarhistoryitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SidebarHistoryItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var historyPosition: Int = 0
    open var sourceType: org.lds.ldssa.model.database.types.SideBarSourceType = org.lds.ldssa.model.database.types.SideBarSourceType.UNKNOWN
    open var title: String = ""
    open var extrasJson: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return SidebarHistoryItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return SidebarHistoryItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SidebarHistoryItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SidebarHistoryItemConst.C_HISTORY_POSITION, historyPosition.toLong())
        values.put(SidebarHistoryItemConst.C_SOURCE_TYPE, sourceType.ordinal.toLong())
        values.put(SidebarHistoryItemConst.C_TITLE, title)
        values.put(SidebarHistoryItemConst.C_EXTRAS_JSON, extrasJson)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            historyPosition.toLong(),
            sourceType.ordinal.toLong(),
            title,
            extrasJson)
    }

    open fun copy() : SidebarHistoryItem {
        val copy = SidebarHistoryItem()
        copy.id = id
        copy.historyPosition = historyPosition
        copy.sourceType = sourceType
        copy.title = title
        copy.extrasJson = extrasJson
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, historyPosition.toLong())
        statement.bindLong(2, sourceType.ordinal.toLong())
        statement.bindString(3, title)
        statement.bindString(4, extrasJson)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, historyPosition.toLong())
        statement.bindLong(2, sourceType.ordinal.toLong())
        statement.bindString(3, title)
        statement.bindString(4, extrasJson)
        statement.bindLong(5, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        historyPosition = values.getAsInteger(SidebarHistoryItemConst.C_HISTORY_POSITION)
        sourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.SideBarSourceType::class.java, values.getAsInteger(SidebarHistoryItemConst.C_SOURCE_TYPE), org.lds.ldssa.model.database.types.SideBarSourceType.UNKNOWN)
        title = values.getAsString(SidebarHistoryItemConst.C_TITLE)
        extrasJson = values.getAsString(SidebarHistoryItemConst.C_EXTRAS_JSON)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(SidebarHistoryItemConst.C_ID))
        historyPosition = cursor.getInt(cursor.getColumnIndexOrThrow(SidebarHistoryItemConst.C_HISTORY_POSITION))
        sourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.SideBarSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(SidebarHistoryItemConst.C_SOURCE_TYPE)), org.lds.ldssa.model.database.types.SideBarSourceType.UNKNOWN)
        title = cursor.getString(cursor.getColumnIndexOrThrow(SidebarHistoryItemConst.C_TITLE))
        extrasJson = cursor.getString(cursor.getColumnIndexOrThrow(SidebarHistoryItemConst.C_EXTRAS_JSON))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
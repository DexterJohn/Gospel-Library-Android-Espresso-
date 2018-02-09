/*
 * TabHistoryItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.tabhistoryitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class TabHistoryItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var tabId: Long = 0
    open var historyPosition: Int = 0
    open var sourceType: org.lds.ldssa.model.database.types.ScreenSourceType = org.lds.ldssa.model.database.types.ScreenSourceType.UNKNOWN
    open var title: String = ""
    open var description: String = ""
    open var extrasJson: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return TabHistoryItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return TabHistoryItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return TabHistoryItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(TabHistoryItemConst.C_TAB_ID, tabId)
        values.put(TabHistoryItemConst.C_HISTORY_POSITION, historyPosition.toLong())
        values.put(TabHistoryItemConst.C_SOURCE_TYPE, sourceType.ordinal.toLong())
        values.put(TabHistoryItemConst.C_TITLE, title)
        values.put(TabHistoryItemConst.C_DESCRIPTION, description)
        values.put(TabHistoryItemConst.C_EXTRAS_JSON, extrasJson)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            tabId,
            historyPosition.toLong(),
            sourceType.ordinal.toLong(),
            title,
            description,
            extrasJson)
    }

    open fun copy() : TabHistoryItem {
        val copy = TabHistoryItem()
        copy.id = id
        copy.tabId = tabId
        copy.historyPosition = historyPosition
        copy.sourceType = sourceType
        copy.title = title
        copy.description = description
        copy.extrasJson = extrasJson
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, tabId)
        statement.bindLong(2, historyPosition.toLong())
        statement.bindLong(3, sourceType.ordinal.toLong())
        statement.bindString(4, title)
        statement.bindString(5, description)
        statement.bindString(6, extrasJson)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, tabId)
        statement.bindLong(2, historyPosition.toLong())
        statement.bindLong(3, sourceType.ordinal.toLong())
        statement.bindString(4, title)
        statement.bindString(5, description)
        statement.bindString(6, extrasJson)
        statement.bindLong(7, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        tabId = values.getAsLong(TabHistoryItemConst.C_TAB_ID)
        historyPosition = values.getAsInteger(TabHistoryItemConst.C_HISTORY_POSITION)
        sourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ScreenSourceType::class.java, values.getAsInteger(TabHistoryItemConst.C_SOURCE_TYPE), org.lds.ldssa.model.database.types.ScreenSourceType.UNKNOWN)
        title = values.getAsString(TabHistoryItemConst.C_TITLE)
        description = values.getAsString(TabHistoryItemConst.C_DESCRIPTION)
        extrasJson = values.getAsString(TabHistoryItemConst.C_EXTRAS_JSON)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(TabHistoryItemConst.C_ID))
        tabId = cursor.getLong(cursor.getColumnIndexOrThrow(TabHistoryItemConst.C_TAB_ID))
        historyPosition = cursor.getInt(cursor.getColumnIndexOrThrow(TabHistoryItemConst.C_HISTORY_POSITION))
        sourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ScreenSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(TabHistoryItemConst.C_SOURCE_TYPE)), org.lds.ldssa.model.database.types.ScreenSourceType.UNKNOWN)
        title = cursor.getString(cursor.getColumnIndexOrThrow(TabHistoryItemConst.C_TITLE))
        description = cursor.getString(cursor.getColumnIndexOrThrow(TabHistoryItemConst.C_DESCRIPTION))
        extrasJson = cursor.getString(cursor.getColumnIndexOrThrow(TabHistoryItemConst.C_EXTRAS_JSON))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
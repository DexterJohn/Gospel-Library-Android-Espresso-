/*
 * ScreenHistoryItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.screenhistoryitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class ScreenHistoryItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var screenId: Long = 0
    open var historyPosition: Int = 0
    open var sourceType: org.lds.ldssa.model.database.types.ScreenSourceType = org.lds.ldssa.model.database.types.ScreenSourceType.UNKNOWN
    open var title: String = ""
    open var description: String = ""
    open var extrasJson: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ScreenHistoryItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return ScreenHistoryItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return ScreenHistoryItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(ScreenHistoryItemConst.C_SCREEN_ID, screenId)
        values.put(ScreenHistoryItemConst.C_HISTORY_POSITION, historyPosition.toLong())
        values.put(ScreenHistoryItemConst.C_SOURCE_TYPE, sourceType.ordinal.toLong())
        values.put(ScreenHistoryItemConst.C_TITLE, title)
        values.put(ScreenHistoryItemConst.C_DESCRIPTION, description)
        values.put(ScreenHistoryItemConst.C_EXTRAS_JSON, extrasJson)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            screenId,
            historyPosition.toLong(),
            sourceType.ordinal.toLong(),
            title,
            description,
            extrasJson)
    }

    open fun copy() : ScreenHistoryItem {
        val copy = ScreenHistoryItem()
        copy.id = id
        copy.screenId = screenId
        copy.historyPosition = historyPosition
        copy.sourceType = sourceType
        copy.title = title
        copy.description = description
        copy.extrasJson = extrasJson
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, historyPosition.toLong())
        statement.bindLong(3, sourceType.ordinal.toLong())
        statement.bindString(4, title)
        statement.bindString(5, description)
        statement.bindString(6, extrasJson)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, historyPosition.toLong())
        statement.bindLong(3, sourceType.ordinal.toLong())
        statement.bindString(4, title)
        statement.bindString(5, description)
        statement.bindString(6, extrasJson)
        statement.bindLong(7, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        screenId = values.getAsLong(ScreenHistoryItemConst.C_SCREEN_ID)
        historyPosition = values.getAsInteger(ScreenHistoryItemConst.C_HISTORY_POSITION)
        sourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ScreenSourceType::class.java, values.getAsInteger(ScreenHistoryItemConst.C_SOURCE_TYPE), org.lds.ldssa.model.database.types.ScreenSourceType.UNKNOWN)
        title = values.getAsString(ScreenHistoryItemConst.C_TITLE)
        description = values.getAsString(ScreenHistoryItemConst.C_DESCRIPTION)
        extrasJson = values.getAsString(ScreenHistoryItemConst.C_EXTRAS_JSON)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(ScreenHistoryItemConst.C_ID))
        screenId = cursor.getLong(cursor.getColumnIndexOrThrow(ScreenHistoryItemConst.C_SCREEN_ID))
        historyPosition = cursor.getInt(cursor.getColumnIndexOrThrow(ScreenHistoryItemConst.C_HISTORY_POSITION))
        sourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ScreenSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(ScreenHistoryItemConst.C_SOURCE_TYPE)), org.lds.ldssa.model.database.types.ScreenSourceType.UNKNOWN)
        title = cursor.getString(cursor.getColumnIndexOrThrow(ScreenHistoryItemConst.C_TITLE))
        description = cursor.getString(cursor.getColumnIndexOrThrow(ScreenHistoryItemConst.C_DESCRIPTION))
        extrasJson = cursor.getString(cursor.getColumnIndexOrThrow(ScreenHistoryItemConst.C_EXTRAS_JSON))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
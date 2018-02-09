/*
 * HistoryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.history

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class HistoryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var time: org.threeten.bp.LocalDateTime = org.threeten.bp.LocalDateTime.now()
    open var title: String = ""
    open var description: String = ""
    open var contentItemId: Long = 0
    open var itemUri: String? = null
    open var scrollPosition: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return HistoryConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return HistoryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return HistoryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(HistoryConst.C_TIME, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(time)!!)
        values.put(HistoryConst.C_TITLE, title)
        values.put(HistoryConst.C_DESCRIPTION, description)
        values.put(HistoryConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(HistoryConst.C_ITEM_URI, itemUri)
        values.put(HistoryConst.C_SCROLL_POSITION, scrollPosition.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(time)!!,
            title,
            description,
            contentItemId,
            itemUri,
            scrollPosition.toLong())
    }

    open fun copy() : History {
        val copy = History()
        copy.id = id
        copy.time = time
        copy.title = title
        copy.description = description
        copy.contentItemId = contentItemId
        copy.itemUri = itemUri
        copy.scrollPosition = scrollPosition
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(time)!!)
        statement.bindString(2, title)
        statement.bindString(3, description)
        statement.bindLong(4, contentItemId)
        if (itemUri != null) {
            statement.bindString(5, itemUri!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindLong(6, scrollPosition.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(time)!!)
        statement.bindString(2, title)
        statement.bindString(3, description)
        statement.bindLong(4, contentItemId)
        if (itemUri != null) {
            statement.bindString(5, itemUri!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindLong(6, scrollPosition.toLong())
        statement.bindLong(7, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        time = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(HistoryConst.C_TIME))!!
        title = values.getAsString(HistoryConst.C_TITLE)
        description = values.getAsString(HistoryConst.C_DESCRIPTION)
        contentItemId = values.getAsLong(HistoryConst.C_CONTENT_ITEM_ID)
        itemUri = values.getAsString(HistoryConst.C_ITEM_URI)
        scrollPosition = values.getAsInteger(HistoryConst.C_SCROLL_POSITION)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(HistoryConst.C_ID))
        time = if (!cursor.isNull(cursor.getColumnIndexOrThrow(HistoryConst.C_TIME))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(HistoryConst.C_TIME)))!! else null!!
        title = cursor.getString(cursor.getColumnIndexOrThrow(HistoryConst.C_TITLE))
        description = cursor.getString(cursor.getColumnIndexOrThrow(HistoryConst.C_DESCRIPTION))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(HistoryConst.C_CONTENT_ITEM_ID))
        itemUri = if (!cursor.isNull(cursor.getColumnIndexOrThrow(HistoryConst.C_ITEM_URI))) cursor.getString(cursor.getColumnIndexOrThrow(HistoryConst.C_ITEM_URI)) else null
        scrollPosition = cursor.getInt(cursor.getColumnIndexOrThrow(HistoryConst.C_SCROLL_POSITION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
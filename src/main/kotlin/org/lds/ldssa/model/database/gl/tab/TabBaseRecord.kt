/*
 * TabBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.tab

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class TabBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var androidTaskId: Int = 0
    open var languageId: Long = 0
    open var name: String = ""
    open var displayOrder: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return TabConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return TabConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return TabConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(TabConst.C_ANDROID_TASK_ID, androidTaskId.toLong())
        values.put(TabConst.C_LANGUAGE_ID, languageId)
        values.put(TabConst.C_NAME, name)
        values.put(TabConst.C_DISPLAY_ORDER, displayOrder.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            androidTaskId.toLong(),
            languageId,
            name,
            displayOrder.toLong())
    }

    open fun copy() : Tab {
        val copy = Tab()
        copy.id = id
        copy.androidTaskId = androidTaskId
        copy.languageId = languageId
        copy.name = name
        copy.displayOrder = displayOrder
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, androidTaskId.toLong())
        statement.bindLong(2, languageId)
        statement.bindString(3, name)
        statement.bindLong(4, displayOrder.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, androidTaskId.toLong())
        statement.bindLong(2, languageId)
        statement.bindString(3, name)
        statement.bindLong(4, displayOrder.toLong())
        statement.bindLong(5, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        androidTaskId = values.getAsInteger(TabConst.C_ANDROID_TASK_ID)
        languageId = values.getAsLong(TabConst.C_LANGUAGE_ID)
        name = values.getAsString(TabConst.C_NAME)
        displayOrder = values.getAsInteger(TabConst.C_DISPLAY_ORDER)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(TabConst.C_ID))
        androidTaskId = cursor.getInt(cursor.getColumnIndexOrThrow(TabConst.C_ANDROID_TASK_ID))
        languageId = cursor.getLong(cursor.getColumnIndexOrThrow(TabConst.C_LANGUAGE_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(TabConst.C_NAME))
        displayOrder = cursor.getInt(cursor.getColumnIndexOrThrow(TabConst.C_DISPLAY_ORDER))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
/*
 * ScreenBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.screen

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class ScreenBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var androidTaskId: Int = 0
    open var languageId: Long = 0
    open var name: String = ""
    open var displayOrder: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ScreenConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return ScreenConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return ScreenConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(ScreenConst.C_ANDROID_TASK_ID, androidTaskId.toLong())
        values.put(ScreenConst.C_LANGUAGE_ID, languageId)
        values.put(ScreenConst.C_NAME, name)
        values.put(ScreenConst.C_DISPLAY_ORDER, displayOrder.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            androidTaskId.toLong(),
            languageId,
            name,
            displayOrder.toLong())
    }

    open fun copy() : Screen {
        val copy = Screen()
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
        androidTaskId = values.getAsInteger(ScreenConst.C_ANDROID_TASK_ID)
        languageId = values.getAsLong(ScreenConst.C_LANGUAGE_ID)
        name = values.getAsString(ScreenConst.C_NAME)
        displayOrder = values.getAsInteger(ScreenConst.C_DISPLAY_ORDER)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(ScreenConst.C_ID))
        androidTaskId = cursor.getInt(cursor.getColumnIndexOrThrow(ScreenConst.C_ANDROID_TASK_ID))
        languageId = cursor.getLong(cursor.getColumnIndexOrThrow(ScreenConst.C_LANGUAGE_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(ScreenConst.C_NAME))
        displayOrder = cursor.getInt(cursor.getColumnIndexOrThrow(ScreenConst.C_DISPLAY_ORDER))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
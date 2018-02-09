/*
 * SubItemContentBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.subitemcontent

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SubItemContentBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var subitemId: Int = 0
    open var contentHtml: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return SubItemContentConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return SubItemContentConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SubItemContentConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SubItemContentConst.C_SUBITEM_ID, subitemId.toLong())
        values.put(SubItemContentConst.C_CONTENT_HTML, contentHtml)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            subitemId.toLong(),
            contentHtml)
    }

    open fun copy() : SubItemContent {
        val copy = SubItemContent()
        copy.id = id
        copy.subitemId = subitemId
        copy.contentHtml = contentHtml
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId.toLong())
        statement.bindString(2, contentHtml)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId.toLong())
        statement.bindString(2, contentHtml)
        statement.bindLong(3, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        subitemId = values.getAsInteger(SubItemContentConst.C_SUBITEM_ID)
        contentHtml = values.getAsString(SubItemContentConst.C_CONTENT_HTML)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(SubItemContentConst.C_ID))
        subitemId = cursor.getInt(cursor.getColumnIndexOrThrow(SubItemContentConst.C_SUBITEM_ID))
        contentHtml = cursor.getString(cursor.getColumnIndexOrThrow(SubItemContentConst.C_CONTENT_HTML))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
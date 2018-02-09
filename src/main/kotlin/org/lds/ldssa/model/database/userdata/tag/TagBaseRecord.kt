/*
 * TagBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.tag

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class TagBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var annotationId: Long = 0
    open var name: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return TagConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return TagConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return TagConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(TagConst.C_ANNOTATION_ID, annotationId)
        values.put(TagConst.C_NAME, name)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            annotationId,
            name)
    }

    open fun copy() : Tag {
        val copy = Tag()
        copy.id = id
        copy.annotationId = annotationId
        copy.name = name
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, annotationId)
        statement.bindString(2, name)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, annotationId)
        statement.bindString(2, name)
        statement.bindLong(3, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        annotationId = values.getAsLong(TagConst.C_ANNOTATION_ID)
        name = values.getAsString(TagConst.C_NAME)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(TagConst.C_ID))
        annotationId = cursor.getLong(cursor.getColumnIndexOrThrow(TagConst.C_ANNOTATION_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(TagConst.C_NAME))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
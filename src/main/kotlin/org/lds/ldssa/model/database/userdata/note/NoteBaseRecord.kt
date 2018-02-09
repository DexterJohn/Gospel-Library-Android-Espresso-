/*
 * NoteBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.note

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class NoteBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var annotationId: Long = 0
    open var title: String? = null
    open var content: String? = null

    constructor() {
    }

    override fun getIdColumnName() : String {
        return NoteConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return NoteConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return NoteConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(NoteConst.C_ANNOTATION_ID, annotationId)
        values.put(NoteConst.C_TITLE, title)
        values.put(NoteConst.C_CONTENT, content)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            annotationId,
            title,
            content)
    }

    open fun copy() : Note {
        val copy = Note()
        copy.id = id
        copy.annotationId = annotationId
        copy.title = title
        copy.content = content
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, annotationId)
        if (title != null) {
            statement.bindString(2, title!!)
        } else {
            statement.bindNull(2)
        }
        if (content != null) {
            statement.bindString(3, content!!)
        } else {
            statement.bindNull(3)
        }
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, annotationId)
        if (title != null) {
            statement.bindString(2, title!!)
        } else {
            statement.bindNull(2)
        }
        if (content != null) {
            statement.bindString(3, content!!)
        } else {
            statement.bindNull(3)
        }
        statement.bindLong(4, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        annotationId = values.getAsLong(NoteConst.C_ANNOTATION_ID)
        title = values.getAsString(NoteConst.C_TITLE)
        content = values.getAsString(NoteConst.C_CONTENT)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(NoteConst.C_ID))
        annotationId = cursor.getLong(cursor.getColumnIndexOrThrow(NoteConst.C_ANNOTATION_ID))
        title = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NoteConst.C_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(NoteConst.C_TITLE)) else null
        content = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NoteConst.C_CONTENT))) cursor.getString(cursor.getColumnIndexOrThrow(NoteConst.C_CONTENT)) else null
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
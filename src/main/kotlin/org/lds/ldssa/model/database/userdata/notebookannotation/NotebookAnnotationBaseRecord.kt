/*
 * NotebookAnnotationBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.notebookannotation

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class NotebookAnnotationBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var notebookId: Long = 0
    open var annotationId: Long = 0
    open var displayOrder: Int = -1
    open var uniqueAnnotationId: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return NotebookAnnotationConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return NotebookAnnotationConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return NotebookAnnotationConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(NotebookAnnotationConst.C_NOTEBOOK_ID, notebookId)
        values.put(NotebookAnnotationConst.C_ANNOTATION_ID, annotationId)
        values.put(NotebookAnnotationConst.C_DISPLAY_ORDER, displayOrder.toLong())
        values.put(NotebookAnnotationConst.C_UNIQUE_ANNOTATION_ID, uniqueAnnotationId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            notebookId,
            annotationId,
            displayOrder.toLong(),
            uniqueAnnotationId)
    }

    open fun copy() : NotebookAnnotation {
        val copy = NotebookAnnotation()
        copy.id = id
        copy.notebookId = notebookId
        copy.annotationId = annotationId
        copy.displayOrder = displayOrder
        copy.uniqueAnnotationId = uniqueAnnotationId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, notebookId)
        statement.bindLong(2, annotationId)
        statement.bindLong(3, displayOrder.toLong())
        statement.bindString(4, uniqueAnnotationId)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, notebookId)
        statement.bindLong(2, annotationId)
        statement.bindLong(3, displayOrder.toLong())
        statement.bindString(4, uniqueAnnotationId)
        statement.bindLong(5, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        notebookId = values.getAsLong(NotebookAnnotationConst.C_NOTEBOOK_ID)
        annotationId = values.getAsLong(NotebookAnnotationConst.C_ANNOTATION_ID)
        displayOrder = values.getAsInteger(NotebookAnnotationConst.C_DISPLAY_ORDER)
        uniqueAnnotationId = values.getAsString(NotebookAnnotationConst.C_UNIQUE_ANNOTATION_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(NotebookAnnotationConst.C_ID))
        notebookId = cursor.getLong(cursor.getColumnIndexOrThrow(NotebookAnnotationConst.C_NOTEBOOK_ID))
        annotationId = cursor.getLong(cursor.getColumnIndexOrThrow(NotebookAnnotationConst.C_ANNOTATION_ID))
        displayOrder = cursor.getInt(cursor.getColumnIndexOrThrow(NotebookAnnotationConst.C_DISPLAY_ORDER))
        uniqueAnnotationId = cursor.getString(cursor.getColumnIndexOrThrow(NotebookAnnotationConst.C_UNIQUE_ANNOTATION_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
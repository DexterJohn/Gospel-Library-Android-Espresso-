/*
 * LinkBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.link

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class LinkBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var annotationId: Long = 0
    open var name: String = ""
    open var docId: String? = null
    open var paragraphAid: String? = null
    open var contentVersion: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return LinkConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return LinkConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return LinkConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(LinkConst.C_ANNOTATION_ID, annotationId)
        values.put(LinkConst.C_NAME, name)
        values.put(LinkConst.C_DOC_ID, docId)
        values.put(LinkConst.C_PARAGRAPH_AID, paragraphAid)
        values.put(LinkConst.C_CONTENT_VERSION, contentVersion.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            annotationId,
            name,
            docId,
            paragraphAid,
            contentVersion.toLong())
    }

    open fun copy() : Link {
        val copy = Link()
        copy.id = id
        copy.annotationId = annotationId
        copy.name = name
        copy.docId = docId
        copy.paragraphAid = paragraphAid
        copy.contentVersion = contentVersion
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, annotationId)
        statement.bindString(2, name)
        if (docId != null) {
            statement.bindString(3, docId!!)
        } else {
            statement.bindNull(3)
        }
        if (paragraphAid != null) {
            statement.bindString(4, paragraphAid!!)
        } else {
            statement.bindNull(4)
        }
        statement.bindLong(5, contentVersion.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, annotationId)
        statement.bindString(2, name)
        if (docId != null) {
            statement.bindString(3, docId!!)
        } else {
            statement.bindNull(3)
        }
        if (paragraphAid != null) {
            statement.bindString(4, paragraphAid!!)
        } else {
            statement.bindNull(4)
        }
        statement.bindLong(5, contentVersion.toLong())
        statement.bindLong(6, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        annotationId = values.getAsLong(LinkConst.C_ANNOTATION_ID)
        name = values.getAsString(LinkConst.C_NAME)
        docId = values.getAsString(LinkConst.C_DOC_ID)
        paragraphAid = values.getAsString(LinkConst.C_PARAGRAPH_AID)
        contentVersion = values.getAsInteger(LinkConst.C_CONTENT_VERSION)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(LinkConst.C_ID))
        annotationId = cursor.getLong(cursor.getColumnIndexOrThrow(LinkConst.C_ANNOTATION_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(LinkConst.C_NAME))
        docId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(LinkConst.C_DOC_ID))) cursor.getString(cursor.getColumnIndexOrThrow(LinkConst.C_DOC_ID)) else null
        paragraphAid = if (!cursor.isNull(cursor.getColumnIndexOrThrow(LinkConst.C_PARAGRAPH_AID))) cursor.getString(cursor.getColumnIndexOrThrow(LinkConst.C_PARAGRAPH_AID)) else null
        contentVersion = cursor.getInt(cursor.getColumnIndexOrThrow(LinkConst.C_CONTENT_VERSION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
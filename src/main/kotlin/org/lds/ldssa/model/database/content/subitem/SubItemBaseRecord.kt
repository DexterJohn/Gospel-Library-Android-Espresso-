/*
 * SubItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.subitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SubItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var uri: String = ""
    open var position: Int = 0
    open var titleHtml: String = ""
    open var title: String = ""
    open var webUrl: String = ""
    open var docId: String = ""
    open var docVersion: Int = 0
    open var contentType: org.lds.ldssa.model.database.types.SubItemContentType = org.lds.ldssa.model.database.types.SubItemContentType.UNKNOWN

    constructor() {
    }

    override fun getIdColumnName() : String {
        return SubItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return SubItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SubItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SubItemConst.C_URI, uri)
        values.put(SubItemConst.C_POSITION, position.toLong())
        values.put(SubItemConst.C_TITLE_HTML, titleHtml)
        values.put(SubItemConst.C_TITLE, title)
        values.put(SubItemConst.C_WEB_URL, webUrl)
        values.put(SubItemConst.C_DOC_ID, docId)
        values.put(SubItemConst.C_DOC_VERSION, docVersion.toLong())
        values.put(SubItemConst.C_CONTENT_TYPE, contentType.ordinal.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            uri,
            position.toLong(),
            titleHtml,
            title,
            webUrl,
            docId,
            docVersion.toLong(),
            contentType.ordinal.toLong())
    }

    open fun copy() : SubItem {
        val copy = SubItem()
        copy.id = id
        copy.uri = uri
        copy.position = position
        copy.titleHtml = titleHtml
        copy.title = title
        copy.webUrl = webUrl
        copy.docId = docId
        copy.docVersion = docVersion
        copy.contentType = contentType
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, uri)
        statement.bindLong(2, position.toLong())
        statement.bindString(3, titleHtml)
        statement.bindString(4, title)
        statement.bindString(5, webUrl)
        statement.bindString(6, docId)
        statement.bindLong(7, docVersion.toLong())
        statement.bindLong(8, contentType.ordinal.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, uri)
        statement.bindLong(2, position.toLong())
        statement.bindString(3, titleHtml)
        statement.bindString(4, title)
        statement.bindString(5, webUrl)
        statement.bindString(6, docId)
        statement.bindLong(7, docVersion.toLong())
        statement.bindLong(8, contentType.ordinal.toLong())
        statement.bindLong(9, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        uri = values.getAsString(SubItemConst.C_URI)
        position = values.getAsInteger(SubItemConst.C_POSITION)
        titleHtml = values.getAsString(SubItemConst.C_TITLE_HTML)
        title = values.getAsString(SubItemConst.C_TITLE)
        webUrl = values.getAsString(SubItemConst.C_WEB_URL)
        docId = values.getAsString(SubItemConst.C_DOC_ID)
        docVersion = values.getAsInteger(SubItemConst.C_DOC_VERSION)
        contentType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.SubItemContentType::class.java, values.getAsInteger(SubItemConst.C_CONTENT_TYPE), org.lds.ldssa.model.database.types.SubItemContentType.UNKNOWN)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(SubItemConst.C_ID))
        uri = cursor.getString(cursor.getColumnIndexOrThrow(SubItemConst.C_URI))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(SubItemConst.C_POSITION))
        titleHtml = cursor.getString(cursor.getColumnIndexOrThrow(SubItemConst.C_TITLE_HTML))
        title = cursor.getString(cursor.getColumnIndexOrThrow(SubItemConst.C_TITLE))
        webUrl = cursor.getString(cursor.getColumnIndexOrThrow(SubItemConst.C_WEB_URL))
        docId = cursor.getString(cursor.getColumnIndexOrThrow(SubItemConst.C_DOC_ID))
        docVersion = cursor.getInt(cursor.getColumnIndexOrThrow(SubItemConst.C_DOC_VERSION))
        contentType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.SubItemContentType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(SubItemConst.C_CONTENT_TYPE)), org.lds.ldssa.model.database.types.SubItemContentType.UNKNOWN)
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
/*
 * RelatedContentItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.relatedcontentitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class RelatedContentItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var subitemId: Int = 0
    open var refId: String = ""
    open var labelHtml: String = ""
    open var originId: String = ""
    open var contentHtml: String = ""
    open var wordOffset: Int = 0
    open var byteLocation: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return RelatedContentItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return RelatedContentItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return RelatedContentItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(RelatedContentItemConst.C_SUBITEM_ID, subitemId.toLong())
        values.put(RelatedContentItemConst.C_REF_ID, refId)
        values.put(RelatedContentItemConst.C_LABEL_HTML, labelHtml)
        values.put(RelatedContentItemConst.C_ORIGIN_ID, originId)
        values.put(RelatedContentItemConst.C_CONTENT_HTML, contentHtml)
        values.put(RelatedContentItemConst.C_WORD_OFFSET, wordOffset.toLong())
        values.put(RelatedContentItemConst.C_BYTE_LOCATION, byteLocation.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            subitemId.toLong(),
            refId,
            labelHtml,
            originId,
            contentHtml,
            wordOffset.toLong(),
            byteLocation.toLong())
    }

    open fun copy() : RelatedContentItem {
        val copy = RelatedContentItem()
        copy.id = id
        copy.subitemId = subitemId
        copy.refId = refId
        copy.labelHtml = labelHtml
        copy.originId = originId
        copy.contentHtml = contentHtml
        copy.wordOffset = wordOffset
        copy.byteLocation = byteLocation
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId.toLong())
        statement.bindString(2, refId)
        statement.bindString(3, labelHtml)
        statement.bindString(4, originId)
        statement.bindString(5, contentHtml)
        statement.bindLong(6, wordOffset.toLong())
        statement.bindLong(7, byteLocation.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId.toLong())
        statement.bindString(2, refId)
        statement.bindString(3, labelHtml)
        statement.bindString(4, originId)
        statement.bindString(5, contentHtml)
        statement.bindLong(6, wordOffset.toLong())
        statement.bindLong(7, byteLocation.toLong())
        statement.bindLong(8, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        subitemId = values.getAsInteger(RelatedContentItemConst.C_SUBITEM_ID)
        refId = values.getAsString(RelatedContentItemConst.C_REF_ID)
        labelHtml = values.getAsString(RelatedContentItemConst.C_LABEL_HTML)
        originId = values.getAsString(RelatedContentItemConst.C_ORIGIN_ID)
        contentHtml = values.getAsString(RelatedContentItemConst.C_CONTENT_HTML)
        wordOffset = values.getAsInteger(RelatedContentItemConst.C_WORD_OFFSET)
        byteLocation = values.getAsInteger(RelatedContentItemConst.C_BYTE_LOCATION)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(RelatedContentItemConst.C_ID))
        subitemId = cursor.getInt(cursor.getColumnIndexOrThrow(RelatedContentItemConst.C_SUBITEM_ID))
        refId = cursor.getString(cursor.getColumnIndexOrThrow(RelatedContentItemConst.C_REF_ID))
        labelHtml = cursor.getString(cursor.getColumnIndexOrThrow(RelatedContentItemConst.C_LABEL_HTML))
        originId = cursor.getString(cursor.getColumnIndexOrThrow(RelatedContentItemConst.C_ORIGIN_ID))
        contentHtml = cursor.getString(cursor.getColumnIndexOrThrow(RelatedContentItemConst.C_CONTENT_HTML))
        wordOffset = cursor.getInt(cursor.getColumnIndexOrThrow(RelatedContentItemConst.C_WORD_OFFSET))
        byteLocation = cursor.getInt(cursor.getColumnIndexOrThrow(RelatedContentItemConst.C_BYTE_LOCATION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
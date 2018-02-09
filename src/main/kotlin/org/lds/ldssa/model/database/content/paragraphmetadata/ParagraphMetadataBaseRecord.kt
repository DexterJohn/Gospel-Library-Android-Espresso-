/*
 * ParagraphMetadataBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.paragraphmetadata

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class ParagraphMetadataBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var subitemId: Int = 0
    open var paragraphId: String = ""
    open var paragraphAid: String = ""
    open var verseNumber: String? = null
    open var startIndex: Int = 0
    open var endIndex: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ParagraphMetadataConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return ParagraphMetadataConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return ParagraphMetadataConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(ParagraphMetadataConst.C_SUBITEM_ID, subitemId.toLong())
        values.put(ParagraphMetadataConst.C_PARAGRAPH_ID, paragraphId)
        values.put(ParagraphMetadataConst.C_PARAGRAPH_AID, paragraphAid)
        values.put(ParagraphMetadataConst.C_VERSE_NUMBER, verseNumber)
        values.put(ParagraphMetadataConst.C_START_INDEX, startIndex.toLong())
        values.put(ParagraphMetadataConst.C_END_INDEX, endIndex.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            subitemId.toLong(),
            paragraphId,
            paragraphAid,
            verseNumber,
            startIndex.toLong(),
            endIndex.toLong())
    }

    open fun copy() : ParagraphMetadata {
        val copy = ParagraphMetadata()
        copy.id = id
        copy.subitemId = subitemId
        copy.paragraphId = paragraphId
        copy.paragraphAid = paragraphAid
        copy.verseNumber = verseNumber
        copy.startIndex = startIndex
        copy.endIndex = endIndex
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId.toLong())
        statement.bindString(2, paragraphId)
        statement.bindString(3, paragraphAid)
        if (verseNumber != null) {
            statement.bindString(4, verseNumber!!)
        } else {
            statement.bindNull(4)
        }
        statement.bindLong(5, startIndex.toLong())
        statement.bindLong(6, endIndex.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId.toLong())
        statement.bindString(2, paragraphId)
        statement.bindString(3, paragraphAid)
        if (verseNumber != null) {
            statement.bindString(4, verseNumber!!)
        } else {
            statement.bindNull(4)
        }
        statement.bindLong(5, startIndex.toLong())
        statement.bindLong(6, endIndex.toLong())
        statement.bindLong(7, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        subitemId = values.getAsInteger(ParagraphMetadataConst.C_SUBITEM_ID)
        paragraphId = values.getAsString(ParagraphMetadataConst.C_PARAGRAPH_ID)
        paragraphAid = values.getAsString(ParagraphMetadataConst.C_PARAGRAPH_AID)
        verseNumber = values.getAsString(ParagraphMetadataConst.C_VERSE_NUMBER)
        startIndex = values.getAsInteger(ParagraphMetadataConst.C_START_INDEX)
        endIndex = values.getAsInteger(ParagraphMetadataConst.C_END_INDEX)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(ParagraphMetadataConst.C_ID))
        subitemId = cursor.getInt(cursor.getColumnIndexOrThrow(ParagraphMetadataConst.C_SUBITEM_ID))
        paragraphId = cursor.getString(cursor.getColumnIndexOrThrow(ParagraphMetadataConst.C_PARAGRAPH_ID))
        paragraphAid = cursor.getString(cursor.getColumnIndexOrThrow(ParagraphMetadataConst.C_PARAGRAPH_AID))
        verseNumber = if (!cursor.isNull(cursor.getColumnIndexOrThrow(ParagraphMetadataConst.C_VERSE_NUMBER))) cursor.getString(cursor.getColumnIndexOrThrow(ParagraphMetadataConst.C_VERSE_NUMBER)) else null
        startIndex = cursor.getInt(cursor.getColumnIndexOrThrow(ParagraphMetadataConst.C_START_INDEX))
        endIndex = cursor.getInt(cursor.getColumnIndexOrThrow(ParagraphMetadataConst.C_END_INDEX))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
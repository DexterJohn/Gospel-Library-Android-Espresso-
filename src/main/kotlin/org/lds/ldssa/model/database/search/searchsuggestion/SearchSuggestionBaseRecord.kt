/*
 * SearchSuggestionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchsuggestion

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SearchSuggestionBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var contentItemId: Long = 0
    open var chapterNumber: String = ""
    open var verseNumber: String = ""
    open var type: org.lds.ldssa.model.database.types.SearchSuggestionType = org.lds.ldssa.model.database.types.SearchSuggestionType.UNKNOWN
    open var title: String = ""
    open var subTitle: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ""
    }

    override fun getPrimaryKeyId() : Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
    }

    override fun getAllColumns() : Array<String> {
        return SearchSuggestionConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SearchSuggestionConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SearchSuggestionConst.C_ID, id)
        values.put(SearchSuggestionConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(SearchSuggestionConst.C_CHAPTER_NUMBER, chapterNumber)
        values.put(SearchSuggestionConst.C_VERSE_NUMBER, verseNumber)
        values.put(SearchSuggestionConst.C_TYPE, type.ordinal.toLong())
        values.put(SearchSuggestionConst.C_TITLE, title)
        values.put(SearchSuggestionConst.C_SUB_TITLE, subTitle)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            contentItemId,
            chapterNumber,
            verseNumber,
            type.ordinal.toLong(),
            title,
            subTitle)
    }

    open fun copy() : SearchSuggestion {
        val copy = SearchSuggestion()
        copy.id = id
        copy.contentItemId = contentItemId
        copy.chapterNumber = chapterNumber
        copy.verseNumber = verseNumber
        copy.type = type
        copy.title = title
        copy.subTitle = subTitle
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, contentItemId)
        statement.bindString(3, chapterNumber)
        statement.bindString(4, verseNumber)
        statement.bindLong(5, type.ordinal.toLong())
        statement.bindString(6, title)
        statement.bindString(7, subTitle)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, contentItemId)
        statement.bindString(3, chapterNumber)
        statement.bindString(4, verseNumber)
        statement.bindLong(5, type.ordinal.toLong())
        statement.bindString(6, title)
        statement.bindString(7, subTitle)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(SearchSuggestionConst.C_ID)
        contentItemId = values.getAsLong(SearchSuggestionConst.C_CONTENT_ITEM_ID)
        chapterNumber = values.getAsString(SearchSuggestionConst.C_CHAPTER_NUMBER)
        verseNumber = values.getAsString(SearchSuggestionConst.C_VERSE_NUMBER)
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.SearchSuggestionType::class.java, values.getAsInteger(SearchSuggestionConst.C_TYPE), org.lds.ldssa.model.database.types.SearchSuggestionType.UNKNOWN)
        title = values.getAsString(SearchSuggestionConst.C_TITLE)
        subTitle = values.getAsString(SearchSuggestionConst.C_SUB_TITLE)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(SearchSuggestionConst.C_ID))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchSuggestionConst.C_CONTENT_ITEM_ID))
        chapterNumber = cursor.getString(cursor.getColumnIndexOrThrow(SearchSuggestionConst.C_CHAPTER_NUMBER))
        verseNumber = cursor.getString(cursor.getColumnIndexOrThrow(SearchSuggestionConst.C_VERSE_NUMBER))
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.SearchSuggestionType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(SearchSuggestionConst.C_TYPE)), org.lds.ldssa.model.database.types.SearchSuggestionType.UNKNOWN)
        title = cursor.getString(cursor.getColumnIndexOrThrow(SearchSuggestionConst.C_TITLE))
        subTitle = cursor.getString(cursor.getColumnIndexOrThrow(SearchSuggestionConst.C_SUB_TITLE))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
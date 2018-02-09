/*
 * SearchHistoryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchhistory

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SearchHistoryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var contentItemId: Long = 0
    open var chapterNumber: String = ""
    open var verseNumber: String = ""
    open var type: org.lds.ldssa.model.database.types.SearchSuggestionType = org.lds.ldssa.model.database.types.SearchSuggestionType.UNKNOWN
    open var title: String = ""
    open var subTitle: String = ""
    open var languageId: Long = 0
    open var lastUpdate: org.threeten.bp.LocalDateTime = org.threeten.bp.LocalDateTime.now()

    constructor() {
    }

    override fun getAllColumns() : Array<String> {
        return SearchHistoryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SearchHistoryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SearchHistoryConst.C_ID, id)
        values.put(SearchHistoryConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(SearchHistoryConst.C_CHAPTER_NUMBER, chapterNumber)
        values.put(SearchHistoryConst.C_VERSE_NUMBER, verseNumber)
        values.put(SearchHistoryConst.C_TYPE, type.ordinal.toLong())
        values.put(SearchHistoryConst.C_TITLE, title)
        values.put(SearchHistoryConst.C_SUB_TITLE, subTitle)
        values.put(SearchHistoryConst.C_LANGUAGE_ID, languageId)
        values.put(SearchHistoryConst.C_LAST_UPDATE, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastUpdate)!!)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            contentItemId,
            chapterNumber,
            verseNumber,
            type.ordinal.toLong(),
            title,
            subTitle,
            languageId,
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastUpdate)!!)
    }

    open fun copy() : SearchHistory {
        val copy = SearchHistory()
        copy.id = id
        copy.contentItemId = contentItemId
        copy.chapterNumber = chapterNumber
        copy.verseNumber = verseNumber
        copy.type = type
        copy.title = title
        copy.subTitle = subTitle
        copy.languageId = languageId
        copy.lastUpdate = lastUpdate
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
        statement.bindLong(8, languageId)
        statement.bindLong(9, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastUpdate)!!)
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
        statement.bindLong(8, languageId)
        statement.bindLong(9, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastUpdate)!!)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(SearchHistoryConst.C_ID)
        contentItemId = values.getAsLong(SearchHistoryConst.C_CONTENT_ITEM_ID)
        chapterNumber = values.getAsString(SearchHistoryConst.C_CHAPTER_NUMBER)
        verseNumber = values.getAsString(SearchHistoryConst.C_VERSE_NUMBER)
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.SearchSuggestionType::class.java, values.getAsInteger(SearchHistoryConst.C_TYPE), org.lds.ldssa.model.database.types.SearchSuggestionType.UNKNOWN)
        title = values.getAsString(SearchHistoryConst.C_TITLE)
        subTitle = values.getAsString(SearchHistoryConst.C_SUB_TITLE)
        languageId = values.getAsLong(SearchHistoryConst.C_LANGUAGE_ID)
        lastUpdate = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(SearchHistoryConst.C_LAST_UPDATE))!!
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(SearchHistoryConst.C_ID))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchHistoryConst.C_CONTENT_ITEM_ID))
        chapterNumber = cursor.getString(cursor.getColumnIndexOrThrow(SearchHistoryConst.C_CHAPTER_NUMBER))
        verseNumber = cursor.getString(cursor.getColumnIndexOrThrow(SearchHistoryConst.C_VERSE_NUMBER))
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.SearchSuggestionType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(SearchHistoryConst.C_TYPE)), org.lds.ldssa.model.database.types.SearchSuggestionType.UNKNOWN)
        title = cursor.getString(cursor.getColumnIndexOrThrow(SearchHistoryConst.C_TITLE))
        subTitle = cursor.getString(cursor.getColumnIndexOrThrow(SearchHistoryConst.C_SUB_TITLE))
        languageId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchHistoryConst.C_LANGUAGE_ID))
        lastUpdate = if (!cursor.isNull(cursor.getColumnIndexOrThrow(SearchHistoryConst.C_LAST_UPDATE))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(SearchHistoryConst.C_LAST_UPDATE)))!! else null!!
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }

    override fun getIdColumnName() : String {
        return "NO_PRIMARY_KEY"
    }

    override fun getPrimaryKeyId() : Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
        // NO_PRIMARY_KEY
    }


}
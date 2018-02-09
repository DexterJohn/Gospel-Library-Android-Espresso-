/*
 * SearchAllCountBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchallcount

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SearchAllCountBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var screenId: Long = 0
    open var type: org.lds.ldssa.search.SearchAllType = org.lds.ldssa.search.SearchAllType.COLLECTION
    open var title: String = ""
    open var subtitle: String = ""
    open var itemCount: Long = 0
    open var phraseCount: Long = 0
    open var keywordCount: Long = 0
    open var position: Int = 0

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
        return SearchAllCountConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SearchAllCountConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SearchAllCountConst.C_ID, id)
        values.put(SearchAllCountConst.C_SCREEN_ID, screenId)
        values.put(SearchAllCountConst.C_TYPE, type.ordinal.toLong())
        values.put(SearchAllCountConst.C_TITLE, title)
        values.put(SearchAllCountConst.C_SUBTITLE, subtitle)
        values.put(SearchAllCountConst.C_ITEM_COUNT, itemCount)
        values.put(SearchAllCountConst.C_PHRASE_COUNT, phraseCount)
        values.put(SearchAllCountConst.C_KEYWORD_COUNT, keywordCount)
        values.put(SearchAllCountConst.C_POSITION, position.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            screenId,
            type.ordinal.toLong(),
            title,
            subtitle,
            itemCount,
            phraseCount,
            keywordCount,
            position.toLong())
    }

    open fun copy() : SearchAllCount {
        val copy = SearchAllCount()
        copy.id = id
        copy.screenId = screenId
        copy.type = type
        copy.title = title
        copy.subtitle = subtitle
        copy.itemCount = itemCount
        copy.phraseCount = phraseCount
        copy.keywordCount = keywordCount
        copy.position = position
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, screenId)
        statement.bindLong(3, type.ordinal.toLong())
        statement.bindString(4, title)
        statement.bindString(5, subtitle)
        statement.bindLong(6, itemCount)
        statement.bindLong(7, phraseCount)
        statement.bindLong(8, keywordCount)
        statement.bindLong(9, position.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, screenId)
        statement.bindLong(3, type.ordinal.toLong())
        statement.bindString(4, title)
        statement.bindString(5, subtitle)
        statement.bindLong(6, itemCount)
        statement.bindLong(7, phraseCount)
        statement.bindLong(8, keywordCount)
        statement.bindLong(9, position.toLong())
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(SearchAllCountConst.C_ID)
        screenId = values.getAsLong(SearchAllCountConst.C_SCREEN_ID)
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchAllType::class.java, values.getAsInteger(SearchAllCountConst.C_TYPE), org.lds.ldssa.search.SearchAllType.COLLECTION)
        title = values.getAsString(SearchAllCountConst.C_TITLE)
        subtitle = values.getAsString(SearchAllCountConst.C_SUBTITLE)
        itemCount = values.getAsLong(SearchAllCountConst.C_ITEM_COUNT)
        phraseCount = values.getAsLong(SearchAllCountConst.C_PHRASE_COUNT)
        keywordCount = values.getAsLong(SearchAllCountConst.C_KEYWORD_COUNT)
        position = values.getAsInteger(SearchAllCountConst.C_POSITION)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(SearchAllCountConst.C_ID))
        screenId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchAllCountConst.C_SCREEN_ID))
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchAllType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(SearchAllCountConst.C_TYPE)), org.lds.ldssa.search.SearchAllType.COLLECTION)
        title = cursor.getString(cursor.getColumnIndexOrThrow(SearchAllCountConst.C_TITLE))
        subtitle = cursor.getString(cursor.getColumnIndexOrThrow(SearchAllCountConst.C_SUBTITLE))
        itemCount = cursor.getLong(cursor.getColumnIndexOrThrow(SearchAllCountConst.C_ITEM_COUNT))
        phraseCount = cursor.getLong(cursor.getColumnIndexOrThrow(SearchAllCountConst.C_PHRASE_COUNT))
        keywordCount = cursor.getLong(cursor.getColumnIndexOrThrow(SearchAllCountConst.C_KEYWORD_COUNT))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(SearchAllCountConst.C_POSITION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
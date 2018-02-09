/*
 * SubItemSearchQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.subitemsearchquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SubItemSearchQueryBaseRecord  : AndroidBaseRecord {

    open var subItemId: Long = 0
    open var title: String = ""
    open var snippet: String = ""
    open var matchInfo: ByteArray = byteArrayOf()
    open var position: Int = 0
    open var searchResultCountType: org.lds.ldssa.search.SearchResultCountType = org.lds.ldssa.search.SearchResultCountType.KEYWORD

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
        return SubItemSearchQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SubItemSearchQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SubItemSearchQueryConst.C_SUB_ITEM_ID, subItemId)
        values.put(SubItemSearchQueryConst.C_TITLE, title)
        values.put(SubItemSearchQueryConst.C_SNIPPET, snippet)
        values.put(SubItemSearchQueryConst.C_MATCH_INFO, matchInfo)
        values.put(SubItemSearchQueryConst.C_POSITION, position.toLong())
        values.put(SubItemSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE, searchResultCountType.ordinal.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            subItemId,
            title,
            snippet,
            matchInfo,
            position.toLong(),
            searchResultCountType.ordinal.toLong())
    }

    open fun copy() : SubItemSearchQuery {
        val copy = SubItemSearchQuery()
        copy.subItemId = subItemId
        copy.title = title
        copy.snippet = snippet
        copy.matchInfo = matchInfo
        copy.position = position
        copy.searchResultCountType = searchResultCountType
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, subItemId)
        statement.bindString(2, title)
        statement.bindString(3, snippet)
        statement.bindBlob(4, matchInfo)
        statement.bindLong(5, position.toLong())
        statement.bindLong(6, searchResultCountType.ordinal.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, subItemId)
        statement.bindString(2, title)
        statement.bindString(3, snippet)
        statement.bindBlob(4, matchInfo)
        statement.bindLong(5, position.toLong())
        statement.bindLong(6, searchResultCountType.ordinal.toLong())
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        subItemId = values.getAsLong(SubItemSearchQueryConst.C_SUB_ITEM_ID)
        title = values.getAsString(SubItemSearchQueryConst.C_TITLE)
        snippet = values.getAsString(SubItemSearchQueryConst.C_SNIPPET)
        matchInfo = values.getAsByteArray(SubItemSearchQueryConst.C_MATCH_INFO)
        position = values.getAsInteger(SubItemSearchQueryConst.C_POSITION)
        searchResultCountType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchResultCountType::class.java, values.getAsInteger(SubItemSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE), org.lds.ldssa.search.SearchResultCountType.KEYWORD)
    }

    override fun setContent(cursor: Cursor) {
        subItemId = cursor.getLong(cursor.getColumnIndexOrThrow(SubItemSearchQueryConst.C_SUB_ITEM_ID))
        title = cursor.getString(cursor.getColumnIndexOrThrow(SubItemSearchQueryConst.C_TITLE))
        snippet = cursor.getString(cursor.getColumnIndexOrThrow(SubItemSearchQueryConst.C_SNIPPET))
        matchInfo = cursor.getBlob(cursor.getColumnIndexOrThrow(SubItemSearchQueryConst.C_MATCH_INFO))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(SubItemSearchQueryConst.C_POSITION))
        searchResultCountType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchResultCountType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(SubItemSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE)), org.lds.ldssa.search.SearchResultCountType.KEYWORD)
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
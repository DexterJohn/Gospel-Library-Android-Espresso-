/*
 * SearchGotoQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.searchgotoquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SearchGotoQueryBaseRecord  : AndroidBaseRecord {

    open var languageId: Long = 0
    open var contentItemId: Long = 0
    open var itemPosition: Int = 0
    open var navSectionId: Int = 0
    open var navCollectionId: Long? = null
    open var navPosition: Int = 0
    open var subitemId: Long? = null
    open var title: String = ""
    open var shortTitle: String? = null
    open var subTitle: String? = null
    open var chapterCount: Int = 0
    open var hasVerses: Boolean = false

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
        return SearchGotoQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SearchGotoQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SearchGotoQueryConst.C_LANGUAGE_ID, languageId)
        values.put(SearchGotoQueryConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(SearchGotoQueryConst.C_ITEM_POSITION, itemPosition.toLong())
        values.put(SearchGotoQueryConst.C_NAV_SECTION_ID, navSectionId.toLong())
        values.put(SearchGotoQueryConst.C_NAV_COLLECTION_ID, navCollectionId)
        values.put(SearchGotoQueryConst.C_NAV_POSITION, navPosition.toLong())
        values.put(SearchGotoQueryConst.C_SUBITEM_ID, subitemId)
        values.put(SearchGotoQueryConst.C_TITLE, title)
        values.put(SearchGotoQueryConst.C_SHORT_TITLE, shortTitle)
        values.put(SearchGotoQueryConst.C_SUB_TITLE, subTitle)
        values.put(SearchGotoQueryConst.C_CHAPTER_COUNT, chapterCount.toLong())
        values.put(SearchGotoQueryConst.C_HAS_VERSES, if (hasVerses) 1L else 0L)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            languageId,
            contentItemId,
            itemPosition.toLong(),
            navSectionId.toLong(),
            navCollectionId,
            navPosition.toLong(),
            subitemId,
            title,
            shortTitle,
            subTitle,
            chapterCount.toLong(),
            if (hasVerses) 1L else 0L)
    }

    open fun copy() : SearchGotoQuery {
        val copy = SearchGotoQuery()
        copy.languageId = languageId
        copy.contentItemId = contentItemId
        copy.itemPosition = itemPosition
        copy.navSectionId = navSectionId
        copy.navCollectionId = navCollectionId
        copy.navPosition = navPosition
        copy.subitemId = subitemId
        copy.title = title
        copy.shortTitle = shortTitle
        copy.subTitle = subTitle
        copy.chapterCount = chapterCount
        copy.hasVerses = hasVerses
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, languageId)
        statement.bindLong(2, contentItemId)
        statement.bindLong(3, itemPosition.toLong())
        statement.bindLong(4, navSectionId.toLong())
        if (navCollectionId != null) {
            statement.bindLong(5, navCollectionId!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindLong(6, navPosition.toLong())
        if (subitemId != null) {
            statement.bindLong(7, subitemId!!)
        } else {
            statement.bindNull(7)
        }
        statement.bindString(8, title)
        if (shortTitle != null) {
            statement.bindString(9, shortTitle!!)
        } else {
            statement.bindNull(9)
        }
        if (subTitle != null) {
            statement.bindString(10, subTitle!!)
        } else {
            statement.bindNull(10)
        }
        statement.bindLong(11, chapterCount.toLong())
        statement.bindLong(12, if (hasVerses) 1L else 0L)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, languageId)
        statement.bindLong(2, contentItemId)
        statement.bindLong(3, itemPosition.toLong())
        statement.bindLong(4, navSectionId.toLong())
        if (navCollectionId != null) {
            statement.bindLong(5, navCollectionId!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindLong(6, navPosition.toLong())
        if (subitemId != null) {
            statement.bindLong(7, subitemId!!)
        } else {
            statement.bindNull(7)
        }
        statement.bindString(8, title)
        if (shortTitle != null) {
            statement.bindString(9, shortTitle!!)
        } else {
            statement.bindNull(9)
        }
        if (subTitle != null) {
            statement.bindString(10, subTitle!!)
        } else {
            statement.bindNull(10)
        }
        statement.bindLong(11, chapterCount.toLong())
        statement.bindLong(12, if (hasVerses) 1L else 0L)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        languageId = values.getAsLong(SearchGotoQueryConst.C_LANGUAGE_ID)
        contentItemId = values.getAsLong(SearchGotoQueryConst.C_CONTENT_ITEM_ID)
        itemPosition = values.getAsInteger(SearchGotoQueryConst.C_ITEM_POSITION)
        navSectionId = values.getAsInteger(SearchGotoQueryConst.C_NAV_SECTION_ID)
        navCollectionId = values.getAsLong(SearchGotoQueryConst.C_NAV_COLLECTION_ID)
        navPosition = values.getAsInteger(SearchGotoQueryConst.C_NAV_POSITION)
        subitemId = values.getAsLong(SearchGotoQueryConst.C_SUBITEM_ID)
        title = values.getAsString(SearchGotoQueryConst.C_TITLE)
        shortTitle = values.getAsString(SearchGotoQueryConst.C_SHORT_TITLE)
        subTitle = values.getAsString(SearchGotoQueryConst.C_SUB_TITLE)
        chapterCount = values.getAsInteger(SearchGotoQueryConst.C_CHAPTER_COUNT)
        hasVerses = values.getAsBoolean(SearchGotoQueryConst.C_HAS_VERSES)
    }

    override fun setContent(cursor: Cursor) {
        languageId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_LANGUAGE_ID))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_CONTENT_ITEM_ID))
        itemPosition = cursor.getInt(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_ITEM_POSITION))
        navSectionId = cursor.getInt(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_NAV_SECTION_ID))
        navCollectionId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_NAV_COLLECTION_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_NAV_COLLECTION_ID)) else null
        navPosition = cursor.getInt(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_NAV_POSITION))
        subitemId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_SUBITEM_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_SUBITEM_ID)) else null
        title = cursor.getString(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_TITLE))
        shortTitle = if (!cursor.isNull(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_SHORT_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_SHORT_TITLE)) else null
        subTitle = if (!cursor.isNull(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_SUB_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_SUB_TITLE)) else null
        chapterCount = cursor.getInt(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_CHAPTER_COUNT))
        hasVerses = cursor.getInt(cursor.getColumnIndexOrThrow(SearchGotoQueryConst.C_HAS_VERSES)) != 0
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
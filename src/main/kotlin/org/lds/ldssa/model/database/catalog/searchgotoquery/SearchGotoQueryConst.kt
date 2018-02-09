/*
 * SearchGotoQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.searchgotoquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SearchGotoQueryConst  {

    const val DATABASE = "catalog"
    const val C_LANGUAGE_ID = "language_id"
    const val FULL_C_LANGUAGE_ID = "search_goto_query.language_id"
    const val C_CONTENT_ITEM_ID = "item_id"
    const val FULL_C_CONTENT_ITEM_ID = "search_goto_query.item_id"
    const val C_ITEM_POSITION = "item_position"
    const val FULL_C_ITEM_POSITION = "search_goto_query.item_position"
    const val C_NAV_SECTION_ID = "nav_section_id"
    const val FULL_C_NAV_SECTION_ID = "search_goto_query.nav_section_id"
    const val C_NAV_COLLECTION_ID = "nav_collection_id"
    const val FULL_C_NAV_COLLECTION_ID = "search_goto_query.nav_collection_id"
    const val C_NAV_POSITION = "nav_position"
    const val FULL_C_NAV_POSITION = "search_goto_query.nav_position"
    const val C_SUBITEM_ID = "subitem_id"
    const val FULL_C_SUBITEM_ID = "search_goto_query.subitem_id"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "search_goto_query.title"
    const val C_SHORT_TITLE = "short_title"
    const val FULL_C_SHORT_TITLE = "search_goto_query.short_title"
    const val C_SUB_TITLE = "sub_title"
    const val FULL_C_SUB_TITLE = "search_goto_query.sub_title"
    const val C_CHAPTER_COUNT = "chapter_count"
    const val FULL_C_CHAPTER_COUNT = "search_goto_query.chapter_count"
    const val C_HAS_VERSES = "has_verses"
    const val FULL_C_HAS_VERSES = "search_goto_query.has_verses"
    val ALL_COLUMNS = arrayOf(
        C_LANGUAGE_ID,
        C_CONTENT_ITEM_ID,
        C_ITEM_POSITION,
        C_NAV_SECTION_ID,
        C_NAV_COLLECTION_ID,
        C_NAV_POSITION,
        C_SUBITEM_ID,
        C_TITLE,
        C_SHORT_TITLE,
        C_SUB_TITLE,
        C_CHAPTER_COUNT,
        C_HAS_VERSES)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_LANGUAGE_ID,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_ITEM_POSITION,
        FULL_C_NAV_SECTION_ID,
        FULL_C_NAV_COLLECTION_ID,
        FULL_C_NAV_POSITION,
        FULL_C_SUBITEM_ID,
        FULL_C_TITLE,
        FULL_C_SHORT_TITLE,
        FULL_C_SUB_TITLE,
        FULL_C_CHAPTER_COUNT,
        FULL_C_HAS_VERSES)

    fun getLanguageId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LANGUAGE_ID))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }

    fun getItemPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_ITEM_POSITION))
    }

    fun getNavSectionId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_NAV_SECTION_ID))
    }

    fun getNavCollectionId(cursor: Cursor) : Long? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_NAV_COLLECTION_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(C_NAV_COLLECTION_ID)) else null
    }

    fun getNavPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_NAV_POSITION))
    }

    fun getSubitemId(cursor: Cursor) : Long? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_SUBITEM_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(C_SUBITEM_ID)) else null
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getShortTitle(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_SHORT_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(C_SHORT_TITLE)) else null
    }

    fun getSubTitle(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_SUB_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(C_SUB_TITLE)) else null
    }

    fun getChapterCount(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_CHAPTER_COUNT))
    }

    fun isHasVerses(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_HAS_VERSES)) != 0
    }


}
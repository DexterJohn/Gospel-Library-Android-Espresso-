/*
 * NavigationTrailQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.navigationtrailquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object NavigationTrailQueryConst  {

    const val DATABASE = "catalog"
    const val C_ID = "_id"
    const val FULL_C_ID = "navigation_trail_query._id"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "navigation_trail_query.content_item_id"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "navigation_trail_query.title"
    const val C_TYPE = "type"
    const val FULL_C_TYPE = "navigation_trail_query.type"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_CONTENT_ITEM_ID,
        C_TITLE,
        C_TYPE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_TITLE,
        FULL_C_TYPE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getType(cursor: Cursor) : org.lds.ldssa.model.database.types.ScreenSourceType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ScreenSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_TYPE)), org.lds.ldssa.model.database.types.ScreenSourceType.CATALOG_DIRECTORY)
    }


}
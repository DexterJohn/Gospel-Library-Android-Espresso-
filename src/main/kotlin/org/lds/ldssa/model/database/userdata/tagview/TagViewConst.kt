/*
 * TagViewBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.tagview

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object TagViewConst  {

    const val DATABASE = "userdata"
    const val TABLE = "tag_view"
    const val FULL_TABLE = "userdata.tag_view"
    const val C_NAME = "name"
    const val FULL_C_NAME = "tag_view.name"
    const val C_COUNT = "count"
    const val FULL_C_COUNT = "tag_view.count"
    const val C_LAST_MODIFIED = "last_modified"
    const val FULL_C_LAST_MODIFIED = "tag_view.last_modified"
    const val C_SELECTED = "selected"
    const val FULL_C_SELECTED = "tag_view.selected"
    val ALL_COLUMNS = arrayOf(
        C_NAME,
        C_COUNT,
        C_LAST_MODIFIED,
        C_SELECTED)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_NAME,
        FULL_C_COUNT,
        FULL_C_LAST_MODIFIED,
        FULL_C_SELECTED)

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }

    fun getCount(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_COUNT))
    }

    fun getLastModified(cursor: Cursor) : org.threeten.bp.LocalDateTime {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED)))!! else null!!
    }

    fun isSelected(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SELECTED)) != 0
    }


}
/*
 * TipQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.tips.tipquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object TipQueryConst  {

    const val DATABASE = "tips"
    const val C_ID = "_id"
    const val FULL_C_ID = "tip_query._id"
    const val C_ISO6393 = "iso639_3"
    const val FULL_C_ISO6393 = "tip_query.iso639_3"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "tip_query.title"
    const val C_VERSION_ID = "version_id"
    const val FULL_C_VERSION_ID = "tip_query.version_id"
    const val C_VERSION_NAME = "version_name"
    const val FULL_C_VERSION_NAME = "tip_query.version_name"
    const val C_VIEWED = "viewed"
    const val FULL_C_VIEWED = "tip_query.viewed"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_ISO6393,
        C_TITLE,
        C_VERSION_ID,
        C_VERSION_NAME,
        C_VIEWED)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_ISO6393,
        FULL_C_TITLE,
        FULL_C_VERSION_ID,
        FULL_C_VERSION_NAME,
        FULL_C_VIEWED)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getIso6393(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_ISO6393))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getVersionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_VERSION_ID))
    }

    fun getVersionName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_VERSION_NAME))
    }

    fun isViewed(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_VIEWED)) != 0
    }


}
/*
 * LibraryCollectionQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.librarycollectionquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object LibraryCollectionQueryConst  {

    const val DATABASE = "catalog"
    const val C_ID = "_id"
    const val FULL_C_ID = "library_collection_query._id"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "library_collection_query.title"
    const val C_ROOT = "root"
    const val FULL_C_ROOT = "library_collection_query.root"
    const val C_PARENT_TITLE = "parent_title"
    const val FULL_C_PARENT_TITLE = "library_collection_query.parent_title"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_TITLE,
        C_ROOT,
        C_PARENT_TITLE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_TITLE,
        FULL_C_ROOT,
        FULL_C_PARENT_TITLE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun isRoot(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_ROOT)) != 0
    }

    fun getParentTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_PARENT_TITLE))
    }


}
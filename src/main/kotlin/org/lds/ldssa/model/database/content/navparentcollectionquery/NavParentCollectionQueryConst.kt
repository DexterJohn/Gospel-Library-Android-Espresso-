/*
 * NavParentCollectionQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.navparentcollectionquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object NavParentCollectionQueryConst  {

    const val DATABASE = "content"
    const val C_ID = "_id"
    const val FULL_C_ID = "nav_parent_collection_query._id"
    const val C_NAV_SECTION_ID = "nav_section_id"
    const val FULL_C_NAV_SECTION_ID = "nav_parent_collection_query.nav_section_id"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAV_SECTION_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAV_SECTION_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getNavSectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_NAV_SECTION_ID))
    }


}
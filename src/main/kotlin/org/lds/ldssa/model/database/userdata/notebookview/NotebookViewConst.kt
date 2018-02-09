/*
 * NotebookViewBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.notebookview

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object NotebookViewConst  {

    const val DATABASE = "userdata"
    const val TABLE = "notebook_view"
    const val FULL_TABLE = "userdata.notebook_view"
    const val C_ID = "_id"
    const val FULL_C_ID = "notebook_view._id"
    const val C_UNIQUE_ID = "unique_id"
    const val FULL_C_UNIQUE_ID = "notebook_view.unique_id"
    const val C_NAME = "name"
    const val FULL_C_NAME = "notebook_view.name"
    const val C_COUNT = "count"
    const val FULL_C_COUNT = "notebook_view.count"
    const val C_STATUS = "status"
    const val FULL_C_STATUS = "notebook_view.status"
    const val C_LAST_MODIFIED = "last_modified"
    const val FULL_C_LAST_MODIFIED = "notebook_view.last_modified"
    const val C_SELECTED = "selected"
    const val FULL_C_SELECTED = "notebook_view.selected"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_UNIQUE_ID,
        C_NAME,
        C_COUNT,
        C_STATUS,
        C_LAST_MODIFIED,
        C_SELECTED)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_UNIQUE_ID,
        FULL_C_NAME,
        FULL_C_COUNT,
        FULL_C_STATUS,
        FULL_C_LAST_MODIFIED,
        FULL_C_SELECTED)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getUniqueId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_UNIQUE_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }

    fun getCount(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_COUNT))
    }

    fun getStatus(cursor: Cursor) : org.lds.ldssa.model.database.types.AnnotationStatusType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.AnnotationStatusType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_STATUS)), org.lds.ldssa.model.database.types.AnnotationStatusType.ACTIVE)
    }

    fun getLastModified(cursor: Cursor) : org.threeten.bp.LocalDateTime {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED)))!! else null!!
    }

    fun isSelected(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SELECTED)) != 0
    }


}
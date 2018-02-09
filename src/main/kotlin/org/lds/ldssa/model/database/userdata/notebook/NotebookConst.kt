/*
 * NotebookBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.notebook

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object NotebookConst  {

    const val DATABASE = "userdata"
    const val TABLE = "notebook"
    const val FULL_TABLE = "userdata.notebook"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "notebook._id"
    const val C_UNIQUE_ID = "unique_id"
    const val FULL_C_UNIQUE_ID = "notebook.unique_id"
    const val C_NAME = "name"
    const val FULL_C_NAME = "notebook.name"
    const val C_DESCRIPTION = "description"
    const val FULL_C_DESCRIPTION = "notebook.description"
    const val C_STATUS = "status"
    const val FULL_C_STATUS = "notebook.status"
    const val C_LAST_MODIFIED = "last_modified"
    const val FULL_C_LAST_MODIFIED = "notebook.last_modified"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS notebook (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "unique_id TEXT NOT NULL," + 
        "name TEXT NOT NULL," + 
        "description TEXT NOT NULL," + 
        "status INTEGER NOT NULL," + 
        "last_modified INTEGER NOT NULL," + 
        "UNIQUE(unique_id) ON CONFLICT REPLACE" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS notebook;"
    const val INSERT_STATEMENT = "INSERT INTO notebook (unique_id,name,description,status,last_modified) VALUES (?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE notebook SET unique_id=?, name=?, description=?, status=?, last_modified=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_UNIQUE_ID,
        C_NAME,
        C_DESCRIPTION,
        C_STATUS,
        C_LAST_MODIFIED)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_UNIQUE_ID,
        FULL_C_NAME,
        FULL_C_DESCRIPTION,
        FULL_C_STATUS,
        FULL_C_LAST_MODIFIED)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getUniqueId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_UNIQUE_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }

    fun getDescription(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_DESCRIPTION))
    }

    fun getStatus(cursor: Cursor) : org.lds.ldssa.model.database.types.AnnotationStatusType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.AnnotationStatusType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_STATUS)), org.lds.ldssa.model.database.types.AnnotationStatusType.ACTIVE)
    }

    fun getLastModified(cursor: Cursor) : org.threeten.bp.LocalDateTime {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED)))!! else null!!
    }


}
/*
 * TipViewedBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.tipviewed

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object TipViewedConst  {

    const val DATABASE = "gl"
    const val TABLE = "tip_viewed"
    const val FULL_TABLE = "gl.tip_viewed"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "tip_viewed._id"
    const val C_TIP_ID = "tip_id"
    const val FULL_C_TIP_ID = "tip_viewed.tip_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS tip_viewed (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "tip_id INTEGER NOT NULL," + 
        "UNIQUE(tip_id) ON CONFLICT IGNORE" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS tip_viewed;"
    const val INSERT_STATEMENT = "INSERT INTO tip_viewed (tip_id) VALUES (?)"
    const val UPDATE_STATEMENT = "UPDATE tip_viewed SET tip_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_TIP_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_TIP_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getTipId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_TIP_ID))
    }


}
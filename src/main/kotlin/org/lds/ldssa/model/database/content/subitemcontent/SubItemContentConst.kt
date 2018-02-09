/*
 * SubItemContentBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.subitemcontent

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SubItemContentConst  {

    const val DATABASE = "content"
    const val TABLE = "subitem_content"
    const val FULL_TABLE = "content.subitem_content"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "subitem_content._id"
    const val C_SUBITEM_ID = "subitem_id"
    const val FULL_C_SUBITEM_ID = "subitem_content.subitem_id"
    const val C_CONTENT_HTML = "content_html"
    const val FULL_C_CONTENT_HTML = "subitem_content.content_html"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS subitem_content (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "subitem_id INTEGER NOT NULL," + 
        "content_html TEXT NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS subitem_content;"
    const val INSERT_STATEMENT = "INSERT INTO subitem_content (subitem_id,content_html) VALUES (?,?)"
    const val UPDATE_STATEMENT = "UPDATE subitem_content SET subitem_id=?, content_html=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_SUBITEM_ID,
        C_CONTENT_HTML)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_SUBITEM_ID,
        FULL_C_CONTENT_HTML)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getSubitemId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SUBITEM_ID))
    }

    fun getContentHtml(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_CONTENT_HTML))
    }


}
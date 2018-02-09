/*
 * TabHistoryItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.tabhistoryitem

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object TabHistoryItemConst  {

    const val DATABASE = "gl"
    const val TABLE = "tab_history_item"
    const val FULL_TABLE = "gl.tab_history_item"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "tab_history_item._id"
    const val C_TAB_ID = "tab_id"
    const val FULL_C_TAB_ID = "tab_history_item.tab_id"
    const val C_HISTORY_POSITION = "history_position"
    const val FULL_C_HISTORY_POSITION = "tab_history_item.history_position"
    const val C_SOURCE_TYPE = "source_type"
    const val FULL_C_SOURCE_TYPE = "tab_history_item.source_type"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "tab_history_item.title"
    const val C_DESCRIPTION = "description"
    const val FULL_C_DESCRIPTION = "tab_history_item.description"
    const val C_EXTRAS_JSON = "extras_json"
    const val FULL_C_EXTRAS_JSON = "tab_history_item.extras_json"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS tab_history_item (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "tab_id INTEGER NOT NULL," + 
        "history_position INTEGER DEFAULT 0 NOT NULL," + 
        "source_type INTEGER NOT NULL," + 
        "title TEXT NOT NULL," + 
        "description TEXT NOT NULL," + 
        "extras_json TEXT NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS tab_history_item;"
    const val INSERT_STATEMENT = "INSERT INTO tab_history_item (tab_id,history_position,source_type,title,description,extras_json) VALUES (?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE tab_history_item SET tab_id=?, history_position=?, source_type=?, title=?, description=?, extras_json=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_TAB_ID,
        C_HISTORY_POSITION,
        C_SOURCE_TYPE,
        C_TITLE,
        C_DESCRIPTION,
        C_EXTRAS_JSON)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_TAB_ID,
        FULL_C_HISTORY_POSITION,
        FULL_C_SOURCE_TYPE,
        FULL_C_TITLE,
        FULL_C_DESCRIPTION,
        FULL_C_EXTRAS_JSON)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getTabId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_TAB_ID))
    }

    fun getHistoryPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_HISTORY_POSITION))
    }

    fun getSourceType(cursor: Cursor) : org.lds.ldssa.model.database.types.ScreenSourceType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ScreenSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_SOURCE_TYPE)), org.lds.ldssa.model.database.types.ScreenSourceType.UNKNOWN)
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getDescription(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_DESCRIPTION))
    }

    fun getExtrasJson(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_EXTRAS_JSON))
    }


}
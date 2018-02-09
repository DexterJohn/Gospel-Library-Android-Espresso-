/*
 * CustomCollectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.customcollection

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object CustomCollectionConst  {

    const val DATABASE = "userdata"
    const val TABLE = "custom_collection"
    const val FULL_TABLE = "userdata.custom_collection"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "custom_collection._id"
    const val C_UNIQUE_ID = "unique_id"
    const val FULL_C_UNIQUE_ID = "custom_collection.unique_id"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "custom_collection.title"
    const val C_ORDER_POSITION = "order_position"
    const val FULL_C_ORDER_POSITION = "custom_collection.order_position"
    const val C_CREATED = "created"
    const val FULL_C_CREATED = "custom_collection.created"
    const val C_LAST_MODIFIED = "last_modified"
    const val FULL_C_LAST_MODIFIED = "custom_collection.last_modified"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS custom_collection (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "unique_id TEXT NOT NULL," + 
        "title TEXT NOT NULL," + 
        "order_position INTEGER NOT NULL," + 
        "created INTEGER NOT NULL," + 
        "last_modified INTEGER NOT NULL" + 
        ");" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS custom_collectionunique_id_IDX ON custom_collection (unique_id);" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS custom_collection;"
    const val INSERT_STATEMENT = "INSERT INTO custom_collection (unique_id,title,order_position,created,last_modified) VALUES (?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE custom_collection SET unique_id=?, title=?, order_position=?, created=?, last_modified=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_UNIQUE_ID,
        C_TITLE,
        C_ORDER_POSITION,
        C_CREATED,
        C_LAST_MODIFIED)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_UNIQUE_ID,
        FULL_C_TITLE,
        FULL_C_ORDER_POSITION,
        FULL_C_CREATED,
        FULL_C_LAST_MODIFIED)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getUniqueId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_UNIQUE_ID))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getOrderPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_ORDER_POSITION))
    }

    fun getCreated(cursor: Cursor) : org.threeten.bp.LocalDateTime {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_CREATED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_CREATED)))!! else null!!
    }

    fun getLastModified(cursor: Cursor) : org.threeten.bp.LocalDateTime {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED)))!! else null!!
    }


}
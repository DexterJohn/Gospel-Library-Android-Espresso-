/*
 * SubitemTopicBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.subitemtopic

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SubitemTopicConst  {

    const val DATABASE = "content"
    const val TABLE = "subitem_topic"
    const val FULL_TABLE = "content.subitem_topic"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "subitem_topic._id"
    const val C_SUBITEM_ID = "subitem_id"
    const val FULL_C_SUBITEM_ID = "subitem_topic.subitem_id"
    const val C_TOPIC_ID = "topic_id"
    const val FULL_C_TOPIC_ID = "subitem_topic.topic_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS subitem_topic (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "subitem_id INTEGER NOT NULL," + 
        "topic_id INTEGER NOT NULL," + 
        "UNIQUE(subitem_id, topic_id)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS subitem_topic;"
    const val INSERT_STATEMENT = "INSERT INTO subitem_topic (subitem_id,topic_id) VALUES (?,?)"
    const val UPDATE_STATEMENT = "UPDATE subitem_topic SET subitem_id=?, topic_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_SUBITEM_ID,
        C_TOPIC_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_SUBITEM_ID,
        FULL_C_TOPIC_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getSubitemId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SUBITEM_ID))
    }

    fun getTopicId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_TOPIC_ID))
    }


}
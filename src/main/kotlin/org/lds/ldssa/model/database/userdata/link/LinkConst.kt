/*
 * LinkBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.link

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object LinkConst  {

    const val DATABASE = "userdata"
    const val TABLE = "link"
    const val FULL_TABLE = "userdata.link"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "link._id"
    const val C_ANNOTATION_ID = "annotation_id"
    const val FULL_C_ANNOTATION_ID = "link.annotation_id"
    const val C_NAME = "name"
    const val FULL_C_NAME = "link.name"
    const val C_DOC_ID = "doc_id"
    const val FULL_C_DOC_ID = "link.doc_id"
    const val C_PARAGRAPH_AID = "paragraph_aid"
    const val FULL_C_PARAGRAPH_AID = "link.paragraph_aid"
    const val C_CONTENT_VERSION = "content_version"
    const val FULL_C_CONTENT_VERSION = "link.content_version"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS link (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "annotation_id INTEGER NOT NULL," + 
        "name TEXT NOT NULL," + 
        "doc_id TEXT," + 
        "paragraph_aid TEXT," + 
        "content_version INTEGER NOT NULL" + 
        ");" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS linkannotation_id_IDX ON link (annotation_id);" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS link;"
    const val INSERT_STATEMENT = "INSERT INTO link (annotation_id,name,doc_id,paragraph_aid,content_version) VALUES (?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE link SET annotation_id=?, name=?, doc_id=?, paragraph_aid=?, content_version=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_ANNOTATION_ID,
        C_NAME,
        C_DOC_ID,
        C_PARAGRAPH_AID,
        C_CONTENT_VERSION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_ANNOTATION_ID,
        FULL_C_NAME,
        FULL_C_DOC_ID,
        FULL_C_PARAGRAPH_AID,
        FULL_C_CONTENT_VERSION)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getAnnotationId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ANNOTATION_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }

    fun getDocId(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_DOC_ID))) cursor.getString(cursor.getColumnIndexOrThrow(C_DOC_ID)) else null
    }

    fun getParagraphAid(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_PARAGRAPH_AID))) cursor.getString(cursor.getColumnIndexOrThrow(C_PARAGRAPH_AID)) else null
    }

    fun getContentVersion(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_CONTENT_VERSION))
    }


}
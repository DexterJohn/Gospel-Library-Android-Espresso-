/*
 * SubItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.subitem

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SubItemConst  {

    const val DATABASE = "content"
    const val TABLE = "subitem"
    const val FULL_TABLE = "content.subitem"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "subitem._id"
    const val C_URI = "uri"
    const val FULL_C_URI = "subitem.uri"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "subitem.position"
    const val C_TITLE_HTML = "title_html"
    const val FULL_C_TITLE_HTML = "subitem.title_html"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "subitem.title"
    const val C_WEB_URL = "web_url"
    const val FULL_C_WEB_URL = "subitem.web_url"
    const val C_DOC_ID = "doc_id"
    const val FULL_C_DOC_ID = "subitem.doc_id"
    const val C_DOC_VERSION = "doc_version"
    const val FULL_C_DOC_VERSION = "subitem.doc_version"
    const val C_CONTENT_TYPE = "content_type"
    const val FULL_C_CONTENT_TYPE = "subitem.content_type"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS subitem (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "uri TEXT NOT NULL," + 
        "position INTEGER NOT NULL," + 
        "title_html TEXT NOT NULL," + 
        "title TEXT NOT NULL," + 
        "web_url TEXT NOT NULL," + 
        "doc_id TEXT NOT NULL," + 
        "doc_version INTEGER NOT NULL," + 
        "content_type INTEGER NOT NULL," + 
        "UNIQUE(position)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS subitem;"
    const val INSERT_STATEMENT = "INSERT INTO subitem (uri,position,title_html,title,web_url,doc_id,doc_version,content_type) VALUES (?,?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE subitem SET uri=?, position=?, title_html=?, title=?, web_url=?, doc_id=?, doc_version=?, content_type=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_URI,
        C_POSITION,
        C_TITLE_HTML,
        C_TITLE,
        C_WEB_URL,
        C_DOC_ID,
        C_DOC_VERSION,
        C_CONTENT_TYPE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_URI,
        FULL_C_POSITION,
        FULL_C_TITLE_HTML,
        FULL_C_TITLE,
        FULL_C_WEB_URL,
        FULL_C_DOC_ID,
        FULL_C_DOC_VERSION,
        FULL_C_CONTENT_TYPE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getUri(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_URI))
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }

    fun getTitleHtml(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE_HTML))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getWebUrl(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_WEB_URL))
    }

    fun getDocId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_DOC_ID))
    }

    fun getDocVersion(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_DOC_VERSION))
    }

    fun getContentType(cursor: Cursor) : org.lds.ldssa.model.database.types.SubItemContentType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.SubItemContentType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_CONTENT_TYPE)), org.lds.ldssa.model.database.types.SubItemContentType.UNKNOWN)
    }


}
/*
 * NavCollectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.navcollection

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object NavCollectionConst  {

    const val DATABASE = "content"
    const val TABLE = "nav_collection"
    const val FULL_TABLE = "content.nav_collection"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "nav_collection._id"
    const val C_NAV_SECTION_ID = "nav_section_id"
    const val FULL_C_NAV_SECTION_ID = "nav_collection.nav_section_id"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "nav_collection.position"
    const val C_IMAGE_RENDITIONS = "image_renditions"
    const val FULL_C_IMAGE_RENDITIONS = "nav_collection.image_renditions"
    const val C_TITLE_HTML = "title_html"
    const val FULL_C_TITLE_HTML = "nav_collection.title_html"
    const val C_SUBTITLE = "subtitle"
    const val FULL_C_SUBTITLE = "nav_collection.subtitle"
    const val C_URI = "uri"
    const val FULL_C_URI = "nav_collection.uri"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS nav_collection (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "nav_section_id INTEGER," + 
        "position INTEGER NOT NULL," + 
        "image_renditions TEXT," + 
        "title_html TEXT NOT NULL," + 
        "subtitle TEXT," + 
        "uri TEXT NOT NULL," + 
        "UNIQUE(uri)," + 
        "UNIQUE(nav_section_id, position)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS nav_collection;"
    const val INSERT_STATEMENT = "INSERT INTO nav_collection (nav_section_id,position,image_renditions,title_html,subtitle,uri) VALUES (?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE nav_collection SET nav_section_id=?, position=?, image_renditions=?, title_html=?, subtitle=?, uri=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAV_SECTION_ID,
        C_POSITION,
        C_IMAGE_RENDITIONS,
        C_TITLE_HTML,
        C_SUBTITLE,
        C_URI)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAV_SECTION_ID,
        FULL_C_POSITION,
        FULL_C_IMAGE_RENDITIONS,
        FULL_C_TITLE_HTML,
        FULL_C_SUBTITLE,
        FULL_C_URI)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getNavSectionId(cursor: Cursor) : Long? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_NAV_SECTION_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(C_NAV_SECTION_ID)) else null
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }

    fun getImageRenditions(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_IMAGE_RENDITIONS))) cursor.getString(cursor.getColumnIndexOrThrow(C_IMAGE_RENDITIONS)) else null
    }

    fun getTitleHtml(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE_HTML))
    }

    fun getSubtitle(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_SUBTITLE))) cursor.getString(cursor.getColumnIndexOrThrow(C_SUBTITLE)) else null
    }

    fun getUri(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_URI))
    }


}
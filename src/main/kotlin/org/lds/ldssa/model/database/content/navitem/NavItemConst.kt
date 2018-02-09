/*
 * NavItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.navitem

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object NavItemConst  {

    const val DATABASE = "content"
    const val TABLE = "nav_item"
    const val FULL_TABLE = "content.nav_item"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "nav_item._id"
    const val C_NAV_SECTION_ID = "nav_section_id"
    const val FULL_C_NAV_SECTION_ID = "nav_item.nav_section_id"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "nav_item.position"
    const val C_IMAGE_RENDITIONS = "image_renditions"
    const val FULL_C_IMAGE_RENDITIONS = "nav_item.image_renditions"
    const val C_TITLE_HTML = "title_html"
    const val FULL_C_TITLE_HTML = "nav_item.title_html"
    const val C_SUBTITLE = "subtitle"
    const val FULL_C_SUBTITLE = "nav_item.subtitle"
    const val C_PREVIEW = "preview"
    const val FULL_C_PREVIEW = "nav_item.preview"
    const val C_URI = "uri"
    const val FULL_C_URI = "nav_item.uri"
    const val C_SUBITEM_ID = "subitem_id"
    const val FULL_C_SUBITEM_ID = "nav_item.subitem_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS nav_item (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "nav_section_id INTEGER NOT NULL," + 
        "position INTEGER NOT NULL," + 
        "image_renditions TEXT," + 
        "title_html TEXT NOT NULL," + 
        "subtitle TEXT," + 
        "preview TEXT," + 
        "uri TEXT NOT NULL," + 
        "subitem_id INTEGER NOT NULL," + 
        "UNIQUE(uri)," + 
        "UNIQUE(nav_section_id, position)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS nav_item;"
    const val INSERT_STATEMENT = "INSERT INTO nav_item (nav_section_id,position,image_renditions,title_html,subtitle,preview,uri,subitem_id) VALUES (?,?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE nav_item SET nav_section_id=?, position=?, image_renditions=?, title_html=?, subtitle=?, preview=?, uri=?, subitem_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAV_SECTION_ID,
        C_POSITION,
        C_IMAGE_RENDITIONS,
        C_TITLE_HTML,
        C_SUBTITLE,
        C_PREVIEW,
        C_URI,
        C_SUBITEM_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAV_SECTION_ID,
        FULL_C_POSITION,
        FULL_C_IMAGE_RENDITIONS,
        FULL_C_TITLE_HTML,
        FULL_C_SUBTITLE,
        FULL_C_PREVIEW,
        FULL_C_URI,
        FULL_C_SUBITEM_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getNavSectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_NAV_SECTION_ID))
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

    fun getPreview(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_PREVIEW))) cursor.getString(cursor.getColumnIndexOrThrow(C_PREVIEW)) else null
    }

    fun getUri(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_URI))
    }

    fun getSubitemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SUBITEM_ID))
    }


}
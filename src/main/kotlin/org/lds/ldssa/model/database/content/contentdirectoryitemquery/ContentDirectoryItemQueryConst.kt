/*
 * ContentDirectoryItemQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.contentdirectoryitemquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object ContentDirectoryItemQueryConst  {

    const val DATABASE = "content"
    const val C_ID = "_id"
    const val FULL_C_ID = "content_directory_item_query._id"
    const val C_TYPE = "type"
    const val FULL_C_TYPE = "content_directory_item_query.type"
    const val C_SECTION_ID = "section_id"
    const val FULL_C_SECTION_ID = "content_directory_item_query.section_id"
    const val C_SECTION_POSITION = "section_position"
    const val FULL_C_SECTION_POSITION = "content_directory_item_query.section_position"
    const val C_SECTION_TITLE = "section_title"
    const val FULL_C_SECTION_TITLE = "content_directory_item_query.section_title"
    const val C_SECTION_INDENT_LEVEL = "section_indent_level"
    const val FULL_C_SECTION_INDENT_LEVEL = "content_directory_item_query.section_indent_level"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "content_directory_item_query.position"
    const val C_IMAGE_RENDITIONS = "image_renditions"
    const val FULL_C_IMAGE_RENDITIONS = "content_directory_item_query.image_renditions"
    const val C_TITLE_HTML = "title_html"
    const val FULL_C_TITLE_HTML = "content_directory_item_query.title_html"
    const val C_SUBTITLE = "subtitle"
    const val FULL_C_SUBTITLE = "content_directory_item_query.subtitle"
    const val C_PREVIEW = "preview"
    const val FULL_C_PREVIEW = "content_directory_item_query.preview"
    const val C_URI = "uri"
    const val FULL_C_URI = "content_directory_item_query.uri"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_TYPE,
        C_SECTION_ID,
        C_SECTION_POSITION,
        C_SECTION_TITLE,
        C_SECTION_INDENT_LEVEL,
        C_POSITION,
        C_IMAGE_RENDITIONS,
        C_TITLE_HTML,
        C_SUBTITLE,
        C_PREVIEW,
        C_URI)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_TYPE,
        FULL_C_SECTION_ID,
        FULL_C_SECTION_POSITION,
        FULL_C_SECTION_TITLE,
        FULL_C_SECTION_INDENT_LEVEL,
        FULL_C_POSITION,
        FULL_C_IMAGE_RENDITIONS,
        FULL_C_TITLE_HTML,
        FULL_C_SUBTITLE,
        FULL_C_PREVIEW,
        FULL_C_URI)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getType(cursor: Cursor) : org.lds.ldssa.model.database.types.QueryItemType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.QueryItemType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_TYPE)), org.lds.ldssa.model.database.types.QueryItemType.UNKNOWN)
    }

    fun getSectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SECTION_ID))
    }

    fun getSectionPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SECTION_POSITION))
    }

    fun getSectionTitle(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_SECTION_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(C_SECTION_TITLE)) else null
    }

    fun getSectionIndentLevel(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SECTION_INDENT_LEVEL))
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


}
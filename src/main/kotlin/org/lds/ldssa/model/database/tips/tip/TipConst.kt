/*
 * TipBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.tips.tip

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object TipConst  {

    const val DATABASE = "tips"
    const val TABLE = "tip"
    const val FULL_TABLE = "tips.tip"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "tip._id"
    const val C_VERSION_ID = "version_id"
    const val FULL_C_VERSION_ID = "tip.version_id"
    const val C_ISO6393 = "iso639_3"
    const val FULL_C_ISO6393 = "tip.iso639_3"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "tip.title"
    const val C_DESCRIPTION = "description"
    const val FULL_C_DESCRIPTION = "tip.description"
    const val C_PHONE_IMAGE_FILENAME = "phone_image_filename"
    const val FULL_C_PHONE_IMAGE_FILENAME = "tip.phone_image_filename"
    const val C_PHONE_IMAGE_RENDITIONS = "phone_image_renditions"
    const val FULL_C_PHONE_IMAGE_RENDITIONS = "tip.phone_image_renditions"
    const val C_PHONE_VIDEO_URL = "phone_video_url"
    const val FULL_C_PHONE_VIDEO_URL = "tip.phone_video_url"
    const val C_TABLET_IMAGE_FILENAME = "tablet_image_filename"
    const val FULL_C_TABLET_IMAGE_FILENAME = "tip.tablet_image_filename"
    const val C_TABLET_IMAGE_RENDITIONS = "tablet_image_renditions"
    const val FULL_C_TABLET_IMAGE_RENDITIONS = "tip.tablet_image_renditions"
    const val C_TABLET_VIDEO_URL = "tablet_video_url"
    const val FULL_C_TABLET_VIDEO_URL = "tip.tablet_video_url"
    const val C_SHOW_IN_WELCOME = "show_in_welcome"
    const val FULL_C_SHOW_IN_WELCOME = "tip.show_in_welcome"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "tip.position"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS tip (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "version_id INTEGER NOT NULL," + 
        "iso639_3 TEXT NOT NULL," + 
        "title TEXT NOT NULL," + 
        "description TEXT NOT NULL," + 
        "phone_image_filename TEXT," + 
        "phone_image_renditions TEXT NOT NULL," + 
        "phone_video_url TEXT," + 
        "tablet_image_filename TEXT," + 
        "tablet_image_renditions TEXT NOT NULL," + 
        "tablet_video_url TEXT," + 
        "show_in_welcome INTEGER DEFAULT 0 NOT NULL," + 
        "position INTEGER NOT NULL," + 
        "FOREIGN KEY (version_id) REFERENCES version (_id)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS tip;"
    const val INSERT_STATEMENT = "INSERT INTO tip (version_id,iso639_3,title,description,phone_image_filename,phone_image_renditions,phone_video_url,tablet_image_filename,tablet_image_renditions,tablet_video_url,show_in_welcome,position) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE tip SET version_id=?, iso639_3=?, title=?, description=?, phone_image_filename=?, phone_image_renditions=?, phone_video_url=?, tablet_image_filename=?, tablet_image_renditions=?, tablet_video_url=?, show_in_welcome=?, position=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_VERSION_ID,
        C_ISO6393,
        C_TITLE,
        C_DESCRIPTION,
        C_PHONE_IMAGE_FILENAME,
        C_PHONE_IMAGE_RENDITIONS,
        C_PHONE_VIDEO_URL,
        C_TABLET_IMAGE_FILENAME,
        C_TABLET_IMAGE_RENDITIONS,
        C_TABLET_VIDEO_URL,
        C_SHOW_IN_WELCOME,
        C_POSITION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_VERSION_ID,
        FULL_C_ISO6393,
        FULL_C_TITLE,
        FULL_C_DESCRIPTION,
        FULL_C_PHONE_IMAGE_FILENAME,
        FULL_C_PHONE_IMAGE_RENDITIONS,
        FULL_C_PHONE_VIDEO_URL,
        FULL_C_TABLET_IMAGE_FILENAME,
        FULL_C_TABLET_IMAGE_RENDITIONS,
        FULL_C_TABLET_VIDEO_URL,
        FULL_C_SHOW_IN_WELCOME,
        FULL_C_POSITION)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getVersionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_VERSION_ID))
    }

    fun getIso6393(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_ISO6393))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getDescription(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_DESCRIPTION))
    }

    fun getPhoneImageFilename(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_PHONE_IMAGE_FILENAME))) cursor.getString(cursor.getColumnIndexOrThrow(C_PHONE_IMAGE_FILENAME)) else null
    }

    fun getPhoneImageRenditions(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_PHONE_IMAGE_RENDITIONS))
    }

    fun getPhoneVideoUrl(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_PHONE_VIDEO_URL))) cursor.getString(cursor.getColumnIndexOrThrow(C_PHONE_VIDEO_URL)) else null
    }

    fun getTabletImageFilename(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_TABLET_IMAGE_FILENAME))) cursor.getString(cursor.getColumnIndexOrThrow(C_TABLET_IMAGE_FILENAME)) else null
    }

    fun getTabletImageRenditions(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TABLET_IMAGE_RENDITIONS))
    }

    fun getTabletVideoUrl(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_TABLET_VIDEO_URL))) cursor.getString(cursor.getColumnIndexOrThrow(C_TABLET_VIDEO_URL)) else null
    }

    fun isShowInWelcome(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SHOW_IN_WELCOME)) != 0
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }


}
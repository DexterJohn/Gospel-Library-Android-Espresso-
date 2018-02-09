/*
 * ItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.item

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object ItemConst  {

    const val DATABASE = "catalog"
    const val TABLE = "item"
    const val FULL_TABLE = "catalog.item"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "item._id"
    const val C_EXTERNAL_ID = "external_id"
    const val FULL_C_EXTERNAL_ID = "item.external_id"
    const val C_LANGUAGE_ID = "language_id"
    const val FULL_C_LANGUAGE_ID = "item.language_id"
    const val C_SOURCE_ID = "source_id"
    const val FULL_C_SOURCE_ID = "item.source_id"
    const val C_PLATFORM = "platform_id"
    const val FULL_C_PLATFORM = "item.platform_id"
    const val C_URI = "uri"
    const val FULL_C_URI = "item.uri"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "item.title"
    const val C_ITEM_COVER_RENDITIONS = "item_cover_renditions"
    const val FULL_C_ITEM_COVER_RENDITIONS = "item.item_cover_renditions"
    const val C_ITEM_CATEGORY_ID = "item_category_id"
    const val FULL_C_ITEM_CATEGORY_ID = "item.item_category_id"
    const val C_VERSION = "version"
    const val FULL_C_VERSION = "item.version"
    const val C_OBSOLETE = "is_obsolete"
    const val FULL_C_OBSOLETE = "item.is_obsolete"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS item (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "external_id TEXT NOT NULL," + 
        "language_id INTEGER NOT NULL," + 
        "source_id INTEGER NOT NULL," + 
        "platform_id INTEGER NOT NULL," + 
        "uri TEXT NOT NULL," + 
        "title TEXT NOT NULL," + 
        "item_cover_renditions TEXT," + 
        "item_category_id INTEGER NOT NULL," + 
        "version INTEGER NOT NULL," + 
        "is_obsolete INTEGER DEFAULT 0 NOT NULL," + 
        "UNIQUE(external_id)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS item;"
    const val INSERT_STATEMENT = "INSERT INTO item (external_id,language_id,source_id,platform_id,uri,title,item_cover_renditions,item_category_id,version,is_obsolete) VALUES (?,?,?,?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE item SET external_id=?, language_id=?, source_id=?, platform_id=?, uri=?, title=?, item_cover_renditions=?, item_category_id=?, version=?, is_obsolete=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_EXTERNAL_ID,
        C_LANGUAGE_ID,
        C_SOURCE_ID,
        C_PLATFORM,
        C_URI,
        C_TITLE,
        C_ITEM_COVER_RENDITIONS,
        C_ITEM_CATEGORY_ID,
        C_VERSION,
        C_OBSOLETE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_EXTERNAL_ID,
        FULL_C_LANGUAGE_ID,
        FULL_C_SOURCE_ID,
        FULL_C_PLATFORM,
        FULL_C_URI,
        FULL_C_TITLE,
        FULL_C_ITEM_COVER_RENDITIONS,
        FULL_C_ITEM_CATEGORY_ID,
        FULL_C_VERSION,
        FULL_C_OBSOLETE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getExternalId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_EXTERNAL_ID))
    }

    fun getLanguageId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LANGUAGE_ID))
    }

    fun getSourceId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SOURCE_ID))
    }

    fun getPlatform(cursor: Cursor) : org.lds.ldssa.model.database.types.PlatformType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.PlatformType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_PLATFORM)), org.lds.ldssa.model.database.types.PlatformType.ALL)
    }

    fun getUri(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_URI))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getItemCoverRenditions(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_ITEM_COVER_RENDITIONS))) cursor.getString(cursor.getColumnIndexOrThrow(C_ITEM_COVER_RENDITIONS)) else null
    }

    fun getItemCategoryId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ITEM_CATEGORY_ID))
    }

    fun getVersion(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_VERSION))
    }

    fun isObsolete(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_OBSOLETE)) != 0
    }


}
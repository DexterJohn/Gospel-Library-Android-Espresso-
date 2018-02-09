/*
 * CatalogItemQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.catalogitemquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object CatalogItemQueryConst  {

    const val DATABASE = "catalog"
    const val C_ID = "_id"
    const val FULL_C_ID = "catalog_item_query._id"
    const val C_TYPE = "type"
    const val FULL_C_TYPE = "catalog_item_query.type"
    const val C_PARENT_ID = "parent_id"
    const val FULL_C_PARENT_ID = "catalog_item_query.parent_id"
    const val C_LANGUAGE_ID = "language_id"
    const val FULL_C_LANGUAGE_ID = "catalog_item_query.language_id"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "catalog_item_query.title"
    const val C_URI = "uri"
    const val FULL_C_URI = "catalog_item_query.uri"
    const val C_LIBRARY_SECTION_ID = "library_section_id"
    const val FULL_C_LIBRARY_SECTION_ID = "catalog_item_query.library_section_id"
    const val C_SECTION_ID = "section_id"
    const val FULL_C_SECTION_ID = "catalog_item_query.section_id"
    const val C_SECTION_TITLE = "section_title"
    const val FULL_C_SECTION_TITLE = "catalog_item_query.section_title"
    const val C_SECTION_POSITION = "section_position"
    const val FULL_C_SECTION_POSITION = "catalog_item_query.section_position"
    const val C_COLLECTION_TYPE = "collection_type"
    const val FULL_C_COLLECTION_TYPE = "catalog_item_query.collection_type"
    const val C_EXTERNAL_ID = "external_id"
    const val FULL_C_EXTERNAL_ID = "catalog_item_query.external_id"
    const val C_ITEM_COVER_RENDITIONS = "item_cover_renditions"
    const val FULL_C_ITEM_COVER_RENDITIONS = "catalog_item_query.item_cover_renditions"
    const val C_ITEM_POSITION = "item_position"
    const val FULL_C_ITEM_POSITION = "catalog_item_query.item_position"
    const val C_INSTALLED = "installed"
    const val FULL_C_INSTALLED = "catalog_item_query.installed"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_TYPE,
        C_PARENT_ID,
        C_LANGUAGE_ID,
        C_TITLE,
        C_URI,
        C_LIBRARY_SECTION_ID,
        C_SECTION_ID,
        C_SECTION_TITLE,
        C_SECTION_POSITION,
        C_COLLECTION_TYPE,
        C_EXTERNAL_ID,
        C_ITEM_COVER_RENDITIONS,
        C_ITEM_POSITION,
        C_INSTALLED)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_TYPE,
        FULL_C_PARENT_ID,
        FULL_C_LANGUAGE_ID,
        FULL_C_TITLE,
        FULL_C_URI,
        FULL_C_LIBRARY_SECTION_ID,
        FULL_C_SECTION_ID,
        FULL_C_SECTION_TITLE,
        FULL_C_SECTION_POSITION,
        FULL_C_COLLECTION_TYPE,
        FULL_C_EXTERNAL_ID,
        FULL_C_ITEM_COVER_RENDITIONS,
        FULL_C_ITEM_POSITION,
        FULL_C_INSTALLED)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getType(cursor: Cursor) : org.lds.ldssa.model.database.types.CatalogItemQueryType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemQueryType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_TYPE)), org.lds.ldssa.model.database.types.CatalogItemQueryType.UNKNOWN)
    }

    fun getParentId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_PARENT_ID))
    }

    fun getLanguageId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LANGUAGE_ID))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getUri(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_URI))) cursor.getString(cursor.getColumnIndexOrThrow(C_URI)) else null
    }

    fun getLibrarySectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LIBRARY_SECTION_ID))
    }

    fun getSectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SECTION_ID))
    }

    fun getSectionTitle(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_SECTION_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(C_SECTION_TITLE)) else null
    }

    fun getSectionPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SECTION_POSITION))
    }

    fun getCollectionType(cursor: Cursor) : org.lds.ldssa.model.database.types.LibraryCollectionType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.LibraryCollectionType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_COLLECTION_TYPE)), org.lds.ldssa.model.database.types.LibraryCollectionType.UNKNOWN)
    }

    fun getExternalId(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_EXTERNAL_ID))) cursor.getString(cursor.getColumnIndexOrThrow(C_EXTERNAL_ID)) else null
    }

    fun getItemCoverRenditions(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_ITEM_COVER_RENDITIONS))) cursor.getString(cursor.getColumnIndexOrThrow(C_ITEM_COVER_RENDITIONS)) else null
    }

    fun getItemPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_ITEM_POSITION))
    }

    fun isInstalled(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_INSTALLED)) != 0
    }


}
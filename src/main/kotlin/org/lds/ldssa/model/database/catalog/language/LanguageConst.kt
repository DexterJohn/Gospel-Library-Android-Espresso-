/*
 * LanguageBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.language

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object LanguageConst  {

    const val DATABASE = "catalog"
    const val TABLE = "language"
    const val FULL_TABLE = "catalog.language"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "language._id"
    const val C_LDS_LANGUAGE_CODE = "lds_language_code"
    const val FULL_C_LDS_LANGUAGE_CODE = "language.lds_language_code"
    const val C_ISO6393 = "iso639_3"
    const val FULL_C_ISO6393 = "language.iso639_3"
    const val C_BCP47 = "bcp47"
    const val FULL_C_BCP47 = "language.bcp47"
    const val C_ROOT_LIBRARY_COLLECTION_ID = "root_library_collection_id"
    const val FULL_C_ROOT_LIBRARY_COLLECTION_ID = "language.root_library_collection_id"
    const val C_ROOT_LIBRARY_COLLECTION_EXTERNAL_ID = "root_library_collection_external_id"
    const val FULL_C_ROOT_LIBRARY_COLLECTION_EXTERNAL_ID = "language.root_library_collection_external_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS language (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "lds_language_code TEXT NOT NULL," + 
        "iso639_3 TEXT NOT NULL," + 
        "bcp47 TEXT," + 
        "root_library_collection_id INTEGER NOT NULL," + 
        "root_library_collection_external_id INTEGER NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS language;"
    const val INSERT_STATEMENT = "INSERT INTO language (lds_language_code,iso639_3,bcp47,root_library_collection_id,root_library_collection_external_id) VALUES (?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE language SET lds_language_code=?, iso639_3=?, bcp47=?, root_library_collection_id=?, root_library_collection_external_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_LDS_LANGUAGE_CODE,
        C_ISO6393,
        C_BCP47,
        C_ROOT_LIBRARY_COLLECTION_ID,
        C_ROOT_LIBRARY_COLLECTION_EXTERNAL_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_LDS_LANGUAGE_CODE,
        FULL_C_ISO6393,
        FULL_C_BCP47,
        FULL_C_ROOT_LIBRARY_COLLECTION_ID,
        FULL_C_ROOT_LIBRARY_COLLECTION_EXTERNAL_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getLdsLanguageCode(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_LDS_LANGUAGE_CODE))
    }

    fun getIso6393(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_ISO6393))
    }

    fun getBcp47(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_BCP47))) cursor.getString(cursor.getColumnIndexOrThrow(C_BCP47)) else null
    }

    fun getRootLibraryCollectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ROOT_LIBRARY_COLLECTION_ID))
    }

    fun getRootLibraryCollectionExternalId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ROOT_LIBRARY_COLLECTION_EXTERNAL_ID))
    }


}
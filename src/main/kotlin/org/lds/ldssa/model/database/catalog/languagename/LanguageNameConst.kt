/*
 * LanguageNameBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.languagename

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object LanguageNameConst  {

    const val DATABASE = "catalog"
    const val TABLE = "language_name"
    const val FULL_TABLE = "catalog.language_name"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "language_name._id"
    const val C_LANGUAGE_ID = "language_id"
    const val FULL_C_LANGUAGE_ID = "language_name.language_id"
    const val C_LOCALIZATION_LANGUAGE_ID = "localization_language_id"
    const val FULL_C_LOCALIZATION_LANGUAGE_ID = "language_name.localization_language_id"
    const val C_NAME = "name"
    const val FULL_C_NAME = "language_name.name"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS language_name (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "language_id INTEGER NOT NULL," + 
        "localization_language_id INTEGER NOT NULL," + 
        "name TEXT NOT NULL," + 
        "UNIQUE(language_id, localization_language_id)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS language_name;"
    const val INSERT_STATEMENT = "INSERT INTO language_name (language_id,localization_language_id,name) VALUES (?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE language_name SET language_id=?, localization_language_id=?, name=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_LANGUAGE_ID,
        C_LOCALIZATION_LANGUAGE_ID,
        C_NAME)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_LANGUAGE_ID,
        FULL_C_LOCALIZATION_LANGUAGE_ID,
        FULL_C_NAME)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getLanguageId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LANGUAGE_ID))
    }

    fun getLocalizationLanguageId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LOCALIZATION_LANGUAGE_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }


}
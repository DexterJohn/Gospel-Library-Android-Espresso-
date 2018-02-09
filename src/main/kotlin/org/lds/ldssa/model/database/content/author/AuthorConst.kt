/*
 * AuthorBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.author

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object AuthorConst  {

    const val DATABASE = "content"
    const val TABLE = "author"
    const val FULL_TABLE = "content.author"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "author._id"
    const val C_GIVEN_NAME = "given_name"
    const val FULL_C_GIVEN_NAME = "author.given_name"
    const val C_FAMILY_NAME = "family_name"
    const val FULL_C_FAMILY_NAME = "author.family_name"
    const val C_IMAGE_RENDITIONS = "image_renditions"
    const val FULL_C_IMAGE_RENDITIONS = "author.image_renditions"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS author (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "given_name TEXT NOT NULL," + 
        "family_name TEXT NOT NULL," + 
        "image_renditions TEXT," + 
        "UNIQUE(given_name,family_name)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS author;"
    const val INSERT_STATEMENT = "INSERT INTO author (given_name,family_name,image_renditions) VALUES (?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE author SET given_name=?, family_name=?, image_renditions=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_GIVEN_NAME,
        C_FAMILY_NAME,
        C_IMAGE_RENDITIONS)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_GIVEN_NAME,
        FULL_C_FAMILY_NAME,
        FULL_C_IMAGE_RENDITIONS)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getGivenName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_GIVEN_NAME))
    }

    fun getFamilyName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_FAMILY_NAME))
    }

    fun getImageRenditions(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_IMAGE_RENDITIONS))) cursor.getString(cursor.getColumnIndexOrThrow(C_IMAGE_RENDITIONS)) else null
    }


}
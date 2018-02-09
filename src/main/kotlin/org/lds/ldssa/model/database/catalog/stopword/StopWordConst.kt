/*
 * StopWordBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.stopword

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object StopWordConst  {

    const val DATABASE = "catalog"
    const val TABLE = "stopword"
    const val FULL_TABLE = "catalog.stopword"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "stopword._id"
    const val C_LANGUAGE_ID = "language_id"
    const val FULL_C_LANGUAGE_ID = "stopword.language_id"
    const val C_WORD = "word"
    const val FULL_C_WORD = "stopword.word"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS stopword (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "language_id INTEGER NOT NULL," + 
        "word TEXT NOT NULL," + 
        "FOREIGN KEY (language_id) REFERENCES language (_id)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS stopword;"
    const val INSERT_STATEMENT = "INSERT INTO stopword (language_id,word) VALUES (?,?)"
    const val UPDATE_STATEMENT = "UPDATE stopword SET language_id=?, word=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_LANGUAGE_ID,
        C_WORD)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_LANGUAGE_ID,
        FULL_C_WORD)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getLanguageId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LANGUAGE_ID))
    }

    fun getWord(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_WORD))
    }


}
/*
 * ParagraphMetadataBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.paragraphmetadata

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object ParagraphMetadataConst  {

    const val DATABASE = "content"
    const val TABLE = "paragraph_metadata"
    const val FULL_TABLE = "content.paragraph_metadata"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "paragraph_metadata._id"
    const val C_SUBITEM_ID = "subitem_id"
    const val FULL_C_SUBITEM_ID = "paragraph_metadata.subitem_id"
    const val C_PARAGRAPH_ID = "paragraph_id"
    const val FULL_C_PARAGRAPH_ID = "paragraph_metadata.paragraph_id"
    const val C_PARAGRAPH_AID = "paragraph_aid"
    const val FULL_C_PARAGRAPH_AID = "paragraph_metadata.paragraph_aid"
    const val C_VERSE_NUMBER = "verse_number"
    const val FULL_C_VERSE_NUMBER = "paragraph_metadata.verse_number"
    const val C_START_INDEX = "start_index"
    const val FULL_C_START_INDEX = "paragraph_metadata.start_index"
    const val C_END_INDEX = "end_index"
    const val FULL_C_END_INDEX = "paragraph_metadata.end_index"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS paragraph_metadata (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "subitem_id INTEGER NOT NULL," + 
        "paragraph_id TEXT NOT NULL," + 
        "paragraph_aid TEXT NOT NULL," + 
        "verse_number TEXT," + 
        "start_index INTEGER NOT NULL," + 
        "end_index INTEGER NOT NULL," + 
        "UNIQUE(subitem_id)," + 
        "UNIQUE(subitem_id, paragraph_id)" + 
        ");" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS paragraph_metadatasubitem_id_IDX ON paragraph_metadata (subitem_id);" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS paragraph_metadata;"
    const val INSERT_STATEMENT = "INSERT INTO paragraph_metadata (subitem_id,paragraph_id,paragraph_aid,verse_number,start_index,end_index) VALUES (?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE paragraph_metadata SET subitem_id=?, paragraph_id=?, paragraph_aid=?, verse_number=?, start_index=?, end_index=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_SUBITEM_ID,
        C_PARAGRAPH_ID,
        C_PARAGRAPH_AID,
        C_VERSE_NUMBER,
        C_START_INDEX,
        C_END_INDEX)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_SUBITEM_ID,
        FULL_C_PARAGRAPH_ID,
        FULL_C_PARAGRAPH_AID,
        FULL_C_VERSE_NUMBER,
        FULL_C_START_INDEX,
        FULL_C_END_INDEX)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getSubitemId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SUBITEM_ID))
    }

    fun getParagraphId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_PARAGRAPH_ID))
    }

    fun getParagraphAid(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_PARAGRAPH_AID))
    }

    fun getVerseNumber(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_VERSE_NUMBER))) cursor.getString(cursor.getColumnIndexOrThrow(C_VERSE_NUMBER)) else null
    }

    fun getStartIndex(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_START_INDEX))
    }

    fun getEndIndex(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_END_INDEX))
    }


}
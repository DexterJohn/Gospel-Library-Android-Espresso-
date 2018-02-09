/*
 * NotebookAnnotationBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.notebookannotation

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object NotebookAnnotationConst  {

    const val DATABASE = "userdata"
    const val TABLE = "notebook_annotation"
    const val FULL_TABLE = "userdata.notebook_annotation"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "notebook_annotation._id"
    const val C_NOTEBOOK_ID = "notebook_id"
    const val FULL_C_NOTEBOOK_ID = "notebook_annotation.notebook_id"
    const val C_ANNOTATION_ID = "annotation_id"
    const val FULL_C_ANNOTATION_ID = "notebook_annotation.annotation_id"
    const val C_DISPLAY_ORDER = "display_order"
    const val FULL_C_DISPLAY_ORDER = "notebook_annotation.display_order"
    const val C_UNIQUE_ANNOTATION_ID = "unique_annotation_id"
    const val FULL_C_UNIQUE_ANNOTATION_ID = "notebook_annotation.unique_annotation_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS notebook_annotation (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "notebook_id INTEGER NOT NULL," + 
        "annotation_id INTEGER NOT NULL," + 
        "display_order INTEGER DEFAULT -1 NOT NULL," + 
        "unique_annotation_id TEXT NOT NULL," + 
        "UNIQUE(unique_annotation_id, notebook_id) ON CONFLICT REPLACE" + 
        ");" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS notebook_annotationnotebook_id_IDX ON notebook_annotation (notebook_id);" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS notebook_annotationannotation_id_IDX ON notebook_annotation (annotation_id);" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS notebook_annotation;"
    const val INSERT_STATEMENT = "INSERT INTO notebook_annotation (notebook_id,annotation_id,display_order,unique_annotation_id) VALUES (?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE notebook_annotation SET notebook_id=?, annotation_id=?, display_order=?, unique_annotation_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NOTEBOOK_ID,
        C_ANNOTATION_ID,
        C_DISPLAY_ORDER,
        C_UNIQUE_ANNOTATION_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NOTEBOOK_ID,
        FULL_C_ANNOTATION_ID,
        FULL_C_DISPLAY_ORDER,
        FULL_C_UNIQUE_ANNOTATION_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getNotebookId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_NOTEBOOK_ID))
    }

    fun getAnnotationId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ANNOTATION_ID))
    }

    fun getDisplayOrder(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_DISPLAY_ORDER))
    }

    fun getUniqueAnnotationId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_UNIQUE_ANNOTATION_ID))
    }


}
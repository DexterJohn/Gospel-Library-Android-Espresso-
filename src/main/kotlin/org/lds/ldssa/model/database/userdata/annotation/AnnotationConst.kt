/*
 * AnnotationBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.annotation

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object AnnotationConst  {

    const val DATABASE = "userdata"
    const val TABLE = "annotation"
    const val FULL_TABLE = "userdata.annotation"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "annotation._id"
    const val C_UNIQUE_ID = "unique_id"
    const val FULL_C_UNIQUE_ID = "annotation.unique_id"
    const val C_CONTENT_VERSION = "content_version"
    const val FULL_C_CONTENT_VERSION = "annotation.content_version"
    const val C_DEVICE = "device"
    const val FULL_C_DEVICE = "annotation.device"
    const val C_SOURCE = "source"
    const val FULL_C_SOURCE = "annotation.source"
    const val C_STATUS = "status"
    const val FULL_C_STATUS = "annotation.status"
    const val C_DOC_ID = "doc_id"
    const val FULL_C_DOC_ID = "annotation.doc_id"
    const val C_LAST_MODIFIED = "last_modified"
    const val FULL_C_LAST_MODIFIED = "annotation.last_modified"
    const val C_CITATION = "citation"
    const val FULL_C_CITATION = "annotation.citation"
    const val C_SCOPE = "scope"
    const val FULL_C_SCOPE = "annotation.scope"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS annotation (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "unique_id TEXT NOT NULL," + 
        "content_version INTEGER NOT NULL," + 
        "device TEXT DEFAULT 'android'," + 
        "source TEXT," + 
        "status INTEGER NOT NULL," + 
        "doc_id TEXT," + 
        "last_modified INTEGER NOT NULL," + 
        "citation TEXT," + 
        "scope TEXT" + 
        ");" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS annotationunique_id_IDX ON annotation (unique_id);" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS annotationdoc_id_IDX ON annotation (doc_id);" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS annotation;"
    const val INSERT_STATEMENT = "INSERT INTO annotation (unique_id,content_version,device,source,status,doc_id,last_modified,citation,scope) VALUES (?,?,?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE annotation SET unique_id=?, content_version=?, device=?, source=?, status=?, doc_id=?, last_modified=?, citation=?, scope=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_UNIQUE_ID,
        C_CONTENT_VERSION,
        C_DEVICE,
        C_SOURCE,
        C_STATUS,
        C_DOC_ID,
        C_LAST_MODIFIED,
        C_CITATION,
        C_SCOPE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_UNIQUE_ID,
        FULL_C_CONTENT_VERSION,
        FULL_C_DEVICE,
        FULL_C_SOURCE,
        FULL_C_STATUS,
        FULL_C_DOC_ID,
        FULL_C_LAST_MODIFIED,
        FULL_C_CITATION,
        FULL_C_SCOPE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getUniqueId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_UNIQUE_ID))
    }

    fun getContentVersion(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_CONTENT_VERSION))
    }

    fun getDevice(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_DEVICE))) cursor.getString(cursor.getColumnIndexOrThrow(C_DEVICE)) else null
    }

    fun getSource(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_SOURCE))) cursor.getString(cursor.getColumnIndexOrThrow(C_SOURCE)) else null
    }

    fun getStatus(cursor: Cursor) : org.lds.ldssa.model.database.types.AnnotationStatusType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.AnnotationStatusType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_STATUS)), org.lds.ldssa.model.database.types.AnnotationStatusType.ACTIVE)
    }

    fun getDocId(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_DOC_ID))) cursor.getString(cursor.getColumnIndexOrThrow(C_DOC_ID)) else null
    }

    fun getLastModified(cursor: Cursor) : org.threeten.bp.LocalDateTime {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED)))!! else null!!
    }

    fun getCitation(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_CITATION))) cursor.getString(cursor.getColumnIndexOrThrow(C_CITATION)) else null
    }

    fun getScope(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_SCOPE))) cursor.getString(cursor.getColumnIndexOrThrow(C_SCOPE)) else null
    }


}
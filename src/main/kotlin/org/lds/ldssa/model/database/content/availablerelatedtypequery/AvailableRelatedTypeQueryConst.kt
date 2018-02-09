/*
 * AvailableRelatedTypeQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.availablerelatedtypequery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object AvailableRelatedTypeQueryConst  {

    const val DATABASE = "content"
    const val C_ID = "_id"
    const val FULL_C_ID = "available_related_type_query._id"
    const val C_SUBITEM_ID = "subitem_id"
    const val FULL_C_SUBITEM_ID = "available_related_type_query.subitem_id"
    const val C_AUDIO_AVAILABLE = "audio_available"
    const val FULL_C_AUDIO_AVAILABLE = "available_related_type_query.audio_available"
    const val C_PDF_AVAILABLE = "pdfAvailable"
    const val FULL_C_PDF_AVAILABLE = "available_related_type_query.pdfAvailable"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_SUBITEM_ID,
        C_AUDIO_AVAILABLE,
        C_PDF_AVAILABLE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_SUBITEM_ID,
        FULL_C_AUDIO_AVAILABLE,
        FULL_C_PDF_AVAILABLE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getSubitemId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SUBITEM_ID))
    }

    fun isAudioAvailable(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_AUDIO_AVAILABLE)) != 0
    }

    fun isPdfAvailable(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_PDF_AVAILABLE)) != 0
    }


}
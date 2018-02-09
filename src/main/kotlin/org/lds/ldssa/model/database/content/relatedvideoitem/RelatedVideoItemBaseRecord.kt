/*
 * RelatedVideoItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.relatedvideoitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class RelatedVideoItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var subitemId: Int = 0
    open var posterUrl: String? = null
    open var videoId: String = ""
    open var title: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return RelatedVideoItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return RelatedVideoItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return RelatedVideoItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(RelatedVideoItemConst.C_SUBITEM_ID, subitemId.toLong())
        values.put(RelatedVideoItemConst.C_POSTER_URL, posterUrl)
        values.put(RelatedVideoItemConst.C_VIDEO_ID, videoId)
        values.put(RelatedVideoItemConst.C_TITLE, title)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            subitemId.toLong(),
            posterUrl,
            videoId,
            title)
    }

    open fun copy() : RelatedVideoItem {
        val copy = RelatedVideoItem()
        copy.id = id
        copy.subitemId = subitemId
        copy.posterUrl = posterUrl
        copy.videoId = videoId
        copy.title = title
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId.toLong())
        if (posterUrl != null) {
            statement.bindString(2, posterUrl!!)
        } else {
            statement.bindNull(2)
        }
        statement.bindString(3, videoId)
        statement.bindString(4, title)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId.toLong())
        if (posterUrl != null) {
            statement.bindString(2, posterUrl!!)
        } else {
            statement.bindNull(2)
        }
        statement.bindString(3, videoId)
        statement.bindString(4, title)
        statement.bindLong(5, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        subitemId = values.getAsInteger(RelatedVideoItemConst.C_SUBITEM_ID)
        posterUrl = values.getAsString(RelatedVideoItemConst.C_POSTER_URL)
        videoId = values.getAsString(RelatedVideoItemConst.C_VIDEO_ID)
        title = values.getAsString(RelatedVideoItemConst.C_TITLE)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(RelatedVideoItemConst.C_ID))
        subitemId = cursor.getInt(cursor.getColumnIndexOrThrow(RelatedVideoItemConst.C_SUBITEM_ID))
        posterUrl = if (!cursor.isNull(cursor.getColumnIndexOrThrow(RelatedVideoItemConst.C_POSTER_URL))) cursor.getString(cursor.getColumnIndexOrThrow(RelatedVideoItemConst.C_POSTER_URL)) else null
        videoId = cursor.getString(cursor.getColumnIndexOrThrow(RelatedVideoItemConst.C_VIDEO_ID))
        title = cursor.getString(cursor.getColumnIndexOrThrow(RelatedVideoItemConst.C_TITLE))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}
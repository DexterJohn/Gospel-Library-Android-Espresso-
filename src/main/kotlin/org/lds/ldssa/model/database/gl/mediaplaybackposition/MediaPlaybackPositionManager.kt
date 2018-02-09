/*
 * MediaPlaybackPositionManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.gl.mediaplaybackposition

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.types.ItemMediaType
import javax.inject.Inject


@javax.inject.Singleton
class MediaPlaybackPositionManager @Inject constructor(databaseManager: DatabaseManager) : MediaPlaybackPositionBaseManager(databaseManager) {
    val SELECTION = "${MediaPlaybackPositionConst.C_CONTENT_ITEM_ID} = ? AND ${MediaPlaybackPositionConst.C_SUB_ITEM_ID} = ? AND ${MediaPlaybackPositionConst.C_TYPE} = ? AND ${MediaPlaybackPositionConst.C_MEDIA_ID} = ? "

    fun getPlaybackPosition(contentId: Long, navItemId: Long, type: ItemMediaType, extraMediaId: String?): Int {
        val newExtraMediaId = extraMediaId ?: ""
        return findValueBySelection(valueType = Int::class.java,
                column = MediaPlaybackPositionConst.C_PLAYBACK_POSITION,
                selection = SELECTION,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(contentId, navItemId, type.ordinal, newExtraMediaId),
                defaultValue = 0)
    }

    fun setPlaybackPosition(contentId: Long, navItemId: Long, type: ItemMediaType, extraMediaId: String?, playbackPosition: Int) {
        val args = SQLQueryBuilder.toSelectionArgs(contentId, navItemId, type.ordinal, extraMediaId)

        var mediaPlaybackPosition = findBySelection(selection = SELECTION, selectionArgs = args)

        if (mediaPlaybackPosition == null) {
            mediaPlaybackPosition = MediaPlaybackPosition()
            mediaPlaybackPosition.contentItemId = contentId
            mediaPlaybackPosition.subItemId = navItemId
            mediaPlaybackPosition.type = type
            mediaPlaybackPosition.mediaId = extraMediaId ?: ""
        }

        mediaPlaybackPosition.playbackPosition = playbackPosition
        save(mediaPlaybackPosition)
    }
}
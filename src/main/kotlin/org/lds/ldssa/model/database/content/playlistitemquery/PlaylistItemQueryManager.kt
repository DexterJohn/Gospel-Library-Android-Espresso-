/*
 * PlaylistItemQueryManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.content.playlistitemquery

import org.dbtools.query.shared.CompareType
import org.dbtools.query.shared.JoinType
import org.dbtools.query.shared.filter.CompareFilter
import org.dbtools.query.shared.filter.OrFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.content.author.AuthorManager
import org.lds.ldssa.model.database.content.navitem.NavItemConst
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemConst
import org.lds.ldssa.model.database.content.relatedaudiovoice.RelatedAudioVoiceConst
import org.lds.ldssa.model.database.content.subitem.SubItemConst
import org.lds.ldssa.model.database.types.SubItemContentType
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.prefs.type.AudioPlaybackVoiceType
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class PlaylistItemQueryManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil, val itemManager: ItemManager, val authorManager: AuthorManager, val prefs: Prefs) : PlaylistItemQueryBaseManager(databaseManager) {
    companion object {
        val QUERY = SQLQueryBuilder()
                .field(NavItemConst.FULL_C_ID, PlaylistItemQueryConst.C_ID)
                .field(NavItemConst.FULL_C_SUBITEM_ID, PlaylistItemQueryConst.C_SUBITEM_ID)
                .field("?", PlaylistItemQueryConst.C_CONTENT_ITEM_ID)
                .field(NavItemConst.FULL_C_TITLE_HTML, PlaylistItemQueryConst.C_TITLE)

                .field(RelatedAudioItemConst.C_MEDIA_URL, PlaylistItemQueryConst.C_MEDIA_URL)
                .field("\"\"", PlaylistItemQueryConst.C_DOWNLOADED_MEDIA_URL) //Updated via the AVPlayListService
                .field("\"\"", PlaylistItemQueryConst.C_ARTWORK_RENDITIONS) //updated in the PlaylistItemQueryManager

                .field(RelatedAudioVoiceConst.FULL_C_NAME, PlaylistItemQueryConst.C_VOICE)

                .table(NavItemConst.TABLE)
                .join(RelatedAudioItemConst.TABLE, NavItemConst.FULL_C_SUBITEM_ID, RelatedAudioItemConst.FULL_C_SUBITEM_ID)
                .join(JoinType.LEFT_JOIN, RelatedAudioVoiceConst.TABLE, RelatedAudioItemConst.FULL_C_RELATED_AUDIO_VOICE_ID, RelatedAudioVoiceConst.FULL_C_ID)
                .orderBy(PlaylistItemQueryConst.C_SUBITEM_ID, true)
                .filter(OrFilter.create(CompareFilter.create(PlaylistItemQueryConst.C_VOICE, "?"),
                        CompareFilter.create(PlaylistItemQueryConst.C_VOICE, CompareType.IS_NULL)))

        val QUERY_BY_TYPE = QUERY.join(SubItemConst.TABLE, NavItemConst.FULL_C_SUBITEM_ID, SubItemConst.FULL_C_ID)
                .filter(SubItemConst.FULL_C_CONTENT_TYPE, "?")
    }

    override fun getQuery() : String {
        return QUERY.buildQuery()
    }

    fun findAllByContentItemId(contentItemId: Long): List<PlaylistItemQuery> {
        val voice = prefs.audioVoice.prefValue
        val args = SQLQueryBuilder.toSelectionArgs(contentItemId, voice)
        val queries = findAllByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                rawQuery = getQuery(),
                selectionArgs = args)

        //Updates the artwork renditions
        if (!queries.isEmpty()) {
            val coverRenditions = itemManager.findImageCoverRenditionsById(contentItemId)

            for (query in queries) {
                val authorRenditions = authorManager.findImageRenditionsBySubItemId(contentItemId, query.subitemId.toLong())
                query.artworkRenditions = when {
                    authorRenditions.isNotBlank() -> authorRenditions
                    coverRenditions.isNotBlank() -> coverRenditions
                    else -> ""
                }
            }
        }

        return queries
    }

    fun findAllByContentItemIdAndType(contentItemId: Long, subItemContentType: SubItemContentType): List<PlaylistItemQuery> {
        val voice = prefs.audioVoice.prefValue
        val args = SQLQueryBuilder.toSelectionArgs(contentItemId, voice, subItemContentType.ordinal)
        val queries = findAllByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                rawQuery = QUERY_BY_TYPE.buildQuery(),
                selectionArgs = args)

        //Updates the artwork renditions
        if (!queries.isEmpty()) {
            val coverRenditions = itemManager.findImageCoverRenditionsById(contentItemId)

            for (query in queries) {
                val authorRenditions = authorManager.findImageRenditionsBySubItemId(contentItemId, query.subitemId.toLong())
                query.artworkRenditions = when {
                    authorRenditions.isNotBlank() -> authorRenditions
                    coverRenditions.isNotBlank() -> coverRenditions
                    else -> ""
                }
            }
        }

        return queries
    }

    fun find(contentItemId: Long, subItemId: Long): PlaylistItemQuery? {
        var voice = prefs.audioVoice.prefValue
        if (voice == AudioPlaybackVoiceType.TEXT_TO_SPEECH.prefValue) {
            voice = AudioPlaybackVoiceType.MALE.prefValue
        }
        val args = SQLQueryBuilder.toSelectionArgs(contentItemId, voice)
        val queries = findAllByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                rawQuery = getQuery(),
                selectionArgs = args)

        val item: PlaylistItemQuery? = queries.firstOrNull { it.subitemId.toLong() == subItemId }

        //Updates the artwork renditions
        if (item != null) {
            val coverRenditions = itemManager.findImageCoverRenditionsById(contentItemId)
            item.artworkRenditions = coverRenditions
        }

        return item
    }
}
/*
 * RelatedAudioItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.relatedaudioitem

import org.dbtools.query.shared.JoinType
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.content.navcollection.NavCollectionConst
import org.lds.ldssa.model.database.content.navitem.NavItemConst
import org.lds.ldssa.model.database.content.navsection.NavSectionConst
import org.lds.ldssa.model.database.content.subitem.SubItemConst
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class RelatedAudioItemManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : RelatedAudioItemBaseManager(databaseManager) {
    companion object {
        val NAV_COLLECTION_QUERY: SQLQueryBuilder = SQLQueryBuilder()
                .table(RelatedAudioItemConst.TABLE)
                .join(JoinType.JOIN, SubItemConst.TABLE, SubItemConst.FULL_C_ID, RelatedAudioItemConst.FULL_C_SUBITEM_ID)
                .join(JoinType.JOIN, NavItemConst.TABLE, NavItemConst.FULL_C_SUBITEM_ID, SubItemConst.FULL_C_ID)
                .join(JoinType.JOIN, NavSectionConst.TABLE, NavSectionConst.FULL_C_ID, NavItemConst.FULL_C_NAV_SECTION_ID)
                .join(JoinType.JOIN, NavCollectionConst.TABLE, NavCollectionConst.FULL_C_ID, NavSectionConst.FULL_C_NAV_COLLECTION_ID)
                .filter(NavCollectionConst.FULL_C_ID, "?")
                .filter(RelatedAudioItemConst.FULL_C_RELATED_AUDIO_VOICE_ID, "?")
                .orderBy(RelatedAudioItemConst.FULL_C_RELATED_AUDIO_VOICE_ID, false)

        val NAV_COLLECTION_TOTAL_SIZE_QUERY: SQLQueryBuilder = NAV_COLLECTION_QUERY.clone().field("sum(" + RelatedAudioItemConst.C_FILE_SIZE + ")")
    }

    fun findAllByVoiceId(contentItemId: Long, voiceId: Int): List<RelatedAudioItem> {
        return findAllBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID + " = ? OR " + RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID + " IS NULL ",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(voiceId),
                orderBy = RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID + " DESC ")
    }

    fun findAllByNavCollectionIdAndVoiceId(contentItemId: Long, navCollectionId: Long, voiceId: Long): List<RelatedAudioItem> {
        return findAllByRawQuery(NAV_COLLECTION_QUERY.buildQuery(), SQLQueryBuilder.toSelectionArgs(navCollectionId, voiceId), contentItemUtil.getOpenedDatabaseName(contentItemId))
    }

    fun findBySubItemIdAndVoiceId(contentItemId: Long, subItemId: Long, voiceId: Long): RelatedAudioItem? {
        return findBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = RelatedAudioItemConst.C_SUBITEM_ID + " = ?  AND ( " + RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID + " = ? OR " + RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID + " IS NULL )",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId, voiceId),
                orderBy = RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID + " DESC ")
    }

    fun getItemCount(contentItemId: Long, voiceId: Int): Long {
        return findCountBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID + " = ? OR " + RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID + " IS NULL ",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(voiceId))
    }

    fun getItemCountByNavCollectionIdAndVoiceId(contentItemId: Long, navCollectionId: Long, voiceId: Int): Long {
        return findCountByRawQuery(NAV_COLLECTION_QUERY.buildQuery(), SQLQueryBuilder.toSelectionArgs(navCollectionId, voiceId), contentItemUtil.getOpenedDatabaseName(contentItemId))
    }

    fun getTotalDownloadSizeForVoice(contentItemId: Long, voiceId: Int): Long {
        return findValueBySelection(valueType = Long::class.java,
                databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                column = "sum(" + RelatedAudioItemConst.C_FILE_SIZE + ")",
                selection = RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID + " = ? OR " + RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID + " IS NULL ",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(voiceId),
                defaultValue = 0L)
    }

    fun getTotalDownloadSizeForNavCollectionAndVoice(contentItemId: Long, navCollectionId: Long, voiceId: Long): Long {
        return findValueByRawQuery(Long::class.java, rawQuery = NAV_COLLECTION_TOTAL_SIZE_QUERY.buildQuery(), selectionArgs = SQLQueryBuilder.toSelectionArgs(navCollectionId, voiceId), defaultValue = 0L, databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId))
    }
}
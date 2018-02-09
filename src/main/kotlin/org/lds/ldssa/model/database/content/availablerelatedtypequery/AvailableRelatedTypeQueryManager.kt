/*
 * AvailableRelatedTypeQueryManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.content.availablerelatedtypequery

import org.dbtools.query.shared.CompareType
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.content.navitem.NavItemConst
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemConst
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class AvailableRelatedTypeQueryManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : AvailableRelatedTypeQueryBaseManager(databaseManager) {
    companion object {
        val QUERY: String

        init {
            val audioAvailableQuery = SQLQueryBuilder()
                    .field("CAST(COUNT(1) AS BIT)")
                    .table(RelatedAudioItemConst.TABLE)
                    .filter(RelatedAudioItemConst.FULL_C_SUBITEM_ID, CompareType.EQUAL, "?")

            QUERY = SQLQueryBuilder()
                    .field(NavItemConst.FULL_C_ID, AvailableRelatedTypeQueryConst.C_ID)
                    .field(NavItemConst.FULL_C_SUBITEM_ID, AvailableRelatedTypeQueryConst.C_SUBITEM_ID)

                    .field("(${audioAvailableQuery.buildQuery()})", AvailableRelatedTypeQueryConst.C_AUDIO_AVAILABLE)
                    .field("0", AvailableRelatedTypeQueryConst.C_PDF_AVAILABLE)

                    .table(NavItemConst.TABLE)
                    .filter(NavItemConst.FULL_C_SUBITEM_ID, CompareType.EQUAL, "?").buildQuery()
        }
    }

    override fun getQuery() : String {
        return QUERY
    }

    fun findBySubItemId(contentItemId: Long, subItemId: Long): AvailableRelatedTypeQuery? {
        return findByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                rawQuery = QUERY,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId, subItemId))
    }
}
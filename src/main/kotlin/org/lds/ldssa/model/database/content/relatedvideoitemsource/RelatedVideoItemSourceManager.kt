/*
 * RelatedVideoItemSourceManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.relatedvideoitemsource

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class RelatedVideoItemSourceManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : RelatedVideoItemSourceBaseManager(databaseManager) {
    fun findAllByRelatedVideoItem(contentItemId: Long, relatedVideoItemId: Long): List<RelatedVideoItemSource> {
        return findAllBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = RelatedVideoItemSourceConst.C_RELATED_VIDEO_ITEM_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(relatedVideoItemId))
    }

}
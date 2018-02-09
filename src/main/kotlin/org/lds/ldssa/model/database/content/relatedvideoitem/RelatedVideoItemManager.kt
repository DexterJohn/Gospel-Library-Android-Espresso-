/*
 * RelatedVideoItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.relatedvideoitem

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class RelatedVideoItemManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : RelatedVideoItemBaseManager(databaseManager) {

    fun findAllBySubitem(contentItemId: Long, subItemId: Long): List<RelatedVideoItem> {
        return findAllBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = RelatedVideoItemConst.C_SUBITEM_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId))
    }
}
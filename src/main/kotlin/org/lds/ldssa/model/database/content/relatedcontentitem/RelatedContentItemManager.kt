/*
 * RelatedContentItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.relatedcontentitem

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class RelatedContentItemManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : RelatedContentItemBaseManager(databaseManager) {

    fun findBySubItemIdAndRefId(contentItemId: Long, subItemId: Long, refId: String): RelatedContentItem? {
        return findBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = RelatedContentItemConst.C_SUBITEM_ID + " = ? AND " + RelatedContentItemConst.C_REF_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId, refId))
    }

    fun findAllBySubItemId(contentItemId: Long, subItemId: Long): List<RelatedContentItem> {
        return findAllBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = RelatedContentItemConst.C_SUBITEM_ID + " = ? ",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId),
                orderBy = RelatedContentItemConst.C_BYTE_LOCATION)
    }

}
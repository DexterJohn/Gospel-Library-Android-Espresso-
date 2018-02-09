/*
 * NavCollectionIndexEntryManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.navcollectionindexentry

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class NavCollectionIndexEntryManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : NavCollectionIndexEntryBaseManager(databaseManager) {


    private val FIND_BY_ID_SELECTION = NavCollectionIndexEntryConst.C_NAV_COLLECTION_ID + "=?"

    fun findCount(contentItemId: Long): Long {
        return findCount(contentItemUtil.getOpenedDatabaseName(contentItemId))
    }

    fun findAllById(contentItemId: Long, collectionId: Long): List<NavCollectionIndexEntry> {
        return findAllBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = FIND_BY_ID_SELECTION,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(collectionId),
                orderBy = NavCollectionIndexEntryConst.C_POSITION)
    }

}
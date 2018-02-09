/*
 * SubItemMetadataManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.subitemmetadata

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import javax.inject.Inject


@javax.inject.Singleton
class SubItemMetadataManager @Inject constructor(databaseManager: DatabaseManager) : SubItemMetadataBaseManager(databaseManager) {

    /**
     * @return found version for docId or -1 if not found
     */
    fun findDocVersionByDocId(docId: String?): Int {
        return findValueBySelection(Int::class.java,
                column = SubItemMetadataConst.C_DOC_VERSION,
                selection = DOC_VERSION_SELECTION,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(docId),
                defaultValue = -1)
    }

    fun findByDocId(docId: String): SubItemMetadata? {
        return findBySelection(
                selection = DOC_VERSION_SELECTION,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(docId))
    }

    fun findAllByItemId(itemId: Long): List<SubItemMetadata> {
        return findAllBySelection(
                selection = SubItemMetadataConst.C_ITEM_ID + " = ? ",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(itemId))
    }

    fun findDocIdBySubItemId(contentItemId: Long, subItemId: Long): String? {
        return findValueBySelection(String::class.java, SubItemMetadataConst.C_DOC_ID,
                selection = SubItemMetadataConst.C_ITEM_ID + " = ? AND " + SubItemMetadataConst.C_SUBITEM_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(contentItemId, subItemId),
                defaultValue = null)
    }

    companion object {
        private const val DOC_VERSION_SELECTION = SubItemMetadataConst.C_DOC_ID + " = ? "
    }
}
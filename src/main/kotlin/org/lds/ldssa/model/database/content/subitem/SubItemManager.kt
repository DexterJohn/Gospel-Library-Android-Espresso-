/*
 * SubItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.subitem

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.types.SubItemContentType
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class SubItemManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : SubItemBaseManager(databaseManager) {

    fun findByRowId(contentItemId: Long, id: Long): SubItem? {
        return findByRowId(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                rowId = id)
    }

    fun findAllIds(contentItemId: Long): List<Long> {
        return findAllValuesBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                column = SubItemConst.C_ID,
                orderBy = SubItemConst.C_POSITION)
    }

    fun findAllByContentItemId(contentItemId: Long): List<SubItem> {
        return findAllBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                orderBy = SubItemConst.C_POSITION)
    }

    fun findCount(contentItemId: Long): Long {
        return super.findCount(contentItemUtil.getOpenedDatabaseName(contentItemId))
    }

    fun findPositionById(contentItemId: Long, id: Long): Int {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Int::class.java,
                column = SubItemConst.C_POSITION,
                selection = SubItemConst.C_ID + " = " + id,
                defaultValue = 0)
    }

    fun findDocIdById(contentItemId: Long, id: Long): String? {
        return findValueByRowId(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                column = SubItemConst.C_DOC_ID,
                rowId = id,
                defaultValue = null)
    }

    fun findTitleById(contentItemId: Long, id: Long): String {
        return findValueByRowId(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                column = SubItemConst.C_TITLE,
                rowId = id,
                defaultValue = "")
    }

    fun findIdByUri(contentItemId: Long, subItemUri: String): Long {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                column = SubItemConst.C_ID,
                selection = SubItemConst.C_URI + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemUri),
                defaultValue = 0L)
    }

    fun findUriById(contentItemId: Long, subItemId: Long): String {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                column = SubItemConst.C_URI,
                selection = SubItemConst.C_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId),
                defaultValue = "")
    }

    fun getWebUrl(contentItemId: Long, id: Long): String {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                column = SubItemConst.C_WEB_URL,
                selection = SubItemConst.C_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(id),
                defaultValue = "")
    }

    fun findTypeById(contentItemId: Long, subItemId: Long): SubItemContentType {
        val enumValue = findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Int::class.java,
                column = SubItemConst.C_CONTENT_TYPE,
                selection = SubItemConst.C_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(subItemId),
                defaultValue = 0)

        return SubItemContentType.values()[enumValue]
    }

    fun findIdByDocId(contentItemId: Long, docId: String): Long {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                column = SubItemConst.C_ID,
                selection = SubItemConst.FULL_C_DOC_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(docId),
                defaultValue = 0L)
    }

}
/*
 * SubItemContentManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.content.subitemcontent

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class SubItemContentManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : SubItemContentBaseManager(databaseManager) {


    fun findContentById(contentItemId: Long, id: Long): String {
        // FTS NOTE:  The SQLITE database seems to create fields of type BLOB for items in the FTS virtual table.  Because of this, note the following:
        // 1. The content appears as BLOB datatype (hence the 'new String(content)')
        // 2. The regular selectionArgs don't seem to work properly... so manually perform the replace
        val rawQuery = CONTENT_QUERY.replace("?", id.toString())
        val content = findValueByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = ByteArray::class.java,
                rawQuery = rawQuery,
                defaultValue = null)

        return if (content != null) String(content) else "" // note: new String(byte[]) relies on default encoding
    }

    fun search(contentItemId: Long, text: String) {
        val query = "SELECT * FROM subitem_content_fts WHERE content MATCH ?"
        val cursor = getWritableDatabase(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId)).rawQuery(query, arrayOf(text))
        cursor.count
    }

    companion object {
        private val CONTENT_QUERY = SQLQueryBuilder()
                .field(SubItemContentConst.C_CONTENT_HTML)
                .table(SubItemContentConst.TABLE)
                .filter(SubItemContentConst.C_SUBITEM_ID, "?")
                .buildQuery()
    }
}
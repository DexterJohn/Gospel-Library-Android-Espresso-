/*
 * ContentMetaDataManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.contentmetadata

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class ContentMetaDataManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : ContentMetaDataBaseManager(databaseManager) {

    private fun findValueByKey(contentItemId: Long, key: String): String? {
        if (contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            return findValueByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                    valueType = String::class.java,
                    rawQuery = VALUE_QUERY,
                    selectionArgs = SQLQueryBuilder.toSelectionArgs(key),
                    defaultValue = null)
        }
        return null
    }

    fun findLanguageCode(contentItemId: Long): String? {
        return findValueByKey(contentItemId, KEY_LANGUAGE)
    }

    fun findSchemaVersion(contentItemId: Long): String? {
        return findValueByKey(contentItemId, KEY_SCHEMA_VERSION)
    }

    fun findItemPackageVersion(contentItemId: Long): String? {
        return findValueByKey(contentItemId, KEY_ITEM_PACKAGE_VERSION)
    }

    companion object {
        private const val KEY_SCHEMA_VERSION = "schemaVersion"
        private const val KEY_ITEM_PACKAGE_VERSION = "itemPackageVersion"
        private const val KEY_LANGUAGE = "iso639_3"

        private val VALUE_QUERY: String = SQLQueryBuilder()
                .field(ContentMetaDataConst.C_VALUE)
                .table(ContentMetaDataConst.TABLE)
                .filter(ContentMetaDataConst.C_KEY, "?")
                .toString()
    }
}
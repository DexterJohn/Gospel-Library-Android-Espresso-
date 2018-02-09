/*
 * CatalogMetaDataManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.catalogmetadata

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import javax.inject.Inject


@javax.inject.Singleton
class CatalogMetaDataManager @Inject constructor(databaseManager: DatabaseManager) : CatalogMetaDataBaseManager(databaseManager) {

    /**
     * Version of the catalog
     * @return -1 if the version could not be determined
     */
    fun findVersion(): Int {
        val value = findValueByRawQuery(String::class.java, rawQuery = CATALOG_VERSION_QUERY, defaultValue = "")
        return value.toIntOrNull() ?: -1
    }

    @Deprecated("this SHOULD ONLY be used by test")
    fun updateVersion(newVersion: Long) {
        val contentValues = createNewDBToolsContentValues()
        contentValues.put(CatalogMetaDataConst.C_VALUE, newVersion)
        getWritableDatabase().update(getTableName(), contentValues, """${CatalogMetaDataConst.C_KEY} = ?""", SQLQueryBuilder.toSelectionArgs(KEY_CATALOG_VERSION))
    }

    companion object {
        private const val KEY_CATALOG_VERSION = "catalogVersion"
        private val CATALOG_VERSION_QUERY = """SELECT ${CatalogMetaDataConst.C_VALUE}
            | FROM ${CatalogMetaDataConst.TABLE}
            | WHERE ${CatalogMetaDataConst.C_KEY} = '$KEY_CATALOG_VERSION'
            | LIMIT 1;""".trimMargin()
    }
}
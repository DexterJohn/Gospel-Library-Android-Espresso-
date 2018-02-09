/*
 * TipsMetaDataManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.tips.tipsmetadata

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataConst
import javax.inject.Inject


@javax.inject.Singleton
class TipsMetaDataManager @Inject constructor(databaseManager: DatabaseManager) : TipsMetaDataBaseManager(databaseManager) {

    /**
     * Version of the tips
     * @return -1 if the version could not be determined
     */
    fun findVersion(): Int {
        val value = findValueByRawQuery(String::class.java, rawQuery = TIPS_VERSION_QUERY, defaultValue = "")
        return value.toIntOrNull() ?: -1
    }

    @Deprecated("this SHOULD ONLY be used by test")
    fun updateVersion(newVersion: Int) {
        val contentValues = createNewDBToolsContentValues()
        contentValues.put(CatalogMetaDataConst.C_VALUE, newVersion)
        getWritableDatabase().update(getTableName(), contentValues, """${CatalogMetaDataConst.C_KEY} = ?""", SQLQueryBuilder.toSelectionArgs(KEY_CATALOG_VERSION))
    }

    companion object {
        private const val KEY_CATALOG_VERSION = "catalogVersion"
        private val TIPS_VERSION_QUERY = """SELECT ${TipsMetaDataConst.C_VALUE}
            | FROM ${TipsMetaDataConst.TABLE}
            | WHERE ${TipsMetaDataConst.C_KEY} = '$KEY_CATALOG_VERSION'
            | LIMIT 1;""".trimMargin()
    }
}
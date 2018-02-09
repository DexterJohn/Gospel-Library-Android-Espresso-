/*
 * RoleCatalogManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.gl.rolecatalog

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import javax.inject.Inject


@javax.inject.Singleton
class RoleCatalogManager @Inject constructor(databaseManager: DatabaseManager) : RoleCatalogBaseManager(databaseManager) {

    fun findBaseUrlByName(name: String): String? {
        return findValueBySelection(valueType = String::class.java,
                column = RoleCatalogConst.C_BASE_URL,
                selection = RoleCatalogConst.C_NAME + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(name),
                defaultValue = null)
    }

    fun updateStatus(catalogName: String, catalogVersion: Long, installed: Boolean) {
        val values = createNewDBToolsContentValues()
        values.put(RoleCatalogConst.C_INSTALLED, installed)
        values.put(RoleCatalogConst.C_VERSION, catalogVersion)

        update(values, RoleCatalogConst.C_NAME + " = ?", SQLQueryBuilder.toSelectionArgs(catalogName))
    }

    fun findAllInstalled(): List<RoleCatalog> {
        return findAllBySelection(RoleCatalogConst.C_INSTALLED + " = ?", SQLQueryBuilder.toSelectionArgs(1))
    }

    fun findAllInstalledNames(): List<String> {
        return findAllValuesBySelection(String::class.java, RoleCatalogConst.C_NAME, RoleCatalogConst.C_INSTALLED + " = ?", SQLQueryBuilder.toSelectionArgs(1))
    }

    fun isInstalled(roleCatalogName: String): Boolean {
        return findCountBySelection(RoleCatalogConst.C_NAME + " = ? AND " + RoleCatalogConst.C_INSTALLED + " = ?", SQLQueryBuilder.toSelectionArgs(roleCatalogName, 1)) > 0
    }

    fun isUpdateNeeded(roleCatalogName: String, availableVersion: Int): Boolean {
        if (!isInstalled(roleCatalogName)) {
            return true
        }

        val existingVersion = findValueBySelection(
                valueType = Long::class.java,
                column = RoleCatalogConst.C_VERSION,
                selection = RoleCatalogConst.C_NAME + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(roleCatalogName),
                defaultValue = -1L)
        return existingVersion < availableVersion
    }

    fun deleteByCatalogName(installedCatalogName: String) {
        delete(RoleCatalogConst.C_NAME + " = ?", SQLQueryBuilder.toSelectionArgs(installedCatalogName))
    }
}
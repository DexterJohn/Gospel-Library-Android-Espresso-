/*
 * CatalogSourceManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.catalogsource

import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.types.CatalogItemSourceType


@javax.inject.Singleton
class CatalogSourceManager @javax.inject.Inject constructor(databaseManager: DatabaseManager) : CatalogSourceBaseManager(databaseManager) {

    /**
     * Each CatalogItemSourceType will have an associated name that can be used to pull the base url for content downloads
     */
    fun findSourceTypeBaseUrlName(id: Long): String? {
        return findValueByRowId(String::class.java, CatalogSourceConst.C_NAME, id, defaultValue = null)
    }

    fun findItemSourceTypeById(id: Long): CatalogItemSourceType {
        val ordinal = findValueByRowId(Int::class.java, CatalogSourceConst.C_SOURCE_TYPE, id, CatalogItemSourceType.DEFAULT.ordinal)
        return CatalogItemSourceType.values()[ordinal]
    }

    fun findSecureContentCount(): Long {
        return findCountBySelection(CatalogSourceConst.C_SOURCE_TYPE + " = " + CatalogItemSourceType.SECURE.ordinal, null)
    }
}
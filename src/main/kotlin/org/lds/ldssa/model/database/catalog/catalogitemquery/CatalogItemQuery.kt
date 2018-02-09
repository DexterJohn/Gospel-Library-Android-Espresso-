/*
 * CatalogItemQuery.kt
 *
 * Created: 02/09/2017 11:39:54
 */



package org.lds.ldssa.model.database.catalog.catalogitemquery

import org.lds.ldssa.model.database.types.CatalogItemQueryType

class CatalogItemQuery : CatalogItemQueryBaseRecord() {

    fun isCustomCollection(): Boolean {
        return type == CatalogItemQueryType.CUSTOM_COLLECTION
    }

    fun areItemsTheSame(otherItem: CatalogItemQuery?): Boolean {
        otherItem ?: return false

        return id == otherItem.id
    }

    fun areContentsTheSame(otherItem: CatalogItemQuery?): Boolean {
        otherItem ?: return false

        return when (type) {
            CatalogItemQueryType.COLLECTION_CONTENT_ITEM -> installed == otherItem.installed
            CatalogItemQueryType.CUSTOM_COLLECTION_CONTENT_ITEM -> installed == otherItem.installed && title == otherItem.title
            CatalogItemQueryType.CUSTOM_COLLECTION -> title == otherItem.title
            else -> true
        }
    }
}
/*
 * CustomCollectionItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.customcollectionitem

import org.dbtools.query.shared.filter.CompareFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.util.UserdataDbUtil
import java.util.LinkedList
import javax.inject.Inject


@javax.inject.Singleton
class CustomCollectionItemManager @Inject constructor(databaseManager: DatabaseManager,
                                                      val userdataDbUtil: UserdataDbUtil,
                                                      val itemManager: ItemManager) : CustomCollectionItemBaseManager(databaseManager) {

    private val MAX_POSITION_QUERY = SQLQueryBuilder()
            .table(CustomCollectionItemConst.TABLE)
            .field("MAX(${CustomCollectionItemConst.C_ORDER_POSITION})")
            .filter(CustomCollectionItemConst.C_CUSTOM_COLLECTION_ID, "?").buildQuery()

    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    fun findMaxOrderPosition(collectionId: Long): Int {
        return findValueByRawQuery(Int::class.java, rawQuery = MAX_POSITION_QUERY, selectionArgs = SQLQueryBuilder.toSelectionArgs(collectionId), defaultValue = -1)
    }

    fun deleteFromCollectionExternalIds(collectionId: Long, catalogItemExternalIds: List<String>): Int {
        var filter: CompareFilter? = null
        for (catalogItemExternalId in catalogItemExternalIds) {
            if (filter == null) {
                filter = CompareFilter.create(CustomCollectionItemConst.C_CATALOG_ITEM_EXTERNAL_ID, "'$catalogItemExternalId'")
            } else {
                filter.or(CustomCollectionItemConst.C_CATALOG_ITEM_EXTERNAL_ID, "'$catalogItemExternalId'")
            }
        }

        if (filter != null) {
            filter.and(CustomCollectionItemConst.C_CUSTOM_COLLECTION_ID, "?")
            return delete(filter.toString(), SQLQueryBuilder.toSelectionArgs(collectionId))
        }

        return 0
    }

    fun deleteAllByCollectionId(collectionId: Long): Int {
        return delete(CustomCollectionItemConst.C_CUSTOM_COLLECTION_ID + " = ?", SQLQueryBuilder.toSelectionArgs(collectionId))
    }

    fun findAllItemExternalIdByCollectionId(collectionId: Long): List<String> {
        return findAllValuesBySelection(String::class.java, CustomCollectionItemConst.C_CATALOG_ITEM_EXTERNAL_ID,
                CustomCollectionItemConst.C_CUSTOM_COLLECTION_ID + " = ? ", SQLQueryBuilder.toSelectionArgs(collectionId))
    }

    fun findAllItemIdsByCollectionId(collectionId: Long): List<Long> {
        val externalIds = findAllItemExternalIdByCollectionId(collectionId)
        if (externalIds.isEmpty()) {
            return LinkedList()
        }

        return itemManager.findAllIdsByExternalIds(externalIds)
    }
}
/*
 * ItemCategoryManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.itemcategory

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.item.ItemConst
import javax.inject.Inject


@javax.inject.Singleton
class ItemCategoryManager @Inject constructor(databaseManager: DatabaseManager) : ItemCategoryBaseManager(databaseManager) {

    private val NAME_BY_ITEM_ID_QUERY = SQLQueryBuilder()
            .field(ItemCategoryConst.FULL_C_NAME)
            .table(ItemCategoryConst.TABLE)
            .join(ItemConst.TABLE, ItemCategoryConst.FULL_C_ID, ItemConst.C_ITEM_CATEGORY_ID)
            .filter(ItemConst.FULL_C_ID, "?")
            .buildQuery()

    /**
     * This value should ONLY be used by analytics (this string value is NOT localized)
     */
    fun findNameByItemId(itemId: Long): String {
        return findValueByRawQuery(String::class.java, rawQuery = NAME_BY_ITEM_ID_QUERY, selectionArgs = SQLQueryBuilder.toSelectionArgs(itemId), defaultValue = "")
    }
}
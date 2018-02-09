/*
 * ItemCollectionViewManager.kt
 *
 * Generated on: 02/24/2017 03:36:23
 *
 */



package org.lds.ldssa.model.database.catalog.itemcollectionview

import org.dbtools.query.shared.JoinType
import org.dbtools.query.shared.filter.InFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionConst
import org.lds.ldssa.model.database.catalog.libraryitem.LibraryItemConst
import org.lds.ldssa.model.database.catalog.librarysection.LibrarySectionConst
import javax.inject.Inject

@javax.inject.Singleton
class ItemCollectionViewManager @Inject constructor(databaseManager: DatabaseManager) : ItemCollectionViewBaseManager(databaseManager) {

    companion object {
        val DROP_VIEW = "DROP VIEW IF EXISTS ${ItemCollectionViewConst.TABLE};"

        val CREATE_VIEW = "CREATE VIEW IF NOT EXISTS ${ItemCollectionViewConst.TABLE} AS " +
                SQLQueryBuilder()
                        .field(LibraryItemConst.FULL_C_ITEM_ID, ItemCollectionViewConst.C_ITEM_ID)
                        .field(LibraryItemConst.FULL_C_TITLE_HTML, ItemCollectionViewConst.C_ITEM_TITLE)
                        .field(LibraryItemConst.FULL_C_POSITION, ItemCollectionViewConst.C_ITEM_POSITION)
                        .field(LibraryCollectionConst.FULL_C_ID, ItemCollectionViewConst.C_COLLECTION_ID)
                        .field(LibraryCollectionConst.FULL_C_TITLE_HTML, ItemCollectionViewConst.C_COLLECTION_TITLE)
                        .field(LibraryCollectionConst.FULL_C_POSITION, ItemCollectionViewConst.C_COLLECTION_POSITION)
                        .field(LibraryItemConst.FULL_C_LIBRARY_SECTION_ID, ItemCollectionViewConst.C_SECTION_ID)
                        .field(LibrarySectionConst.FULL_C_POSITION, ItemCollectionViewConst.C_SECTION_POSITION)
                        .table(LibraryItemConst.TABLE)
                        .join(JoinType.JOIN, LibrarySectionConst.TABLE, LibrarySectionConst.FULL_C_ID, LibraryItemConst.FULL_C_LIBRARY_SECTION_ID)
                        .join(JoinType.JOIN, LibraryCollectionConst.TABLE, LibraryCollectionConst.FULL_C_ID, LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID)
                        .buildQuery()
    }

    /**
     * Ordered list for search results
     */
    fun findAllSearchItemsByItemIds(itemIds: List<Long>): List<ItemCollectionView> {
        if (itemIds.isEmpty()) {
            return emptyList()
        }

        val query = SQLQueryBuilder()
                .table(ItemCollectionViewConst.TABLE)
                .filter(InFilter.create(ItemCollectionViewConst.C_ITEM_ID, itemIds))
                .orderBy(ItemCollectionViewConst.FULL_C_COLLECTION_POSITION, ItemCollectionViewConst.FULL_C_SECTION_POSITION, ItemCollectionViewConst.FULL_C_ITEM_POSITION)
                .buildQuery()

        return findAllByRawQuery(query)
    }

}
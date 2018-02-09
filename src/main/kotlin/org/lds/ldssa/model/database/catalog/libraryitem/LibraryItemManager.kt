/*
 * LibraryItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.libraryitem

import org.dbtools.query.shared.JoinType
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionConst
import org.lds.ldssa.model.database.catalog.librarysection.LibrarySectionConst
import javax.inject.Inject


@javax.inject.Singleton
class LibraryItemManager @Inject constructor(databaseManager: DatabaseManager) : LibraryItemBaseManager(databaseManager) {

    private val GET_ITEM_SECTION_ID: String
    private val GET_ITEM_IDS_IN_COLLECTION: String

    init {
        val joins = SQLQueryBuilder()
                .table(LibraryItemConst.TABLE)
                .join(JoinType.JOIN, LibrarySectionConst.TABLE, LibrarySectionConst.FULL_C_ID, LibraryItemConst.FULL_C_LIBRARY_SECTION_ID)
                .join(JoinType.JOIN, LibraryCollectionConst.TABLE, LibraryCollectionConst.FULL_C_ID, LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID)

        val filter = SQLQueryBuilder()
                .orderBy(LibrarySectionConst.FULL_C_POSITION, LibraryCollectionConst.FULL_C_POSITION)
                .filter(LibraryItemConst.FULL_C_ITEM_ID, "?")

        val itemSectionId = SQLQueryBuilder()
                .apply(joins)
                .field(LibrarySectionConst.FULL_C_ID)
                .apply(filter)

        val itemsInCollection = SQLQueryBuilder()
                .apply(joins)
                .field(LibraryItemConst.FULL_C_ITEM_ID)
                .filter(LibraryCollectionConst.FULL_C_ID, "?")

        GET_ITEM_SECTION_ID = itemSectionId.buildQuery()
        GET_ITEM_IDS_IN_COLLECTION = itemsInCollection.buildQuery()
    }

    fun findItemSectionId(itemId: Long): Long {
        return findValueByRawQuery(Long::class.java, rawQuery = GET_ITEM_SECTION_ID, selectionArgs = SQLQueryBuilder.toSelectionArgs(itemId), defaultValue = -1L)
    }

    fun findItemIdsInCollection(collectionId: Long): List<Long> {
        return findAllValuesByRawQuery(Long::class.java, GET_ITEM_IDS_IN_COLLECTION, SQLQueryBuilder.toSelectionArgs(collectionId))
    }
}
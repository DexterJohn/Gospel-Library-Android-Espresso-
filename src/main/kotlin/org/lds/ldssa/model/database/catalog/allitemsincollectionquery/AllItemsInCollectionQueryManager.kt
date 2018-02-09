/*
 * AllItemsInCollectionQueryManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.allitemsincollectionquery

import org.dbtools.query.shared.CompareType
import org.dbtools.query.shared.filter.CompareFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.item.ItemConst
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionConst
import org.lds.ldssa.model.database.catalog.libraryitem.LibraryItemConst
import org.lds.ldssa.model.database.catalog.librarysection.LibrarySectionConst
import org.lds.ldssa.model.database.types.PlatformType
import javax.inject.Inject


@javax.inject.Singleton
class AllItemsInCollectionQueryManager @Inject constructor(databaseManager: DatabaseManager) : AllItemsInCollectionQueryBaseManager(databaseManager) {

    private var GET_ALL_ITEMS_COLLECTION: String
    private var GET_ALL_ITEMS_COLLECTION_WITH_OBSOLETE: String

    private val RECURSIVE_SECTIONS_TABLE = "sections"
    private val C_RECURSIVE_SECTIONS_TITLE = "title"
    private val C_RECURSIVE_SECTIONS_ID = "section_id"
    private val FULL_C_RECURSIVE_SECTIONS_ID = RECURSIVE_SECTIONS_TABLE + "." + C_RECURSIVE_SECTIONS_ID
    private val C_RECURSIVE_SECTIONS_COLLECTION_ID = "collection_id"

    init {
        val initial = SQLQueryBuilder()
                .table(LibrarySectionConst.TABLE)
                .field(LibrarySectionConst.FULL_C_TITLE)
                .field(LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID)
                .field(LibrarySectionConst.FULL_C_ID)
                .join(LibraryCollectionConst.TABLE, LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID, LibraryCollectionConst.FULL_C_ID)
                .filter(LibraryCollectionConst.FULL_C_ID, "?")

        val recursive = SQLQueryBuilder()
                .table(LibraryCollectionConst.TABLE)
                .field(LibrarySectionConst.FULL_C_TITLE)
                .field(LibraryCollectionConst.FULL_C_ID)
                .field(LibrarySectionConst.FULL_C_ID)
                .join(RECURSIVE_SECTIONS_TABLE, FULL_C_RECURSIVE_SECTIONS_ID, LibraryCollectionConst.FULL_C_LIBRARY_SECTION_ID)
                .join(LibrarySectionConst.TABLE, LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID, LibraryCollectionConst.FULL_C_ID)

        val platform = CompareFilter.create(ItemConst.C_PLATFORM, PlatformType.ALL.ordinal)
                .or(ItemConst.C_PLATFORM, PlatformType.ANDROID_ONLY.ordinal)

        val items = SQLQueryBuilder()
                .table(LibraryItemConst.TABLE)
                .field(LibraryItemConst.FULL_C_ITEM_ID, AllItemsInCollectionQueryConst.C_CONTENT_ITEM_ID)
                .join(RECURSIVE_SECTIONS_TABLE, FULL_C_RECURSIVE_SECTIONS_ID, LibraryItemConst.FULL_C_LIBRARY_SECTION_ID)
                .join(ItemConst.TABLE, ItemConst.FULL_C_ID, LibraryItemConst.FULL_C_ITEM_ID)
                .filter(platform)

        val notObsolete = SQLQueryBuilder()
                .filter(LibraryItemConst.FULL_C_OBSOLETE, CompareType.NOT_EQUAL, true)

        val recursiveQuery = "WITH RECURSIVE " +
                RECURSIVE_SECTIONS_TABLE + "(" + C_RECURSIVE_SECTIONS_TITLE + "," + C_RECURSIVE_SECTIONS_COLLECTION_ID + "," + C_RECURSIVE_SECTIONS_ID + ") AS " +
                SQLQueryBuilder.union(initial, recursive)

        GET_ALL_ITEMS_COLLECTION = recursiveQuery + items.clone().apply(notObsolete).buildQuery()

        GET_ALL_ITEMS_COLLECTION_WITH_OBSOLETE = recursiveQuery + items.buildQuery()
    }

    fun findAllItemIdsByCollectionId(collectionId: Long, withObsolete: Boolean): List<Long> {
        return findAllValuesByRawQuery(Long::class.java,
                rawQuery = if (withObsolete) GET_ALL_ITEMS_COLLECTION_WITH_OBSOLETE else GET_ALL_ITEMS_COLLECTION,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(collectionId))
    }

    override fun getQuery() : String {
        return GET_ALL_ITEMS_COLLECTION
    }
}
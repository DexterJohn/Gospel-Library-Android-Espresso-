/*
 * CatalogItemQueryManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.catalogitemquery

import org.dbtools.query.shared.CompareType
import org.dbtools.query.shared.filter.CompareFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.item.ItemConst
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionConst
import org.lds.ldssa.model.database.catalog.libraryitem.LibraryItemConst
import org.lds.ldssa.model.database.catalog.librarysection.LibrarySectionConst
import org.lds.ldssa.model.database.types.CatalogItemQueryType
import org.lds.ldssa.model.database.types.LibraryCollectionType
import org.lds.ldssa.model.database.types.PlatformType
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionConst
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemConst
import org.lds.ldssa.util.UserdataDbUtil
import java.util.HashMap
import javax.inject.Inject


@javax.inject.Singleton
class CatalogItemQueryManager @Inject constructor(databaseManager: DatabaseManager, private val itemManager: ItemManager, private val userdataDbUtil: UserdataDbUtil) : CatalogItemQueryBaseManager(databaseManager) {
    private val itemDataQuery: SQLQueryBuilder
    private val collectionQuery: SQLQueryBuilder
    private val itemJoinQuery: SQLQueryBuilder
    private val collectionJoinQuery: SQLQueryBuilder

    private val DEFAULT_QUERY: String
    private val DEFAULT_QUERY_WITH_OBSOLETE: String
    private val DEFAULT_ORDER_BY = " ORDER BY " + CatalogItemQueryConst.C_SECTION_POSITION + ", " + CatalogItemQueryConst.C_ITEM_POSITION

    private val CUSTOM_COLLECTION_QUERY: String
    private val CUSTOM_COLLECTION_ITEMS: String

    init {
        val defaultItemQuery = SQLQueryBuilder()
        val defaultCollectionQuery = SQLQueryBuilder()

        val platform = CompareFilter.create(ItemConst.C_PLATFORM, PlatformType.ALL.ordinal)
                .or(ItemConst.C_PLATFORM, PlatformType.ANDROID_ONLY.ordinal)

        // === Items ===
        itemDataQuery = SQLQueryBuilder()
                .field(ItemConst.FULL_C_ID, CatalogItemQueryConst.C_ID)
                .field("?", CatalogItemQueryConst.C_PARENT_ID)
                .field("?", CatalogItemQueryConst.C_LANGUAGE_ID)
                .field(ItemConst.FULL_C_TITLE, CatalogItemQueryConst.C_TITLE)
                .field(LibraryCollectionType.UNKNOWN.ordinal.toString() + "", CatalogItemQueryConst.C_COLLECTION_TYPE)
                .field(CatalogItemQueryType.COLLECTION_CONTENT_ITEM.ordinal.toString(), CatalogItemQueryConst.C_TYPE)
                .field(ItemConst.FULL_C_URI, CatalogItemQueryConst.C_URI)
                .field(LibraryItemConst.FULL_C_LIBRARY_SECTION_ID, CatalogItemQueryConst.C_LIBRARY_SECTION_ID)
                .field(LibrarySectionConst.FULL_C_ID, CatalogItemQueryConst.C_SECTION_ID)
                .field(LibrarySectionConst.FULL_C_TITLE, CatalogItemQueryConst.C_SECTION_TITLE)
                .field(ItemConst.FULL_C_EXTERNAL_ID, CatalogItemQueryConst.C_EXTERNAL_ID)
                .field(ItemConst.FULL_C_ITEM_COVER_RENDITIONS, CatalogItemQueryConst.C_ITEM_COVER_RENDITIONS)
                .field(LibrarySectionConst.FULL_C_POSITION, CatalogItemQueryConst.C_SECTION_POSITION)
                .field(LibraryItemConst.FULL_C_POSITION, CatalogItemQueryConst.C_ITEM_POSITION)
                .field("0", CatalogItemQueryConst.C_INSTALLED)

        itemJoinQuery = SQLQueryBuilder()
                .table(ItemConst.TABLE)
                .join(LibraryItemConst.TABLE, ItemConst.FULL_C_ID, LibraryItemConst.FULL_C_ITEM_ID)
                .join(LibrarySectionConst.TABLE, LibrarySectionConst.FULL_C_ID, LibraryItemConst.FULL_C_LIBRARY_SECTION_ID)
                .join(LibraryCollectionConst.TABLE, LibraryCollectionConst.FULL_C_ID, LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID)
                .filter(LibrarySectionConst.C_LIBRARY_COLLECTION_ID, "?")
                .filter(platform)

        // === Collections ===
        collectionQuery = SQLQueryBuilder()
                .field(LibraryCollectionConst.FULL_C_ID, CatalogItemQueryConst.C_ID)
                .field("?", CatalogItemQueryConst.C_PARENT_ID)
                .field("?", CatalogItemQueryConst.C_LANGUAGE_ID)
                .field(LibraryCollectionConst.FULL_C_TITLE_HTML, CatalogItemQueryConst.C_TITLE)
                .field(LibraryCollectionConst.FULL_C_TYPE, CatalogItemQueryConst.C_COLLECTION_TYPE)
                .field(CatalogItemQueryType.COLLECTION.ordinal.toString(), CatalogItemQueryConst.C_TYPE)
                .field("NULL AS " + CatalogItemQueryConst.C_URI)
                .field(LibraryCollectionConst.FULL_C_LIBRARY_SECTION_ID, CatalogItemQueryConst.C_LIBRARY_SECTION_ID)
                .field(LibrarySectionConst.FULL_C_ID, CatalogItemQueryConst.C_SECTION_ID)
                .field(LibrarySectionConst.FULL_C_TITLE, CatalogItemQueryConst.C_SECTION_TITLE)
                .field(LibraryCollectionConst.FULL_C_EXTERNAL_ID, CatalogItemQueryConst.C_EXTERNAL_ID)
                .field(LibraryCollectionConst.FULL_C_COVER_RENDITIONS, CatalogItemQueryConst.C_ITEM_COVER_RENDITIONS)
                .field(LibrarySectionConst.FULL_C_POSITION, CatalogItemQueryConst.C_SECTION_POSITION)
                .field(LibraryCollectionConst.FULL_C_POSITION, CatalogItemQueryConst.C_ITEM_POSITION)
                .field("0", CatalogItemQueryConst.C_INSTALLED)

        collectionJoinQuery = SQLQueryBuilder()
                .table(LibraryCollectionConst.TABLE)
                .join(LibrarySectionConst.TABLE, LibrarySectionConst.FULL_C_ID, LibraryCollectionConst.FULL_C_LIBRARY_SECTION_ID)
                .filter(LibrarySectionConst.C_LIBRARY_COLLECTION_ID, "?")


        // create default query
        defaultItemQuery.apply(itemDataQuery)
        defaultItemQuery.apply(itemJoinQuery)
        defaultCollectionQuery.apply(collectionQuery)
        defaultCollectionQuery.apply(collectionJoinQuery)

        val topItemExternalId = SQLQueryBuilder()
                .table(CustomCollectionItemConst.TABLE)
                .field(CustomCollectionItemConst.C_CATALOG_ITEM_EXTERNAL_ID)
                .filter(CustomCollectionConst.FULL_C_ID, CustomCollectionItemConst.FULL_C_CUSTOM_COLLECTION_ID)
                .groupBy(CustomCollectionItemConst.FULL_C_ORDER_POSITION)
                .groupBy(CustomCollectionItemConst.FULL_C_CUSTOM_COLLECTION_ID)

        val customCollectionsQuery = SQLQueryBuilder()
                .table(CustomCollectionConst.TABLE)
                .field(CustomCollectionConst.FULL_C_ID, CatalogItemQueryConst.C_ID)
                .field("0", CatalogItemQueryConst.C_LANGUAGE_ID)
                .field(CustomCollectionConst.FULL_C_TITLE, CatalogItemQueryConst.C_TITLE)
                .field("0", CatalogItemQueryConst.C_PARENT_ID)
                .field(CatalogItemQueryType.CUSTOM_COLLECTION.ordinal.toString(), CatalogItemQueryConst.C_TYPE)
                .field(LibraryCollectionType.UNKNOWN.ordinal.toString() + "", CatalogItemQueryConst.C_COLLECTION_TYPE)
                .field("NULL AS " + CatalogItemQueryConst.C_URI)
                .field("0", CatalogItemQueryConst.C_LIBRARY_SECTION_ID)
                .field("0", CatalogItemQueryConst.C_SECTION_ID)
                .field("NULL AS " + CatalogItemQueryConst.C_SECTION_TITLE)
                .field("(" + topItemExternalId.buildQuery() + ")", CatalogItemQueryConst.C_EXTERNAL_ID)
                .field("NULL AS " + CatalogItemQueryConst.C_ITEM_COVER_RENDITIONS)
                .field("0", CatalogItemQueryConst.C_SECTION_POSITION)
                .field("0", CatalogItemQueryConst.C_INSTALLED)
                .field(CustomCollectionConst.FULL_C_ORDER_POSITION, CatalogItemQueryConst.C_ITEM_POSITION)
                .orderBy(CustomCollectionConst.FULL_C_ORDER_POSITION)

        val customCollectionsItems = SQLQueryBuilder()
                .table(CustomCollectionItemConst.TABLE)
                .field(CustomCollectionItemConst.FULL_C_ID, CatalogItemQueryConst.C_ID)
                .field("0", CatalogItemQueryConst.C_LANGUAGE_ID)
                .field("'' AS " + CatalogItemQueryConst.C_TITLE)
                .field("0", CatalogItemQueryConst.C_PARENT_ID)
                .field(CatalogItemQueryType.CUSTOM_COLLECTION_CONTENT_ITEM.ordinal.toString(), CatalogItemQueryConst.C_TYPE)
                .field(LibraryCollectionType.UNKNOWN.ordinal.toString() + "", CatalogItemQueryConst.C_COLLECTION_TYPE)
                .field("NULL AS " + CatalogItemQueryConst.C_URI)
                .field("0", CatalogItemQueryConst.C_LIBRARY_SECTION_ID)
                .field(CustomCollectionConst.FULL_C_ID, CatalogItemQueryConst.C_SECTION_ID)
                .field(CustomCollectionConst.FULL_C_TITLE, CatalogItemQueryConst.C_SECTION_TITLE)
                .field(CustomCollectionItemConst.FULL_C_CATALOG_ITEM_EXTERNAL_ID, CatalogItemQueryConst.C_EXTERNAL_ID)
                .field("NULL AS " + CatalogItemQueryConst.C_ITEM_COVER_RENDITIONS)
                .field("0", CatalogItemQueryConst.C_SECTION_POSITION)
                .field(CustomCollectionItemConst.FULL_C_ORDER_POSITION, CatalogItemQueryConst.C_ITEM_POSITION)
                .field("0", CatalogItemQueryConst.C_INSTALLED)
                .join(CustomCollectionConst.TABLE, CustomCollectionConst.FULL_C_ID, CustomCollectionItemConst.FULL_C_CUSTOM_COLLECTION_ID)
                .filter(CustomCollectionItemConst.FULL_C_CUSTOM_COLLECTION_ID, "?")
                .orderBy(CustomCollectionItemConst.FULL_C_ORDER_POSITION)

        val collectionUnion = " UNION " +
                defaultCollectionQuery.toString() +
                DEFAULT_ORDER_BY

        DEFAULT_QUERY_WITH_OBSOLETE = defaultItemQuery
                .toString() + collectionUnion

        DEFAULT_QUERY = defaultItemQuery
                .filter(ItemConst.FULL_C_OBSOLETE, CompareType.NOT_EQUAL, true)
                .toString() + collectionUnion


        CUSTOM_COLLECTION_QUERY = customCollectionsQuery.buildQuery()
        CUSTOM_COLLECTION_ITEMS = customCollectionsItems.buildQuery()
    }

    private fun assembleQuery(includeObsolete: Boolean): String {
        return if (includeObsolete) DEFAULT_QUERY_WITH_OBSOLETE else DEFAULT_QUERY
    }

    override fun getQuery(): String {
        return "" // because of performance concerns, for all queries to this Manager to be RAW
    }

    fun findCatalogListView(collectionId: Long, languageId: Long, includeObsolete: Boolean): List<CatalogItemQuery> {
        return findAllByRawQuery(
                assembleQuery(includeObsolete),
                SQLQueryBuilder.toSelectionArgs(collectionId, languageId, collectionId, collectionId, languageId, collectionId)
        )
    }

    fun findAllCustomCollections(): List<CatalogItemQuery> {
        val list = findAllByRawQuery(CUSTOM_COLLECTION_QUERY,
                SQLQueryBuilder.toSelectionArgs(),
                userdataDbUtil.currentOpenedDatabaseName
        )
        return findItemDetails(list, false)
    }

    fun findCustomCollectionItemsByCustomCollectionId(customCollectionId: Long): List<CatalogItemQuery> {
        val list = findAllByRawQuery(CUSTOM_COLLECTION_ITEMS,
                SQLQueryBuilder.toSelectionArgs(customCollectionId),
                userdataDbUtil.currentOpenedDatabaseName
        )

        return findItemDetails(list, true)
    }

    /**
     * Because the userdata database and the catalog database are separate, manually find the title, parentId, coverRenditions
     */
    private fun findItemDetails(itemQueryList: List<CatalogItemQuery>, isCollectionItem: Boolean): List<CatalogItemQuery> {
        val itemExternalIdMap = HashMap<String, CatalogItemQuery>()
        for (catalogItemQuery in itemQueryList) {
            val externalId = catalogItemQuery.externalId
            if (externalId != null) {
                itemExternalIdMap.put(externalId, catalogItemQuery)
            }
        }

        val catalogItems = itemManager.findAllByExternalIds(itemExternalIdMap.keys)
        for (catalogItem in catalogItems) {
            val catalogQueryItem = itemExternalIdMap[catalogItem.externalId] ?: continue

            if (isCollectionItem) {
                catalogQueryItem.title = catalogItem.title
                catalogQueryItem.parentId = catalogItem.id
            }
            catalogQueryItem.itemCoverRenditions = catalogItem.itemCoverRenditions
        }

        return itemQueryList
    }
}
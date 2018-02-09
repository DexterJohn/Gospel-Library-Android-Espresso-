/*
 * AllSubItemsInNavCollectionQueryManager.kt
 *
 * Generated on: 04/02/2017 03:09:55
 *
 */
package org.lds.ldssa.model.database.content.allsubitemsinnavcollectionquery

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.content.navcollection.NavCollectionConst
import org.lds.ldssa.model.database.content.navitem.NavItemConst
import org.lds.ldssa.model.database.content.navsection.NavSectionConst
import org.lds.ldssa.model.database.content.subitem.SubItemConst
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject

@javax.inject.Singleton
class AllSubItemsInNavCollectionQueryManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : AllSubItemsInNavCollectionQueryBaseManager(databaseManager) {

    private var GET_ALL_ITEMS_COLLECTION: String

    private val RECURSIVE_SECTIONS_TABLE = "nav_sections"
    private val C_RECURSIVE_SECTIONS_TITLE = "title"
    private val C_RECURSIVE_SECTIONS_ID = "nav_section_id"
    private val FULL_C_RECURSIVE_SECTIONS_ID = RECURSIVE_SECTIONS_TABLE + "." + C_RECURSIVE_SECTIONS_ID
    private val C_RECURSIVE_SECTIONS_COLLECTION_ID = "nav_collection_id"

    init {
        val initial = SQLQueryBuilder()
                .table(NavSectionConst.TABLE)
                .field(NavSectionConst.FULL_C_TITLE)
                .field(NavSectionConst.FULL_C_NAV_COLLECTION_ID)
                .field(NavSectionConst.FULL_C_ID)
                .join(NavCollectionConst.TABLE, NavSectionConst.FULL_C_NAV_COLLECTION_ID, NavCollectionConst.FULL_C_ID)
                .filter(NavCollectionConst.FULL_C_ID, "?")

        val recursive = SQLQueryBuilder()
                .table(NavCollectionConst.TABLE)
                .field(NavSectionConst.FULL_C_TITLE)
                .field(NavCollectionConst.FULL_C_ID)
                .field(NavSectionConst.FULL_C_ID)
                .join(RECURSIVE_SECTIONS_TABLE, FULL_C_RECURSIVE_SECTIONS_ID, NavCollectionConst.FULL_C_NAV_SECTION_ID)
                .join(NavSectionConst.TABLE, NavSectionConst.FULL_C_NAV_COLLECTION_ID, NavCollectionConst.FULL_C_ID)

        val items = SQLQueryBuilder()
                .table(NavItemConst.TABLE)
                .field(NavItemConst.FULL_C_SUBITEM_ID, AllSubItemsInNavCollectionQueryConst.C_SUB_ITEM_ID)
                .join(RECURSIVE_SECTIONS_TABLE, FULL_C_RECURSIVE_SECTIONS_ID, NavItemConst.FULL_C_NAV_SECTION_ID)
                .join(SubItemConst.TABLE, SubItemConst.FULL_C_ID, NavItemConst.FULL_C_SUBITEM_ID)

        val recursiveQuery = "WITH RECURSIVE " +
                RECURSIVE_SECTIONS_TABLE + "(" + C_RECURSIVE_SECTIONS_TITLE + "," + C_RECURSIVE_SECTIONS_COLLECTION_ID + "," + C_RECURSIVE_SECTIONS_ID + ") AS " +
                SQLQueryBuilder.union(initial, recursive)

        GET_ALL_ITEMS_COLLECTION = recursiveQuery + items.clone().buildQuery()
    }

    fun findAllSubItemIdsByNavCollectionId(contentItemId: Long, navCollectionId: Long): List<Long> {
        return findAllValuesByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                rawQuery = GET_ALL_ITEMS_COLLECTION,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(navCollectionId))
    }

    override fun getQuery(): String {
        return GET_ALL_ITEMS_COLLECTION
    }
}
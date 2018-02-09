/*
 * ContentDirectoryItemQueryManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.content.contentdirectoryitemquery

import org.dbtools.query.shared.CompareType
import org.dbtools.query.shared.JoinType
import org.dbtools.query.shared.filter.CompareFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.content.navcollection.NavCollectionConst
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager
import org.lds.ldssa.model.database.content.navitem.NavItemConst
import org.lds.ldssa.model.database.content.navsection.NavSectionConst
import org.lds.ldssa.model.database.content.subitemcontent.SubItemContentConst
import org.lds.ldssa.model.database.types.QueryItemType
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class ContentDirectoryItemQueryManager @Inject constructor(databaseManager: DatabaseManager, val navCollectionManager: NavCollectionManager, val contentItemUtil: ContentItemUtil) : ContentDirectoryItemQueryBaseManager(databaseManager) {
    companion object {
        val QUERY: String
        val QUERY_COUNT: String
        val QUERY_ALL: String
        val SUBSELECT_QUERY: String
        private val BY_COLLECTION_QUERY: String = SQLQueryBuilder()
                .field(NavSectionConst.C_ID)
                .table(NavSectionConst.TABLE)
                .filter(NavSectionConst.C_NAV_COLLECTION_ID, "?")
                .toString()

        init {
            // === Items ===
            // contains the query parameter
            val itemFilter = CompareFilter.create(ContentDirectoryItemQueryConst.C_SECTION_ID, CompareType.IN, BY_COLLECTION_QUERY)
            val itemQuery = SQLQueryBuilder()
                    .field(NavItemConst.FULL_C_ID, ContentDirectoryItemQueryConst.C_ID)
                    .field(QueryItemType.ITEM.ordinal.toString(), ContentDirectoryItemQueryConst.C_TYPE)
                    .field(NavSectionConst.FULL_C_ID, ContentDirectoryItemQueryConst.C_SECTION_ID)
                    .field(NavSectionConst.FULL_C_TITLE, ContentDirectoryItemQueryConst.C_SECTION_TITLE)
                    .field(NavSectionConst.FULL_C_POSITION, ContentDirectoryItemQueryConst.C_SECTION_POSITION)
                    .field(NavSectionConst.FULL_C_INDENT_LEVEL, ContentDirectoryItemQueryConst.C_SECTION_INDENT_LEVEL)
                    .field(NavItemConst.FULL_C_POSITION, ContentDirectoryItemQueryConst.C_POSITION)
                    .field(NavItemConst.FULL_C_IMAGE_RENDITIONS, ContentDirectoryItemQueryConst.C_IMAGE_RENDITIONS)
                    .field(NavItemConst.FULL_C_TITLE_HTML, ContentDirectoryItemQueryConst.C_TITLE_HTML)
                    .field(NavItemConst.FULL_C_SUBTITLE, ContentDirectoryItemQueryConst.C_SUBTITLE)
                    .field(NavItemConst.FULL_C_PREVIEW, ContentDirectoryItemQueryConst.C_PREVIEW)
                    .field(NavItemConst.FULL_C_URI, ContentDirectoryItemQueryConst.C_URI)
                    .table(NavItemConst.TABLE)
                    .join(JoinType.JOIN, NavSectionConst.TABLE, NavSectionConst.FULL_C_ID, NavItemConst.C_NAV_SECTION_ID)
                    .join(JoinType.JOIN, SubItemContentConst.TABLE, SubItemContentConst.FULL_C_SUBITEM_ID, NavItemConst.C_NAV_SECTION_ID)

            // === Collections ===
            // contains the query parameter
            val collectionFilter = CompareFilter.create(ContentDirectoryItemQueryConst.C_SECTION_ID, CompareType.IN, BY_COLLECTION_QUERY)
            val collectionQuery = SQLQueryBuilder()
                    .field(NavCollectionConst.FULL_C_ID, ContentDirectoryItemQueryConst.C_ID)
                    .field(QueryItemType.COLLECTION.ordinal.toString(), ContentDirectoryItemQueryConst.C_TYPE)
                    .field(NavSectionConst.FULL_C_ID, ContentDirectoryItemQueryConst.C_SECTION_ID)
                    .field(NavSectionConst.FULL_C_TITLE, ContentDirectoryItemQueryConst.C_SECTION_TITLE)
                    .field(NavSectionConst.FULL_C_POSITION, ContentDirectoryItemQueryConst.C_SECTION_POSITION)
                    .field(NavSectionConst.FULL_C_INDENT_LEVEL, ContentDirectoryItemQueryConst.C_SECTION_INDENT_LEVEL)
                    .field(NavCollectionConst.FULL_C_POSITION, ContentDirectoryItemQueryConst.C_POSITION)
                    .field(NavCollectionConst.FULL_C_IMAGE_RENDITIONS, ContentDirectoryItemQueryConst.C_IMAGE_RENDITIONS)
                    .field(NavCollectionConst.FULL_C_TITLE_HTML, ContentDirectoryItemQueryConst.C_TITLE_HTML)
                    .field(NavCollectionConst.FULL_C_SUBTITLE, ContentDirectoryItemQueryConst.C_SUBTITLE)
                    .field("\"\"", ContentDirectoryItemQueryConst.C_PREVIEW)
                    .field(NavCollectionConst.FULL_C_URI, ContentDirectoryItemQueryConst.C_URI)
                    .table(NavCollectionConst.TABLE)
                    .join(JoinType.JOIN, NavSectionConst.TABLE, NavSectionConst.FULL_C_ID, NavItemConst.C_NAV_SECTION_ID)

            QUERY_ALL = SQLQueryBuilder()
                    .orderBy(ContentDirectoryItemQueryConst.C_SECTION_POSITION, ContentDirectoryItemQueryConst.C_POSITION)
                    .table(SQLQueryBuilder.union(itemQuery, collectionQuery)).toString()

            // create base query
            val baseQuery = SQLQueryBuilder()
                    .table(SQLQueryBuilder.union(itemQuery.filter(itemFilter), collectionQuery.filter(collectionFilter)))

            // create main query (query with sub query)
            QUERY = baseQuery.clone()
                    .orderBy(ContentDirectoryItemQueryConst.C_SECTION_POSITION, ContentDirectoryItemQueryConst.C_POSITION)
                    .toString()

            // create count query
            QUERY_COUNT = baseQuery.clone()
                    .field("count(1)")
                    .toString()

            // item by collection
            SUBSELECT_QUERY = "SELECT * FROM (" + QUERY + ") WHERE " + ContentDirectoryItemQueryConst.C_ID + " = ? "
        }
    }

    override fun getQuery() : String {
        return QUERY
    }

    fun findAllByContentItemId(contentItemId: Long): List<ContentDirectoryItemQuery> {
        return findAllByCollectionId(contentItemId, navCollectionManager.findRootCollectionId(contentItemId))
    }

    fun findAllByCollectionId(contentItemId: Long, collectionId: Long): List<ContentDirectoryItemQuery> {
        return findAllByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                rawQuery = QUERY,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(collectionId, collectionId))
    }

    fun findCountByContentItemId(contentItemId: Long): Int {
        val rootCollectionId = navCollectionManager.findRootCollectionId(contentItemId)
        return findCountByCollectionId(contentItemId, rootCollectionId)
    }

    fun findCountByCollectionId(contentItemId: Long, collectionId: Long): Int {
        if (collectionId == 0L) {
            return findCountByContentItemId(contentItemId)
        }
        return findValueByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Int::class.java,
                rawQuery = QUERY_COUNT,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(collectionId, collectionId),
                defaultValue = 0)
    }


}
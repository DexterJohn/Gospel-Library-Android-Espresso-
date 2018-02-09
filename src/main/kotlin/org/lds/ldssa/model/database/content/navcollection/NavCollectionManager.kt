/*
 * NavCollectionManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.navcollection

import org.dbtools.query.shared.CompareType
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.content.navitem.NavItemConst
import org.lds.ldssa.model.database.content.navsection.NavSectionConst
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class NavCollectionManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : NavCollectionBaseManager(databaseManager) {
    companion object {
        private val ROOT_COLLECTION_ID_QUERY: String = SQLQueryBuilder()
                .field(NavCollectionConst.C_ID)
                .table(NavCollectionConst.TABLE)
                .filter(NavCollectionConst.C_NAV_SECTION_ID, CompareType.IS_NULL)
                .toString()
        private val ROOT_COLLECTION_URI_QUERY: String = SQLQueryBuilder()
                .field(NavCollectionConst.C_URI)
                .table(NavCollectionConst.TABLE)
                .filter(NavCollectionConst.C_NAV_SECTION_ID, CompareType.IS_NULL)
                .toString()
        private val CHAPTER_COUNT_QUERY: String = SQLQueryBuilder()
                .field("count(1)")
                .table(NavItemConst.TABLE)
                .join(NavSectionConst.TABLE, NavItemConst.FULL_C_NAV_SECTION_ID, NavSectionConst.FULL_C_ID)
                .filter(NavSectionConst.FULL_C_NAV_COLLECTION_ID, "?")
                .buildQuery()

        private val NAV_COLLECTION_PARENT: String
        private val NAV_COLLECTION_PARENT_TITLE_BY_COLLECTION: String
        private val NAV_COLLECTION_PARENT_TITLE_BY_ITEM: String
        private val FIRST_ITEM_ID_QUERY: String
        private val INTERNAL_ITEM_COUNT_QUERY: String
        private val FULL_C_NAV_COLLECTION_ID: String
        private val ALL_KEYS_FULL: Array<String>
        private val SECTION_ID = "SECTION_ID"
        private val SECTIONS = "SECTIONS"
        val ITEM_COUNT_QUERY: String


        init {
            FULL_C_NAV_COLLECTION_ID = "CASE WHEN " + NavCollectionConst.FULL_C_ID + " = " + "(" +
                    ROOT_COLLECTION_ID_QUERY + ") THEN 0 ELSE " +
                    NavCollectionConst.FULL_C_ID + " END AS " + NavCollectionConst.C_ID

            ALL_KEYS_FULL = arrayOf(FULL_C_NAV_COLLECTION_ID, NavCollectionConst.FULL_C_NAV_SECTION_ID, NavCollectionConst.FULL_C_POSITION, NavCollectionConst.FULL_C_TITLE_HTML, NavCollectionConst.FULL_C_SUBTITLE, NavCollectionConst.FULL_C_URI)

            val parent = SQLQueryBuilder()
                    .table(NavCollectionConst.TABLE)
                    .join(NavSectionConst.TABLE, NavSectionConst.FULL_C_NAV_COLLECTION_ID, NavCollectionConst.FULL_C_ID)
                    .join(NavItemConst.TABLE, NavItemConst.FULL_C_NAV_SECTION_ID, NavSectionConst.FULL_C_ID)
                    .filter(NavItemConst.FULL_C_ID, "?")

            NAV_COLLECTION_PARENT = parent.clone().fields(*ALL_KEYS_FULL).buildQuery()
            NAV_COLLECTION_PARENT_TITLE_BY_ITEM = parent.clone()
                    .field(NavCollectionConst.FULL_C_TITLE_HTML)
                    .buildQuery()

            val navSection = SQLQueryBuilder()
                    .field(NavSectionConst.FULL_C_ID)
                    .table(NavSectionConst.TABLE)
                    .join(NavCollectionConst.TABLE, NavCollectionConst.FULL_C_ID, NavSectionConst.FULL_C_NAV_COLLECTION_ID)
                    .filter(NavCollectionConst.FULL_C_ID, "%s")

            val navCollection = SQLQueryBuilder()
                    .field(NavSectionConst.FULL_C_ID)
                    .table(NavCollectionConst.TABLE)
                    .join(SECTIONS, SECTIONS + "." + SECTION_ID, NavCollectionConst.FULL_C_NAV_SECTION_ID)
                    .join(NavSectionConst.TABLE, NavSectionConst.FULL_C_NAV_COLLECTION_ID, NavCollectionConst.FULL_C_ID)

            val count = SQLQueryBuilder()
                    .table(NavItemConst.TABLE)
                    .join(SECTIONS, SECTIONS + "." + SECTION_ID, NavItemConst.FULL_C_NAV_SECTION_ID)

            val countQuery = "WITH RECURSIVE " + SECTIONS + "(" + SECTION_ID + ") AS " +
                    SQLQueryBuilder.union(navSection, navCollection)

            ITEM_COUNT_QUERY = countQuery + count.clone().field("COUNT(*)").buildQuery()
            INTERNAL_ITEM_COUNT_QUERY = String.format(ITEM_COUNT_QUERY, "?")
            FIRST_ITEM_ID_QUERY = String.format(countQuery + count.clone()
                    .field(NavItemConst.FULL_C_SUBITEM_ID)
                    .orderBy(NavItemConst.FULL_C_POSITION)
                    .buildQuery() + " LIMIT 1", "?")

            val collectionBottom = "c1"
            val collectionTop = "c2"

            NAV_COLLECTION_PARENT_TITLE_BY_COLLECTION = SQLQueryBuilder()
                    .table(NavCollectionConst.TABLE, collectionBottom)
                    .field(collectionTop + "." + NavCollectionConst.C_TITLE_HTML)
                    .join(NavSectionConst.TABLE, NavSectionConst.FULL_C_ID, collectionBottom + "." + NavCollectionConst.C_NAV_SECTION_ID)
                    .join(NavCollectionConst.TABLE + " " + collectionTop, collectionTop + "." + NavCollectionConst.C_ID, NavSectionConst.FULL_C_NAV_COLLECTION_ID)
                    .filter(collectionBottom + "." + NavCollectionConst.C_ID, "?")
                    .buildQuery()
        }
    }


    fun findRootCollectionId(contentItemId: Long): Long {
        return findValueByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                rawQuery = ROOT_COLLECTION_ID_QUERY,
                defaultValue = 0L)
    }

    fun findRootCollectionUri(contentItemId: Long): String {
        return findValueByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                rawQuery = ROOT_COLLECTION_URI_QUERY,
                defaultValue = "")
    }

    fun findCollectionTitleById(contentItemId: Long, navCollectionId: Long): String {
        return findValueByRowId(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                column = NavCollectionConst.C_TITLE_HTML,
                rowId = navCollectionId,
                defaultValue = "")
    }

    fun findCollectionParentTitleById(contentItemId: Long, navCollectionId: Long): String {
        return findValueByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                rawQuery = NAV_COLLECTION_PARENT_TITLE_BY_COLLECTION,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(navCollectionId),
                defaultValue = "")
    }

    fun findIdByUri(contentItemId: Long, navCollectionUri: String): Long {
        return findValueBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                column = NavCollectionConst.C_ID,
                selection = NavCollectionConst.C_URI + " = ? ",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(navCollectionUri),
                defaultValue = 0L)
    }

    fun findUriById(contentItemId: Long, id: Long): String {
        return findValueByRowId(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                column = NavCollectionConst.C_URI,
                rowId = id,
                defaultValue = "")
    }

    fun findCountOfItemsByNavCollectionId(contentItemId: Long, navCollectionId: Long): Long {
        return findCountByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                rawQuery = INTERNAL_ITEM_COUNT_QUERY,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(navCollectionId))
    }

    fun findFirstItemByNavCollectionId(contentItemId: Long, navCollectionId: Long): Long {
        return findValueByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = Long::class.java,
                rawQuery = FIRST_ITEM_ID_QUERY,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(navCollectionId),
                defaultValue = 0L)
    }

    fun findAllSearchSuggestionGotoItems(contentItemId: Long): List<NavCollection> {
        return findAllBySelection(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                selection = NavCollectionConst.C_NAV_SECTION_ID + " NOT NULL")
    }

    fun findAllDCSearchSuggestionGotoItems(contentItemId: Long): List<NavCollection> {
        return findAll(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId))
    }

    fun findChapterCountById(contentItemId: Long, id: Long): Long {
        return findCountByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                rawQuery = CHAPTER_COUNT_QUERY,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(id))
    }

    fun findTitleById(contentItemId: Long, navCollectionId: Long): String {
        return findValueByRowId(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                valueType = String::class.java,
                column = NavCollectionConst.C_TITLE_HTML,
                rowId = navCollectionId, defaultValue = "")
    }

}
/*
 * NavigationTrailQueryManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.navigationtrailquery

import android.app.Application
import org.apache.commons.lang3.StringUtils
import org.dbtools.query.shared.CompareType
import org.dbtools.query.shared.filter.CompareFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.R
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionConst
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionManager
import org.lds.ldssa.model.database.catalog.librarysection.LibrarySectionConst
import org.lds.ldssa.model.database.content.navcollection.NavCollection
import org.lds.ldssa.model.database.content.navcollection.NavCollectionConst
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager
import org.lds.ldssa.model.database.content.navitem.NavItemConst
import org.lds.ldssa.model.database.content.navitem.NavItemManager
import org.lds.ldssa.model.database.content.navsection.NavSectionConst
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionConst
import org.lds.ldssa.util.ContentItemUtil
import org.lds.ldssa.ux.annotations.notes.NotesActivity
import org.lds.ldssa.ux.study.plans.StudyPlansActivity
import java.util.ArrayList
import javax.inject.Inject


@javax.inject.Singleton
class NavigationTrailQueryManager @Inject constructor(databaseManager: DatabaseManager, val application: Application, val contentItemUtil: ContentItemUtil, val libraryCollectionManager: LibraryCollectionManager, val navItemManager: NavItemManager) : NavigationTrailQueryBaseManager(databaseManager) {
    companion object {
        private val GET_TRAIL_BY_CATALOG_COLLECTION: String
        private val GET_TRAIL_BY_CATALOG_COLLECTION_FILTER_SELF: String
        private val GET_TRAIL_BY_CATALOG_SECTION: String
        private val GET_TRAIL_BY_CONTENT_COLLECTION: String
        private val GET_TRAIL_BY_CONTENT_ITEM: String
        private val GET_TRAIL_BY_CUSTOM_COLLECTION: String
        private val ROOT_COLLECTION_ID_QUERY: String = SQLQueryBuilder()
                .field(NavCollectionConst.C_ID)
                .table(NavCollectionConst.TABLE)
                .filter(NavCollectionConst.C_NAV_SECTION_ID, CompareType.IS_NULL)
                .toString()


        private val TRAIL_TABLE = "trail"
        private val TRAIL_ID = "_id"
        private val TRAIL_TITLE = "title"
        private val TRAIL_SECTION_ID = "section_id"
        private val TRAIL_TYPE = "type"
        private val TRAIL_LEVEL = "level"
        private val TRAIL_CONTENT_ITEM_ID = "content_item_id"

        init {
            val catalogFields = SQLQueryBuilder()
                    .field(LibraryCollectionConst.FULL_C_ID)
                    .field(LibraryCollectionConst.FULL_C_TITLE_HTML)
                    .field(LibraryCollectionConst.FULL_C_LIBRARY_SECTION_ID)
                    .field("0", NavigationTrailQueryConst.C_CONTENT_ITEM_ID)
                    .field(ScreenSourceType.CATALOG_DIRECTORY.ordinal.toString(), NavigationTrailQueryConst.C_TYPE)

            val contentFields = SQLQueryBuilder()
                    .field(NavCollectionConst.FULL_C_ID)
                    .field(NavCollectionConst.FULL_C_TITLE_HTML)
                    .field(NavCollectionConst.FULL_C_NAV_SECTION_ID)
                    .field("?", NavigationTrailQueryConst.C_CONTENT_ITEM_ID)
                    .field(ScreenSourceType.CONTENT_DIRECTORY.ordinal.toString(), NavigationTrailQueryConst.C_TYPE)

            val catalogInitial = SQLQueryBuilder()
                    .table(LibraryCollectionConst.TABLE)
                    .apply(catalogFields)
                    .field("0", TRAIL_LEVEL)

            val contentInitial = SQLQueryBuilder()
                    .table(NavCollectionConst.TABLE)
                    .apply(contentFields)
                    .field("0", TRAIL_LEVEL)

            val byCatalogSection = SQLQueryBuilder()
                    .apply(catalogInitial)
                    .join(LibrarySectionConst.TABLE, LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID, LibraryCollectionConst.FULL_C_ID)
                    .filter(LibrarySectionConst.FULL_C_ID, "?")

            val byContentItem = SQLQueryBuilder()
                    .apply(contentInitial)
                    .join(NavSectionConst.TABLE, NavSectionConst.FULL_C_NAV_COLLECTION_ID, NavCollectionConst.FULL_C_ID)
                    .join(NavItemConst.TABLE, NavItemConst.FULL_C_NAV_SECTION_ID, NavSectionConst.FULL_C_ID)
                    .filter(NavItemConst.FULL_C_ID, "?")

            val byCatalogCollection = SQLQueryBuilder()
                    .apply(catalogInitial)
                    .join(LibrarySectionConst.TABLE, LibrarySectionConst.FULL_C_ID, LibraryCollectionConst.FULL_C_LIBRARY_SECTION_ID)
                    .filter(LibraryCollectionConst.FULL_C_ID, "?")

            val byContentCollection = SQLQueryBuilder()
                    .apply(contentInitial)
                    .join(NavSectionConst.TABLE, NavSectionConst.FULL_C_ID, NavCollectionConst.FULL_C_NAV_SECTION_ID)
                    .filter(NavCollectionConst.FULL_C_ID, "?")

            val catalogRecursive = SQLQueryBuilder()
                    .table(LibrarySectionConst.TABLE)
                    .apply(catalogFields)
                    .field(TRAIL_LEVEL + " + 1", TRAIL_LEVEL)
                    .join(TRAIL_TABLE, getFullTrailField(TRAIL_SECTION_ID), LibrarySectionConst.FULL_C_ID)
                    .join(LibraryCollectionConst.TABLE, LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID, LibraryCollectionConst.FULL_C_ID)

            val contentRecursive = SQLQueryBuilder()
                    .table(NavSectionConst.TABLE)
                    .apply(contentFields)
                    .field(TRAIL_LEVEL + " + 1", TRAIL_LEVEL)
                    .join(TRAIL_TABLE, getFullTrailField(TRAIL_SECTION_ID), NavSectionConst.FULL_C_ID)
                    .join(NavCollectionConst.TABLE, NavSectionConst.FULL_C_NAV_COLLECTION_ID, NavCollectionConst.FULL_C_ID)

            val items = SQLQueryBuilder()
                    .table(TRAIL_TABLE)
                    .orderBy(TRAIL_LEVEL)

            val contentItems = SQLQueryBuilder()
                    .table(TRAIL_TABLE)
                    .fields(TRAIL_TITLE, TRAIL_TYPE, TRAIL_CONTENT_ITEM_ID)
                    .field("CASE WHEN " + TRAIL_ID + " = " + "(" +
                            ROOT_COLLECTION_ID_QUERY + ") THEN " + NavCollection.ROOT_NAV_COLLECTION_ID + " ELSE " +
                            TRAIL_ID + " END", TRAIL_ID)
                    .filter(CompareFilter.create("(" + String.format(NavCollectionManager.ITEM_COUNT_QUERY, getFullTrailField(TRAIL_ID)) + ")", CompareType.GREATERTHAN, 1))
                    .orderBy(TRAIL_LEVEL)

            val idFilter = SQLQueryBuilder()
                    .filter(TRAIL_ID, CompareType.NOT_EQUAL, "?")

            val recursiveQuery = "WITH RECURSIVE " +
                    TRAIL_TABLE + "(" +
                    StringUtils.join(arrayOf(TRAIL_ID, TRAIL_TITLE, TRAIL_SECTION_ID, TRAIL_CONTENT_ITEM_ID, TRAIL_TYPE, TRAIL_LEVEL), ',') +
                    ") AS "

            GET_TRAIL_BY_CATALOG_COLLECTION_FILTER_SELF = recursiveQuery +
                    SQLQueryBuilder.union(byCatalogCollection, catalogRecursive) +
                    items.clone().apply(idFilter).buildQuery()

            GET_TRAIL_BY_CATALOG_COLLECTION = recursiveQuery +
                    SQLQueryBuilder.union(byCatalogCollection, catalogRecursive) +
                    items.clone().buildQuery()

            GET_TRAIL_BY_CATALOG_SECTION = recursiveQuery +
                    SQLQueryBuilder.union(byCatalogSection, catalogRecursive) +
                    items.buildQuery()

            GET_TRAIL_BY_CONTENT_COLLECTION = recursiveQuery +
                    SQLQueryBuilder.union(byContentCollection, contentRecursive) +
                    contentItems.clone().apply(idFilter)
                            .field(TRAIL_LEVEL + " - 1", TRAIL_LEVEL)
                            .buildQuery()

            GET_TRAIL_BY_CONTENT_ITEM = recursiveQuery +
                    SQLQueryBuilder.union(byContentItem, contentRecursive) +
                    contentItems.buildQuery()

            GET_TRAIL_BY_CUSTOM_COLLECTION = SQLQueryBuilder()
                    .table(CustomCollectionConst.TABLE)
                    .field(CustomCollectionConst.FULL_C_ID, NavigationTrailQueryConst.C_ID)
                    .field(CustomCollectionConst.FULL_C_TITLE, NavigationTrailQueryConst.C_TITLE)
                    .field("0", NavigationTrailQueryConst.C_CONTENT_ITEM_ID)
                    .field(ScreenSourceType.CUSTOM_CATALOG_DIRECTORY.ordinal.toString(), NavigationTrailQueryConst.C_TYPE)
                    .filter(CustomCollectionConst.C_ID, "?").buildQuery()
        }

        private fun getFullTrailField(field: String) = "$TRAIL_TABLE.$field"
    }

    override fun getQuery() = GET_TRAIL_BY_CATALOG_COLLECTION_FILTER_SELF

    fun findAllForCollection(collectionId: Long, filterSelf: Boolean): List<NavigationTrailQuery> {
        return if (filterSelf) {
            findAllByRawQuery(GET_TRAIL_BY_CATALOG_COLLECTION_FILTER_SELF, SQLQueryBuilder.toSelectionArgs(collectionId, collectionId))
        } else {
            findAllByRawQuery(GET_TRAIL_BY_CATALOG_COLLECTION, SQLQueryBuilder.toSelectionArgs(collectionId))
        }
    }

    fun findAllForCatalogDirectory(collectionId: Long) = findAllForCollection(collectionId, true)

    fun findDefaultAllForCustomCollection(languageId: Long): List<NavigationTrailQuery> {
        // todo remove???  (as long as we don't have to show custom collection in the trail when clicking on content)
        //        NavigationTrailQuery customCollection = findByRawQuery(userdataDbUtil.getCurrentOpenedDatabaseName(),
        //                GET_TRAIL_BY_CUSTOM_COLLECTION, SQLQueryBuilder.toSelectionArgs(customCollectionId));
        //        list.add(customCollection);

        return listOf(createTrailItemForRootCollection(languageId))
    }

    fun findDefaultAllForNotesOrTips(languageId: Long) = listOf(createTrailItemForRootCollection(languageId))

    fun findDefaultAllForStudyPlans(languageId: Long) = listOf(createTrailItemForStudyPlans(languageId), createTrailItemForRootCollection(languageId))

    fun findDefaultAllForNotebook(languageId: Long) = listOf(createTrailItemForNotebookNotes(), createTrailItemForRootCollection(languageId))

    fun findDefaultAllForAnnotationView(languageId: Long) = listOf(createTrailItemForRootCollection(languageId))

    fun findDefaultAllForContentDirectory(contentItemId: Long, navCollectionId: Long): List<NavigationTrailQuery> {
        // NOTE: Due to the incredible complexity and the many permutations of tab history in trying to determine the navigation trail of a content item
        // We intentionally look for the default trail for a content item (this dramatically reduces the lines of code and the testing, and should
        // be correct more than 95% of the time (there are very few content items that have more than one navigation trail...)

        // catalog trail
        val catalogTrail = findDefaultAllCatalogTrailForContent(contentItemId)

        // content directory trail
        val contentTrail = findAllByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                rawQuery = GET_TRAIL_BY_CONTENT_COLLECTION,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(contentItemId, navCollectionId, contentItemId, navCollectionId))

        val fullTrail = ArrayList<NavigationTrailQuery>()

        // combine everything together
        fullTrail.addAll(contentTrail)
        fullTrail.addAll(catalogTrail)

        return fullTrail
    }

    fun findAllForContent(contentItemId: Long, subItemId: Long): List<NavigationTrailQuery> {
        // NOTE: Due to the incredible complexity and the many permutations of tab history in trying to determine the navigation trail of a content item
        // We intentionally look for the default trail for a content item (this dramatically reduces the lines of code and the testing, and should
        // be correct more than 95% of the time (there are very few content items that have more than one navigation trail...)
        return findDefaultAllCatalogAndContentTrailBySubItemId(contentItemId, subItemId)
    }

    /**
     * When there is no tab history, use this method to find the default catalog history
     */
    private fun findDefaultAllCatalogTrailForContent(contentItemId: Long): List<NavigationTrailQuery> {
        val collectionId = libraryCollectionManager.findDefaultIdByContentItemId(contentItemId)
        return findAllForCollection(collectionId, false)
    }

    /**
     * Default/Fallback navigation trail for a Content item
     */
    private fun findDefaultAllCatalogAndContentTrailBySubItemId(contentItemId: Long, subItemId: Long): List<NavigationTrailQuery> {
        val fullTrail = ArrayList<NavigationTrailQuery>()
        // add the content trail
        fullTrail.addAll(findDefaultAllContentTrailBySubItemId(contentItemId, subItemId))

        // add the catalog trail
        fullTrail.addAll(findDefaultAllCatalogTrailForContent(contentItemId))

        return fullTrail
    }

    private fun findDefaultAllContentTrailBySubItemId(contentItemId: Long, navItemId: Long): List<NavigationTrailQuery> {
        return findDefaultAllContentTrailByNavItemId(contentItemId, navItemManager.findIdBySubItemId(contentItemId, navItemId))
    }

    private fun findDefaultAllContentTrailByNavItemId(contentItemId: Long, navItemId: Long): List<NavigationTrailQuery> {
        return findAllByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                rawQuery = GET_TRAIL_BY_CONTENT_ITEM,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(contentItemId, navItemId, contentItemId))
    }

    private fun createTrailItemForRootCollection(languageId: Long): NavigationTrailQuery {
        val root = libraryCollectionManager.findRootCollection(languageId)
        val main = NavigationTrailQuery()

        if (root != null) {
            main.title = root.titleHtml
            main.type = ScreenSourceType.CATALOG_DIRECTORY
            main.id = root.id
        }

        return main
    }

    private fun createTrailItemForNotebookNotes() = NavigationTrailQuery().apply {
        title = application.getString(R.string.notes)
        type = ScreenSourceType.NOTES
        id = NotesActivity.TAB_POSITION_NOTEBOOK.toLong()
    }

    private fun createTrailItemForStudyPlans(languageId: Long) = NavigationTrailQuery().apply {
        title = application.getString(R.string.study_plans)
        type = ScreenSourceType.STUDY_PLANS
        id = StudyPlansActivity.TAB_POSITION_STUDY_PLANS.toLong()
    }
}
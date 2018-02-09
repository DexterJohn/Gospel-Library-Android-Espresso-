/*
 * SearchSuggestionManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.search.searchsuggestion

import android.app.Application
import android.support.annotation.StringRes
import org.apache.commons.lang3.StringUtils
import org.dbtools.query.shared.CompareType
import org.dbtools.query.shared.filter.CompareFilter
import org.dbtools.query.shared.filter.InFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.R
import org.lds.ldssa.model.GLContentContext
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.DatabaseManagerConst
import org.lds.ldssa.model.database.catalog.item.ItemConst
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.catalog.language.LanguageManager
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionConst
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionManager
import org.lds.ldssa.model.database.catalog.libraryitem.LibraryItemConst
import org.lds.ldssa.model.database.catalog.librarysection.LibrarySectionConst
import org.lds.ldssa.model.database.catalog.searchgotoquery.SearchGotoQuery
import org.lds.ldssa.model.database.catalog.searchgotoquery.SearchGotoQueryManager
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager
import org.lds.ldssa.model.database.content.navitem.NavItemConst
import org.lds.ldssa.model.database.types.ItemCategoryType
import org.lds.ldssa.model.database.types.PlatformType
import org.lds.ldssa.model.database.types.SearchSuggestionType
import org.lds.ldssa.model.database.userdata.note.NoteManager
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.search.SearchUtil
import org.lds.ldssa.search.goto.GotoParts
import org.lds.ldssa.search.goto.GotoTextParser
import org.lds.ldssa.util.ContentItemUtil
import org.lds.mobile.util.LdsTimeUtil
import java.util.ArrayList
import java.util.Arrays
import javax.inject.Inject


@javax.inject.Singleton
class SearchSuggestionManager @Inject constructor(databaseManager: DatabaseManager,
                                                  val application: Application,
                                                  val searchUtil: SearchUtil,
                                                  val timeUtil: LdsTimeUtil,
                                                  val notebookManager: NotebookManager,
                                                  val searchGotoQueryManager: SearchGotoQueryManager,
                                                  val noteManager: NoteManager,
                                                  val itemManager: ItemManager,
                                                  val libraryCollectionManager: LibraryCollectionManager,
                                                  val languageManager: LanguageManager,
                                                  val navCollectionManager: NavCollectionManager,
                                                  val contentItemUtil: ContentItemUtil) : SearchSuggestionBaseManager(databaseManager) {

    private val DEFAULT_GOTO_LIMIT = 20
    private val DEFAULT_CONTENT_ITEM_LIMIT = 10
    private val DEFAULT_NOTEBOOK_LIMIT = 4
    private val DEFAULT_NOTE_TITLE_LIMIT = 4

    private val SEARCH_ITEM_VIEW_QUERY = SQLQueryBuilder()
            .table(ItemConst.TABLE)
            .field(ItemConst.FULL_C_ID)
            .filter(ItemConst.FULL_C_OBSOLETE, CompareType.NOT_EQUAL, true)
            .filter(ItemConst.C_PLATFORM, CompareType.IN, Arrays.asList(PlatformType.ALL.ordinal, PlatformType.ANDROID_ONLY.ordinal))

    fun findSuggestions(languageId: Long, _searchText: String, glContentContext: GLContentContext): List<SearchSuggestion> {
        var searchText = _searchText
        val allSuggestions = ArrayList<SearchSuggestion>()

        // SPECIAL... handle English shortcuts
        val lowerText = _searchText.toLowerCase()
        if (languageId == 1L) {
            searchText = when {
                lowerText.startsWith("d&c") -> DOCTRINE_AND_C + searchText.substring(3)
                lowerText.startsWith("dc") || searchText.startsWith("d&") -> DOCTRINE_AND_C + searchText.substring(2)
                lowerText.startsWith("bd") -> BIBLE_D + searchText.substring(2)
                lowerText.startsWith("tg") -> TOPICAL_G + searchText.substring(2)
                /*todo these should all be in a database table and/or translated in the strings file
                lowerText.startsWith("bom") -> BOOK_OF_M + searchText.substring(3)
                lowerText.startsWith("bm") -> BOOK_OF_M + searchText.substring(2)
                lowerText.startsWith("pogp") -> PEARL_OF_GREAT_P + searchText.substring(4)
                lowerText.startsWith("pgp") -> PEARL_OF_GREAT_P + searchText.substring(3)
                lowerText.startsWith("pg") -> PEARL_OF_GREAT_P + searchText.substring(2)
                lowerText.startsWith("ot") -> OLD_T + searchText.substring(2)
                lowerText.startsWith("nt") -> NEW_T + searchText.substring(2)
                lowerText.startsWith("gs") -> GUIDE_TO_THE_S + searchText.substring(2)*/
                else -> searchText
            }
        }

        // 1. Add Find on Page or Find in XXX
        val findOnXSuggestion = findFindOnXSuggestion(searchText, glContentContext)
        if (findOnXSuggestion != null) {
            allSuggestions.add(findOnXSuggestion)
        }

        // 2. Goto items (Books IN the Book of Mormon, D&C, etc)
        allSuggestions.addAll(findGotoSuggestions(languageId, searchText, DEFAULT_GOTO_LIMIT))

        // 3. Find Content Items (Book of Mormon, October Ensign, Primary 2)
        allSuggestions.addAll(findContentItemSuggestions(languageId, searchText, DEFAULT_CONTENT_ITEM_LIMIT))

        // 4. Find Collections (Scriptures, General Conference, Leaders) NOTE: Cannot do this because collections don't have a language column and the search text is html (with non-breaking spaces)
        // allSuggestions.addAll(libraryCollectionManager.findAllSearchSuggestions(searchText, DEFAULT_COLLECTION_LIMIT));

        // 5. Notebook titles
        allSuggestions.addAll(notebookManager.findAllSearchSuggestions(searchText, DEFAULT_NOTEBOOK_LIMIT))

        // 6. Note titles
        allSuggestions.addAll(noteManager.findAllSearchSuggestions(searchText, DEFAULT_NOTE_TITLE_LIMIT))

        return allSuggestions
    }

    private fun findFindOnXSuggestion(searchText: String, glContentContext: GLContentContext): SearchSuggestion? {
        @StringRes
        val findXinXStringRes: Int
        val findXinXText: String
        if (searchUtil.isExactPhraseSearchText(searchText)) {
            findXinXStringRes = R.string.find_exact_x_in_x
            findXinXText = searchUtil.removeSearchQuotes(searchText)
        } else {
            findXinXStringRes = R.string.find_x_in_x
            findXinXText = searchText
        }

        if (glContentContext.subItemId > 0) {
            return SearchSuggestion().apply {
                type = SearchSuggestionType.FIND_ON_PAGE
                id = glContentContext.subItemId
                contentItemId = glContentContext.contentItemId
                title = application.getString(R.string.find_x_on_page, searchUtil.removeSearchQuotes(searchText))
            }
        } else if (glContentContext.navCollectionId > 0 && glContentContext.contentItemId > 0) {
            val navCollectionItemTitle = navCollectionManager.findTitleById(glContentContext.contentItemId, glContentContext.navCollectionId)

            return SearchSuggestion().apply {
                type = SearchSuggestionType.FIND_IN_NAV_COLLECTION
                id = glContentContext.navCollectionId
                contentItemId = glContentContext.contentItemId
                title = application.getString(findXinXStringRes, findXinXText, navCollectionItemTitle)
            }
        } else if (glContentContext.contentItemId > 0) {
            val contentItemTitle = itemManager.findTitleById(glContentContext.contentItemId)

            return SearchSuggestion().apply {
                type = SearchSuggestionType.FIND_IN_CONTENT_ITEM
                id = glContentContext.contentItemId
                contentItemId = glContentContext.contentItemId
                title = application.getString(findXinXStringRes, findXinXText, contentItemTitle)
            }
        } else if (glContentContext.libraryCollectionId > 0) {
            // don't suggest root collection
            if (languageManager.isRootCollection(glContentContext.libraryCollectionId)) {
                return null
            }

            // get collection title
            val collectionItemTitle = libraryCollectionManager.findTitleById(glContentContext.libraryCollectionId)

            return SearchSuggestion().apply {
                type = SearchSuggestionType.FIND_IN_LIBRARY_COLLECTION
                id = glContentContext.libraryCollectionId
                title = application.getString(findXinXStringRes, findXinXText, collectionItemTitle)
            }
        }

        return null
    }

    fun findSuggestionsByContentItemId(contentItemId: Long, navSectionId: Long): List<SearchSuggestion> {
        val contentItemTitle = itemManager.findTitleById(contentItemId)

        val queryBuilder = SQLQueryBuilder()
                .table(NavItemConst.TABLE)
                .field(NavItemConst.C_ID, SearchSuggestionConst.C_ID)
                .field("" + contentItemId, SearchSuggestionConst.C_CONTENT_ITEM_ID)
                .field("0", SearchSuggestionConst.C_CHAPTER_NUMBER)
                .field("0", SearchSuggestionConst.C_VERSE_NUMBER)
                .field("" + SearchSuggestionType.GOTO_SUB_ITEM.ordinal, SearchSuggestionConst.C_TYPE)
                .field(NavItemConst.C_TITLE_HTML, SearchSuggestionConst.C_TITLE)
                .field("\"" + contentItemTitle + "\"", SearchSuggestionConst.C_SUB_TITLE)
                .orderBy(NavItemConst.C_POSITION)

        if (navSectionId > 0) {
            queryBuilder.filter(NavItemConst.C_NAV_SECTION_ID, navSectionId)
        }

        return findAllByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                rawQuery = queryBuilder.buildQuery())
    }

    fun findGotoSuggestions(languageId: Long, searchText: String, limit: Int): List<SearchSuggestion> {
        val formattedSearchText = StringUtils.trim(searchText)

        val allGotoSuggestions = ArrayList<SearchSuggestion>()

        // parse the search text into parts
        val gotoParts = GotoTextParser.parseGoto(application, formattedSearchText)

        // find the goto items... add 2 to allow post filtering and still achieving the limit
        val searchSuggestionGotoList = searchGotoQueryManager.findAllByGoto(languageId, gotoParts, limit + 2)

        // query for gotoSuggestions
        for (gotoSuggestion in searchSuggestionGotoList) {
            // create an individual gotoparts and adjust the chapter (if needed)
            val individualGotoParts = GotoParts(application, gotoParts)

            // don't look for additional options if the chapter and verse are already defined (DEFINED: "dc 12 5"  NOT-DEFINED: )
            val chapterAndVersePreDefined = individualGotoParts.containsChapterAndVerse()

            individualGotoParts.setMaxChapterCount(gotoSuggestion.chapterCount)

            if (individualGotoParts.isChapterDefined) {
                // If the chapter is defined, but there are no verses in this type of book, then skip
                if (gotoSuggestion.hasVerses.not()) {
                    continue
                }

                val chapterValue = individualGotoParts.chapterValue
                // after adjustments to chapter and verse, if the chapter is STILL too high, then skip this record (there is no such chapter for this book)
                if (chapterValue > gotoSuggestion.chapterCount) {
                    continue
                }

                // if the chapter is 0, then skip this record (there is no such chapter for this book)
                if (chapterValue <= 0) {
                    continue
                }

                // no such thing as a 0 verse
                if (individualGotoParts.verseDefined && individualGotoParts.verseValue <= 0) {
                    continue
                }
            }

            // if the verse is 0 (or less), then skip
            if (individualGotoParts.isVerseDefined && individualGotoParts.verseValue <= 0) {
                continue
            }

            // add to list
            allGotoSuggestions.add(createGotoSuggestion(gotoSuggestion, individualGotoParts))

            // add any other possibilities
            // Example Search: d125
            // Result Above: "Doctrine and Covenants 125"
            // Additional Results below: "Doctrine and Covenants 12:5" "Doctrine and Covenants 1:25"
            if (!chapterAndVersePreDefined) {

                while (individualGotoParts.shiftLastChapterNumberToVerse() && allGotoSuggestions.size < limit) {
                    // don't allow verses that start with 0
                    if (individualGotoParts.verse.startsWith("0")) {
                        continue
                    }

                    allGotoSuggestions.add(createGotoSuggestion(gotoSuggestion, individualGotoParts))
                }
            }

            // if we have reached our limit, exit
            if (allGotoSuggestions.size == limit) {
                break
            }
        }

        return allGotoSuggestions
    }

    private fun createGotoSuggestion(searchGoto: SearchGotoQuery, gotoParts: GotoParts) = SearchSuggestion().apply {
        when {
            searchGoto.navCollectionId != null -> {
                id = searchGoto.navCollectionId ?: 0
                type = SearchSuggestionType.GOTO_NAV_COLLECTION
            }
            searchGoto.subitemId != null -> {
                id = searchGoto.subitemId ?: 0
                type = SearchSuggestionType.GOTO_SUB_ITEM
            }
        }

        contentItemId = searchGoto.contentItemId
        chapterNumber = gotoParts.chapter
        verseNumber = gotoParts.verse

        title = when {
            !searchGoto.shortTitle.isNullOrBlank() -> searchGoto.shortTitle + gotoParts.getFormattedChapterVerse()
            else -> searchGoto.title + gotoParts.getFormattedChapterVerse()
        }
        subTitle = searchGoto.subTitle ?: ""
    }

    /**
     * Find suggestions for book names from the given searchText
     */
    fun findContentItemSuggestions(languageId: Long, searchText: String, limit: Int): List<SearchSuggestion> {
        if (StringUtils.isBlank(searchText)) {
            return ArrayList()
        }

        val startMs = System.currentTimeMillis()
        val sqlQueryParams = ArrayList<Any>()

        // === 1. BUILD QUERIES TO FIND ITEM ID's BY ITEM TITLE ===

        // a. starts with exact
        val itemIdByStartsWithTitleQuery = SEARCH_ITEM_VIEW_QUERY.clone()
                .filter(ItemConst.C_LANGUAGE_ID, "?")
                .filter(ItemConst.C_TITLE, CompareType.LIKE, "?")
                .buildQuery()

        // query params
        sqlQueryParams.add(languageId)
        sqlQueryParams.add(searchText + "%")

        // b. contains exact
        val itemIdByContainsTitleQuery = SEARCH_ITEM_VIEW_QUERY.clone()
                .filter(ItemConst.C_LANGUAGE_ID, "?")
                .filter(ItemConst.C_TITLE, CompareType.LIKE, "?")
                .buildQuery()

        // query params
        sqlQueryParams.add(languageId)
        sqlQueryParams.add("%$searchText%")

        // c. keywords (search without stop words)
        val keywordTitleQueryBuilder = SEARCH_ITEM_VIEW_QUERY.clone()
                .filter(ItemConst.C_LANGUAGE_ID, "?")

        // query params
        sqlQueryParams.add(languageId)

        // add each keyword as a "AND" item
        var filter: CompareFilter? = null
        for (keywordText in searchUtil.createSearchKeywords(languageId, searchText)) {
            if (filter == null) {
                filter = CompareFilter.create(ItemConst.C_TITLE, CompareType.LIKE, "?")
            } else {
                filter.and(ItemConst.C_TITLE, CompareType.LIKE, "?")
            }

            // query params
            sqlQueryParams.add("%$keywordText%")
        }
        keywordTitleQueryBuilder.filter(filter)
        val itemIdByKeywordTitleQuery = keywordTitleQueryBuilder.buildQuery()


        // === 2. BUILD QUERIES ORDER RESULTS (based on library collection, library section, library item) ===
        val fullStartsWithTitleQuery: SQLQueryBuilder
        val fullContainsTitleQuery: SQLQueryBuilder
        val fullKeywordTitleQuery: SQLQueryBuilder

        val C_SEARCH_QUERY_POSITION = "search_query_position"
        val C_SEARCH_ITEM_ID = "search_item_id"
        val C_SEARCH_TITLE = "search_item_title"
        val C_SEARCH_POSITION_PRIORITY = "search_position_priority" // allow scriptures to ALWAYS be at the top
        val C_SEARCH_SUBTITLE = "search_item_sub_title"
        val C_SEARCH_SECTION_POSITION = "search_section_position"
        val C_SEARCH_ITEM_POSITION = "search_item_position"

        val searchBuilder = SQLQueryBuilder()
                .field(LibraryItemConst.FULL_C_ITEM_ID, C_SEARCH_ITEM_ID)
                .field(ItemConst.FULL_C_TITLE, C_SEARCH_TITLE)
                .field(LibraryCollectionConst.FULL_C_TITLE_HTML, C_SEARCH_SUBTITLE)
                .field(LibrarySectionConst.FULL_C_POSITION, C_SEARCH_SECTION_POSITION)
                .field(LibraryItemConst.FULL_C_POSITION, C_SEARCH_ITEM_POSITION)
                .field("CASE WHEN " + ItemConst.FULL_C_ITEM_CATEGORY_ID + " = " + ItemCategoryType.SCRIPTURES.ordinal + " THEN 1 ELSE 9 END", C_SEARCH_POSITION_PRIORITY)
                .table(LibraryItemConst.TABLE)
                .join(ItemConst.TABLE, ItemConst.FULL_C_ID, LibraryItemConst.FULL_C_ITEM_ID)
                .join(LibrarySectionConst.TABLE, LibrarySectionConst.FULL_C_ID, LibraryItemConst.FULL_C_LIBRARY_SECTION_ID)
                .join(LibraryCollectionConst.TABLE, LibraryCollectionConst.FULL_C_ID, LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID)

        fullStartsWithTitleQuery = searchBuilder.clone()
                .field("1", C_SEARCH_QUERY_POSITION)
                .filter(InFilter.create(LibraryItemConst.FULL_C_ITEM_ID, true, itemIdByStartsWithTitleQuery))

        fullContainsTitleQuery = searchBuilder.clone()
                .field("2", C_SEARCH_QUERY_POSITION)
                .filter(InFilter.create(LibraryItemConst.FULL_C_ITEM_ID, true, itemIdByContainsTitleQuery))

        fullKeywordTitleQuery = searchBuilder.clone()
                .field("3", C_SEARCH_QUERY_POSITION)
                .filter(InFilter.create(LibraryItemConst.FULL_C_ITEM_ID, true, itemIdByKeywordTitleQuery))


        // === 3. FINALIZE (union, groupby, sort) ===
        val union = SQLQueryBuilder.union(fullStartsWithTitleQuery, fullContainsTitleQuery, fullKeywordTitleQuery)

        var fullQuery = SQLQueryBuilder()
                .field("MIN($C_SEARCH_QUERY_POSITION)")
                .field(C_SEARCH_ITEM_ID, SearchSuggestionConst.C_ID)
                .field(SearchSuggestionType.CONTENT_ITEM.ordinal.toString(), SearchSuggestionConst.C_TYPE)
                .field(C_SEARCH_ITEM_ID, SearchSuggestionConst.C_CONTENT_ITEM_ID)
                .field("'0'", SearchSuggestionConst.C_CHAPTER_NUMBER)
                .field("'0'", SearchSuggestionConst.C_VERSE_NUMBER)
                .field(C_SEARCH_TITLE, SearchSuggestionConst.C_TITLE)
                .field(C_SEARCH_SUBTITLE, SearchSuggestionConst.C_SUB_TITLE)
                .table("($union)")
                .groupBy(C_SEARCH_ITEM_ID)
                .orderBy(C_SEARCH_QUERY_POSITION, C_SEARCH_POSITION_PRIORITY, C_SEARCH_SECTION_POSITION, C_SEARCH_ITEM_POSITION) // priority order is after C_SEARCH_QUERY_POSITION because exact book mat
                .buildQuery()


        if (limit > 0) {
            fullQuery += " LIMIT " + limit
        }

        // === 4. ExecuteQuery ===
        val allBookSuggestions = findAllByRawQuery(databaseName = DatabaseManagerConst.CATALOG_DATABASE_NAME,
                rawQuery = fullQuery,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(*sqlQueryParams.toTypedArray()))

        timeUtil.logTimeElapsedFromNow("SearchSugestion", "findContentItemSuggestions time: ", startMs)
        return allBookSuggestions

    }

    override fun getQuery(): String {
        return ""
    }

    companion object {
        const val DOCTRINE_AND_C = "doctrine and c"
        const val BIBLE_D = "bible d"
        const val TOPICAL_G = "topical g"
        const val BOOK_OF_M = "book of m"
        const val PEARL_OF_GREAT_P = "pearl of great p"
        const val OLD_T = "old t"
        const val NEW_T = "new t"
        const val GUIDE_TO_THE_S = "guide to the s"
    }
}
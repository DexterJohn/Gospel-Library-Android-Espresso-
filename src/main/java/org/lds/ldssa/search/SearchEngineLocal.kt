package org.lds.ldssa.search

import android.content.Context
import android.content.Intent
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist
import org.lds.ldssa.event.SearchFinishedEvent
import org.lds.ldssa.model.database.catalog.allitemsincollectionquery.AllItemsInCollectionQueryManager
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.catalog.itemcollectionview.ItemCollectionViewManager
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionManager
import org.lds.ldssa.model.database.content.subitemsearchquery.SubItemSearchQueryManager
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager
import org.lds.ldssa.model.database.search.searchcollection.SearchCollection
import org.lds.ldssa.model.database.search.searchcollection.SearchCollectionManager
import org.lds.ldssa.model.database.search.searchcontentcollectionmap.SearchContentCollectionMap
import org.lds.ldssa.model.database.search.searchcontentcollectionmap.SearchContentCollectionMapManager
import org.lds.ldssa.model.database.search.searchcountallnotes.SearchCountAllNotes
import org.lds.ldssa.model.database.search.searchcountallnotes.SearchCountAllNotesManager
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContent
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContentManager
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNote
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNoteManager
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitem
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitemManager
import org.lds.ldssa.model.database.userdata.notesearchquery.NoteSearchQueryManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.mobile.util.LdsTimeUtil
import pocketbus.Bus
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchEngineLocal @Inject constructor(private val bus: Bus,
                                            private val prefs: Prefs,
                                            private val ldsTimeUtil: LdsTimeUtil,
                                            private val downloadedItemManager: DownloadedItemManager,
                                            private val itemCollectionViewManager: ItemCollectionViewManager,
                                            private val noteSearchQueryManager: NoteSearchQueryManager,
                                            private val subItemSearchQueryManager: SubItemSearchQueryManager,
                                            private val searchCountContentManager: SearchCountContentManager,
                                            private val searchCollectionManager: SearchCollectionManager,
                                            private val libraryCollectionManager: LibraryCollectionManager,
                                            private val searchContentCollectionMapManager: SearchContentCollectionMapManager,
                                            private val searchPreviewSubitemManager: SearchPreviewSubitemManager,
                                            private val searchCountAllNotesManager: SearchCountAllNotesManager,
                                            private val searchPreviewNoteManager: SearchPreviewNoteManager,
                                            private val allItemsInCollectionQueryManager: AllItemsInCollectionQueryManager,
                                            private val itemManager: ItemManager,
                                            private val searchUtil: SearchUtil) {


    fun findAllContentCount(screenId: Long, searchFilter: SearchFilter) {
        val startMs = System.currentTimeMillis()
        val searchText = searchFilter.searchText
        Timber.i("Searching content/note count for [%s]", searchText)
        searchUtil.clearSearch(screenId)

        val totalResultCount: Long
        val searchTextRequest = searchUtil.createSearchRequest(searchText)
        if (searchTextRequest != null) {

            val noteCount: Long
            // Find NOTES ... don't search notes if there is any content filters
            if (!searchFilter.containsContentFilter()) {
                noteCount = findAndSaveNoteCounts(screenId, searchTextRequest)
            } else {
                noteCount = 0
            }

            // get list of downloaded content items
            val downloadedContentItemIds = downloadedItemManager.findAllInstalledIds()

            if (downloadedContentItemIds.isNotEmpty()) {
                // filter out items that are not in searchFilter
                val filteredContentItemIds = filterContentItemIds(downloadedContentItemIds, searchFilter)

                // Retrieve an ORDERED list of collections for all of the content items downloaded.
                // NOTE: this has to be done before searching content, because we have to get a list of collections and what order
                // they show up in... AND show results as we find them (we cannot wait to show everything at once)
                findAndSaveCollectionPositions(screenId, filteredContentItemIds)

                // find and save all of the counts for the content items that have a count > 0, then create a list of the contentItemIds with count > 0
                val contentCount = findAndSaveContentItemCounts(screenId, filteredContentItemIds, searchTextRequest, searchFilter.navCollectionId)

                // sum the total count
                totalResultCount = contentCount + noteCount
            } else {
                // nothing to search
                totalResultCount = 0L
            }
        } else {
            totalResultCount = 0L
        }

        // log
        ldsTimeUtil.logTimeElapsedFromNow("SEARCH", "findAllContentCount(...) finished", startMs)

        bus.post(SearchFinishedEvent(screenId, totalResultCount))
    }

    private fun filterContentItemIds(downloadedContentItemIds: List<Long>, searchFilter: SearchFilter): List<Long> {
        if (!searchFilter.containsContentFilter()) {
            // nothing to filter... just return the list
            return downloadedContentItemIds
        }

        val filteredList = ArrayList<Long>()
        if (searchFilter.contentItemId > 0) {
            if (downloadedContentItemIds.contains(searchFilter.contentItemId)) {
                filteredList.add(searchFilter.contentItemId)
            }
        } else if (searchFilter.libraryCollectionId > 0) {
            // recursively go through collections and gather all contentItemIds
            val collectionContentItemIds = allItemsInCollectionQueryManager.findAllItemIdsByCollectionId(searchFilter.libraryCollectionId, prefs.isObsoleteContentEnabled).toHashSet()

            // filter downloadedContentItemIds to ONLY contain ids from the items within the search collections
            filteredList.addAll(downloadedContentItemIds.filter {
                collectionContentItemIds.contains(it)
            })
        }

        return filteredList
    }

    private fun findAndSaveCollectionPositions(screenId: Long, contentItemIds: List<Long>) {
        val itemCollectionItems = itemCollectionViewManager.findAllSearchItemsByItemIds(contentItemIds)

        var collectionPosition = 0

        searchCollectionManager.inTransaction {
            val addedCollectionSet = HashSet<Long>()
            itemCollectionItems.forEach { itemCollection ->
                // save collections
                val searchCollection = SearchCollection().apply {
                    this.screenId = screenId
                    collectionId = itemCollection.collectionId
                    title = itemCollection.collectionTitle
                    position = collectionPosition
                }

                // gather info for parent collection
                val parentCollection = libraryCollectionManager.findParentCollection(itemCollection.collectionId)

                if (parentCollection != null) {
                    searchCollection.parentCollectionId = parentCollection.id

                    // don't include the title if it is the root collection (sectionId is null on root collections)
                    if (parentCollection.librarySectionId != null) {
                        searchCollection.parentCollectionTitle = parentCollection.titleHtml
                    } else {
                        searchCollection.parentCollectionIsRoot = true
                    }
                }

                // save search collection (make sure to ONLY add one of each collection)
                if (!addedCollectionSet.contains(searchCollection.collectionId)) {
                    searchCollectionManager.save(searchCollection)
                    addedCollectionSet.add(searchCollection.collectionId)
                }

                // save many-to-many mapping
                val collectionContentSearchResult = SearchContentCollectionMap().apply {
                    this.screenId = screenId
                    contentItemId = itemCollection.itemId
                    collectionId = itemCollection.collectionId
                }
                searchContentCollectionMapManager.save(collectionContentSearchResult)

                collectionPosition++
            }

            true // transaction successful
        }

    }

    private fun findAndSaveNoteCounts(screenId: Long, searchTextRequest: SearchTextRequest): Long {
        val searchCount = noteSearchQueryManager.findCountsBySearchTextRequest(searchTextRequest) // check notes
        val noteSearchCount = SearchCountAllNotes().apply {
            this.screenId = screenId
            noteCount = searchCount.itemCount
            phraseCount = searchCount.phraseCount
            keywordCount = searchCount.keywordCount
        }

        searchCountAllNotesManager.save(noteSearchCount)

        return searchCount.itemCount
    }

    private fun findAndSaveContentItemCounts(screenId: Long, contentItemIds: List<Long>, searchTextRequest: SearchTextRequest, navCollectionId: Long): Long {
        var totalCount = 0L

        // result position
        var resultPosition = 0

        // ONLY use the navCollectionId IF there is ONLY 1 contenItemId
        val searchNavCollectionId = if (contentItemIds.size == 1) navCollectionId else 0L

        var subItemCount = 0
        contentItemIds.forEach { contentItemId ->
            subItemCount++

            // find count by contentItemId
            val resultCount = subItemSearchQueryManager.findCountBySearchTextRequest(contentItemId, searchTextRequest, searchNavCollectionId)

            // save the result
            if (!searchCountContentManager.inTransaction()) {
                searchCountContentManager.beginTransaction()
            }

            if (resultCount.isNotZero()) {
                val itemTitle = itemManager.findTitleById(contentItemId)
                val contentSearchResult = SearchCountContent().apply {
                    this.screenId = screenId
                    this.contentItemId = contentItemId
                    title = itemTitle
                    phraseCount = resultCount.phraseCount
                    keywordCount = resultCount.keywordCount
                    position = resultPosition
                }

                searchCountContentManager.save(contentSearchResult)

                // increment position
                resultPosition++
            }

            // increment the total count
            totalCount += resultCount.keywordCount + resultCount.phraseCount

            // close the transaction every once in a while OR if it is the last item
            if (subItemCount == 1 || subItemCount == 5 || subItemCount % 10 == 0) {
                searchCountContentManager.endTransaction(true)
            }
        }

        // make sure the transaction is closed
        if (searchCountContentManager.inTransaction()) {
            searchCountContentManager.endTransaction(true)
        }

        return totalCount
    }

    fun findNotePreview(screenId: Long, searchText: String) {
        Timber.i("Searching note previews for [%s]", searchText)

        val searchTextRequest = searchUtil.createSearchRequest(searchText)
        searchTextRequest ?: return

        val noteSearchQueryList = noteSearchQueryManager.findAllBySearchTextRequest(searchTextRequest)

        noteSearchQueryList.forEachIndexed { index, noteSearchQuery ->
            val searchPreviewNote = SearchPreviewNote().apply {
                this.screenId = screenId
                annotationId = noteSearchQuery.annotationId
                title = noteSearchQuery.titleSnippet ?: ""
                text = noteSearchQuery.contentSnippet ?: ""
                searchResultCountType = noteSearchQuery.searchResultCountType
                count = searchUtil.getFtsCount(noteSearchQuery.matchInfo)
                position = index
            }

            searchPreviewNoteManager.save(searchPreviewNote)
        }
    }

    fun findContentPreview(screenId: Long, searchFilter: SearchFilter) {
        Timber.i("Searching content preview for [%s]  contentItemId: [%d]", searchFilter.searchText, searchFilter.contentItemId)

        val searchTextRequest = searchUtil.createSearchRequest(searchFilter.searchText) ?: return

        val subItemSearchQueryResults = subItemSearchQueryManager.findPreviewBySearchTextRequest(searchFilter.contentItemId, searchTextRequest, searchFilter.navCollectionId)
        subItemSearchQueryResults.forEach { subItemSearchItem ->
            val subItemSearchResult = SearchPreviewSubitem().apply {
                this.screenId = screenId
                subItemId = subItemSearchItem.subItemId
                contentItemId = searchFilter.contentItemId
                title = subItemSearchItem.title
                text = formatSubItemSearchResultText(subItemSearchItem.snippet)
                searchResultCountType = subItemSearchItem.searchResultCountType
                count = searchUtil.getFtsCount(subItemSearchItem.matchInfo)
                position = subItemSearchItem.position
            }

            searchPreviewSubitemManager.save(subItemSearchResult)
        }

        bus.post(SearchFinishedEvent(screenId, subItemSearchQueryResults.size.toLong()))
    }

    private fun formatSubItemSearchResultText(snippet: String): String {
        var preview = snippet
        // check for a verse number span that doesn't have an opening tag
        if (snippet.indexOf(SPAN_CLOSE) < snippet.indexOf(SPAN_OPEN)) {
            preview = SPAN_ADD_VERSE_NUMBER + preview
        }

        // remove all study markers
        val doc = Jsoup.parse(preview)
        removeTags(doc, SUP_STUDY_MARKER)
        removeTags(doc, LDS_META_TAG)
        removeTags(doc, TITLE_NUMBER_TAG)

        // add a space after the verse numbers
        for (verseNumber in doc.select(SPAN_VERSE_NUMBER)) {
            verseNumber.appendText(" ")
        }

        // remove all tags except for bold
        val whitelist = Whitelist()
        whitelist.addTags(BOLD)

        return Jsoup.clean(doc.body().html(), whitelist)
    }

    private fun removeTags(doc: Document, tag: String) {
        for (element in doc.select(tag)) {
            element.remove()
        }
    }


    companion object {
        private val SUP_STUDY_MARKER = "sup.marker"
        private val SPAN_VERSE_NUMBER = "span.verse-number"
        private val SPAN_ADD_VERSE_NUMBER = """<span class="verse-number">"""
        private val LDS_META_TAG = "lds|meta"
        private val TITLE_NUMBER_TAG = "p.title-number"
        private val SPAN_OPEN = "<span"
        private val SPAN_CLOSE = "</span"
        private val BOLD = "b"


        @JvmStatic
        fun executeContentCountSearch(context: Context, screenId: Long, searchFilter: SearchFilter) {
            val searchIntent = Intent(context, SearchService::class.java)
            searchIntent.action = SearchService.ACTION_SEARCH_COUNT
            addTabIdToIntent(searchIntent, screenId)

            // add filter to intent
            searchFilter.addToIntent(searchIntent)

            context.startService(searchIntent)
        }

        @JvmStatic
        fun executeContentPreviewSearch(context: Context, screenId: Long, searchFilter: SearchFilter) {
            val searchIntent = Intent(context, SearchService::class.java)
            searchIntent.action = SearchService.ACTION_SEARCH_PREVIEW_CONTENT
            addTabIdToIntent(searchIntent, screenId)

            // add filter to intent
            searchFilter.addToIntent(searchIntent)

            context.startService(searchIntent)
        }

        @JvmStatic
        fun executeNotePreviewSearch(context: Context, screenId: Long, searchFilter: SearchFilter) {
            val searchIntent = Intent(context, SearchService::class.java)
            searchIntent.action = SearchService.ACTION_SEARCH_PREVIEW_NOTE
            addTabIdToIntent(searchIntent, screenId)

            // add filter to intent
            searchFilter.addToIntent(searchIntent)

            context.startService(searchIntent)
        }

        private fun addTabIdToIntent(intent: Intent, screenId: Long) {
            if (screenId <= 0) {
                throw IllegalStateException("screenId is required for search")
            }

            intent.putExtra(SearchService.EXTRA_SCREEN_ID, screenId)
        }
    }

    class SearchFilter constructor(val searchText: String = "",
                                   val libraryCollectionId: Long = 0,
                                   val contentItemId: Long = 0,
                                   val navCollectionId: Long = 0,
                                   val subItemId: Long = 0) {

        constructor(intent: Intent) : this(
                searchText = intent.getStringExtra(SearchService.EXTRA_SEARCH_TEXT),
                libraryCollectionId = intent.getLongExtra(SearchService.EXTRA_FILTER_LIBRARY_COLLECTION_ID, 0L),
                contentItemId = intent.getLongExtra(SearchService.EXTRA_FILTER_CONTENT_ITEM_ID, 0L),
                navCollectionId = intent.getLongExtra(SearchService.EXTRA_FILTER_NAV_COLLECTION_ID, 0L),
                subItemId = intent.getLongExtra(SearchService.EXTRA_FILTER_SUB_ITEM_ID, 0L)
        )

        fun addToIntent(intent: Intent) {
            intent.putExtra(SearchService.EXTRA_SEARCH_TEXT, searchText)
            intent.putExtra(SearchService.EXTRA_FILTER_LIBRARY_COLLECTION_ID, libraryCollectionId)
            intent.putExtra(SearchService.EXTRA_FILTER_CONTENT_ITEM_ID, contentItemId)
            intent.putExtra(SearchService.EXTRA_FILTER_NAV_COLLECTION_ID, navCollectionId)
            intent.putExtra(SearchService.EXTRA_FILTER_SUB_ITEM_ID, subItemId)
        }

        fun containsContentFilter(): Boolean {
            return libraryCollectionId > 0 || contentItemId > 0 || navCollectionId > 0 || subItemId > 0
        }
    }
}

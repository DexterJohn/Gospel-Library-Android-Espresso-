@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package org.lds.ldssa.ux.search

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.event.SearchFinishedEvent
import org.lds.ldssa.intent.ContentIntentParams
import org.lds.ldssa.model.GLContentContext
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.content.navitem.NavItemManager
import org.lds.ldssa.model.database.content.navsection.NavSectionManager
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager
import org.lds.ldssa.model.database.content.subitemsearchquery.SubItemSearchQueryManager
import org.lds.ldssa.model.database.search.searchallcount.SearchAllCount
import org.lds.ldssa.model.database.search.searchallcount.SearchAllCountManager
import org.lds.ldssa.model.database.search.searchcollection.SearchCollectionManager
import org.lds.ldssa.model.database.search.searchcountallnotes.SearchCountAllNotesManager
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContent
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContentManager
import org.lds.ldssa.model.database.search.searchhistory.SearchHistory
import org.lds.ldssa.model.database.search.searchhistory.SearchHistoryManager
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNote
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNoteManager
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitem
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitemManager
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestion
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestionManager
import org.lds.ldssa.model.database.types.SearchSuggestionType
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasSearch
import org.lds.ldssa.search.SearchAllType
import org.lds.ldssa.search.SearchEngineLocal
import org.lds.ldssa.search.SearchResultCountType
import org.lds.ldssa.search.SearchUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ui.mvp.BasePresenter
import pocketbus.Bus
import pocketbus.Subscribe
import pocketbus.ThreadMode
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchPresenter @Inject constructor(
        private val bus: Bus,
        private val analytics: Analytics,
        private val cc: CoroutineContextProvider,
        private val searchUtil: SearchUtil,
        private val itemManager: ItemManager,
        private val searchHistoryManager: SearchHistoryManager,
        private val navSectionManager: NavSectionManager,
        private val navItemManager: NavItemManager,
        private val paragraphMetadataManager: ParagraphMetadataManager,
        private val searchPreviewSubitemManager: SearchPreviewSubitemManager,
        private val subItemSearchQueryManager: SubItemSearchQueryManager,
        private val searchPreviewNoteManager: SearchPreviewNoteManager,
        private val searchSuggestionManager: SearchSuggestionManager,
        private val searchAllCountManager: SearchAllCountManager,
        private val searchCountContentManager: SearchCountContentManager,
        private val searchCollectionManager: SearchCollectionManager,
        private val searchCountAllNotesManager: SearchCountAllNotesManager) : BasePresenter() {

    private var compositeDisposable: CompositeDisposable? = null

    private lateinit var view: SearchContract.View

    private var languageId: Long = 0L
    private var screenId: Long = 0L

    lateinit var mode: SearchMode
    var modeCollectionId = 0L
    var modeContentItemId = 0L
    lateinit var initialSearchText: String
    var scrollPosition = 0

    // where was search launched from?
    private lateinit var searchGlContentContext: GLContentContext

    fun init(view: SearchContract.View, languageId: Long, screenId: Long, mode: SearchMode, modeCollectionId: Long, modeContentItemId: Long,
             contextLibraryCollectionId: Long, contextContentItemId: Long, contextNavCollectionId: Long, contextSubItemId: Long, searchText: String, scrollPosition: Int) {
        this.languageId = languageId
        this.screenId = screenId
        this.view = view
        this.mode = mode
        this.modeCollectionId = modeCollectionId
        this.modeContentItemId = modeContentItemId

        this.searchGlContentContext = GLContentContext(contextLibraryCollectionId, contextContentItemId, contextNavCollectionId, contextSubItemId)

        addDisposable(searchCountAllNotesManager.tableChanges().throttleWithTimeout(DEFAULT_DEBOUNCE, TimeUnit.MILLISECONDS).subscribe {
            loadSearchAllCounts()
        })
        addDisposable(searchCountContentManager.tableChanges().throttleWithTimeout(DEFAULT_DEBOUNCE, TimeUnit.MILLISECONDS).subscribe {
            loadSearchAllCounts()
            loadSearchContentCollectionCounts()
        })
        addDisposable(searchPreviewSubitemManager.tableChanges().throttleWithTimeout(DEFAULT_DEBOUNCE, TimeUnit.MILLISECONDS).subscribe {
            loadSearchPreviewContent()
        })
        addDisposable(searchPreviewNoteManager.tableChanges().throttleWithTimeout(DEFAULT_DEBOUNCE, TimeUnit.MILLISECONDS).subscribe {
            loadSearchPreviewNotes()
        })

        this.initialSearchText = searchText
        this.scrollPosition = scrollPosition
    }

    override fun load(): Job? {
        if (initialSearchText.isNotBlank()) {
            setSearchText(initialSearchText)

            // if the mode is default (HISTORY), and there is some initial text... then change to SUGGESTION
            if (mode == SearchMode.HISTORY) {
                mode = SearchMode.SUGGESTION
            }

            view.showKeyboard()
        }

        changeMode(mode)

        analytics.postEvent(Analytics.Event.SEARCH_OPENED)

        view.enableSearchEditTextListeners()

        return null
    }

    override fun register() {
        super.register()
        bus.register(this)
    }

    override fun unregister() {
        bus.unregister(this)

        compositeDisposable?.let {
            it.clear()
            compositeDisposable = null
        }

        super.unregister()
    }

    fun logAnalytics(searchText: String) {
        val attributes = mutableMapOf<String, String>()
        attributes.put(Analytics.Attribute.SEARCH_STRING, searchText)
        analytics.postEvent(Analytics.Event.SEARCH_PERFORMED, attributes)
    }

    @Subscribe(ThreadMode.MAIN)
    fun handle(event: SearchFinishedEvent) {
        if (event.screenId == screenId) {
            view.showSearchProgress(false)
            view.setEmptyStateIndicatorVisible(event.totalResultCount <= 0)
        }
    }

    fun onSearchTextChanged() {
        val searchText = view.getSearchText()
        if (searchText.isBlank()) {
            changeMode(SearchMode.HISTORY)
        } else {
            changeMode(SearchMode.SUGGESTION)
        }
    }

    fun onSearchKeyboardEnterPressed() {
        executeSearch(SearchEngineLocal.SearchFilter(searchText = view.getSearchText()))
    }

    private fun executeSearch(searchFilter: SearchEngineLocal.SearchFilter) {
        // hide the empty state
        view.setEmptyStateIndicatorVisible(false)

        // get the search text
        val searchText = searchFilter.searchText

        // log the search text
        searchHistoryManager.addItemToHistory(searchText)
        logAnalytics(searchText)

        // since this is a new search, remove any existing search results
        searchUtil.clearSearch(screenId)

        // determine whether to show counts or jut straight to previews
        when {
            searchFilter.contentItemId > 0 -> {
                // if the search filter limits the scope to a single content item, then just go to the PREVIEW
                modeContentItemId = searchFilter.contentItemId
                changeMode(SearchMode.PREVIEW_CONTENT)
            }
            else -> {
                // show search progress
                view.showSearchProgress(true)

                // search multiple content items and show counts
                changeMode(SearchMode.SEARCH_ALL_COUNT)

                // execute the search
                view.executeSearchServiceContentCount(screenId, searchFilter)
            }
        }
    }

    private fun executeContentPreviewSearch() {
        // remove any existing results
        view.setPreviewContentList(emptyList())

        // set the header text
        compositeJob.add(launch(cc.ui) {
            val headerText = async(coroutineContext + cc.commonPool) {
                itemManager.findTitleById(modeContentItemId)
            }

            view.setSearchHeaderText(headerText.await())
        })

        // if there is already a cache... just load it, otherwise... do a new search
        val previewCacheAvailable = searchPreviewSubitemManager.findCountByContentItemId(screenId, modeContentItemId) != 0L
        if (previewCacheAvailable) {
            loadSearchPreviewContent()
        } else {
            // show search progress
            view.showSearchProgress(true)

            view.executeSearchServiceContentPreview(screenId, SearchEngineLocal.SearchFilter(searchText = view.getSearchText(), contentItemId = modeContentItemId, navCollectionId = searchGlContentContext.navCollectionId))
        }
    }

    private fun executeNotePreviewSearch() {
        // remove any existing results
        view.setPreviewNoteList(emptyList())

        // if there is already a cache... just load it, otherwise... do a new search
        if (searchPreviewNoteManager.findCountByScreenId(screenId) == 0L) {
            view.executeSearchServiceNotePreview(screenId, SearchEngineLocal.SearchFilter(searchText = view.getSearchText()))
        } else {
            loadSearchPreviewNotes()
        }
    }


    fun onSearchSuggestionSelected(searchSuggestion: SearchSuggestion) {
        when (searchSuggestion.type) {
            SearchSuggestionType.FIND_ON_PAGE -> findOnPage(searchSuggestion)
            SearchSuggestionType.FIND_IN_LIBRARY_COLLECTION -> executeSearch(SearchEngineLocal.SearchFilter(searchText = view.getSearchText(), libraryCollectionId = searchSuggestion.id))
            SearchSuggestionType.FIND_IN_CONTENT_ITEM -> executeSearch(SearchEngineLocal.SearchFilter(searchText = view.getSearchText(), contentItemId = searchSuggestion.id))
            SearchSuggestionType.FIND_IN_NAV_COLLECTION -> executeSearch(SearchEngineLocal.SearchFilter(searchText = view.getSearchText(), contentItemId = searchSuggestion.contentItemId, navCollectionId = searchSuggestion.id))
            SearchSuggestionType.CONTENT_ITEM -> view.showContentDirectoryRoot(screenId, searchSuggestion.contentItemId, Analytics.Referrer.SEARCH_RESULT) // Book of Mormon, October Ensign, True to the Faith, etc
            SearchSuggestionType.GOTO_NAV_COLLECTION -> showGotoCollectionItem(searchSuggestion) // NAV_COLLECTION (Multiple chapter items: 1 Nephi, Alma, etc)
            SearchSuggestionType.GOTO_SUB_ITEM -> showGotoItem(searchSuggestion) // NAV_ITEM (Single chapter books... Omni, 4 Nephi, etc)
            SearchSuggestionType.HISTORY -> SearchEngineLocal.SearchFilter(searchText = searchSuggestion.title)
            SearchSuggestionType.NOTEBOOK -> view.showNotebook(screenId, searchSuggestion.id)
            SearchSuggestionType.NOTE -> view.showNote(screenId, searchSuggestion.id)
            SearchSuggestionType.SEARCH_FOR -> SearchEngineLocal.SearchFilter(searchText = searchSuggestion.title)
            else -> {
                // do nothing
            }
        }
    }

    fun onSearchHistoryPopulateClicked(searchHistory: SearchHistory) {
        setSearchText(searchHistory.title, false)
        changeMode(SearchMode.SUGGESTION)
    }

    fun onSearchSuggestionPopulateClicked(searchSuggestion: SearchSuggestion) {
        setSearchText(searchSuggestion.title, false)
    }

    fun onTextFromMic(text: String) {
        setSearchText(text)
    }

    private fun setSearchText(text: String, hideKeyboard: Boolean = true) {
        // don't listen to events if the text is being set programmatically
        view.disableSearchEditTextListeners()

        // be sure the text for the search is in the search bar
        view.setSearchText(text)

        // hide the keyboard
        if (hideKeyboard && text.isNotBlank()) {
            view.hideKeyboard()
        }

        view.enableSearchEditTextListeners()
    }

    private fun findOnPage(searchSuggestion: SearchSuggestion) {
        val contentIntentParams = ContentIntentParams(screenId,
                contentItemId = searchSuggestion.contentItemId,
                subItemId = searchSuggestion.id,
                referrer = Analytics.Referrer.SEARCH)

        val searchText = searchUtil.removeSearchQuotes(view.getSearchText())

        contentIntentParams.findOnPageText = searchText

        searchHistoryManager.addItemToHistory(searchText)

        view.showContentWithFindOnPage(contentIntentParams)
    }

    private fun showGotoItem(searchSuggestion: SearchSuggestion) {
        val contentItemId = searchSuggestion.contentItemId

        if (!view.verifyContentDownloaded(contentItemId)) {
            return
        }

        // goto items _id are nav_item_ids... find the subItemId for it
        val subItemId = navItemManager.findSubItemIdById(contentItemId, searchSuggestion.id)

        showGoto(contentItemId, subItemId, searchSuggestion.getVerseNumberValue())
    }

    private fun showGotoCollectionItem(searchSuggestion: SearchSuggestion) {
        val contentItemId = searchSuggestion.contentItemId
        val navCollectionId = searchSuggestion.id

        if (!view.verifyContentDownloaded(contentItemId)) {
            return
        }

        val sectionId = navSectionManager.findIdByCollectionId(contentItemId, navCollectionId)

        if (searchSuggestion.getChapterNumberValue() > 0) {
            val subItemId = navItemManager.findSubItemIdByChapter(contentItemId, sectionId, searchSuggestion.chapterNumber)
            showGoto(contentItemId, subItemId, searchSuggestion.getVerseNumberValue())
        } else {
            view.showContentDirectory(screenId, contentItemId, navCollectionId, Analytics.Referrer.SCRIPTURE_GO_TO)
        }
    }

    private fun showGoto(contentItemId: Long, subItemId: Long, verseNumber: Int) {
        if (verseNumber > 0) {
            val paragraphAid = paragraphMetadataManager.findAidByVerseNumber(contentItemId, subItemId, verseNumber.toString())

            val contentIntentParams = ContentIntentParams(screenId, contentItemId, subItemId, Analytics.Referrer.SCRIPTURE_GO_TO)
            contentIntentParams.scrollToParagraphAid = paragraphAid
            view.showContent(contentIntentParams)
        } else {
            val contentIntentParams = ContentIntentParams(screenId, contentItemId, subItemId, Analytics.Referrer.SCRIPTURE_GO_TO)
            view.showContent(contentIntentParams)
        }
    }

    fun onSearchHistorySelected(searchHistory: SearchHistory) {
        val searchText = searchHistory.title
        view.setSearchText(searchText)
        executeSearch(SearchEngineLocal.SearchFilter(searchText))
    }

    fun onSearchCountCollectionItemSelected(searchAllCount: SearchAllCount) {
        when (searchAllCount.type) {
            SearchAllType.NOTE -> {
                changeMode(SearchMode.PREVIEW_NOTES)
            }
            SearchAllType.COLLECTION -> {
                // remove any existing results
                view.setSearchHeaderText("")
                view.setSearchCountCollectionList(emptyList())

                modeCollectionId = searchAllCount.id
                changeMode(SearchMode.SEARCH_COUNT_COLLECTION)
            }
        }
    }

    fun onSearchContentCountItemSelected(searchCountContent: SearchCountContent) {
        modeContentItemId = searchCountContent.contentItemId
        scrollPosition = 0 // Reset the scroll position since they are previewing a different result list
        changeMode(SearchMode.PREVIEW_CONTENT)
    }

    fun onSearchPreviewCountItemSelected(searchPreviewSubitem: SearchPreviewSubitem) {
        searchPreviewSubitemManager.saveViewed(screenId, searchPreviewSubitem.subItemId)

        val searchRequest = searchUtil.createSearchRequest(view.getSearchText(), forceExactSearchOnly = searchPreviewSubitem.searchResultCountType == SearchResultCountType.EXACT_PHRASE)

        val markTextOffsetSqliteOffsets: String
        val markTextOffsetSqliteExactPhrase: Boolean
        if (searchRequest != null) {
            markTextOffsetSqliteOffsets = subItemSearchQueryManager.findTextOffsets(searchPreviewSubitem.contentItemId, searchPreviewSubitem.subItemId, searchRequest)
            markTextOffsetSqliteExactPhrase = searchRequest.exactSearchOnly
        } else {
            markTextOffsetSqliteOffsets = ""
            markTextOffsetSqliteExactPhrase = false
        }

        // create Intent and show content
        val contentIntentParams = ContentIntentParams(screenId,
                contentItemId = searchPreviewSubitem.contentItemId,
                subItemId = searchPreviewSubitem.subItemId,
                referrer = Analytics.Referrer.SEARCH)

        contentIntentParams.markTextOffsetSqliteOffsets = markTextOffsetSqliteOffsets
        contentIntentParams.markTextOffsetSqliteExactPhrase = markTextOffsetSqliteExactPhrase

        view.showContent(contentIntentParams)
    }

    fun onSearchPreviewNoteItemSelected(searchPreviewNote: SearchPreviewNote) {
        searchPreviewNoteManager.saveViewed(screenId, searchPreviewNote.annotationId)
        view.showNote(screenId, searchPreviewNote.annotationId)
    }

    private fun loadSearchHistory() {
        compositeJob.add(launch(cc.ui) {
            val list = run(coroutineContext + cc.commonPool) {
                searchHistoryManager.findAllByMostRecent()
            }

            view.setSearchHistoryList(list)
        })
    }

    private fun loadSuggestions() {
        compositeJob.add(launch(cc.ui) {
            val list = run(coroutineContext + cc.commonPool) {
                searchSuggestionManager.findSuggestions(languageId, view.getSearchText(), searchGlContentContext)
            }

            view.setSearchSuggestionList(list)
        })
    }

    private fun loadSearchAllCounts() {
        if (mode != SearchMode.SEARCH_ALL_COUNT) {
            return
        }

        compositeJob.add(launch(cc.ui) {
            val list = run(coroutineContext + cc.commonPool) {
                searchAllCountManager.findAllResults(screenId)
            }

            view.setSearchCountList(list)
        })
    }

    private fun loadSearchContentCollectionCounts() {
        if (mode != SearchMode.SEARCH_COUNT_COLLECTION) {
            return
        }

        compositeJob.add(launch(cc.ui) {
            val headerText = async(coroutineContext + cc.commonPool) {
                searchCollectionManager.findCollectionTitle(screenId, modeCollectionId)
            }
            val list = async(coroutineContext + cc.commonPool) {
                searchCountContentManager.findAllResultsByCollection(screenId, modeCollectionId)
            }

            view.setSearchHeaderText(headerText.await())
            view.setSearchCountCollectionList(list.await())
        })
    }

    private fun loadSearchPreviewContent() {
        compositeJob.add(launch(cc.ui) {
            val list = async(coroutineContext + cc.commonPool) {
                searchPreviewSubitemManager.findAllForPreviewByContentItemId(screenId, modeContentItemId)
            }

            view.setPreviewContentList(list.await())
            view.scrollToPosition(scrollPosition)
        })
    }

    private fun loadSearchPreviewNotes() {
        compositeJob.add(launch(cc.ui) {
            val list = run(coroutineContext + cc.commonPool) {
                searchPreviewNoteManager.findAllForPreview(screenId)
            }

            view.setSearchHeaderText(R.string.notes)
            view.setPreviewNoteList(list)
        })
    }

    private fun changeMode(newMode: SearchMode) {
        val modeChange = newMode != mode
        mode = newMode

        when (mode) {
            SearchMode.HISTORY -> {
                view.setEmptyStateIndicatorVisible(false)
                view.showSearchHeader(false)
                view.changeAdapter(mode)

                loadSearchHistory()
            }
            SearchMode.SUGGESTION -> {
                if (modeChange || !view.searchSuggestionAdapterShowing()) { // because this gets called a lot, only replace this adapter when starting this activity or if the mode changes
                    view.setEmptyStateIndicatorVisible(false)
                    view.showSearchHeader(false)
                    view.changeAdapter(mode)
                }

                loadSuggestions()
            }
            SearchMode.SEARCH_ALL_COUNT -> {
                view.showSearchHeader(false)
                view.changeAdapter(mode)

                view.hideKeyboard()
                loadSearchAllCounts()
            }
            SearchMode.SEARCH_COUNT_COLLECTION -> {
                view.showSearchHeader(true)
                view.changeAdapter(mode)

                view.hideKeyboard()
                loadSearchContentCollectionCounts()
            }
            SearchMode.PREVIEW_CONTENT -> {
                view.showSearchHeader(true)
                view.changeAdapter(mode)

                view.hideKeyboard()
                executeContentPreviewSearch()
            }
            SearchMode.PREVIEW_NOTES -> {
                view.showSearchHeader(true)
                view.changeAdapter(mode)

                view.hideKeyboard()
                executeNotePreviewSearch()
            }
        }
    }

    fun onBackMode() {
        when (mode) {
            SearchMode.HISTORY, SearchMode.SUGGESTION -> {
                view.close()
            }
            SearchMode.SEARCH_ALL_COUNT -> {
                setSearchText("")
                changeMode(SearchMode.HISTORY)
            }
            SearchMode.SEARCH_COUNT_COLLECTION -> {
                changeMode(SearchMode.SEARCH_ALL_COUNT)
            }
            SearchMode.PREVIEW_CONTENT -> {
                when {
                    searchCountContentManager.findCountByTabId(screenId) > 0 -> changeMode(SearchMode.SEARCH_COUNT_COLLECTION) // only go back to count if there is any counts
                    else -> view.close()
                }
            }
            SearchMode.PREVIEW_NOTES -> {
                changeMode(SearchMode.SEARCH_ALL_COUNT)
            }
        }
    }

    fun onClearTextClicked() {
        setSearchText("")
        changeMode(SearchMode.HISTORY)
    }

    fun getScreenHistoryExtrasSearch(): ScreenHistoryExtrasSearch {
        return ScreenHistoryExtrasSearch(view.getSearchText(),
                mode,
                modeCollectionId,
                modeContentItemId,
                searchGlContentContext,
                view.getListScrollPosition()
        )
    }

    fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable!!.add(disposable)
    }

    companion object {
        const val DEFAULT_DEBOUNCE = 125L
    }
}
package org.lds.ldssa.ux.search

import android.content.Intent
import android.os.Bundle
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Int
import me.eugeniomarletti.extras.bundle.base.Long
import me.eugeniomarletti.extras.bundle.base.Serializable
import me.eugeniomarletti.extras.bundle.base.String
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Int
import me.eugeniomarletti.extras.intent.base.Long
import me.eugeniomarletti.extras.intent.base.Serializable
import me.eugeniomarletti.extras.intent.base.String
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.intent.ContentIntentParams
import org.lds.ldssa.model.database.search.searchallcount.SearchAllCount
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContent
import org.lds.ldssa.model.database.search.searchhistory.SearchHistory
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNote
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitem
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestion
import org.lds.ldssa.search.SearchEngineLocal
import org.lds.ldssa.ui.activity.BaseActivity

class SearchContract {
    interface View {
        fun showSearchProgress(visible: Boolean)
        fun setEmptyStateIndicatorVisible(visible: Boolean)
        fun setSearchText(text: String) // SHOULD ONLY be called from presenter.setSearchText(...)
        fun showContentWithFindOnPage(contentIntentParams: ContentIntentParams)
        fun getSearchText(): String
        fun verifyContentDownloaded(contentItemId: Long): Boolean
        fun showContent(contentIntentParams: ContentIntentParams)
        fun showContentDirectoryRoot(screenId: Long, contentItemId: Long, referrer: Analytics.Referrer)
        fun showContentDirectory(screenId: Long, contentItemId: Long, navCollectionId: Long, referrer: Analytics.Referrer)
        fun showNotebook(screenId: Long, notebookId: Long)
        fun showNote(screenId: Long, annotationId: Long)
        fun showSearchHeader(visible: Boolean)
        fun changeAdapter(mode: SearchMode)
        fun searchSuggestionAdapterShowing(): Boolean
        fun setSearchHistoryList(list: List<SearchHistory>)
        fun setSearchSuggestionList(list: List<SearchSuggestion>)
        fun setSearchCountList(list: List<SearchAllCount>)
        fun setSearchCountCollectionList(list: List<SearchCountContent>)
        fun setSearchHeaderText(text: String)
        fun setPreviewContentList(list: List<SearchPreviewSubitem>)
        fun scrollToPosition(scrollPosition: Int)
        fun setPreviewNoteList(list: List<SearchPreviewNote>)
        fun setSearchHeaderText(stringResId: Int)
        fun close()
        fun getListScrollPosition(): Int
        fun showKeyboard()
        fun hideKeyboard()
        fun enableSearchEditTextListeners()
        fun disableSearchEditTextListeners()
        fun executeSearchServiceContentCount(screenId: Long, searchFilter: SearchEngineLocal.SearchFilter)
        fun executeSearchServiceContentPreview(screenId: Long, searchFilter: SearchEngineLocal.SearchFilter)
        fun executeSearchServiceNotePreview(screenId: Long, searchFilter: SearchEngineLocal.SearchFilter)
    }

    object IntentOptions {
        var Intent.screenId by IntentExtra.Long(name = BaseActivity.EXTRA_SCREEN_ID, defaultValue = 0L)

        var Intent.mode by IntentExtra.Serializable(defaultValue = SearchMode.HISTORY)
        var Intent.modeCollectionId by IntentExtra.Long(defaultValue = 0L)
        var Intent.modeContentItemId by IntentExtra.Long(defaultValue = 0L)

        // where was search launched from?
        var Intent.contextLibraryCollectionId by IntentExtra.Long(defaultValue = 0L)
        var Intent.contextContentItemId by IntentExtra.Long(defaultValue = 0L)
        var Intent.contextNavCollectionId by IntentExtra.Long(defaultValue = 0L)
        var Intent.contextSubItemId by IntentExtra.Long(defaultValue = 0L)

        var Intent.searchText by IntentExtra.String(defaultValue = "")
        var Intent.scrollPosition by IntentExtra.Int(defaultValue = 0)

    }

    object SaveStateOptions {
        var Bundle.mode by BundleExtra.Serializable<SearchMode>()
        var Bundle.modeCollectionId by BundleExtra.Long()
        var Bundle.modeContentItemId by BundleExtra.Long()

        var Bundle.searchText by BundleExtra.String()
        var Bundle.scrollPosition by BundleExtra.Int()
    }
}

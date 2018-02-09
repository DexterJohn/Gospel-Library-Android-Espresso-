package org.lds.ldssa.ux.search

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.annotation.StringRes
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.devbrackets.android.recyclerext.decoration.StickyHeaderDecoration
import kotlinx.android.synthetic.main.activity_search.*
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.ContentIntentParams
import org.lds.ldssa.model.database.search.searchallcount.SearchAllCount
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContent
import org.lds.ldssa.model.database.search.searchhistory.SearchHistory
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNote
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitem
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestion
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.search.SearchEngineLocal
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.mobile.ui.ext.getScrollPosition
import org.lds.mobile.ui.setVisible
import org.lds.mobile.util.LdsKeyboardUtil
import javax.inject.Inject

class SearchActivity : BaseActivity(), ScreenLauncherUtil.ScreenActivity, SearchContract.View {
    @Inject
    lateinit var keyboardUtil: LdsKeyboardUtil
    @Inject
    lateinit var presenter: SearchPresenter

    private var stickyHeaderDecoration: StickyHeaderDecoration? = null
    private val searchTextWatcher = SearchTextWatcher()
    private val searchEditorActionListener = SearchEditorActionListener()

    private val searchHistoryAdapter = SearchHistoryAdapter().apply {
        itemClickListener = { presenter.onSearchHistorySelected(it) }
        itemPopulateSearchClickListener = { presenter.onSearchHistoryPopulateClicked(it) }
    }

    private val searchSuggestionAdapter = SearchSuggestionAdapter().apply {
        itemClickListener = { presenter.onSearchSuggestionSelected(it) }
        itemPopulateSearchClickListener = { presenter.onSearchSuggestionPopulateClicked(it) }
    }

    private val searchAllCountAdapter = SearchAllCountAdapter().apply {
        itemClickListener = { presenter.onSearchCountCollectionItemSelected(it) }
    }

    private val searchCountContentAdapter = SearchCountContentAdapter().apply {
        itemClickListener = { presenter.onSearchContentCountItemSelected(it) }
    }

    private val searchPreviewContentAdapter = SearchPreviewContentAdapter().apply {
        itemClickListener = { presenter.onSearchPreviewCountItemSelected(it) }
    }

    private val searchPreviewNotesAdapter = SearchPreviewNotesAdapter().apply {
        itemClickListener = { presenter.onSearchPreviewNoteItemSelected(it) }
    }

    private val microphoneAvailable by lazy {
        // Check to see if there is something that can handle the speech input
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        return@lazy packageManager.queryIntentActivities(intent, 0).size > 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.get().inject(this)
        super.onCreate(savedInstanceState)

        with(SearchContract.IntentOptions) {
            presenter.init(this@SearchActivity, getLanguageId(), getScreenId(),
                    intent.mode,
                    intent.modeCollectionId,
                    intent.modeContentItemId,
                    intent.contextLibraryCollectionId,
                    intent.contextContentItemId,
                    intent.contextNavCollectionId,
                    intent.contextSubItemId,
                    intent.searchText,
                    intent.scrollPosition)
        }

        savedInstanceState?.let { restoreState(it) }

        setupViews()
        setupList()
        updateToolbarButtons()

        presenter.load()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_search
    }

    override fun onStart() {
        super.onStart()
        presenter.register()
    }

    override fun onStop() {
        presenter.unregister()
        super.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.let {
            // Handles the voice search results
            if (requestCode == VOICE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                val matches = it.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (!matches.isEmpty()) {
                    presenter.onTextFromMic(matches[0])
                }
            }
        }
    }

    override fun enableSearchEditTextListeners() {
        searchEditText.addTextChangedListener(searchTextWatcher)
        searchEditText.setOnEditorActionListener(searchEditorActionListener)
    }

    override fun disableSearchEditTextListeners() {
        searchEditText.removeTextChangedListener(searchTextWatcher)
        searchEditText.setOnEditorActionListener(null)
    }

    private fun setupList() {
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun addStickyHeader() {
        if (stickyHeaderDecoration == null) {
            stickyHeaderDecoration = StickyHeaderDecoration(searchRecyclerView)
        }
        searchRecyclerView.addItemDecoration(stickyHeaderDecoration)
    }

    private fun removeStickyHeader() {
        if (stickyHeaderDecoration != null) {
            searchRecyclerView.removeItemDecoration(stickyHeaderDecoration)
        }
    }

    private fun setupViews() {
        searchProgressBar.isIndeterminate = true

        backImageView.setOnClickListener { presenter.onBackMode() }
        searchMicImageView.setOnClickListener { onMicClicked() }
        searchClearTextImageView.setOnClickListener { presenter.onClearTextClicked() }
    }

    override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.SEARCH
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveState(outState)
    }

    private fun requestVoicePermissionWithRationale() {
        // Since the are manually clicking on the voice search icon, we will assume that is enough for now
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), VOICE_PERMISSION_REQUEST)
    }

    private fun updateToolbarButtons() {
        searchClearTextImageView.setVisible(searchEditText.text.isNotEmpty())
        searchMicImageView.setVisible(microphoneAvailable && searchEditText.text.isEmpty())
    }


    private fun onMicClicked() {
        // request permissions (if needed)
        if (!hasVoicePermission()) {
            requestVoicePermissionWithRationale()
            return
        }

        // show voice input dialog
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        startActivityForResult(intent, VOICE_REQUEST_CODE)
    }

    /**
     * Determines if the voice permission has been granted for the parent application.

     * @return True if we can use the voice permission
     */
    private fun hasVoicePermission(): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        return permissionCheck == PackageManager.PERMISSION_GRANTED
    }

    // SHOULD ONLY be called from presenter.setSearchText(...)
    override fun setSearchText(text: String) {
        // be sure the text for the search is in the search bar
        searchEditText.setText(text)
        searchEditText.setSelection(searchEditText.text.length) // place the cursor at the end of the text

        updateToolbarButtons()
    }

    override fun getSearchText(): String {
        return searchEditText.text.toString()
    }

    override fun setSearchHeaderText(text: String) {
        searchHeaderTextView.text = text
    }

    override fun setSearchHeaderText(@StringRes stringResId: Int) {
        searchHeaderTextView.setText(stringResId)
    }

    override fun showContentDirectoryRoot(screenId: Long, contentItemId: Long, referrer: Analytics.Referrer) {
        internalIntents.showContentDirectory(this, screenId, contentItemId, true, false, referrer)
    }

    override fun showContentDirectory(screenId: Long, contentItemId: Long, navCollectionId: Long, referrer: Analytics.Referrer) {
        internalIntents.showContentDirectory(this, screenId, contentItemId, navCollectionId, true, false, referrer)
    }

    override fun verifyContentDownloaded(contentItemId: Long): Boolean {
        return internalIntents.verifyContentDownloaded(this, contentItemId, getScreenId())
    }

    override fun showContent(contentIntentParams: ContentIntentParams) {
        internalIntents.showContent(this, contentIntentParams)
    }

    override fun showContentWithFindOnPage(contentIntentParams: ContentIntentParams) {
        internalIntents.showContent(this, contentIntentParams)
    }

    override fun showNotebook(screenId: Long, notebookId: Long) {
        internalIntents.showNotebook(this, screenId, notebookId)
    }

    override fun showNote(screenId: Long, annotationId: Long) {
        internalIntents.showAnnotationView(this, screenId, annotationId, false, getSearchText())
    }

    override fun showSearchHeader(visible: Boolean) {
        searchHeaderLayout.setVisible(visible)
    }

    override fun showSearchProgress(visible: Boolean) {
        searchProgressBar.setVisible(visible)
    }

    override fun setEmptyStateIndicatorVisible(visible: Boolean) {
        searchEmptyStateIndicator.setVisible(visible)
    }

    override fun changeAdapter(mode: SearchMode) {
        when (mode) {
            SearchMode.HISTORY -> {
                removeStickyHeader()
                searchRecyclerView.adapter = searchHistoryAdapter
            }
            SearchMode.SUGGESTION -> {
                removeStickyHeader()
                searchRecyclerView.adapter = searchSuggestionAdapter
            }
            SearchMode.SEARCH_ALL_COUNT -> {
                removeStickyHeader()
                searchRecyclerView.adapter = searchAllCountAdapter
            }
            SearchMode.SEARCH_COUNT_COLLECTION -> {
                removeStickyHeader()
                searchRecyclerView.adapter = searchCountContentAdapter
            }
            SearchMode.PREVIEW_CONTENT -> {
                searchRecyclerView.adapter = searchPreviewContentAdapter
                addStickyHeader()
            }
            SearchMode.PREVIEW_NOTES -> {
                searchRecyclerView.adapter = searchPreviewNotesAdapter
                addStickyHeader()
            }
        }
    }

    override fun searchSuggestionAdapterShowing(): Boolean {
        return searchRecyclerView.adapter === searchSuggestionAdapter
    }

    override fun setSearchHistoryList(list: List<SearchHistory>) {
        searchHistoryAdapter.list = list
    }

    override fun setSearchSuggestionList(list: List<SearchSuggestion>) {
        searchSuggestionAdapter.list = list
    }

    override fun setSearchCountList(list: List<SearchAllCount>) {
        searchAllCountAdapter.list = list
    }

    override fun setSearchCountCollectionList(list: List<SearchCountContent>) {
        searchCountContentAdapter.list = list
    }

    override fun setPreviewContentList(list: List<SearchPreviewSubitem>) {
        searchPreviewContentAdapter.list = list
    }

    override fun setPreviewNoteList(list: List<SearchPreviewNote>) {
        searchPreviewNotesAdapter.list = list
    }

    override fun scrollToPosition(scrollPosition: Int) {
        searchRecyclerView.scrollToPosition(scrollPosition)
    }

    override fun getListScrollPosition(): Int {
        return searchRecyclerView.getScrollPosition()
    }

    override fun showKeyboard() {
        keyboardUtil.showKeyboard(this)
    }

    override fun hideKeyboard() {
        keyboardUtil.hideKeyboardAfterFocus(searchEditText)
        keyboardUtil.hideKeyboard(this)
    }

    override fun executeSearchServiceContentCount(screenId: Long, searchFilter: SearchEngineLocal.SearchFilter) {
        SearchEngineLocal.executeContentCountSearch(this, screenId, searchFilter)
    }

    override fun executeSearchServiceContentPreview(screenId: Long, searchFilter: SearchEngineLocal.SearchFilter) {
        SearchEngineLocal.executeContentPreviewSearch(this, screenId, searchFilter)
    }

    override fun executeSearchServiceNotePreview(screenId: Long, searchFilter: SearchEngineLocal.SearchFilter) {
        SearchEngineLocal.executeNotePreviewSearch(this, screenId, searchFilter)
    }

    override fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem): Boolean {
        return screenHistoryItem.sourceType == ScreenSourceType.SEARCH
    }

    override fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem) {
        screenHistoryItem.sourceType = ScreenSourceType.SEARCH
        screenHistoryItem.title = getString(R.string.search)

        // extras
        screenHistoryItem.setExtras(gson, presenter.getScreenHistoryExtrasSearch())
    }

    override fun onBackPressed() {
        presenter.onBackMode()
    }

    override fun close() {
        super.onBackPressed()
    }

    private fun restoreState(bundle: Bundle) {
        with(SearchContract.SaveStateOptions) {
            presenter.mode = bundle.mode!!
            presenter.modeCollectionId = bundle.modeCollectionId!!
            presenter.modeContentItemId = bundle.modeContentItemId!!

            presenter.initialSearchText = bundle.searchText!!
            presenter.scrollPosition = bundle.scrollPosition!!
        }

        // hide the keyboard on rotate
        keyboardUtil.hideKeyboard(this)
    }

    private fun saveState(bundle: Bundle) {
        with(SearchContract.SaveStateOptions) {
            bundle.mode = presenter.mode
            bundle.modeCollectionId = presenter.modeCollectionId
            bundle.modeContentItemId = presenter.modeContentItemId

            bundle.searchText = getSearchText()
            bundle.scrollPosition = getListScrollPosition()
        }
    }

    inner class SearchEditorActionListener : TextView.OnEditorActionListener {
        override fun onEditorAction(view: TextView?, actionId: Int, keyEvent: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) {
                presenter.onSearchKeyboardEnterPressed()
                return true
            }

            return false
        }
    }

    inner class SearchTextWatcher : TextWatcher {
        // Android 4.0 and 4.1 can excessively call these methods... even if nothing is changing... track to see if there really is any changes
        // this still may be good to keep in to make sure "presenter.onSearchTextChanged()" only gets called if the text really did change
        private var lastSearchText = ""

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence, start: Int, end: Int, count: Int) {
            val newSearchText = charSequence.toString()
            if (lastSearchText != newSearchText) {
                lastSearchText = newSearchText

                presenter.onSearchTextChanged()
                updateToolbarButtons()
            }
        }

        override fun afterTextChanged(editable: Editable) {
        }
    }

    companion object {
        private val VOICE_REQUEST_CODE = 10
        private val VOICE_PERMISSION_REQUEST = 20
    }
}
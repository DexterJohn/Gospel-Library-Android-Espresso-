package org.lds.ldssa.ux.annotations.links

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_links.*
import org.lds.ldssa.R
import org.lds.ldssa.R.string.links
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.content.navitem.NavItemManager
import org.lds.ldssa.model.database.content.navsection.NavSectionManager
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestion
import org.lds.ldssa.model.database.types.SearchSuggestionType
import org.lds.ldssa.model.database.userdata.link.Link
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.widget.AnnotationTagView
import org.lds.ldssa.util.annotations.LinkUtil
import org.lds.ldssa.ux.search.SearchSuggestionAdapter
import org.lds.mobile.ui.util.LdsDrawableUtil
import org.lds.mobile.ui.widget.input.DelayTextWatcher
import pocketknife.BindExtra
import pocketknife.NotRequired
import pocketknife.PocketKnife
import timber.log.Timber
import javax.inject.Inject

class LinksActivity : BaseActivity(), AnnotationTagView.OnTagClickListener {

    @Inject
    lateinit var paragraphMetadataManager: ParagraphMetadataManager
    @Inject
    lateinit var inputMethodManager: InputMethodManager
    @Inject
    lateinit var itemManager: ItemManager
    @Inject
    lateinit var navItemManager: NavItemManager
    @Inject
    lateinit var navSectionManager: NavSectionManager
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @NotRequired
    @BindExtra(EXTRA_ANNOTATION_ID)
    var annotationId: Long = 0

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(LinksViewModel::class.java) }

    private val searchSuggestionAdapter = SearchSuggestionAdapter(linkMode = true).apply {
        itemClickListener = { onSearchSuggestionSelected(it) }
        itemLinkClickListener = { onSearchLinkClicked(it) }
        itemPopulateSearchClickListener = { onSearchPopulateClicked(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.get().inject(this)
        PocketKnife.bindExtras(this)
        PocketKnife.restoreInstanceState(this, savedInstanceState)

        super.onCreate(savedInstanceState)

        setTitle(links)

        //Updates the home icon to be a check mark
        (mainToolbar as Toolbar).navigationIcon = LdsDrawableUtil.tintDrawableForTheme(this, R.drawable.ic_lds_action_done_24dp, R.attr.colorAccent)

        setupSearchBar()
        setupSuggestionRecycler()
        setupViewModelObservers()
    }

    @LayoutRes
    override fun getLayoutResourceId(): Int = R.layout.activity_links

    override fun getAnalyticsScreenName(): String = Analytics.Screen.LINKS

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            searchEditText.text = null
            inputMethodManager.hideSoftInputFromWindow(searchEditText.windowToken, 0)
            appBarLayout.setExpanded(true)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finishWithResult(Activity.RESULT_OK)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finishWithResult(Activity.RESULT_OK)
    }

    private fun finishWithResult(resultCode: Int) {
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_ANNOTATION_ID, annotationId)
        setResult(resultCode, resultIntent)

        finish()
    }

    private fun linkTagsLoaded(data: List<Link>) {
        linksLayout.removeAllViews()

        for (link in data) {
            val linkText = LinkUtil.formatViewableName(link)

            val linkWidget = AnnotationTagView(this, linkText, link.id, resources.getDimension(R.dimen.link_bubble_top_margin).toInt())
            linkWidget.setTagClickListener(this)

            linksLayout.addView(linkWidget, linksLayout.childCount)
        }
    }

    override fun onDeleteTag(name: String, linkId: Long) {
        viewModel.deleteLink(linkId)
    }

    override fun onClickTag(name: String, linkId: Long) {
        viewModel.showLink(linkId)
    }

    private fun createLink(searchSuggestion: SearchSuggestion): Boolean {
        val contentItemId = searchSuggestion.contentItemId

        // prompt to download if this content is not downloaded
        if (!internalIntents.verifyContentDownloaded(this, contentItemId, getScreenId())) {
            Timber.e("Cannot create link without a contentItemId")
            return false
        }

        var subItemId: Long = 0
        when (searchSuggestion.type) {
            SearchSuggestionType.GOTO_NAV_COLLECTION -> {
                val sectionId = navSectionManager.findIdByCollectionId(contentItemId, searchSuggestion.id)
                if (searchSuggestion.getChapterNumberValue() > 0) {
                    subItemId = navItemManager.findSubItemIdByChapter(contentItemId, sectionId, searchSuggestion.chapterNumber)
                }
            }
            SearchSuggestionType.GOTO_SUB_ITEM -> subItemId = navItemManager.findSubItemIdById(contentItemId, searchSuggestion.id)
            else -> {
                Timber.e("Cannot create link for type: %s", searchSuggestion.type)
                return false
            }
        }

        if (subItemId <= 0) {
            Timber.e("Cannot create link without a subItemId")
            return false
        }

        val paragraphAid: String
        paragraphAid = if (searchSuggestion.getVerseNumberValue() > 0) {
            paragraphMetadataManager.findAidByVerseNumber(contentItemId, subItemId, searchSuggestion.verseNumber)
        } else {
            paragraphMetadataManager.findTopMostAid(contentItemId, subItemId)
        }

        if (paragraphAid.isEmpty()) {
            Timber.e("Cannot create link without a paragraphAid")
            return false
        }

        viewModel.addLink(contentItemId, subItemId, paragraphAid, searchSuggestion.title)
        appBarLayout.setExpanded(true)
        searchEditText.text = null
        return true
    }

    private fun onSearchSuggestionSelected(searchSuggestion: SearchSuggestion) {
        when (searchSuggestion.type) {
            SearchSuggestionType.CONTENT_ITEM -> showLinkActivityForContentItem(searchSuggestion)// Book of Mormon, October Ensign, True to the Faith, etc
            SearchSuggestionType.GOTO_NAV_COLLECTION -> showLinkActivityForGotoNavCollection(searchSuggestion) // NAV_COLLECTION (Multiple chapter items: 1 Nephi, Alma, etc)
            SearchSuggestionType.GOTO_SUB_ITEM -> showLinkActivityForGotoNavItem(searchSuggestion)// NAV_ITEM (Single chapter books... Omni, 4 Nephi, etc)
            else -> {
                // do nothing
            }
        }
    }

    private fun setupSuggestionRecycler() {
        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        searchResultsRecyclerView.adapter = searchSuggestionAdapter
    }

    private fun setupSearchBar() {
        val textWatcher = DelayTextWatcher()
        searchEditText.addTextChangedListener(textWatcher)
        textWatcher.asLiveData().observeNotNull { charSequence ->
            viewModel.setSearchText(charSequence.toString(), getLanguageId())
        }
    }

    private fun setupViewModelObservers() {
        viewModel.setAnnotationId(annotationId)
        viewModel.linksForAnnotation.observeNotNull { linkTagsLoaded(it) }
        viewModel.suggestions.observeNotNull { searchSuggestionAdapter.list = it }
        viewModel.showLinkContent.observeNotNull {
            internalIntents.showLinkContentActivity(this@LinksActivity, getScreenId(), it.subItemMetadata.itemId, it.subItemMetadata.subitemId, annotationId, REQUEST_CODE, it.linkId)
        }
    }

    private fun showLinkActivityForContentItem(searchSuggestion: SearchSuggestion) {
        val contentItemId = searchSuggestion.contentItemId

        // prompt to download if this content is not downloaded
        if (!internalIntents.verifyContentDownloaded(this, contentItemId, getScreenId())) {
            return
        }

        // show ALL nav items for the given contentItemId
        viewModel.searchCollection(contentItemId)
    }

    private fun showLinkActivityForGotoNavCollection(searchSuggestion: SearchSuggestion) {
        val contentItemId = searchSuggestion.contentItemId
        val navCollectionId = searchSuggestion.id

        // prompt to download if this content is not downloaded
        if (!internalIntents.verifyContentDownloaded(this, contentItemId, getScreenId())) {
            return
        }

        if (searchSuggestion.getChapterNumberValue() > 0) {
            val sectionId = navSectionManager.findIdByCollectionId(contentItemId, navCollectionId)
            val subItemId = navItemManager.findSubItemIdByChapter(contentItemId, sectionId, searchSuggestion.chapterNumber)
            internalIntents.showLinkContentActivity(this@LinksActivity, getScreenId(), contentItemId, subItemId, annotationId, REQUEST_CODE)
        } else {
            if (itemManager.findUriById(contentItemId) == DOCTRINE_AND_COVENANTS_URI) {
                // show ALL nav items for the D&C
                viewModel.searchCollection(contentItemId)
            } else {
                // a specific sub-item could not be identified, so show sub-items for given collection
                viewModel.searchSubItems(contentItemId, searchSuggestion.id)
            }
        }
    }

    private fun showLinkActivityForGotoNavItem(searchSuggestion: SearchSuggestion) {
        val contentItemId = searchSuggestion.contentItemId

        // prompt to download if this content is not downloaded
        if (!internalIntents.verifyContentDownloaded(this, contentItemId, getScreenId())) {
            return
        }

        // goto items _id are nav_item_ids... find the subItemId for it
        val subItemId = navItemManager.findSubItemIdById(contentItemId, searchSuggestion.id)

        internalIntents.showLinkContentActivity(this@LinksActivity, getScreenId(), searchSuggestion.contentItemId, subItemId, annotationId, REQUEST_CODE)
    }

    private fun onSearchPopulateClicked(searchSuggestion: SearchSuggestion) {
        val searchText = searchSuggestion.title
        searchEditText.setText(searchText)
        searchEditText.setSelection(searchText.length)
    }

    private fun onSearchLinkClicked(searchSuggestion: SearchSuggestion) {
        when (searchSuggestion.type) {
            SearchSuggestionType.GOTO_NAV_COLLECTION -> if (searchSuggestion.getChapterNumberValue() > 0) {
                createLink(searchSuggestion)
            }
            SearchSuggestionType.GOTO_SUB_ITEM -> createLink(searchSuggestion)
            else -> {
                // do nothing
            }
        }
    }

    companion object {
        const val EXTRA_ANNOTATION_ID = "EXTRA_ANNOTATION_ID"
        const val DOCTRINE_AND_COVENANTS_URI = "/scriptures/dc-testament"

        private val REQUEST_CODE = 1
    }
}
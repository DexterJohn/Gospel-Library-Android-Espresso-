package org.lds.ldssa.ux.content.directory

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.devbrackets.android.recyclerext.decoration.StickyHeaderDecoration
import com.devbrackets.android.recyclerext.layoutmanager.AutoColumnGridLayoutManager
import kotlinx.android.synthetic.main.activity_content_directory.*
import kotlinx.coroutines.experimental.launch
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Int
import me.eugeniomarletti.extras.intent.base.Long
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.event.catalog.CatalogReloadEvent
import org.lds.ldssa.event.sync.AnnotationSyncFinishedEvent
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.ContentIntentParams
import org.lds.ldssa.model.database.content.navcollection.NavCollection
import org.lds.ldssa.model.database.content.navitem.NavItemManager
import org.lds.ldssa.model.database.types.QueryItemType
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.sync.AnnotationSync
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.dialog.DownloadMediaDialogFragment
import org.lds.ldssa.util.RelatedAudioAvailableUtil
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.ldssa.util.getScrollPosition
import org.lds.mobile.ui.setVisible
import pocketbus.Subscribe
import pocketbus.ThreadMode
import javax.inject.Inject

class ContentDirectoryActivity : BaseActivity(), ScreenLauncherUtil.ScreenActivity, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var navItemManager: NavItemManager
    @Inject
    lateinit var annotationSync: AnnotationSync
    @Inject
    lateinit var relatedAudioAvailableUtil: RelatedAudioAvailableUtil
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(ContentDirectoryViewModel::class.java) }
    private lateinit var adapter: ContentDirectoryAdapter

    private var stickyHeaderDecoration: StickyHeaderDecoration? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        var scrollPosition = 0
        with (IntentOptions) {
            scrollPosition = intent.scrollPosition
        }

        if (savedInstanceState != null) {
            scrollPosition = viewModel.lastScrollPosition
        }

        setupRecyclerView()
        swipeRefreshLayout.setOnRefreshListener(this)

        setupViewModelObservers(scrollPosition)

        with(IntentOptions) {
            viewModel.setContentDirectoryData(getScreenId(), intent.contentItemId, intent.navCollectionId)
        }
    }

    override fun getLayoutResourceId() = R.layout.activity_content_directory

    override fun getAnalyticsScreenName() = Analytics.Screen.CONTENT_DIRECTORY_VIEW

    /**
     * Determine if this Activity is same as the given currentScreenHistoryItem
     */
    override fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem) = viewModel.isScreenHistoryItemEqual(screenHistoryItem)

    override fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem) {
        viewModel.setCurrentScreenHistoryData(screenHistoryItem, recyclerView.getScrollPosition())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        val menuInflater = menuInflater
        commonMenu.inflateMenuPre(this, menu, menuInflater)
        menuInflater.inflate(R.menu.menu_content_directory, menu)
        commonMenu.inflateMenuPost(this, menu, menuInflater)

        // Enables the download menu item when available
        val downloadItem = menu.findItem(R.id.menu_item_download_audio)
        if (downloadItem != null) {
            relatedAudioAvailableUtil.showMenuOptionIfAvailable(viewModel.contentItemId, viewModel.navCollectionId, downloadItem)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_download_audio) {
            downloadAudio()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.lastScrollPosition = recyclerView.getScrollPosition()
        super.onSaveInstanceState(outState)
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        launch(cc.commonPool) {
            annotationSync.sync()
        }
    }

    override fun getUri() = viewModel.navCollectionUri

    override fun getGlContentContext() = viewModel.getGlContentContext()

    @Suppress("UNUSED_PARAMETER")
    @Subscribe(ThreadMode.MAIN)
    fun handle(event: AnnotationSyncFinishedEvent) {
        swipeRefreshLayout.isRefreshing = false

        // no UI update needed
    }

    @Suppress("UNUSED_PARAMETER")
    @Subscribe(ThreadMode.MAIN)
    fun handle(event: CatalogReloadEvent) {
        viewModel.reloadContentDirectory()
    }

    /**
     * Restores the scroll position based on the item position
     * specified
     *
     * @param position The view position (index) to scroll to
     */
    private fun restoreScrollPosition(position: Int) {
        val manager = recyclerView.layoutManager as LinearLayoutManager
        manager.scrollToPositionWithOffset(position, 0)
    }

    private fun setupRecyclerView() {
        adapter = ContentDirectoryAdapter(this, viewModel.navCollectionId == NavCollection.ROOT_NAV_COLLECTION_ID)
        adapter.clickListener = { itemType: QueryItemType, id: Long ->
            when (itemType) {
                QueryItemType.COLLECTION -> internalIntents.showContentDirectory(this, getScreenId(), viewModel.contentItemId, id, false, false, Analytics.Referrer.ITEM_NAVIGATION)
                QueryItemType.ITEM -> {
                    val subItemId = navItemManager.findSubItemIdById(viewModel.contentItemId, id)
                    internalIntents.showContent(this, ContentIntentParams(getScreenId(), viewModel.contentItemId, subItemId, Analytics.Referrer.ITEM_NAVIGATION))
                }
                else -> {
                    // do nothing
                }
            }
        }

        recyclerView.adapter = adapter
        when {
            prefs.generalDisplayAsList -> setupAsList()
            else -> setupAsGrid()
        }

        fastScroll.attach(recyclerView)
    }

    private fun setupAsList() {
        adapter.setDisplayList(true)

        if (stickyHeaderDecoration == null) {
            stickyHeaderDecoration = StickyHeaderDecoration(recyclerView)
        }

        updateRecyclerPadding(0)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(stickyHeaderDecoration)
    }

    private fun setupAsGrid() {
        adapter.setDisplayList(false)

        recyclerView.removeItemDecoration(stickyHeaderDecoration)
        stickyHeaderDecoration?.dispose()
        stickyHeaderDecoration = null

        val columnWidth = resources.getDimensionPixelSize(R.dimen.content_card_width)

        updateRecyclerPadding(resources.getDimensionPixelSize(R.dimen.catalog_grid_horizontal_padding))
        val manager = AutoColumnGridLayoutManager(this, columnWidth)
        manager.setSpacingMethod(AutoColumnGridLayoutManager.SpacingMethod.ALL)
        manager.setMatchRowAndColumnSpacing(true)
        recyclerView.layoutManager = manager
    }

    private fun updateRecyclerPadding(horizontalPadding: Int) {
        recyclerView.setPadding(horizontalPadding, recyclerView.paddingTop, horizontalPadding, recyclerView.paddingBottom)
    }

    private fun setupViewModelObservers(scrollPosition: Int) {
        viewModel.contentDirectoryDirectoryList.observeNotNull { data ->
            adapter.setIsRootCollection(viewModel.navCollectionId == NavCollection.ROOT_NAV_COLLECTION_ID)
            adapter.clear()
            adapter.addAll(data)
            restoreScrollPosition(scrollPosition)
        }

        viewModel.fastScrollIndexEntriesList.observeNotNull { entries ->
            val hasIndexes = entries.isNotEmpty()

            adapter.setIndexedEntries(entries)
            fastScroll.setVisible(hasIndexes)
            fastScroll.setShowBubble(hasIndexes)
            fastScroll.setTrackClicksAllowed(true)
            fastScroll.setHideHandleAllowed(false)

            // If the content has indexes, then disable the swipe refresh to prevent it from interfering with the fast scroll.
            // The standard scrollbar also needs to be hidden since the fast scroll has its own scrollbar.
            swipeRefreshLayout.isEnabled = !hasIndexes
            recyclerView.isVerticalScrollBarEnabled = !hasIndexes
        }

        viewModel.navigationTrailList.observeNotNull { navTrailList ->
            updateNavigationTrail(navTrailList)
        }

        viewModel.contentDirectoryTitle.observeNotNull { data ->
            title = data.title
            if (data.subTitle.isNotBlank()) {
                setSubTitle(data.subTitle)
            }
        }
    }

    private fun downloadAudio() {
        if (viewModel.navCollectionId != NavCollection.ROOT_NAV_COLLECTION_ID) {
            DownloadMediaDialogFragment.newInstance(viewModel.contentItemId, viewModel.navCollectionId).show(supportFragmentManager)
        } else {
            DownloadMediaDialogFragment.newInstance(viewModel.contentItemId).show(supportFragmentManager)
        }
    }

    object IntentOptions {
        var Intent.contentItemId by IntentExtra.Long(defaultValue = 0L)
        var Intent.navCollectionId by IntentExtra.Long(defaultValue = 0)

        // Optional
        var Intent.scrollPosition by IntentExtra.Int(defaultValue = 0)
    }
}
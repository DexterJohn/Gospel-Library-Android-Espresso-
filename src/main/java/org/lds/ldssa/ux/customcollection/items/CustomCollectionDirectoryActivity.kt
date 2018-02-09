package org.lds.ldssa.ux.customcollection.items

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import com.devbrackets.android.recyclerext.adapter.helper.SimpleElevationItemTouchHelperCallback
import com.devbrackets.android.recyclerext.layoutmanager.AutoColumnGridLayoutManager
import kotlinx.android.synthetic.main.activity_catalog_directory.*
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Int
import me.eugeniomarletti.extras.intent.base.Long
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.download.GLDownloadManager
import org.lds.ldssa.event.catalog.CatalogReloadEvent
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.allitemsincollectionquery.AllItemsInCollectionQueryManager
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQuery
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager
import org.lds.ldssa.model.database.types.CatalogItemQueryType
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionManager
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemManager
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasCustomCollectionDirectory
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.menu.CustomCollectionDirectoryMenu
import org.lds.ldssa.util.ContentItemUtil
import org.lds.ldssa.util.CustomCollectionUtil
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.ldssa.util.getScrollPosition
import org.lds.ldssa.ux.catalog.CatalogDirectoryAdapter
import org.lds.mobile.ui.setVisible
import pocketbus.Subscribe
import pocketbus.ThreadMode
import java.util.ArrayList
import javax.inject.Inject

class CustomCollectionDirectoryActivity : BaseActivity(), ScreenLauncherUtil.ScreenActivity {

    @Inject
    lateinit var customCollectionManager: CustomCollectionManager
    @Inject
    lateinit var customCollectionItemManager: CustomCollectionItemManager
    @Inject
    lateinit var navigationTrailQueryManager: NavigationTrailQueryManager
    @Inject
    lateinit var downloadManager: GLDownloadManager
    @Inject
    lateinit var customCollectionDirectoryMenu: CustomCollectionDirectoryMenu
    @Inject
    lateinit var contentItemUtil: ContentItemUtil
    @Inject
    lateinit var downloadedItemManager: DownloadedItemManager
    @Inject
    lateinit var itemsInCollectionManager: AllItemsInCollectionQueryManager
    @Inject
    lateinit var customCollectionUtil: CustomCollectionUtil
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(CustomCollectionDirectoryViewModel::class.java) }

    private var customCollectionId = 0L
    private var scrollPosition = 0

    private lateinit var adapter: CatalogDirectoryAdapter

    private lateinit var itemTouchHelper: ItemTouchHelper

    init {
        Injector.get().inject(this)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_catalog_directory
    }

    override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.CUSTOM_COLLECTION_DIRECTORY
    }

    override fun allowFinishOnHome(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(IntentOptions) {
            customCollectionId = intent.customCollectionId
            scrollPosition = intent.scrollPosition
        }

        title = customCollectionManager.findTitleById(customCollectionId)
        updateNavigationTrail(navigationTrailQueryManager.findDefaultAllForCustomCollection(getLanguageId()))

        setupRecyclerView()
        swipeRefreshLayout.isEnabled = false

        setupViewModelObservers()

        viewModel.setCustomCollectionId(customCollectionId)
    }

    public override fun onResume() {
        super.onResume()

        //Handles the change between list and grid
        val displayingGrid = directoryRecyclerView.layoutManager is AutoColumnGridLayoutManager
        if (prefs.generalDisplayAsList == displayingGrid) {
            setLayoutAsList(prefs.generalDisplayAsList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        directoryRecyclerView.adapter = null
    }

    override fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem): Boolean {
        return if (screenHistoryItem.sourceType === ScreenSourceType.CUSTOM_CATALOG_DIRECTORY) {
            screenHistoryItem.getExtras(gson, ScreenHistoryExtrasCustomCollectionDirectory::class.java).customCollectionId == customCollectionId
        } else false
    }

    override fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem) {
        screenHistoryItem.sourceType = ScreenSourceType.CUSTOM_CATALOG_DIRECTORY
        screenHistoryItem.title = customCollectionManager.findTitleById(customCollectionId)
        screenHistoryItem.setExtras(gson, ScreenHistoryExtrasCustomCollectionDirectory(customCollectionId, directoryRecyclerView.getScrollPosition()))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        customCollectionDirectoryMenu.onCreateOptionsMenu(this, menu, menuInflater)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (customCollectionDirectoryMenu.onOptionsItemSelected(this, item, customCollectionId)) {
            return true
        }

        val outIds = ArrayList<Long>()

        when (item.itemId) {
            R.id.context_menu_item_download -> {
                findItemsIdsInFolders(outIds, true, customCollectionId)
                downloadSelectedContent(outIds)
                return true
            }
            R.id.context_menu_item_remove -> {
                findItemsIdsInFolders(outIds, true, customCollectionId)
                removeSelectedContent(outIds)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModelObservers() {
        viewModel.customCollectionDirectoryList.observeNotNull { catalogItemQueryItems ->
            adapter.setItemsWithLiveDataListeners(catalogItemQueryItems)

            itemTouchHelper.attachToRecyclerView(if (adapter.items.isEmpty()) null else directoryRecyclerView)

            restoreScrollPosition(scrollPosition)
            emptyStateIndicator.setVisible(catalogItemQueryItems.isEmpty())
        }
    }

    private fun onCatalogItemClick(catalogItemQuery: CatalogItemQuery) {
        when (catalogItemQuery.type) {
            CatalogItemQueryType.CUSTOM_COLLECTION_CONTENT_ITEM -> internalIntents.showContentDirectory(this, getScreenId(), catalogItemQuery.parentId, false, false, Analytics.Referrer.ITEM_NAVIGATION)
            else -> {
                // do nothing
            }
        }
    }

    private fun onCatalogItemMenuSelection(catalogItemQuery: CatalogItemQuery, menuItem: MenuItem): Boolean {
        val itemIds = ArrayList<Long>()

        when (menuItem.itemId) {
            R.id.context_menu_item_download -> {
                addItemIds(catalogItemQuery, itemIds)
                downloadSelectedContent(itemIds)
                return true
            }
            R.id.context_menu_item_remove -> {
                addItemIds(catalogItemQuery, itemIds)
                removeSelectedContent(itemIds)
                return true
            }
            R.id.menu_item_remove_from_collection -> {
                customCollectionUtil.removeItemFromCollection(catalogItemQuery.id)
                return true
            }
        }

        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        scrollPosition = directoryRecyclerView.getScrollPosition()
        super.onSaveInstanceState(outState)
    }

    @Suppress("UNUSED_PARAMETER")
    @Subscribe(ThreadMode.MAIN)
    fun handle(event: CatalogReloadEvent) {
        viewModel.reloadCustomCollections()
    }

    /**
     * Restores the scroll position based on the item position
     * specified
     *
     * @param position The view position (index) to scroll to
     */
    private fun restoreScrollPosition(position: Int) {
        val manager = directoryRecyclerView.layoutManager as LinearLayoutManager
        manager.scrollToPositionWithOffset(position, 0)
    }

    private fun downloadSelectedContent(itemIds: List<Long>) {
        val downloadIdsNotInstalled = downloadedItemManager.findAllContentItemIdsToInstallOrUpdate(itemIds)
        if (downloadIdsNotInstalled.size > 1) {
            MaterialDialog.Builder(this)
                    .title(R.string.download_all)
                    .content(getString(R.string.download_all_selected_confirm, downloadIdsNotInstalled.size))
                    .positiveText(R.string.download_all)
                    .negativeText(R.string.cancel)
                    .onPositive { _, _ -> downloadManager.downloadContent(downloadIdsNotInstalled) }
                    .show()
        } else if (downloadIdsNotInstalled.size == 1) {
            downloadManager.downloadContent(downloadIdsNotInstalled[0])
        }
    }

    private fun removeSelectedContent(selectedItemIds: List<Long>) {
        val installedItemIds = downloadedItemManager.findContentItemIdsInstalled(selectedItemIds)
        if (installedItemIds.isEmpty()) {
            return
        }

        MaterialDialog.Builder(this)
                .title(if (installedItemIds.size > 1) R.string.remove_all else R.string.remove)
                .content(R.string.remove_all_selected_confirm)
                .positiveText(if (installedItemIds.size > 1) R.string.remove_all else R.string.remove)
                .negativeText(R.string.cancel)
                .onPositive { _, _ -> contentItemUtil.userRemoveDownloadedContentItems(installedItemIds) }
                .show()
    }

    private fun addItemIds(itemQuery: CatalogItemQuery, outIds: MutableList<Long>) {
        when (itemQuery.type) {
            CatalogItemQueryType.COLLECTION, CatalogItemQueryType.CUSTOM_COLLECTION -> findItemsIdsInFolders(outIds, itemQuery.isCustomCollection(), itemQuery.id)
            CatalogItemQueryType.COLLECTION_CONTENT_ITEM, CatalogItemQueryType.CUSTOM_COLLECTION_CONTENT_ITEM -> outIds.add(itemQuery.parentId)
            else -> {
                // Do Nothing
            }
        }
    }

    private fun findItemsIdsInFolders(outItems: MutableList<Long>, isCustomCollection: Boolean, collectionId: Long) {
        if (isCustomCollection) {
            outItems.addAll(customCollectionItemManager.findAllItemIdsByCollectionId(collectionId))
        } else {
            outItems.addAll(itemsInCollectionManager.findAllItemIdsByCollectionId(collectionId, prefs.isObsoleteContentEnabled))
        }
    }

    private fun setupRecyclerView() {
        adapter = CatalogDirectoryAdapter(this, cc).apply {
            itemClickListener = { catalogItemQuery ->
                onCatalogItemClick(catalogItemQuery)
            }
            menuItemClickListener = { catalogItemQuery, menuItem ->
                onCatalogItemMenuSelection(catalogItemQuery, menuItem)
            }
        }

        directoryRecyclerView.adapter = adapter
        setLayoutAsList(prefs.generalDisplayAsList)

        setupRecyclerTouchHelper()
    }

    private fun setupRecyclerTouchHelper() {
        var dragDirections = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        if (!prefs.generalDisplayAsList) {
            dragDirections = dragDirections or (ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        val dragElevationChange = resources.getDimensionPixelSize(R.dimen.drag_elevation_increase).toFloat()

        itemTouchHelper = ItemTouchHelper(object : SimpleElevationItemTouchHelperCallback(dragDirections, 0, dragElevationChange) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val originalPosition = viewHolder.adapterPosition
                val endPosition = target.adapterPosition
                if (originalPosition == RecyclerView.NO_POSITION || endPosition == RecyclerView.NO_POSITION) {
                    return false
                }
                adapter.move(originalPosition, endPosition)
                customCollectionUtil.updateOrderPositions(customCollectionId, adapter.getPositionsMap())
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //Swipe not supported
            }
        })

        itemTouchHelper.attachToRecyclerView(directoryRecyclerView)
    }

    private fun setLayoutAsList(listLayout: Boolean) {
        if (listLayout) {
            setupAsList()
        } else {
            setupAsGrid()
        }
    }

    private fun setupAsList() {
        adapter.setDisplayList(true)

        updateRecyclerPadding(0)
        directoryRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupAsGrid() {
        adapter.setDisplayList(false)

        val columnWidth = resources.getDimensionPixelSize(R.dimen.catalog_card_width)

        updateRecyclerPadding(resources.getDimensionPixelSize(R.dimen.catalog_grid_horizontal_padding))
        val manager = AutoColumnGridLayoutManager(this, columnWidth)
        manager.setMatchRowAndColumnSpacing(true)
        directoryRecyclerView.layoutManager = manager
    }

    private fun updateRecyclerPadding(horizontalPadding: Int) {
        directoryRecyclerView.setPadding(horizontalPadding, directoryRecyclerView.paddingTop, horizontalPadding, directoryRecyclerView.paddingBottom)
    }

    object IntentOptions {
        var Intent.customCollectionId by IntentExtra.Long(defaultValue = 0L)

        // Optional
        var Intent.scrollPosition by IntentExtra.Int(defaultValue = 0)
    }
}

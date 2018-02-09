package org.lds.ldssa.ux.catalog

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import com.devbrackets.android.recyclerext.layoutmanager.AutoColumnGridLayoutManager
import kotlinx.android.synthetic.main.activity_catalog_directory.*
import kotlinx.coroutines.experimental.launch
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Int
import me.eugeniomarletti.extras.intent.base.Long
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.download.GLDownloadManager
import org.lds.ldssa.event.catalog.CatalogReloadEvent
import org.lds.ldssa.event.catalog.CatalogUpdateCheckCompletedEvent
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.GLContentContext
import org.lds.ldssa.model.database.catalog.allitemsincollectionquery.AllItemsInCollectionQueryManager
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQuery
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionManager
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager
import org.lds.ldssa.model.database.types.CatalogItemQueryType
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemManager
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasCatalogDirectory
import org.lds.ldssa.sync.AnnotationSync
import org.lds.ldssa.task.TipsUpdateTask
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.menu.CatalogDirectoryMenu
import org.lds.ldssa.util.CatalogUpdateUtil
import org.lds.ldssa.util.ContentItemUtil
import org.lds.ldssa.util.CustomCollectionUtil
import org.lds.ldssa.util.LanguageUtil
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.ldssa.util.getScrollPosition
import org.lds.ldssa.ux.customcollection.collections.CustomCollectionsActivity
import pocketbus.Subscribe
import pocketbus.ThreadMode
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Provider

class CatalogDirectoryActivity : BaseActivity(), ScreenLauncherUtil.ScreenActivity, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var libraryCollectionManager: LibraryCollectionManager
    @Inject
    lateinit var trailQueryManager: NavigationTrailQueryManager
    @Inject
    lateinit var customCollectionUtil: CustomCollectionUtil
    @Inject
    lateinit var catalogDirectoryMenu: CatalogDirectoryMenu
    @Inject
    lateinit var customCollectionItemManager: CustomCollectionItemManager
    @Inject
    lateinit var itemsInCollectionManager: AllItemsInCollectionQueryManager
    @Inject
    lateinit var downloadedItemManager: DownloadedItemManager
    @Inject
    lateinit var downloadManager: GLDownloadManager
    @Inject
    lateinit var contentItemUtil: ContentItemUtil
    @Inject
    lateinit var catalogUpdateUtil: CatalogUpdateUtil
    @Inject
    lateinit var tipsUpdateTaskProvider: Provider<TipsUpdateTask>
    @Inject
    lateinit var annotationSync: AnnotationSync
    @Inject
    lateinit var languageUtil: LanguageUtil
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(CatalogDirectoryViewModel::class.java) }

    private var collectionId = 0L

    private lateinit var adapter: CatalogDirectoryAdapter

    private var isRootCollection = false

    init {
        Injector.get().inject(this)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_catalog_directory
    }

    override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.CATALOG_DIRECTORY_VIEW
    }

    override fun allowFinishOnHome(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var scrollPosition = 0
        var scrollToContentItemId = 0L
        with(IntentOptions) {
            collectionId = intent.collectionId
            scrollPosition = intent.scrollPosition
            scrollToContentItemId = intent.scrollToContentItemId
        }

        if (savedInstanceState != null) {
            scrollPosition = viewModel.lastScrollPosition
        }

        // make sure we have a good collectionId, or rollback to root collection
        if (!libraryCollectionManager.collectionExists(collectionId)) {
            Timber.e("Failed to show collectionId [%d]... which does not exist... showing root catalog...", collectionId)
            collectionId = languageManager.findRootCollectionIdByLanguageId(languageUtil.deviceLanguageId)
        }

        screenUtil.updateLanguage(getScreenId(), languageManager.findLanguageIdByLibraryCollection(collectionId))
        isRootCollection = languageManager.isRootCollection(collectionId)

        var title = ""
        if (isRootCollection) {
            setTitle(R.string.library)
        } else {
            title = libraryCollectionManager.findTitleById(collectionId)
            setTitle(title)
        }

        setupRecyclerView(title)

        // set selected item
        updateNavigationTrail(trailQueryManager.findAllForCatalogDirectory(collectionId))

        supportActionBar?.setDisplayHomeAsUpEnabled(screenUtil.hasPreviousScreen(this))

        swipeRefreshLayout.setOnRefreshListener(this)

        setupViewModelObservers(scrollPosition, scrollToContentItemId)

        viewModel.setCatalogData(collectionId, getScreenId())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        catalogDirectoryMenu.onCreateOptionsMenu(this, menu, menuInflater, collectionId)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (catalogDirectoryMenu.onOptionsItemSelected(this, item)) {
            return true
        }

        when (item.itemId) {
            R.id.context_menu_item_download -> {
                downloadSelectedContent(findAllContentItemsIdsInCollection(false, collectionId))
                return true
            }
            R.id.context_menu_item_remove -> {
                removeSelectedContent(findAllContentItemsIdsInCollection(false, collectionId))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
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

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            InternalIntents.COLLECTIONS_REQUEST -> if (resultCode == Activity.RESULT_OK) {
                bus.post(CatalogReloadEvent())
            } else if (resultCode == CustomCollectionsActivity.RESULT_ITEMS_ADD_TO_COLLECTION) {
                onReturnFromAddToCollection(data)
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem): Boolean {
        return when {
            screenHistoryItem.sourceType === ScreenSourceType.CATALOG_DIRECTORY -> screenHistoryItem.getExtras(gson, ScreenHistoryExtrasCatalogDirectory::class.java).collectionId == collectionId
            else -> false
        }
    }

    override fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem) {
        screenHistoryItem.sourceType = ScreenSourceType.CATALOG_DIRECTORY
        screenHistoryItem.title = libraryCollectionManager.findTitleById(collectionId)
        screenHistoryItem.setExtras(gson, ScreenHistoryExtrasCatalogDirectory(collectionId, directoryRecyclerView.getScrollPosition()))
    }

    private fun onCatalogItemClick(catalogItemQuery: CatalogItemQuery) {
        when (catalogItemQuery.type) {
            CatalogItemQueryType.COLLECTION -> internalIntents.showCatalog(this, getScreenId(), catalogItemQuery.id)
            CatalogItemQueryType.CUSTOM_COLLECTION -> internalIntents.showCustomCollection(this, getScreenId(), catalogItemQuery.id, false)
            CatalogItemQueryType.COLLECTION_CONTENT_ITEM -> onCollectionContentItemClick(catalogItemQuery.id)
            CatalogItemQueryType.NOTES -> internalIntents.showNotes(this, getScreenId())
            CatalogItemQueryType.TIPS -> internalIntents.showTipList(this, getScreenId(), false)
            CatalogItemQueryType.STUDY_PLANS -> internalIntents.showStudyPlans(this, getScreenId())
            else -> {
                // do nothing
            }
        }
    }

    override fun getGlContentContext(): GLContentContext {
        return GLContentContext(collectionId, 0L, 0L, 0L)
    }

    private fun setupViewModelObservers(scrollPosition: Int, scrollToContentItemId: Long) {
        viewModel.catalogDirectoryList.observeNotNull { catalogItemQueryItems ->
            adapter.setItemsWithLiveDataListeners(catalogItemQueryItems)

            // only restore scroll position on initial load
            if (viewModel.restoreScrollPosition) {
                viewModel.restoreScrollPosition = false
                if (scrollToContentItemId > 0L) {
                    scrollToItem(scrollToContentItemId)
                } else {
                    restoreScrollPosition(scrollPosition)
                }
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
            R.id.context_menu_add_to_custom_collection -> {
                catalogItemQuery.externalId?.let {
                    internalIntents.showAddToCustomCollection(this@CatalogDirectoryActivity, getScreenId(), arrayOf(it))
                    return true
                }
            }
            R.id.menu_item_rename_collection -> {
                customCollectionUtil.promptEditCustomCollectionName(this, catalogItemQuery.id, catalogItemQuery.title)
                return true
            }
            R.id.menu_item_delete_custom_collection -> {
                customCollectionUtil.promptDeleteCollection(this, catalogItemQuery.id)
                return true
            }
        }

        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.lastScrollPosition = directoryRecyclerView.getScrollPosition()
        super.onSaveInstanceState(outState)
    }

    @Suppress("UNUSED_PARAMETER")
    @Subscribe(ThreadMode.MAIN)
    fun handle(event: CatalogReloadEvent) {
        viewModel.reloadCatalog()
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

    /**
     * Scroll to the item specified
     *
     * @param itemId The Item.id for the Item to scroll to
     */
    private fun scrollToItem(itemId: Long) {
        val position = adapter.getPositionsMap()[itemId] ?: 0
        restoreScrollPosition(position)
    }

    private fun setupRecyclerView(title: String) {
        adapter = CatalogDirectoryAdapter(this, cc, title).apply {
            itemClickListener = { catalogItemQuery ->
                onCatalogItemClick(catalogItemQuery)
            }
            menuItemClickListener = { catalogItemQuery, menuItem ->
                onCatalogItemMenuSelection(catalogItemQuery, menuItem)
            }
        }

        directoryRecyclerView.adapter = adapter
        setLayoutAsList(prefs.generalDisplayAsList)
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
        manager.setSpacingMethod(AutoColumnGridLayoutManager.SpacingMethod.ALL)
        manager.setMatchRowAndColumnSpacing(true)
        directoryRecyclerView.layoutManager = manager
    }

    private fun updateRecyclerPadding(horizontalPadding: Int) {
        directoryRecyclerView.setPadding(horizontalPadding, directoryRecyclerView.paddingTop, horizontalPadding, directoryRecyclerView.paddingBottom)
    }

    private fun onReturnFromAddToCollection(intent: Intent?) {
        intent ?: return

        with(CustomCollectionsActivity.ResultIntentOptions) {

            val customCollectionId = intent.customCollectionId

            val externalIds = ArrayList<String>()
            intent.contentItemExternalIds?.forEach {
                if (it != null) {
                    externalIds.add(it)
                }
            }

            //NOTE: this doesn't work the same way all other Snackbars work, usually we don't perform the action until the notification is
            // dismissed.  This one performs the action, then if "undo" is selected will back it out
            showUndoSnackbar(intent.title, UndoAddToCustomCollectionRunnable(customCollectionId, externalIds), Runnable { })
        }
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

    private fun addItemIds(item: CatalogItemQuery, outIds: MutableList<Long>) {
        when (item.type) {
            CatalogItemQueryType.COLLECTION, CatalogItemQueryType.CUSTOM_COLLECTION -> outIds.addAll(findAllContentItemsIdsInCollection(item.isCustomCollection(), item.id))
            CatalogItemQueryType.COLLECTION_CONTENT_ITEM -> outIds.add(item.id)
            else -> {
                // do nothing
            }
        }
    }

    private fun findAllContentItemsIdsInCollection(isCustomCollection: Boolean, collectionId: Long): List<Long> {
        return if (isCustomCollection) {
            customCollectionItemManager.findAllItemIdsByCollectionId(collectionId)
        } else {
            itemsInCollectionManager.findAllItemIdsByCollectionId(collectionId, prefs.isObsoleteContentEnabled)
        }
    }

    private fun onCollectionContentItemClick(contentItemId: Long) {
        internalIntents.showContentDirectory(this, getScreenId(), contentItemId, false, false, Analytics.Referrer.ITEM_NAVIGATION)
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        launch(cc.commonPool) {
            catalogUpdateUtil.updateCatalog(true)
            annotationSync.sync()
        }
        tipsUpdateTaskProvider.get().execute()
    }

    @Suppress("UNUSED_PARAMETER")
    @Subscribe(ThreadMode.MAIN)
    fun handle(event: CatalogUpdateCheckCompletedEvent) {
        swipeRefreshLayout.isRefreshing = false
    }

    private inner class UndoAddToCustomCollectionRunnable(collectionId: Long, contentExternalIds: List<String>) : Runnable {
        private var customContentItemExternalIds: List<String> = emptyList()
        private var customCollectionId: Long = 0

        init {
            setCollectionInfo(collectionId, contentExternalIds)
        }

        fun setCollectionInfo(customCollectionId: Long, externalIds: List<String>) {
            this.customCollectionId = customCollectionId
            this.customContentItemExternalIds = externalIds
        }

        override fun run() {
            if (customCollectionId > 0 && !customContentItemExternalIds.isEmpty()) {
                customCollectionItemManager.deleteFromCollectionExternalIds(customCollectionId, customContentItemExternalIds)
            }
        }
    }

    object IntentOptions {
        var Intent.collectionId by IntentExtra.Long(defaultValue = 0L)

        // Optional
        var Intent.scrollPosition by IntentExtra.Int(defaultValue = 0)
        var Intent.scrollToContentItemId by IntentExtra.Long(defaultValue = 0L)
    }
}
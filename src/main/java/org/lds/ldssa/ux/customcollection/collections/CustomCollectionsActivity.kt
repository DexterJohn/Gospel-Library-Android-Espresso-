package org.lds.ldssa.ux.customcollection.collections

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import butterknife.OnClick
import com.devbrackets.android.recyclerext.adapter.helper.SimpleElevationItemTouchHelperCallback
import com.devbrackets.android.recyclerext.layoutmanager.AutoColumnGridLayoutManager
import kotlinx.android.synthetic.main.activity_custom_collections_manage.*
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Int
import me.eugeniomarletti.extras.intent.base.Long
import me.eugeniomarletti.extras.intent.base.String
import me.eugeniomarletti.extras.intent.base.StringArray
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQuery
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.util.CustomCollectionUtil
import org.lds.mobile.ui.setVisible
import javax.inject.Inject

/**
 * An Activity that allows the user to reorder, rename, delete,
 * and add Custom Collections.
 */
class CustomCollectionsActivity : BaseActivity() {

    @Inject
    lateinit var customCollectionUtil: CustomCollectionUtil
    @Inject
    lateinit var navigationTrailQueryManager: NavigationTrailQueryManager
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(CustomCollectionsViewModel::class.java) }

    private var contentItemExternalIds: Array<String?> = emptyArray()

    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var adapter: CustomCollectionsAdapter

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(IntentOptions) {
            contentItemExternalIds = intent.contentItemExternalIds ?: emptyArray()
        }

        setTitle(R.string.custom_collections)

        setupToolbarAndTrail()
        updateNavigationTrail(navigationTrailQueryManager.findDefaultAllForCustomCollection(getLanguageId()))

        setupRecyclerView()
        setupViewModelObservers()
    }

    override fun getAnalyticsScreenName() = Analytics.Screen.MANAGE_CUSTOM_COLLECTIONS

    override fun getLayoutResourceId() = R.layout.activity_custom_collections_manage

    private fun setupViewModelObservers() {
        viewModel.customCollectionList.observeNotNull { customCollections ->
            adapter.items = customCollections
            itemTouchHelper.attachToRecyclerView(if (adapter.itemCount <= 1) null else manageCustomCollectionRecyclerView)
            emptyStateIndicator.setVisible(customCollections.isEmpty())
        }
    }


    private fun itemClicked(item: CatalogItemQuery) {
        if (contentItemExternalIds.isEmpty()) {
            internalIntents.showCustomCollection(this, getScreenId(), item.id)
            return
        }

        //Add the specified items to the selected collection
        if (contentItemExternalIds.isNotEmpty()) {
            // Save the externalIds to the selected collection
            val count = customCollectionUtil.addItemsToCollection(item.id, contentItemExternalIds)

            // Send results to caller to show Snackbar with option to Undo
            val intent = Intent()
            with(ResultIntentOptions) {
                intent.title = resources.getQuantityString(R.plurals.num_items_add_to, count, count.toString(), item.title)
                intent.customCollectionId = item.id
                intent.contentItemExternalIds = contentItemExternalIds
            }
            setResult(RESULT_ITEMS_ADD_TO_COLLECTION, intent)
            finish()
        }
    }

    private fun onCustomCollectionItemMenuSelection(catalogItemQuery: CatalogItemQuery, menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
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

    @OnClick(R.id.newFloatingActionButton)
    fun onAddNewClick() {
        customCollectionUtil.promptAddCustomCollection(this)
    }

    private fun setupRecyclerView() {
        adapter = CustomCollectionsAdapter(this, cc).apply {
            itemClickListener = { item ->
                itemClicked(item)
            }
            menuItemClickListener = { catalogItemQuery, menuItem ->
                onCustomCollectionItemMenuSelection(catalogItemQuery, menuItem)
            }
        }

        manageCustomCollectionRecyclerView.adapter = adapter
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
                customCollectionUtil.updateCollectionPositionOrder(adapter.getOrderPositions())
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //Swipe not supported
            }
        })

        itemTouchHelper.attachToRecyclerView(manageCustomCollectionRecyclerView)
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
        manageCustomCollectionRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupAsGrid() {
        adapter.setDisplayList(false)

        val columnWidth = resources.getDimensionPixelSize(R.dimen.catalog_card_width)

        updateRecyclerPadding(resources.getDimensionPixelSize(R.dimen.catalog_grid_horizontal_padding))
        val manager = AutoColumnGridLayoutManager(this, columnWidth)
        manager.setMatchRowAndColumnSpacing(true)
        manageCustomCollectionRecyclerView.layoutManager = manager
    }

    private fun updateRecyclerPadding(horizontalPadding: Int) {
        manageCustomCollectionRecyclerView.setPadding(horizontalPadding, manageCustomCollectionRecyclerView.paddingTop, horizontalPadding, manageCustomCollectionRecyclerView.paddingBottom)
    }

    companion object {
        //Used when selecting a collection to add items
        const val RESULT_ITEMS_ADD_TO_COLLECTION = 10
    }

    object IntentOptions {
        var Intent.contentItemExternalIds by IntentExtra.StringArray()

        // Optional
        var Intent.scrollPosition by IntentExtra.Int(defaultValue = 0)
    }

    object ResultIntentOptions {
        var Intent.title by IntentExtra.String(defaultValue = "")
        var Intent.customCollectionId by IntentExtra.Long(defaultValue = 0L)
        var Intent.contentItemExternalIds by IntentExtra.StringArray()
    }
}
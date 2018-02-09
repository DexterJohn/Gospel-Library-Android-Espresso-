package org.lds.ldssa.ux.catalog

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.graphics.Point
import android.support.annotation.DrawableRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder
import com.devbrackets.android.recyclerext.adapter.viewholder.MenuViewHolder
import kotlinx.android.synthetic.main.grid_item_catalog.view.*
import me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable
import org.lds.ldssa.R
import org.lds.ldssa.download.InstallProgress
import org.lds.ldssa.glide.GlideApp
import org.lds.ldssa.glide.GlideRequests
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQuery
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.ldssa.model.database.types.CatalogItemQueryType
import org.lds.ldssa.ui.adapter.viewholder.StandardListHeaderViewHolder
import org.lds.ldssa.util.ContentItemUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.download.DownloadManagerHelper
import org.lds.mobile.glide.ImageRenditions
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.ext.executeOnValidPositionBoolean
import org.lds.mobile.ui.recyclerview.ListItemWithHeader
import org.lds.mobile.ui.recyclerview.RecyclerViewDiffAdapter
import org.lds.mobile.ui.setHtml
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.util.LdsDrawableUtil
import javax.inject.Inject

class CatalogDirectoryAdapter(
        private val lifecycleContext: AppCompatActivity,
        cc: CoroutineContextProvider,
        private var activityTitle: String = ""
) : RecyclerViewDiffAdapter<ListItemWithHeader<CatalogItemQuery, String>, RecyclerView.ViewHolder>(cc), ClickableViewHolder.OnClickListener, MenuViewHolder.OnMenuItemSelectedListener {

    @Inject
    lateinit var downloadQueueItemManager: DownloadQueueItemManager
    @Inject
    lateinit var contentItemUtil: ContentItemUtil

    private val installProgressLiveDataMap = mutableMapOf<Long, LiveData<InstallProgress>>() // key: contentItemId

    var itemClickListener: (catalogItemQuery: CatalogItemQuery) -> Unit = { }
    var menuItemClickListener: (catalogItemQuery: CatalogItemQuery, menuItem: MenuItem) -> Boolean = { _, _ -> false }

    private var displayList = false //True if we should display a list, false for a grid

    private val coverArtImageSize = Point(0, 0)
    private val glideRequests: GlideRequests

    init {
        Injector.get().inject(this)

        glideRequests = GlideApp.with(lifecycleContext)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        updateArtworkImageSize()
    }

    override fun getItemViewType(position: Int) = items[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ListItemWithHeader.HEADER_VIEW_TYPE -> onCreateHeaderViewHolder(parent)
            ListItemWithHeader.ITEM_VIEW_TYPE -> onCreateChildViewHolder(parent)
            else -> error("Invalid ViewType [$viewType]")
        }
    }

    private fun onCreateHeaderViewHolder(parent: ViewGroup) = StandardListHeaderViewHolder.newInstance(parent)

    private fun onCreateChildViewHolder(parent: ViewGroup): CatalogDirectoryViewHolder {
        val holder = CatalogDirectoryViewHolder(parent, displayList, contentItemUtil)
        holder.setOnClickListener(this)
        holder.setOnMenuItemSelectedListener(this)

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StandardListHeaderViewHolder -> onBindHeaderViewHolder(holder, position)
            is CatalogDirectoryViewHolder -> onBindItemViewHolder(holder, position)
        }
    }

    private fun onBindHeaderViewHolder(holder: StandardListHeaderViewHolder, position: Int) {
        val item = items[position]

        item.header?.let {
            holder.setText(Html.fromHtml(it))
        }
    }

    private fun onBindItemViewHolder(holder: CatalogDirectoryViewHolder, position: Int) {
        val item = items[position]

        item.item?.let {
            holder.setTitle(formatTitle(it.title))
            holder.setType(it.type)
            bindViewHolder(it, holder)
        }
    }

    private fun formatTitle(title: String): String {
        if (!displayList && title.contains(':')) {
            val parts = title.split(":", limit = 2)

            if (parts[0] == activityTitle) {
                return parts[1]
            }
        }

        return title
    }

    override fun onClick(viewHolder: ClickableViewHolder) {
        viewHolder.executeOnValidPosition {
            val item = items[it]

            item.item?.let {
                itemClickListener(it)
            }
        }
    }

    override fun onMenuItemSelected(viewHolder: MenuViewHolder, menuItem: MenuItem) = viewHolder.executeOnValidPositionBoolean {
        items[it].item?.let { catalogItemQuery ->
            return@executeOnValidPositionBoolean menuItemClickListener(catalogItemQuery, menuItem)
        }
        return@executeOnValidPositionBoolean false
    }

    override fun areItemsTheSame(oldItem: ListItemWithHeader<CatalogItemQuery, String>, newItem: ListItemWithHeader<CatalogItemQuery, String>): Boolean {
        if (oldItem.type == newItem.type) {
            return when (oldItem.type) {
                ListItemWithHeader.ITEM_VIEW_TYPE -> oldItem.item?.areItemsTheSame(newItem.item) ?: true
                else -> true
            }
        }

        return true
    }

    override fun areContentsTheSame(oldItem: ListItemWithHeader<CatalogItemQuery, String>, newItem: ListItemWithHeader<CatalogItemQuery, String>): Boolean {
        if (oldItem.type == newItem.type) {
            return when (oldItem.type) {
                ListItemWithHeader.ITEM_VIEW_TYPE -> oldItem.item?.areContentsTheSame(newItem.item) ?: true
                else -> true
            }
        }

        return true
    }

    /**
     * Sets if the Adapter should handle displaying a list of items
     * (with Sticky Headers) or a grid of items.
     *
     * @param displayList True if the adapter should display lists
     */
    fun setDisplayList(displayList: Boolean) {
        if (this.displayList == displayList) {
            return
        }

        this.displayList = displayList
        updateArtworkImageSize()
        notifyDataSetChanged()
    }

    private fun updateArtworkImageSize() {
        //Determines the correct size for the coverArtImages
        coverArtImageSize.x = lifecycleContext.resources.getDimensionPixelSize(if (displayList) R.dimen.catalog_list_artwork_width else R.dimen.catalog_card_artwork_width)
        coverArtImageSize.y = lifecycleContext.resources.getDimensionPixelSize(if (displayList) R.dimen.catalog_list_artwork_height else R.dimen.catalog_card_artwork_height)
    }

    /**
     * Gathers data for a view and places in the holder.
     *
     * @param holder - stores the data this method retrieves
     */
    private fun bindViewHolder(item: CatalogItemQuery, holder: CatalogDirectoryViewHolder) {
        when (item.type) {
            CatalogItemQueryType.COLLECTION -> bindCollectionViewHolder(holder, item)
            CatalogItemQueryType.COLLECTION_CONTENT_ITEM -> bindContentViewHolder(holder, item, item.id)
            CatalogItemQueryType.CUSTOM_COLLECTION -> bindCustomCollectionViewHolder(holder)
            CatalogItemQueryType.CUSTOM_COLLECTION_CONTENT_ITEM -> bindContentViewHolder(holder, item, item.parentId)
            CatalogItemQueryType.NOTES, CatalogItemQueryType.TIPS -> bindExtraViewHolder(holder, item.type)
            else -> {
                // do nothing
            }
        }
    }

    private fun bindCollectionViewHolder(holder: CatalogDirectoryViewHolder, item: CatalogItemQuery) {
        item.itemCoverRenditions?.let { loadImage(holder, it) }
    }

    private fun bindExtraViewHolder(holder: CatalogDirectoryViewHolder, type: CatalogItemQueryType) {
        holder.setContentDownloaded(true)
        when (type) {
            CatalogItemQueryType.NOTES -> {
                holder.setImage(R.drawable.notes_all_cover)
                holder.itemView.coverArtImageView.setBackgroundColor(LdsDrawableUtil.resolvedColorIntResourceId(lifecycleContext, R.attr.themeStyleColorCatalogItemBackground))
            }
            CatalogItemQueryType.TIPS -> {
                holder.setImage(R.drawable.cover_art_tips)
                holder.itemView.coverArtImageView.setBackgroundColor(LdsDrawableUtil.resolvedColorIntResourceId(lifecycleContext, R.attr.themeStyleColorCatalogItemBackground))
            }
            CatalogItemQueryType.STUDY_PLANS -> {
                holder.setImage(R.drawable.cover_art_tips)
                holder.itemView.coverArtImageView.setBackgroundColor(LdsDrawableUtil.resolvedColorIntResourceId(lifecycleContext, R.attr.themeStyleColorCatalogItemBackground))
            }
            else -> holder.setImage(R.drawable.cover_default_grid)
        }
    }

    /**
     * Set items for this adapter AND
     * LiveData to monitor when a download of an item starts
     */
    fun setItemsWithLiveDataListeners(newItems: List<ListItemWithHeader<CatalogItemQuery, String>>) {
        items = newItems

        val contentItemIds = newItems.filter { it.item?.type == CatalogItemQueryType.COLLECTION_CONTENT_ITEM }.mapNotNull { it.item?.id }
        val customCollectionContentItemIds = newItems.filter { it.item?.type == CatalogItemQueryType.CUSTOM_COLLECTION_CONTENT_ITEM }.mapNotNull { it.item?.parentId }
        val allContentItemsIds = contentItemIds + customCollectionContentItemIds

        allContentItemsIds.forEach { contentItemId ->
            if (installProgressLiveDataMap.containsKey(contentItemId).not()) {
                installProgressLiveDataMap.put(contentItemId, contentItemUtil.getInstallStatusLiveData(contentItemId))
            }
        }
    }

    private fun removeInstallProgressObserver(observer: Observer<InstallProgress?>) {
        installProgressLiveDataMap.values.forEach {
            it.removeObserver(observer)
        }
    }

    private fun bindContentViewHolder(holder: CatalogDirectoryViewHolder, item: CatalogItemQuery, contentItemId: Long) {
        holder.setContentDownloaded(item.installed)
        item.itemCoverRenditions?.let { loadImage(holder, it) }

        // if content item... add listener to watch for download
        if (item.type == CatalogItemQueryType.COLLECTION_CONTENT_ITEM || item.type == CatalogItemQueryType.CUSTOM_COLLECTION_CONTENT_ITEM) {
            // make sure there is no left-over progress from a re-used viewholder
            holder.resetProgress()

            // removed holder observers from all existing LiveData
            removeInstallProgressObserver(holder.downloadInstallObserver)

            // listen for download progress/change
            installProgressLiveDataMap[contentItemId]?.observe(lifecycleContext, holder.downloadInstallObserver)
        }
    }

    private fun bindCustomCollectionViewHolder(holder: CatalogDirectoryViewHolder) {
        holder.setImage(R.drawable.cover_art_custom_collection)
        holder.itemView.coverArtImageView.setBackgroundColor(LdsDrawableUtil.resolvedColorIntResourceId(lifecycleContext, R.attr.themeStyleColorCatalogItemBackground))
    }

    private fun loadImage(holder: CatalogDirectoryViewHolder, imageRenditions: String) {
        if (imageRenditions.isBlank()) {
            holder.setImage(R.drawable.cover_default_grid)
            return
        }

        glideRequests.clear(holder.itemView.coverArtImageView)
        glideRequests.load(ImageRenditions(imageRenditions))
                .placeholder(R.drawable.cover_default_grid)
                .fallback(R.drawable.cover_default_grid)
                .error(R.drawable.cover_default_grid)
                .override(coverArtImageSize.x, coverArtImageSize.y)
                .into(holder.itemView.coverArtImageView)
    }

    /**
     * Get the order of items in the list Map<id, position>
     */
    fun getPositionsMap(): Map<Long, Int> {
        val map = mutableMapOf<Long, Int>()
        if (items.isEmpty()) {
            return map
        }

        for (i in 0 until itemCount) {
            items[i].item?.let {
                map.put(it.id, i)
            }
        }

        return map
    }

    open class CatalogDirectoryViewHolder(parent: ViewGroup, displayList: Boolean, val contentItemUtil: ContentItemUtil) : MenuViewHolder(LayoutInflater.from(parent.context).inflate(if (displayList) R.layout.list_item_catalog else R.layout.grid_item_catalog, parent, false)) {
        private var isDownloaded = true // True because the view has full opacity to start with
        private var type = CatalogItemQueryType.UNKNOWN

        var downloadInstallObserver: Observer<InstallProgress?> = Observer { progress ->
            progress ?: return@Observer

            contentItemUtil.showInstallProgress(progress, itemView.installProgressBar)
        }

        init {
            val indeterminateDrawable = IndeterminateHorizontalProgressDrawable(itemView.context)
            indeterminateDrawable.useIntrinsicPadding = false
            itemView.installProgressBar.indeterminateDrawable = indeterminateDrawable
            itemView.installProgressBar.max = DownloadManagerHelper.MAX_PROGRESS
        }

        override fun getMenuViewId(): Int {
            return R.id.overflowMenuView
        }

        override fun getMenuResourceId(): Int {
            return R.menu.menu_popup_catalog_directory_item
        }

        override fun onPreparePopupMenu(menu: Menu) {
            val menuInflater = MenuInflater(itemView.context)

            if (type == CatalogItemQueryType.COLLECTION || type == CatalogItemQueryType.CUSTOM_COLLECTION) {
                menu.findItem(R.id.context_menu_item_remove).setTitle(R.string.remove_all)
                menu.findItem(R.id.context_menu_item_download).setTitle(R.string.download_all)
                menu.findItem(R.id.context_menu_add_to_custom_collection).isVisible = false
            }

            // Updates the download and remove visibilities
            if (type.isContentItem) {
                menu.findItem(R.id.context_menu_item_remove).isVisible = isDownloaded
                menu.findItem(R.id.context_menu_item_download).isVisible = !isDownloaded
            }

            // Adds the ability to remove from the custom collection
            if (type === CatalogItemQueryType.CUSTOM_COLLECTION_CONTENT_ITEM) {
                menuInflater.inflate(R.menu.menu_popup_custom_collection_item, menu)
                menu.findItem(R.id.context_menu_add_to_custom_collection).isVisible = false
            }
        }

        fun setType(type: CatalogItemQueryType) {
            this.type = type
            itemView.overflowMenuView.setVisible(type != CatalogItemQueryType.NOTES && type != CatalogItemQueryType.TIPS &&  type != CatalogItemQueryType.STUDY_PLANS)
        }

        fun setImage(@DrawableRes imageRes: Int) {
            itemView.coverArtImageView.setImageResource(imageRes)
        }

        fun setTitle(titleText: String) {
            if (titleText.isBlank()) {
                itemView.titleTextView.text = ""
                return
            }

            if (itemView.subTitleTextView == null) {
                itemView.titleTextView.setHtml(titleText)
            } else {
                if (titleText.contains(':')) {
                    itemView.subTitleTextView?.visibility = View.VISIBLE
                    itemView.titleTextView.setLines(1)

                    val parts = titleText.split(":", limit = 2)
                    itemView.titleTextView.setHtml(parts[0])
                    itemView.subTitleTextView?.setHtml(parts[1])
                } else {
                    itemView.subTitleTextView?.visibility = View.GONE
                    itemView.titleTextView.setLines(2)

                    itemView.titleTextView.setHtml(titleText)
                }
            }
        }

        fun setContentDownloaded(downloaded: Boolean) {
            if (downloaded != isDownloaded) {
                isDownloaded = downloaded
                itemView.coverArtAlphaView.setVisible(!downloaded)
            }
        }

        fun resetProgress() {
            itemView.installProgressBar.isIndeterminate = false
            itemView.installProgressBar.setVisible(false)
        }
    }
}

package org.lds.ldssa.ux.customcollection.collections

import android.content.res.Resources
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder
import com.devbrackets.android.recyclerext.adapter.viewholder.MenuViewHolder
import kotlinx.android.synthetic.main.grid_item_catalog.view.*
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQuery
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.ext.executeOnValidPositionBoolean
import org.lds.mobile.ui.recyclerview.RecyclerViewDiffAdapter

class CustomCollectionsAdapter(lifecycleContext: AppCompatActivity, cc: CoroutineContextProvider) : RecyclerViewDiffAdapter<CatalogItemQuery, CustomCollectionsAdapter.ViewHolder>(cc), ClickableViewHolder.OnClickListener, MenuViewHolder.OnMenuItemSelectedListener {
    var itemClickListener: (item: CatalogItemQuery) -> Unit = { }
    var menuItemClickListener: (catalogItemQuery: CatalogItemQuery, menuItem: MenuItem) -> Boolean = { _, _ -> false }

    private val resources: Resources = lifecycleContext.resources
    private var displayList = false //True if we should display a list, false for a grid
    private val coverArtImageSize = Point(0, 0)

    init {
        Injector.get().inject(this)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        updateArtworkImageSize()
    }

    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): CustomCollectionsAdapter.ViewHolder {
        val holder = ViewHolder(parent, displayList)
        holder.setOnClickListener(this)
        holder.setOnMenuItemSelectedListener(this)

        return holder
    }

    override
    fun onBindViewHolder(holder: CustomCollectionsAdapter.ViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.coverArtImageView.setImageResource(R.drawable.cover_art_custom_collection)
        holder.itemView.titleTextView.text = item.title
    }

    override fun onClick(viewHolder: ClickableViewHolder) = viewHolder.executeOnValidPosition { itemClickListener(items[it]) }

    override fun onMenuItemSelected(viewHolder: MenuViewHolder, menuItem: MenuItem) = viewHolder.executeOnValidPositionBoolean { menuItemClickListener(items[it], menuItem) }

    override fun areContentsTheSame(oldItem: CatalogItemQuery, newItem: CatalogItemQuery) = oldItem.title == newItem.title

    override fun areItemsTheSame(oldItem: CatalogItemQuery, newItem: CatalogItemQuery) = oldItem.id == newItem.id

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

    fun getOrderPositions(): Map<Long, Int> {
        val orderPositionsMap = mutableMapOf<Long, Int>()

        items.forEach { collection -> orderPositionsMap.put(collection.id, orderPositionsMap.size) }

        return orderPositionsMap
    }

    private fun updateArtworkImageSize() {
        //Determines the correct size for the coverArtImages
        coverArtImageSize.x = resources.getDimensionPixelSize(if (displayList) R.dimen.catalog_list_artwork_width else R.dimen.catalog_card_artwork_width)
        coverArtImageSize.y = resources.getDimensionPixelSize(if (displayList) R.dimen.catalog_list_artwork_height else R.dimen.catalog_card_artwork_height)
    }

    class ViewHolder(parent: ViewGroup, displayList: Boolean) : MenuViewHolder(LayoutInflater.from(parent.context).inflate(if (displayList) R.layout.list_item_catalog else R.layout.grid_item_catalog, parent, false)) {
        override fun getMenuViewId() = R.id.overflowMenuView
        override fun getMenuResourceId() = R.menu.menu_custom_collections_manage
    }
}
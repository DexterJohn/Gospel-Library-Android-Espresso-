package org.lds.ldssa.ux.annotations.tags

import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.viewholder.MenuViewHolder
import kotlinx.android.synthetic.main.list_item_checkable_title_subtile_menu.view.*
import org.lds.ldssa.R
import org.lds.ldssa.model.database.userdata.tagview.TagView
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.ext.executeOnValidPositionBoolean
import org.lds.mobile.ui.getQuantityString
import org.lds.mobile.ui.recyclerview.RecyclerViewDiffAdapter
import org.lds.mobile.ui.setVisible

class TagsAdapter(
        cc: CoroutineContextProvider
) : RecyclerViewDiffAdapter<TagView, TagsAdapter.TagViewHolder>(cc) {
    var itemClickListener: (TagView) -> Unit = { }
    var actionImageItemClickListener: () -> Unit = { }
    var menuItemClickListener: (TagView, MenuItem) -> Boolean = { _, _ -> false }
    var actionModeVisible: () -> Boolean = { false }

    val selectedTagNames = mutableSetOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAdapter.TagViewHolder {
        val holder = TagViewHolder(parent)
        holder.setUnSelectedImage(R.drawable.ic_lds_tag_24dp)
        holder.itemClickListener = { viewHolder -> viewHolder.executeOnValidPosition { itemClickListener(items[it]) } }
        holder.actionImageItemClickListener = { viewHolder ->
            toggleSelection(viewHolder)
            actionImageItemClickListener()
        }
        holder.setOnMenuItemSelectedListener { viewHolder, menuItem -> viewHolder.executeOnValidPositionBoolean { menuItemClickListener(items[it], menuItem) } }
        holder.showMenuButton(!actionModeVisible())

        return holder
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val tagView = items[position]
        holder.setTitle(tagView.name)

        val count = tagView.count.toLong()
        holder.setSubTitle(holder.getQuantityString(R.plurals.num_items, count.toInt(), count))
        holder.setSelected(actionModeVisible() && selectedTagNames.contains(tagView.name))
    }


    override fun areContentsTheSame(oldItem: TagView, newItem: TagView): Boolean {
        return oldItem.name == newItem.name && oldItem.count == newItem.count
    }

    override fun areItemsTheSame(oldItem: TagView, newItem: TagView): Boolean {
        return oldItem.name == newItem.name
    }

    private fun toggleSelection(holder: TagViewHolder) {
        holder.executeOnValidPosition {
            val tagView = items[it]

            val selected = !selectedTagNames.contains(tagView.name)
            holder.setSelected(selected)

            if (selected) {
                selectedTagNames.add(tagView.name)
            } else {
                selectedTagNames.remove(tagView.name)
            }
        }
    }

    fun getSelectedCount(): Int {
        return selectedTagNames.size
    }

    fun clearSelection(recyclerView: RecyclerView) {
        selectedTagNames.clear()
        updateVisibleViewHolders(recyclerView)
    }

    private fun updateVisibleViewHolders(recyclerView: RecyclerView) {
        var holder: RecyclerView.ViewHolder?
        for (i in 0 until itemCount) {
            holder = recyclerView.findViewHolderForAdapterPosition(i)
            if (holder != null && holder is TagViewHolder) {
                val tagViewHolder = holder as TagViewHolder?
                if (!actionModeVisible()) {
                    tagViewHolder?.setSelected(false)
                }
                tagViewHolder?.showMenuButton(!actionModeVisible())
            }
        }
    }

    class TagViewHolder(parent: ViewGroup) : MenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_checkable_title_subtile_menu, parent, false)) {
        @DrawableRes
        private var unSelectedImageId: Int = 0
        private var unSelectedImage: Drawable? = null

        var itemClickListener: (holder: TagViewHolder) -> Unit = { }
        var actionImageItemClickListener: (holder: TagViewHolder) -> Unit = { }

        init {
            itemView.setOnClickListener {
                itemClickListener(this)
            }
            itemView.listItemImageView.setOnClickListener {
                setSelected(true)
                actionImageItemClickListener(this)
            }
        }

        override fun getMenuViewId(): Int {
            return R.id.menuButton
        }

        override fun getMenuResourceId(): Int {
            return R.menu.menu_popup_tag
        }

        fun setTitle(title: CharSequence) {
            itemView.titleView.text = title
        }

        fun setSubTitle(subTitle: CharSequence) {
            itemView.subTitleView.setVisible(subTitle.isNotEmpty())
            itemView.subTitleView.text = subTitle
        }

        fun setUnSelectedImage(@DrawableRes drawableId: Int) {
            unSelectedImageId = drawableId
            itemView.listItemImageView.setImageResource(drawableId)
        }

        fun setSelected(selected: Boolean) {
            if (unSelectedImageId == 0 && unSelectedImage == null) {
                return
            }

            if (selected) {
                itemView.listItemImageView.setImageResource(R.drawable.checkmark_in_circle)
            } else {
                if (unSelectedImageId != 0) {
                    itemView.listItemImageView.setImageResource(unSelectedImageId)
                } else {
                    itemView.listItemImageView.setImageDrawable(unSelectedImage)
                }
            }
        }

        fun showMenuButton(displayMenuButton: Boolean) {
            itemView.menuButton.setVisible(displayMenuButton)
        }
    }
}
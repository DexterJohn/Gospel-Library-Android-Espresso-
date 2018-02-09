package org.lds.ldssa.ux.annotations.notebooks

import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.viewholder.MenuViewHolder
import kotlinx.android.synthetic.main.list_item_checkable_title_subtile_menu.view.*
import org.lds.ldssa.R
import org.lds.ldssa.model.database.userdata.notebookview.NotebookView
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.ext.executeOnValidPositionBoolean
import org.lds.mobile.ui.getQuantityString
import org.lds.mobile.ui.recyclerview.RecyclerViewDiffAdapter
import org.lds.mobile.ui.setVisible

class NotebooksAdapter(cc: CoroutineContextProvider) : RecyclerViewDiffAdapter<NotebookView, NotebooksAdapter.NotebookViewHolder>(cc) {
    var itemClickListener: (NotebookView) -> Unit = { }
    var actionImageItemClickListener: () -> Unit = { }
    var menuItemClickListener: (NotebookView, MenuItem) -> Boolean = { _, _ -> false }
    var actionModeVisible: () -> Boolean = { false }

    val selectedNotebookIds = mutableSetOf<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookViewHolder {
        val holder = NotebookViewHolder(parent)
        holder.setUnSelectedImage(R.drawable.ic_lds_notebook_24dp)
        holder.itemClickListener = { viewHolder -> viewHolder.executeOnValidPosition { itemClickListener(items[it]) } }
        holder.actionImageItemClickListener = { viewHolder ->
            toggleSelection(viewHolder)
            actionImageItemClickListener()
        }
        holder.setOnMenuItemSelectedListener { viewHolder, menuItem -> viewHolder.executeOnValidPositionBoolean { menuItemClickListener(items[it], menuItem) } }
        holder.showMenuButton(!actionModeVisible())

        return holder
    }

    override fun onBindViewHolder(holder: NotebookViewHolder, position: Int) {
        val notebookView = items[position]
        holder.setTitle(notebookView.name)

        val count = notebookView.count
        holder.setSubTitle(holder.getQuantityString(R.plurals.num_items, count, count))
        holder.setSelected(actionModeVisible() && selectedNotebookIds.contains(notebookView.id))
    }

    override fun areContentsTheSame(oldItem: NotebookView, newItem: NotebookView): Boolean {
        return oldItem.name == newItem.name && oldItem.count == newItem.count
    }

    override fun areItemsTheSame(oldItem: NotebookView, newItem: NotebookView): Boolean {
        return oldItem.id == newItem.id
    }

    private fun toggleSelection(holder: NotebookViewHolder) {
        holder.executeOnValidPosition {
            val notebookView = items[it]

            val selected = !selectedNotebookIds.contains(notebookView.id)
            holder.setSelected(selected)

            if (selected) {
                selectedNotebookIds.add(notebookView.id)
            } else {
                selectedNotebookIds.remove(notebookView.id)
            }
        }
    }

    fun getSelectedCount(): Int {
        return selectedNotebookIds.size
    }

    fun clearSelection(recyclerView: RecyclerView) {
        selectedNotebookIds.clear()
        updateVisibleViewHolders(recyclerView)
    }

    private fun updateVisibleViewHolders(recyclerView: RecyclerView) {
        var holder: RecyclerView.ViewHolder?
        for (i in 0 until itemCount) {
            holder = recyclerView.findViewHolderForAdapterPosition(i)
            if (holder != null && holder is NotebookViewHolder) {
                if (!actionModeVisible()) {
                    holder.setSelected(false)
                }
                holder.showMenuButton(!actionModeVisible())
            }
        }
    }

    class NotebookViewHolder(parent: ViewGroup) : MenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_checkable_title_subtile_menu, parent, false)) {
        @DrawableRes
        private var unSelectedImageId: Int = 0
        private var unSelectedImage: Drawable? = null

        var itemClickListener: (holder: NotebookViewHolder) -> Unit = { }
        var actionImageItemClickListener: (holder: NotebookViewHolder) -> Unit = { }

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
            return R.menu.menu_popup_notebook
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

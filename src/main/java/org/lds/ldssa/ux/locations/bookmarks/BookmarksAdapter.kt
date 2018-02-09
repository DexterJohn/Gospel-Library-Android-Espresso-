package org.lds.ldssa.ux.locations.bookmarks

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder
import com.devbrackets.android.recyclerext.adapter.viewholder.MenuViewHolder
import kotlinx.android.synthetic.main.list_item_bookmark.view.*
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.userdata.bookmarkquery.BookmarkQuery
import org.lds.ldssa.util.annotations.BookmarkUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.ext.executeOnValidPositionBoolean
import org.lds.mobile.ui.recyclerview.RecyclerViewDiffAdapter
import javax.inject.Inject

class BookmarksAdapter(
        private val showUpdateMenu: Boolean,
        cc: CoroutineContextProvider
) : RecyclerViewDiffAdapter<BookmarkQuery, BookmarksAdapter.ViewHolder>(cc), ClickableViewHolder.OnClickListener {

    @Inject
    lateinit var bookmarkUtil: BookmarkUtil

    var clickListener: (bookmarkId: Long) -> Unit = {}
    var menuItemClickListener: (BookmarkQuery, MenuItem) -> Boolean = { _, _ -> false }

    init {
        Injector.get().inject(this)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(viewGroup, showUpdateMenu).apply {
            setOnClickListener(this@BookmarksAdapter)
            setOnMenuItemSelectedListener { viewHolder, menuItem -> viewHolder.executeOnValidPositionBoolean { menuItemClickListener(items[it], menuItem) } }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookmarkQuery = items[position]

        val name = bookmarkQuery.name
        val citation = bookmarkQuery.citation

        // Title
        holder.setTitle(name)

        // Subtitle
        if (name == citation) {
            //todo findTitleByAnnotationId should be a suspend function (called from a coroutine)
            val contentItemTitle = bookmarkUtil.findTitleByAnnotationId(bookmarkQuery.annotationId)
            holder.setSubTitle(contentItemTitle)
        } else {
            holder.setSubTitle(citation)
        }
    }

    override fun onClick(viewHolder: ClickableViewHolder) = viewHolder.executeOnValidPosition { clickListener(items[it].id) }

    override fun areContentsTheSame(oldItem: BookmarkQuery, newItem: BookmarkQuery): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areItemsTheSame(oldItem: BookmarkQuery, newItem: BookmarkQuery): Boolean {
        return oldItem.id == newItem.id
    }

    class ViewHolder(parent: ViewGroup, val showUpdateMenu: Boolean) : MenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_bookmark, parent, false)) {
        fun setTitle(title: CharSequence) {
            itemView.titleTextView.text = title
        }

        fun setSubTitle(subTitle: CharSequence) {
            itemView.subTitleTextView.text = subTitle
        }

        override fun getMenuViewId() = R.id.menuView

        override fun getMenuResourceId() = if (showUpdateMenu) R.menu.menu_popup_bookmark_update else R.menu.menu_popup_bookmark
    }
}

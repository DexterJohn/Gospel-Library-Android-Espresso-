package org.lds.ldssa.ux.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.RecyclerHeaderAdapter
import kotlinx.android.synthetic.main.list_item_search_preview.view.*
import org.lds.ldssa.R
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNote
import org.lds.ldssa.ui.adapter.viewholder.StandardListHeaderViewHolder
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.ext.setHtml
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.util.LdsDrawableUtil

class SearchPreviewNotesAdapter : RecyclerHeaderAdapter<StandardListHeaderViewHolder, SearchPreviewNotesAdapter.ViewHolder>() {
    var itemClickListener: (SearchPreviewNote) -> Unit = {}

    var list: List<SearchPreviewNote> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getChildCount(): Int {
        return list.size
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): StandardListHeaderViewHolder {
        return StandardListHeaderViewHolder.newInstance(parent)
    }

    override fun onBindHeaderViewHolder(holder: StandardListHeaderViewHolder, firstChildPosition: Int) {
        val searchPreviewItem = list[firstChildPosition]
        holder.setText(searchPreviewItem.searchResultCountType.listHeaderStringRes)
    }

    override fun getHeaderId(childPosition: Int): Long {
        if (list.size <= childPosition) {
            return RecyclerView.NO_ID
        } else {
            return list[childPosition].searchResultCountType.ordinal.toLong()
        }
    }

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent).apply {
            clickListener = { executeOnValidPosition { itemClickListener(list[getChildPosition(it)]) } }
        }
    }

    override fun onBindChildViewHolder(holder: ViewHolder, childPosition: Int) {
        val searchPreviewItem = list[childPosition]

        val context = holder.itemView.context

        if (!searchPreviewItem.visited) {
            holder.searchItemLayout.setBackgroundColor(LdsDrawableUtil.resolvedColorIntResourceId(context, R.attr.themeStyleColorBackground))
        } else {
            holder.searchItemLayout.setBackgroundColor(LdsDrawableUtil.resolvedColorIntResourceId(context, R.attr.themeStyleColorListBackground))
        }

        holder.titleTextView.setHtml(searchPreviewItem.title)
        holder.previewTextView.setHtml(searchPreviewItem.text)

        if (searchPreviewItem.count > 1) {
            holder.resultsTextView.setVisible(true)
            holder.resultsTextView.text = context.resources.getQuantityString(R.plurals.search_count_results, searchPreviewItem.count.toInt(), searchPreviewItem.count)
        } else {
            holder.resultsTextView.setVisible(false)
        }
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_search_preview, parent, false)) {
        val searchItemLayout = itemView.searchItemLayout
        val titleTextView = itemView.titleTextView
        val previewTextView = itemView.previewTextView
        val resultsTextView = itemView.resultsTextView

        var clickListener: (ViewHolder) -> Unit = {}

        init {
            itemView.searchItemLayout.setOnClickListener { clickListener(this) }
        }
    }
}
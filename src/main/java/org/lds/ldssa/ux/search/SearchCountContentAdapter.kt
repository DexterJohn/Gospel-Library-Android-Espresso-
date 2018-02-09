package org.lds.ldssa.ux.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_search_count.view.*
import org.lds.ldssa.R
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContent
import org.lds.mobile.ui.ext.executeOnValidPosition

class SearchCountContentAdapter : RecyclerView.Adapter<SearchCountContentAdapter.ViewHolder>() {

    var list: List<SearchCountContent> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: (SearchCountContent) -> Unit = {}

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent).apply {
            clickListener = { executeOnValidPosition { itemClickListener(list[it]) } }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchCountContent = list[position]

        holder.titleTextView.text = searchCountContent.title

        val fullCount = searchCountContent.getFullCount()
        var subtitle = holder.itemView.context.resources.getQuantityString(R.plurals.search_count_results, fullCount.toInt(), fullCount) // because keywordCount include phrase count... just show keyword
        if (searchCountContent.phraseCount > 0) {
            subtitle += " (${searchCountContent.phraseCount})"
        }
        holder.subTitleTextView.text = subtitle
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_search_count, parent, false)) {
        val titleTextView = itemView.titleTextView
        val subTitleTextView = itemView.subTitleTextView

        var clickListener: (ViewHolder) -> Unit = {}

        init {
            itemView.searchItemLayout.setOnClickListener { clickListener(this) }
        }
    }
}
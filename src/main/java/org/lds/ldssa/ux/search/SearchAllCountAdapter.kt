package org.lds.ldssa.ux.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_search_count.view.*
import org.lds.ldssa.R
import org.lds.ldssa.model.database.search.searchallcount.SearchAllCount
import org.lds.mobile.ui.ext.executeOnValidPosition

class SearchAllCountAdapter : RecyclerView.Adapter<SearchAllCountAdapter.ViewHolder>() {

    var list: List<SearchAllCount> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: (SearchAllCount) -> Unit = {}

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent).apply {
            clickListener = { executeOnValidPosition { itemClickListener(list[it]) } }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchAllCount = list[position]

        holder.titleTextView.text = searchAllCount.title

        var subtitle: String
        val fullCount = searchAllCount.getFullCount()
        subtitle = if (searchAllCount.subtitle.isNullOrBlank()) {
            holder.itemView.context.resources.getQuantityString(R.plurals.search_count_results, fullCount.toInt(), fullCount)
        } else {
            holder.itemView.context.resources.getQuantityString(R.plurals.search_count_results_subtitle, fullCount.toInt(), searchAllCount.subtitle, fullCount)
        }

        if (searchAllCount.phraseCount > 0) {
            subtitle += holder.itemView.context.getString(R.string.space_parentheses_number, searchAllCount.phraseCount)
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
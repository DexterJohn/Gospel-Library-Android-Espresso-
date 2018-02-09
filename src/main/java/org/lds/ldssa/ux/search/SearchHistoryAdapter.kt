package org.lds.ldssa.ux.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_search_history.view.*
import org.lds.ldssa.R
import org.lds.ldssa.model.database.search.searchhistory.SearchHistory
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.util.LdsDrawableUtil

class SearchHistoryAdapter : RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder>() {
    var list: List<SearchHistory> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: (SearchHistory) -> Unit = {}
    var itemPopulateSearchClickListener: (SearchHistory) -> Unit = {}


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent).apply {
            clickListener = { executeOnValidPosition { itemClickListener(list[it]) } }
            populateSearchClickListener = { executeOnValidPosition { itemPopulateSearchClickListener(list[it]) } }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchHistory = list[position]

        holder.titleTextView.text = searchHistory.title

        val subtitle = searchHistory.subTitle
        if (subtitle.isNotBlank()) {
            holder.subTitleTextView.visibility = View.VISIBLE
            holder.subTitleTextView.text = subtitle
        } else {
            holder.subTitleTextView.visibility = View.GONE
        }

        holder.preImageView.setImageDrawable(LdsDrawableUtil.tintDrawableForTheme(holder.itemView.context, R.drawable.ic_lds_history_24dp, R.attr.themeStyleColorToolbarActionModeIcon))
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_search_history, parent, false)) {
        val preImageView = itemView.preImageView
        val subTitleTextView = itemView.subTitleTextView
        val titleTextView = itemView.titleTextView

        var clickListener: (ViewHolder) -> Unit = {}
        var populateSearchClickListener: (ViewHolder) -> Unit = {}

        init {
            itemView.searchPopulateImageView.setOnClickListener { populateSearchClickListener(this) }
            itemView.searchItemLayout.setOnClickListener { clickListener(this) }
        }
    }
}
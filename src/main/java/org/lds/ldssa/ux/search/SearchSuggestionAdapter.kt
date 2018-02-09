package org.lds.ldssa.ux.search

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_search_suggestion.view.*
import org.lds.ldssa.R
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestion
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.util.LdsDrawableUtil

class SearchSuggestionAdapter(private val linkMode: Boolean = false) : RecyclerView.Adapter<SearchSuggestionAdapter.ViewHolder>() {

    var list: List<SearchSuggestion> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: (SearchSuggestion) -> Unit = {}
    var itemPopulateSearchClickListener: (SearchSuggestion) -> Unit = {}
    var itemLinkClickListener: (SearchSuggestion) -> Unit = {}


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent).apply {
            clickListener = { executeOnValidPosition { itemClickListener(list[it]) } }
            populateSearchClickListener = { executeOnValidPosition { itemPopulateSearchClickListener(list[it]) } }
            linkClickListener = { executeOnValidPosition { itemLinkClickListener(list[it]) } }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchSuggestion = list[position]

        holder.titleTextView.text = Html.fromHtml(searchSuggestion.title)

        val subtitle = searchSuggestion.subTitle
        if (subtitle.isNotBlank()) {
            holder.subTitleTextView.visibility = View.VISIBLE
            holder.subTitleTextView.text = subtitle
        } else {
            holder.subTitleTextView.visibility = View.GONE
        }

        holder.preImageView.setImageDrawable(LdsDrawableUtil.tintDrawableForTheme(holder.itemView.context, searchSuggestion.type.preIconRes, R.attr.themeStyleColorToolbarActionModeIcon))
        holder.searchPopulateImageView.setVisible(!linkMode)
        holder.linkImageView.setVisible(linkMode && (searchSuggestion.type.isDirectLinkable || searchSuggestion.getChapterNumberValue() > 0))
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_search_suggestion, parent, false)) {
        val preImageView = itemView.preImageView
        val subTitleTextView = itemView.subTitleTextView
        val titleTextView = itemView.titleTextView
        val searchPopulateImageView = itemView.searchPopulateImageView

        val linkImageView = itemView.linkImageView

        var clickListener: (ViewHolder) -> Unit = {}
        var populateSearchClickListener: (ViewHolder) -> Unit = {}
        var linkClickListener: (ViewHolder) -> Unit = {}

        init {
            itemView.searchPopulateImageView.setOnClickListener { populateSearchClickListener(this) }
            itemView.linkImageView.setOnClickListener { linkClickListener(this) }
            itemView.searchItemLayout.setOnClickListener { clickListener(this) }
        }
    }
}
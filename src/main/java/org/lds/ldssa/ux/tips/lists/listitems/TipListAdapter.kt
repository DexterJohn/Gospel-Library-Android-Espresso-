package org.lds.ldssa.ux.tips.lists.listitems

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder
import kotlinx.android.synthetic.main.list_item_tip.view.*
import org.lds.ldssa.R
import org.lds.ldssa.model.database.tips.tipquery.TipQuery
import org.lds.ldssa.ui.adapter.viewholder.StandardListHeaderViewHolder
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ui.recyclerview.ListItemWithHeader
import org.lds.mobile.ui.recyclerview.RecyclerViewDiffAdapter

class TipListAdapter(
        cc: CoroutineContextProvider
) : RecyclerViewDiffAdapter<ListItemWithHeader<TipQuery, String>, RecyclerView.ViewHolder>(cc) {

    var onClickListener: (TipQuery) -> Unit = {}

    override fun areItemsTheSame(oldItem: ListItemWithHeader<TipQuery, String>, newItem: ListItemWithHeader<TipQuery, String>) = oldItem.item?.id == newItem.item?.id
    override fun areContentsTheSame(oldItem: ListItemWithHeader<TipQuery, String>, newItem: ListItemWithHeader<TipQuery, String>) = oldItem.item?.id == newItem.item?.id
    override fun getItemViewType(position: Int) = items[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ListItemWithHeader.HEADER_VIEW_TYPE -> onCreateHeaderViewHolder(parent)
            ListItemWithHeader.ITEM_VIEW_TYPE -> onCreateChildViewHolder(parent)
            else -> error("Invalid ViewType [$viewType]")
        }
    }

    private fun onCreateHeaderViewHolder(parent: ViewGroup) = StandardListHeaderViewHolder.newInstance(parent)

    private fun onCreateChildViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(parent).apply {
            setOnClickListener({
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val tipQuery = items[adapterPosition].item
                    tipQuery ?: return@setOnClickListener
                    onClickListener(tipQuery)
                }
            })
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StandardListHeaderViewHolder -> onBindHeaderViewHolder(holder, position)
            is ViewHolder -> onBindItemViewHolder(holder, position)
        }
    }

    private fun onBindHeaderViewHolder(holder: StandardListHeaderViewHolder, position: Int) {
        val headerText = items[position].header
        if (headerText != null) {
            holder.setText(headerText)
        }
    }

    private fun onBindItemViewHolder(holder: ViewHolder, position: Int) {
        val tipsQuery = items[position].item
        if (tipsQuery != null) {
            holder.itemView.tipNameTextView.text = tipsQuery.title

            val typeface = if (tipsQuery.viewed) Typeface.NORMAL else Typeface.BOLD
            holder.itemView.tipNameTextView.setTypeface(null, typeface)
        }
    }

    class ViewHolder(parent: ViewGroup) : ClickableViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_tip, parent, false))
}

package org.lds.ldssa.ux.downloadedmedia

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item_downloaded_media.view.*
import org.apache.commons.io.FileUtils
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.gl.downloadedmediacollection.DownloadedMediaCollection
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.setVisible
import javax.inject.Inject

/**
 * A Cursor adapter used to display the collection containing downloaded media; displayed
 * in the [org.lds.ldssa.ux.downloadedmedia.DownloadedMediaActivity]
 */
class DownloadedMediaCollectionsAdapter : RecyclerView.Adapter<DownloadedMediaCollectionsAdapter.ViewHolder>() {

    @Inject
    lateinit var itemManager: ItemManager

    var list: List<DownloadedMediaCollection> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: (DownloadedMediaCollection) -> Unit = {}
    var deleteClickListener: (DownloadedMediaCollection) -> Unit = {}

    init {
        Injector.get().inject(this)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val downloadedMediaCollection = list[position]

        holder.titleTextView.text = itemManager.findTitleById(downloadedMediaCollection.id)
        holder.typeIcon.setVisible(false)

        val context = holder.itemView.context
        val countString = context.resources.getQuantityString(R.plurals.num_items, downloadedMediaCollection.itemCount.toInt(), downloadedMediaCollection.itemCount)
        holder.detailTextView.text = (FileUtils.byteCountToDisplaySize(downloadedMediaCollection.totalSize) + " (" + countString + ")")

        holder.clickListener = { holder.executeOnValidPosition { itemClickListener(list[it]) } }
        holder.deleteClickListener = { holder.executeOnValidPosition { deleteClickListener(list[it]) } }
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_downloaded_media, parent, false)) {
        val titleTextView: TextView = itemView.titleTextView
        val detailTextView: TextView = itemView.detailTextView
        val typeIcon: ImageView = itemView.typeIcon

        var clickListener: (ViewHolder) -> Unit = {}
        var deleteClickListener: (ViewHolder) -> Unit = {}

        init {
            itemView.setOnClickListener { clickListener(this) }
            itemView.deleteIcon.setOnClickListener { deleteClickListener(this) }
        }
    }
}
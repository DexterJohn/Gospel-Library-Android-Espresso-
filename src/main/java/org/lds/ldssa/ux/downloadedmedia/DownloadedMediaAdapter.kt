package org.lds.ldssa.ux.downloadedmedia

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import kotlinx.android.synthetic.main.list_item_downloaded_media.view.*

import org.apache.commons.io.FileUtils
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMedia
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.recyclerview.RecyclerViewDiffAdapter
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.util.LdsDrawableUtil

import javax.inject.Inject

/**
 * A Cursor adapter used to display the individual downloaded
 * media or the collection containing downloaded media; displayed
 * in the [org.lds.ldssa.ui.activity.DownloadedMediaActivity]
 */
class DownloadedMediaAdapter(cc: CoroutineContextProvider) : RecyclerViewDiffAdapter<DownloadedMedia, DownloadedMediaAdapter.ViewHolder>(cc) {

    @Inject
    lateinit var itemManager: ItemManager

    var itemClickListener: (DownloadedMedia) -> Unit = {}
    var deleteClickListener: (DownloadedMedia) -> Unit = {}

    init {
        Injector.get().inject(this)
    }
    override fun areContentsTheSame(oldItem: DownloadedMedia, newItem: DownloadedMedia): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areItemsTheSame(oldItem: DownloadedMedia, newItem: DownloadedMedia): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val downloadedMedia = items[position]

        holder.titleTextView.text = Html.fromHtml(downloadedMedia.title)
        holder.detailTextView.text = FileUtils.byteCountToDisplaySize(downloadedMedia.size.toLong())

        val drawable = LdsDrawableUtil.tintDrawable(holder.itemView.context, downloadedMedia.type.drawableResId, R.color.secondary_text_default_material_light)
        holder.typeIcon.setImageDrawable(drawable)
        holder.typeIcon.setVisible(true)

        holder.clickListener = { holder.executeOnValidPosition { itemClickListener(items[it]) } }
        holder.deleteClickListener = { holder.executeOnValidPosition { deleteClickListener(items[it]) } }
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
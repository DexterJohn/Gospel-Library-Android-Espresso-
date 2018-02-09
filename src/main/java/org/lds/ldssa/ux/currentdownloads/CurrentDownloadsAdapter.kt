package org.lds.ldssa.ux.currentdownloads

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_current_download.view.*
import me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable
import org.lds.ldssa.R
import org.lds.ldssa.download.InstallProgress
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItem
import org.lds.ldssa.util.ContentItemUtil
import org.lds.mobile.download.DownloadManagerHelper
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.util.LdsDrawableUtil
import javax.inject.Inject

class CurrentDownloadsAdapter(private val lifecycleContext: AppCompatActivity) : RecyclerView.Adapter<CurrentDownloadsAdapter.ViewHolder>() {
    @Inject
    lateinit var contentItemUtil: ContentItemUtil

    private val installProgressLiveDataMap = mutableMapOf<Long, LiveData<InstallProgress>>()

    var list: List<DownloadQueueItem> = listOf()
        set(value) {
            field = value

            createAllInstallProgressLiveData(value.map { it.contentItemId  })
            notifyDataSetChanged()
        }

    var cancelDownloadClickListener: (androidDownloadId: Long) -> Unit = {}

    init {
        Injector.get().inject(this)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(parent, contentItemUtil)

        holder.itemView.downloadCancelImageView.setImageDrawable(LdsDrawableUtil.tintDrawableForTheme(parent.context, R.drawable.ic_lds_action_clear_24dp, R.attr.themeStyleColorTextDefault))
        val indeterminateDrawable = IndeterminateHorizontalProgressDrawable(parent.context)
        indeterminateDrawable.useIntrinsicPadding = false
        holder.itemView.installProgressBar.indeterminateDrawable = indeterminateDrawable
        holder.itemView.installProgressBar.max = DownloadManagerHelper.MAX_PROGRESS

        holder.cancelDownloadClickListener = { clickedHolder ->
            clickedHolder.executeOnValidPosition {
                val downloadQueueItem = list[it]

                clickedHolder.itemView.installProgressBar.isIndeterminate = true
                clickedHolder.itemView.installStatusTextView.text = lifecycleContext.getString(R.string.download_canceling)

                cancelDownloadClickListener(downloadQueueItem.androidDownloadId)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val downloadQueueItem = list[position]

        holder.itemView.titleTextView.text = downloadQueueItem.title

        // removed holder observers from all existing LiveData
        removeInstallProgressObserver(holder.downloadInstallObserver)

        // listen for download progress/change
        installProgressLiveDataMap[downloadQueueItem.contentItemId]?.observe(lifecycleContext, holder.downloadInstallObserver)
    }

    /**
     * LiveData to monitor when a download of an item starts
     */
    private fun createAllInstallProgressLiveData(contentItemIds: List<Long>) {
        contentItemIds.forEach { contentItemId ->
            if (installProgressLiveDataMap.containsKey(contentItemId).not()) {
                installProgressLiveDataMap.put(contentItemId, contentItemUtil.getInstallStatusLiveData(contentItemId))
            }
        }
    }

    private fun removeInstallProgressObserver(observer: Observer<InstallProgress?>) {
        installProgressLiveDataMap.values.forEach {
            it.removeObserver(observer)
        }
    }

    class ViewHolder constructor(parent: ViewGroup, val contentItemUtil: ContentItemUtil) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_current_download, parent, false)) {
        var cancelDownloadClickListener: (RecyclerView.ViewHolder) -> Unit = {}

        var downloadInstallObserver: Observer<InstallProgress?> = Observer { progress ->
            progress ?: return@Observer
            contentItemUtil.showInstallProgress(progress, itemView.installProgressBar, itemView.installStatusTextView)
        }

        init {
            itemView.downloadCancelImageView.setOnClickListener {
                cancelDownloadClickListener(this)
            }
        }
    }
}

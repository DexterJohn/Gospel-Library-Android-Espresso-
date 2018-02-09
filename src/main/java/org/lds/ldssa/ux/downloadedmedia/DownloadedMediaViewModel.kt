package org.lds.ldssa.ux.downloadedmedia

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMedia
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager
import org.lds.ldssa.model.database.gl.downloadedmediacollection.DownloadedMediaCollection
import org.lds.ldssa.model.database.gl.downloadedmediacollection.DownloadedMediaCollectionManager
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.livedata.AbsentLiveData
import javax.inject.Inject

class DownloadedMediaViewModel
@Inject constructor(
        private val downloadedMediaCollectionManager: DownloadedMediaCollectionManager,
        private val downloadedMediaManager: DownloadedMediaManager,
        private val cc: CoroutineContextProvider
) : ViewModel() {

    val downloadedMediaList: LiveData<List<DownloadedMedia>>
    val downloadedCollectionsList: LiveData<List<DownloadedMediaCollection>>

    // List of items that the user has asked to delete, but have not yet been deleted (delete does not occur until after the snackbar dismisses)
    private var pendingRemovedItemMap: MutableMap<Long, Boolean> = mutableMapOf()

    private val downloadedMediaData = MutableLiveData<DownloadedMediaData>()

    init {
        downloadedMediaList = AbsentLiveData.switchMap(downloadedMediaData) { loadDownloadedMedia(it) }
        downloadedCollectionsList = loadDownloadedCollections()
    }

    fun setContentItemId(contentItemId: Long) {
        if (downloadedMediaData.value?.contentItemId != contentItemId) {
            downloadedMediaData.value = downloadedMediaData.value?.copy(contentItemId = contentItemId) ?: DownloadedMediaData(contentItemId = contentItemId)
        }
    }

    fun getContentItemId(): Long {
        return downloadedMediaData.value?.contentItemId ?: INVALID_ITEM_ID
    }

    fun setSortBySize(sortBySize: Boolean) {
        if (downloadedMediaData.value?.sortBySize != sortBySize) {
            downloadedMediaData.value = downloadedMediaData.value?.copy(sortBySize = sortBySize) ?: DownloadedMediaData(sortBySize = sortBySize)
        }
    }

    fun getSortBySize(): Boolean {
        return downloadedMediaData.value?.sortBySize ?: false
    }

    fun deleteDownloadedMedia(downloadedMediaId: Long) {
        downloadedMediaManager.delete(downloadedMediaId)
    }

    fun reloadDownloadedMedia() {
        downloadedMediaData.value = downloadedMediaData.value
    }

    fun removeDownloadedMedia(downloadedMediaId: Long) {
        pendingRemovedItemMap.put(downloadedMediaId, false)
        reloadDownloadedMedia()
    }

    fun undoRemoveDownloadedMedia(downloadedMediaId: Long) {
        pendingRemovedItemMap.remove(downloadedMediaId)
        reloadDownloadedMedia()
    }

    private fun loadDownloadedMedia(downloadedMediaData: DownloadedMediaData): LiveData<List<DownloadedMedia>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, downloadedMediaManager) {
            return@toLiveData filterRemovedItems(downloadedMediaManager.findAllByContentItem(downloadedMediaData.contentItemId, downloadedMediaData.sortBySize))
        }
    }

    private fun loadDownloadedCollections(): LiveData<List<DownloadedMediaCollection>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, downloadedMediaManager) {
            downloadedMediaCollectionManager.findAll()
        }
    }

    private fun filterRemovedItems(list: List<DownloadedMedia>): List<DownloadedMedia> {
        if (pendingRemovedItemMap.isEmpty()) {
            return list
        }

        val newList = list.filter {
            val inRemovedList = pendingRemovedItemMap.contains(it.id)
            if (inRemovedList) {
                pendingRemovedItemMap[it.id] = true
            }
            return@filter inRemovedList.not()
        }
        pendingRemovedItemMap = pendingRemovedItemMap.filterValues { it }.toMutableMap()
        pendingRemovedItemMap.forEach { pendingRemovedItemMap[it.key] = false }
        return newList
    }

    data class DownloadedMediaData(val contentItemId: Long = INVALID_ITEM_ID, val sortBySize: Boolean = false)

    companion object {
        val INVALID_ITEM_ID = -1L
    }
}
package org.lds.ldssa.ux.customcollection.items

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQuery
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQueryManager
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemManager
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.livedata.AbsentLiveData
import org.lds.mobile.ui.recyclerview.ListItemWithHeader
import javax.inject.Inject

class CustomCollectionDirectoryViewModel
@Inject constructor(
        private val catalogItemQueryManager: CatalogItemQueryManager,
        private val customCollectionItemManager: CustomCollectionItemManager,
        private val downloadedItemManager: DownloadedItemManager,
        private val cc: CoroutineContextProvider
) : ViewModel() {

    private val customCollectionId = MutableLiveData<Long>()
    val customCollectionDirectoryList: LiveData<List<ListItemWithHeader<CatalogItemQuery, String>>>


    init {
        customCollectionDirectoryList = AbsentLiveData.switchMap(customCollectionId) {
            loadCustomCollections(it)
        }
    }

    fun setCustomCollectionId(customCollectionId: Long) {
        if (customCollectionId != this.customCollectionId.value) {
            this.customCollectionId.value = customCollectionId
        }
    }

    fun reloadCustomCollections() {
        customCollectionId.value = customCollectionId.value
    }

    private fun loadCustomCollections(customCollectionId: Long): LiveData<List<ListItemWithHeader<CatalogItemQuery, String>>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, listOf(downloadedItemManager, customCollectionItemManager)) {
            val collectionItems = when {
                customCollectionId > 0 -> catalogItemQueryManager.findCustomCollectionItemsByCustomCollectionId(customCollectionId)
                else -> catalogItemQueryManager.findAllCustomCollections()
            }

            // list of contentIds (parentId IS the contentItemId for custom collection items)
            val contentItemIds = collectionItems.map { it.parentId }

            // identify if content items are installed
            if (contentItemIds.isNotEmpty()) {
                val downloadedContentIdSet = downloadedItemManager.findContentItemIdsInstalled(contentItemIds).toHashSet()

                collectionItems.filter { downloadedContentIdSet.contains(it.parentId) }
                        .forEach { it.installed = true }
            }

            // convert to List of ListItemWithHeader and return
            return@toLiveData collectionItems.map { ListItemWithHeader<CatalogItemQuery, String>(it) }.toMutableList()
        }
    }
}
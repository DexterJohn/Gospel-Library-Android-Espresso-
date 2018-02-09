package org.lds.ldssa.download

import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager
import org.lds.mobile.task.RxTask
import org.lds.mobile.util.LdsTimeUtil
import timber.log.Timber
import javax.inject.Inject

/**
 * Make sure that all install books are on the same version as the catalog
 */
class CheckContentVersionsTask @Inject
constructor(private val glDownloadManager: GLDownloadManager,
            private val downloadedItemManager: DownloadedItemManager,
            private val itemManager: ItemManager,
            private val timeUtil: LdsTimeUtil) : RxTask<Boolean>() {

    override fun run(): Boolean {
        val startContentVersionCheck = System.currentTimeMillis()
        Timber.i("Checking catalog -> content versions...")

        // make sure that all install books are on the same version as the catalog
        downloadedItemManager.findAll().forEach { downloadedItem ->
            val contentItemId = downloadedItem.contentItemId
            val catalogVersion = itemManager.findVersionById(contentItemId)

            // version did not match, re-download
            if (catalogVersion != downloadedItem.installedVersion) {
                glDownloadManager.downloadContent(contentItemId)
            }
        }
        timeUtil.logTimeElapsedFromNow("CheckContent", "Check catalog -> content versions FINISHED", startContentVersionCheck)

        return true
    }

    override fun onResult(result: Boolean?) {
        unSubscribe()
    }
}

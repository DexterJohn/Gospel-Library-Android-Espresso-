package org.lds.ldssa.download

import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.util.CatalogUpdateUtil
import org.lds.mobile.task.RxTask
import javax.inject.Inject

class DownloadedCatalogProcessor @Inject
constructor(private val internalIntents: InternalIntents, 
            private val catalogUpdateUtil: CatalogUpdateUtil, 
            private val downloadQueueItemManager: DownloadQueueItemManager, 
            private val prefs: Prefs) : RxTask<Boolean>() {

    private var androidDownloadId: Long = 0

    fun init(androidDownloadId: Long): DownloadedCatalogProcessor {
        this.androidDownloadId = androidDownloadId
        return this
    }

    override fun run(): Boolean {
        val isCoreCatalog = downloadQueueItemManager.isCoreCatalogByAndroidDownloadId(androidDownloadId)
        if (!isCoreCatalog) {
            downloadQueueItemManager.findByAndroidDownloadId(androidDownloadId)?.let { downloadQueueItem ->
                catalogUpdateUtil.mergeRoleCatalog(downloadQueueItem.catalogName, downloadQueueItem.version.toLong())
            }
        }

        when {
            prefs.isCatalogForceUpdateWhenAvailable ->
                // apply the catalog update now
                internalIntents.restart()
            else ->
                // indicate when the catalog update has finished downloading and become available for restart/update
                prefs.updateLastCatalogUpdateDownloadTime()
        }

        downloadQueueItemManager.deleteByAndroidDownloadId(androidDownloadId)

        return true
    }

    override fun onResult(result: Boolean?) {
        unSubscribe()
    }
}

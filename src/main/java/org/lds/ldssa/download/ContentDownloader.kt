package org.lds.ldssa.download

import org.lds.ldssa.event.download.DownloadCancelAllEvent
import org.lds.ldssa.model.database.catalog.catalogsource.CatalogSourceManager
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItem
import org.lds.ldssa.model.database.gl.rolecatalog.RoleCatalogManager
import org.lds.ldssa.model.database.types.CatalogItemSourceType
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.util.GLFileUtil
import org.lds.mobile.task.RxTask
import pocketbus.Bus
import pocketbus.Subscribe
import timber.log.Timber
import javax.inject.Inject

class ContentDownloader @Inject
constructor(private val bus: Bus,
            private val prefs: Prefs, 
            private val itemManager: ItemManager, 
            private val fileUtil: GLFileUtil, 
            private val catalogSourceManager: CatalogSourceManager, 
            private val roleCatalogManager: RoleCatalogManager) : RxTask<Boolean>() {

    // non-injected variables
    private lateinit var glDownloadManager: GLDownloadManager
    private lateinit var contentItemIds: List<Long>
    private var cancelRequested = false

    fun init(downloadManager: GLDownloadManager, contentItemIds: List<Long>): ContentDownloader {
        this.glDownloadManager = downloadManager
        this.contentItemIds = contentItemIds
        return this
    }

    public override fun run(): Boolean {
        bus.register(this)
        if (contentItemIds.isEmpty()) {
            return false
        }

        val contentItems = itemManager.findAllByRowIds(contentItemIds)
        if (contentItems.isEmpty()) {
            return false
        }

        contentItems.forEach { contentItem ->
            if (cancelRequested) {
                return true
            }
            val title = contentItem.title
            val sourceUri = buildItemUri(contentItem.sourceId, contentItem.externalId, contentItem.version.toLong())

            val contentItemZipFile = fileUtil.getContentItemZipDownloadFile(contentItem.id)
            if (contentItemZipFile == null) {
                Timber.e("Failed to get contentItem Zip file because zip file == null for contentItemId [%d]", contentItem.id)
                return@forEach
            }

            val destinationUri = contentItemZipFile.toURI().toString()

            val queueItem = DownloadQueueItem()
            queueItem.type = ItemMediaType.CONTENT
            queueItem.title = title
            queueItem.contentItemId = contentItem.id
            queueItem.version = contentItem.version
            queueItem.sourceUri = sourceUri

            glDownloadManager.enqueueDownload(queueItem, destinationUri)
        }

        return true
    }

    override fun onResult(result: Boolean?) {
        bus.unregister(this)
        unSubscribe()
    }

    @Subscribe
    fun handle(event: DownloadCancelAllEvent) {
        cancelRequested = true
    }

    private fun buildItemUri(catalogSourceId: Long, externalId: String, version: Long): String {
        val sourceType = catalogSourceManager.findItemSourceTypeById(catalogSourceId)

        val contentItemBaseUrl = when (sourceType) {
            CatalogItemSourceType.UNKNOWN, CatalogItemSourceType.DEFAULT -> prefs.contentServerType.contentUrl
            CatalogItemSourceType.SECURE -> {
                val sourceName = catalogSourceManager.findSourceTypeBaseUrlName(catalogSourceId) ?: throw IllegalStateException("cannot findSourceTypeBaseUrlName for catalogSourceId: [$catalogSourceId]")
                roleCatalogManager.findBaseUrlByName(sourceName)
            }
            CatalogItemSourceType.FOREIGN -> throw IllegalStateException("Unsupported source type: " + sourceType)
        } ?: prefs.contentServerType.contentUrl // if null fallback to default

        return buildItemUri(contentItemBaseUrl, externalId, version)
    }

    private fun buildItemUri(baseUrl: String, externalId: String, version: Long): String {
        return """$baseUrl/item-packages/$externalId/$version.zip"""
    }
}

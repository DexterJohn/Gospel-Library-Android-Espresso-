package org.lds.ldssa.download


import android.app.Application
import android.net.Uri
import org.lds.ldsaccount.AuthStatus
import org.lds.ldsaccount.LDSAccountUtil
import org.lds.ldssa.BuildConfig
import org.lds.ldssa.R
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItem
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.ldssa.model.database.gl.rolecatalog.RoleCatalog
import org.lds.ldssa.model.database.gl.rolecatalog.RoleCatalogManager
import org.lds.ldssa.model.database.types.CatalogItemSourceType
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.webservice.ServiceModule
import org.lds.ldssa.util.CatalogUtil
import org.lds.ldssa.util.GLFileUtil
import org.lds.mobile.task.RxTask
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class CatalogDownloader @Inject
constructor(private val application: Application,
            private val catalogUtil: CatalogUtil,
            private val fileUtil: GLFileUtil,
            private val roleCatalogManager: RoleCatalogManager,
            private val prefs: Prefs,
            private val ldsAccountUtil: LDSAccountUtil,
            private val downloadQueueItemManager: DownloadQueueItemManager,
            private val downloadManager: GLDownloadManager,
            private val catalogMetaDataManager: CatalogMetaDataManager) : RxTask<Boolean>() {

    private var catalogVersion = 0
    private var downloadCoreCatalog = false
    private var forceDownloadFullCoreCatalog = false

    fun initCoreCatalogDownload(catalogVersion: Int, forceDownloadFullCoreCatalog: Boolean): CatalogDownloader {
        this.catalogVersion = catalogVersion
        this.downloadCoreCatalog = true
        this.forceDownloadFullCoreCatalog = forceDownloadFullCoreCatalog

        return this
    }

    fun initRoleCatalogDownload(): CatalogDownloader {
        this.catalogVersion = -1
        this.downloadCoreCatalog = false
        this.forceDownloadFullCoreCatalog = false

        return this
    }

    override fun run(): Boolean {
        Timber.i("Downloading catalog version: %d", catalogVersion)
        downloadQueueItemManager.deleteAllCatalogs()

        // Download the core catalog
        if (downloadCoreCatalog) {
            if (forceDownloadFullCoreCatalog) {
                downloadCoreCatalog()
            } else {
                downloadCoreCatalogDiff()
            }
        } else {
            // Download role based catalogs... if any
            // role based catalogs should ONLY be downloaded and installed AFTER a catalog update is complete
            downloadRoleBasedCatalogs()
        }

        return true
    }

    private fun downloadCoreCatalog() {
        val catalogZipDownloadFile = fileUtil.getCatalogZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME)

        if (catalogZipDownloadFile == null) {
            Timber.e("Failed to downloadCoreCatalog.  catalogZipDownloadFile == null")
            return
        }

        val sourceUri = catalogUtil.getCatalogDownloadUri(catalogVersion)
        val destinationUri = Uri.fromFile(catalogZipDownloadFile).toString()

        val queueItem = DownloadQueueItem()
        queueItem.title = createCatalogDownloadTitle(CatalogUtil.CORE_CATALOG_NAME)
        queueItem.type = ItemMediaType.CATALOG
        queueItem.catalogName = CatalogUtil.CORE_CATALOG_NAME
        queueItem.version = catalogVersion
        queueItem.sourceUri = sourceUri
        queueItem.catalogItemSourceType = CatalogItemSourceType.DEFAULT

        // start the download
        downloadManager.enqueueDownload(queueItem, destinationUri)
    }

    private fun downloadCoreCatalogDiff() {
        val onlineVersion = catalogUtil.fetchCatalogVersion(CatalogUtil.CORE_CATALOG_NAME, prefs.contentServerType.contentUrl + "/index.json")
        var currentCatalogVersion = 0
        if (fileUtil.catalogFile.exists()) {
            currentCatalogVersion = catalogMetaDataManager.findVersion()
        }

        if (catalogVersion != onlineVersion || catalogVersion < currentCatalogVersion || onlineVersion - currentCatalogVersion > DIFF_VERSION_AVAILABILITY_COUNT) {
            downloadCoreCatalog()
            return
        }

        val catalogDiffZipDownloadFile = fileUtil.getCatalogDiffZipDownloadFile(CatalogUtil.CORE_CATALOG_NAME)
        val sourceUri = catalogUtil.getCatalogDiffDownloadUri(currentCatalogVersion)
        val destinationUri = Uri.fromFile(catalogDiffZipDownloadFile).toString()

        val queueItem = DownloadQueueItem()
        queueItem.title = createCatalogDownloadTitle(CatalogUtil.CORE_CATALOG_NAME)
        queueItem.type = ItemMediaType.CATALOG_DIFF
        queueItem.catalogName = CatalogUtil.CORE_CATALOG_NAME
        queueItem.version = catalogVersion
        queueItem.sourceUri = sourceUri
        queueItem.catalogItemSourceType = CatalogItemSourceType.DEFAULT

        // start the download
        downloadManager.enqueueDownload(queueItem, destinationUri)
    }


    /**
     * Fetch a list of role based content items to download.
     */
    private fun downloadRoleBasedCatalogs() {
        if (ldsAccountUtil.checkAuthenticatedConnection(ServiceModule.CURRENT_ENVIRONMENT) != AuthStatus.SUCCESS) {
            return
        }

        try {
            val dtoCustomCatalogs = catalogUtil.fetchRoleBasedCatalogs()
            // fetch versions available for these catalogs
            dtoCustomCatalogs.catalogs.forEach { dtoRoleCatalog ->
                val roleCatalogName = dtoRoleCatalog.name

                // build the url
                val roleCatalogWithVersionUrl = dtoRoleCatalog.url + BuildConfig.CONTENT_SCHEMA_VERSION

                // get the role catalog version OR -1 if no version found
                val roleCatalogVersion = catalogUtil.fetchCatalogVersion(roleCatalogName, roleCatalogWithVersionUrl + "/index.json")

                if (roleCatalogVersion > 0 && roleCatalogManager.isUpdateNeeded(roleCatalogName, roleCatalogVersion)) {
                    val roleCatalog = RoleCatalog()
                    roleCatalog.catalogItemSourceType = CatalogItemSourceType.SECURE
                    roleCatalog.name = roleCatalogName
                    roleCatalog.baseUrl = roleCatalogWithVersionUrl
                    roleCatalog.version = roleCatalogVersion
                    roleCatalog.installed = false

                    roleCatalogManager.save(roleCatalog)

                    val sourceUri = catalogUtil.getCatalogDownloadUri(roleCatalogWithVersionUrl, roleCatalogVersion)
                    val catalogZipDownloadFile = fileUtil.getCatalogZipDownloadFile(roleCatalogName) ?: throw IllegalStateException("catalogZipDownloadFile == null")
                    val destinationUri = Uri.fromFile(catalogZipDownloadFile).toString()

                    val queueItem = DownloadQueueItem()
                    queueItem.title = createCatalogDownloadTitle(roleCatalogName)
                    queueItem.type = ItemMediaType.CATALOG
                    queueItem.catalogName = roleCatalogName
                    queueItem.version = roleCatalogVersion
                    queueItem.sourceUri = sourceUri
                    queueItem.catalogItemSourceType = CatalogItemSourceType.SECURE

                    // start the download
                    downloadManager.enqueueDownload(queueItem, destinationUri)
                }
            }
        } catch (e: IOException) {
            Timber.e(e, "Failed to add Role based catalogs")
        }

    }

    private fun createCatalogDownloadTitle(catalogName: String): String {
        val subTitle = if (CatalogUtil.CORE_CATALOG_NAME != catalogName && catalogName.isNotBlank()) " - " + catalogName else ""
        return application.getString(R.string.catalog_title) + subTitle
    }

    override fun onResult(result: Boolean?) {
        unSubscribe()
    }

    companion object {
        const val DIFF_VERSION_AVAILABILITY_COUNT = 20
    }
}

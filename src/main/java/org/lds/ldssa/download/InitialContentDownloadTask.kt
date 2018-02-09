package org.lds.ldssa.download

import android.app.Application
import org.apache.commons.lang3.ArrayUtils
import org.lds.ldssa.R
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.mobile.task.RxTask
import org.lds.mobile.util.LdsNetworkUtil
import timber.log.Timber
import java.util.Arrays
import javax.inject.Inject

class InitialContentDownloadTask @Inject
constructor(private val application: Application,
            private val itemManager: ItemManager,
            private val downloadManager: GLDownloadManager,
            private val ldsNetworkUtil: LdsNetworkUtil) : RxTask<List<Long>>() {

    private var languageId: Long = 0

    fun init(languageId: Long): InitialContentDownloadTask {
        this.languageId = languageId
        return this
    }

    override fun run(): List<Long> {
        val urisToDownload = Arrays.asList(*ArrayUtils.addAll(
                application.resources.getStringArray(R.array.initial_downloads)
        ))

        return itemManager.findAllIdsByUris(languageId, urisToDownload)
    }

    override fun onResult(itemIdList: List<Long>?) {
        if (itemIdList == null) {
            return
        }

        if (ldsNetworkUtil.isConnected()) {
            itemIdList.forEach { itemId -> downloadManager.downloadContent(itemId) }
        } else {
            Timber.w("Could not download initial content... no Internet connection")
        }
    }
}

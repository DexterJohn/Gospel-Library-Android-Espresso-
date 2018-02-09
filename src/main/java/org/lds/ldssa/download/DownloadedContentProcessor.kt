package org.lds.ldssa.download

import org.lds.ldssa.util.ContentItemUpdateUtil

import javax.inject.Inject

class DownloadedContentProcessor @Inject
constructor(private val contentItemUpdateUtil: ContentItemUpdateUtil) : Runnable {

    private var androidDownloadId: Long = 0

    fun init(androidDownloadId: Long): DownloadedContentProcessor {
        this.androidDownloadId = androidDownloadId
        return this
    }

    override fun run() {
        contentItemUpdateUtil.installDownloadedContentItem(androidDownloadId)
    }
}

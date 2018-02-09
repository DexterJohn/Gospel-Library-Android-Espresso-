/*
 * DownloadQueueItem.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.gl.downloadqueueitem

import android.app.Application
import android.app.DownloadManager
import android.net.Uri
import org.lds.ldsaccount.LDSAccountAuth
import org.lds.ldssa.R
import org.lds.ldssa.model.database.gl.Downloadable
import org.lds.ldssa.model.prefs.Prefs


class DownloadQueueItem : DownloadQueueItemBaseRecord(), Downloadable {
    @Deprecated("destinationUri is maintained in AndroidDownloadManager")
    override var destinationUri: String = ""
        get() = super.destinationUri
        set(value) { field = "" } // force empty string

    fun toAndroidDownloadRequest(application: Application, prefs: Prefs, ldsAccountAuth: LDSAccountAuth, destinationUri: String): DownloadManager.Request {
        val request = DownloadManager.Request(Uri.parse(sourceUri))
        request.setTitle(title)
        request.setDescription(application.getString(R.string.app_name))
        request.setDestinationUri(Uri.parse(destinationUri))

        request.setVisibleInDownloadsUi(false)
        request.setAllowedOverMetered(!prefs.isMobileNetworkLimited)

        // because some of the content may require a auth connection, always add auth headers (role based content)
        val session = ldsAccountAuth.ldsAccountSession
        if (session != null && !session.isExpired()) {
            request.addRequestHeader("Cookie", session.obSSOCookie)
        }

        return request
    }
}
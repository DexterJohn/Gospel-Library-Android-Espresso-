package org.lds.ldssa.util

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import org.jsoup.Jsoup
import org.lds.ldssa.R
import org.lds.ldssa.model.database.content.subitem.SubItemManager
import org.lds.ldssa.model.database.types.SubItemContentType
import org.lds.ldssa.ui.web.ContentWebView
import javax.inject.Inject

class LdsMusicUtil
@Inject constructor(private val application: Application, private val packageManager: PackageManager, private val subItemManager: SubItemManager) {

    private val defaultMusicUri: Uri by lazy {
        val uriBuilder = Uri.Builder()
        uriBuilder.scheme(ContentWebView.MUSIC_SCHEME).authority(MUSIC_AUTHORITY)
        uriBuilder.build()
    }

    fun isLdsMusicInstalled(): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, defaultMusicUri)
        val activities = packageManager.queryIntentActivities(intent, 0)
        return !activities.isEmpty()
    }

    private fun getMusicUri(itemUri: String): String {
        val uriBuilder = Uri.Builder()
        uriBuilder.scheme(ContentWebView.MUSIC_SCHEME).authority(MUSIC_AUTHORITY).path(itemUri)
        return uriBuilder.build().toString()
    }

    fun isContentMusic(contentItemId: Long, subItemId: Long): Boolean {
        return subItemManager.findTypeById(contentItemId, subItemId) === SubItemContentType.MUSIC
    }

    fun generateExternalLinkUri(contentHtml: String, itemUri: String): String {
        var uri = ""
        val document = Jsoup.parse(contentHtml)
        val elements = document.getElementsByClass("music")
        if (!elements.isEmpty()) {
            uri = getMusicUri(itemUri)
        }
        return uri
    }

    fun generateExternalLinkHtmlText(itemUri: String): String {
        val htmlText = StringBuilder("")
        if (itemUri.isEmpty() || !isLdsMusicInstalled()) {
            return htmlText.toString()
        }

        htmlText.append("<center class='ldsmusic'><a href=\"")
        htmlText.append(itemUri)
        htmlText.append("\">")
        htmlText.append(application.getString(R.string.open_in_music))
        htmlText.append("</a></center>")
        return htmlText.toString()
    }

    companion object {
        private val MUSIC_AUTHORITY = "content"
    }

}

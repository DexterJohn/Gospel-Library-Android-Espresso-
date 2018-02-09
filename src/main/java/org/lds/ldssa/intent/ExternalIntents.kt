package org.lds.ldssa.intent

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import org.lds.ldsaccount.LDSAccountUtil
import org.lds.ldssa.ui.widget.BookmarkWidgetProvider
import org.lds.ldssa.util.LdsMusicUtil
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExternalIntents @Inject
constructor(private val packageManager: PackageManager, private val ldsMusicUtil: LdsMusicUtil) {

    fun showAccountCreation(activity: Activity) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(LDSAccountUtil.DEFAULT_CREATE_URL)
        activity.startActivity(intent)
    }

    fun updateBookmarkWidget(context: Context?) {
        context ?: return

        val bookmarkWidget = ComponentName(context, BookmarkWidgetProvider::class.java)
        val manager = AppWidgetManager.getInstance(context)

        val intent = Intent(context, BookmarkWidgetProvider::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, manager.getAppWidgetIds(bookmarkWidget))
        context.sendBroadcast(intent)
    }

    fun launchPdfViewer(context: Context, uri: Uri) {
        var intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri

        //Make sure an app that can handle PDF is installed, otherwise open the play store
        if (intent.resolveActivity(packageManager) == null) {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_PDF_URI))
        }

        context.startActivity(intent)
    }

    fun showInLdsMusic(context: Context, uriString: String) {
        if (ldsMusicUtil.isLdsMusicInstalled()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
            context.startActivity(intent)
        }
    }

    fun showUri(context: Context, uri: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(uri)
            context.startActivity(intent)
        } catch (e: Exception) {
            // prevent crash if the content uri is invalid
            Timber.e(e, "Failed to handle Uri: [%s]", uri)
        }

    }

    fun showGLPlayStorePage(activity: Activity) {
        try {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=org.lds.ldssa")))
        } catch (e: android.content.ActivityNotFoundException) {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=org.lds.ldssa")))
        }
    }

    fun showTextToSpeechSettings(context: Context) {
        val intent = Intent()
        intent.action = "com.android.settings.TTS_SETTINGS"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun googleSearch(context: Context?, text: String) {
        context ?: return
        val uri = Uri.parse("http://www.google.com/#q=define " + text)
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    companion object {
        private const val MARKET_PDF_URI = "market://details?id=com.adobe.reader"
    }
}

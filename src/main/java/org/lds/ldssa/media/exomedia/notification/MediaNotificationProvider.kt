package org.lds.ldssa.media.exomedia.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.intent.ContentIntentParams
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.media.exomedia.manager.PlaylistManager
import org.lds.ldssa.ui.notification.NotificationChannels
import org.lds.ldssa.util.ScreenUtil
import org.lds.mobile.media.notification.LdsMediaNotificationProvider

class MediaNotificationProvider(val context: Context, val playlistManager: PlaylistManager, val internalIntents: InternalIntents, val screenUtil: ScreenUtil): LdsMediaNotificationProvider(context) {

    override val mediaNotificationChannelId: String
        get() = NotificationChannels.MEDIA_PLAYBACK.channelId

    override val clickPendingIntent: PendingIntent?
        get() = createClickPendingIntent()

    private fun createClickPendingIntent(): PendingIntent? {
        val item = playlistManager.currentItem ?: return null

        val contentIntentParams = ContentIntentParams(screenUtil.lastVisibleScreenId, item.contentItemId, item.subItemId, Analytics.Referrer.MEDIA_PLAYBACK_NOTIFICATION)
        val intent = Intent(internalIntents.getShowContentIntent(context, contentIntentParams))
        return PendingIntent.getActivity(context, FOREGROUND_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    companion object {
        const val FOREGROUND_REQUEST_CODE = 0
    }
}
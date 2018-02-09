package org.lds.ldssa.media.texttospeech

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.media.session.MediaSessionCompat
import com.devbrackets.android.playlistcore.R
import com.devbrackets.android.playlistcore.data.MediaInfo
import com.facebook.FacebookSdk
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.intent.ContentIntentParams
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.ui.notification.NotificationChannels
import org.lds.ldssa.util.ScreenUtil
import org.lds.mobile.media.notification.LdsMediaNotificationProvider

class TextToSpeechNotificationProvider(private val context: Context, private val textToSpeechManager: TextToSpeechManager, private val internalIntents: InternalIntents, private val screenUtil: ScreenUtil): LdsMediaNotificationProvider(context) {

    override val mediaNotificationChannelId: String
        get() = NotificationChannels.MEDIA_PLAYBACK.channelId

    override val clickPendingIntent: PendingIntent?
        get() = createClickPendingIntent()

    override fun buildNotification(info: MediaInfo, mediaSession: MediaSessionCompat, serviceClass: Class<out Service>): Notification {
        return NotificationCompat.Builder(context, mediaNotificationChannelId).apply {
            setSmallIcon(info.appIcon)
            setLargeIcon(info.largeNotificationIcon)

            var contentText = info.album
            if (info.artist.isNotBlank()) {
                contentText += if (contentText.isNotBlank()) " - " + info.artist else info.artist
            }

            setContentTitle(info.title)
            setContentText(contentText)
            setColorized(allowColorizedNotification)

            setContentIntent(clickPendingIntent)
            setDeleteIntent(createPendingIntent(serviceClass, TextToSpeechService.ACTION_STOP))

            val allowSwipe = !(info.mediaState.isPlaying)
            setAutoCancel(allowSwipe)
            setOngoing(!allowSwipe)

            //Set the notification category on lollipop
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setCategory(Notification.CATEGORY_TRANSPORT)
                setVisibility(Notification.VISIBILITY_PUBLIC)
            }

            setActions(this, info, serviceClass)
            setStyle(buildMediaStyle(mediaSession, serviceClass))
        }.build()
    }

    override fun setActions(builder: NotificationCompat.Builder, info: MediaInfo, serviceClass: Class<out Service>) {
        with(info.mediaState) {
            // Previous
            var actionIcon = if (isPreviousEnabled) R.drawable.playlistcore_notification_previous else R.drawable.playlistcore_notification_previous_disabled
            var title = context.resources.getString(R.string.playlistcore_default_notification_previous)
            builder.addAction(actionIcon, title, createPendingIntent(serviceClass, TextToSpeechService.ACTION_PREVIOUS))

            // Play/Pause
            actionIcon = if (isPlaying) {
                title = context.resources.getString(R.string.playlistcore_default_notification_pause)
                if (isLoading) R.drawable.playlistcore_notification_pause_disabled else R.drawable.playlistcore_notification_pause
            } else {
                title = context.resources.getString(R.string.playlistcore_default_notification_play)
                if (isLoading) R.drawable.playlistcore_notification_play_disabled else R.drawable.playlistcore_notification_play
            }
            builder.addAction(actionIcon, title, createPendingIntent(serviceClass, TextToSpeechService.ACTION_PLAY_PAUSE))

            // Next
            actionIcon = if (isNextEnabled) R.drawable.playlistcore_notification_next else R.drawable.playlistcore_notification_next_disabled
            title = context.resources.getString(R.string.playlistcore_default_notification_next)
            builder.addAction(actionIcon, title, createPendingIntent(serviceClass, TextToSpeechService.ACTION_NEXT))
        }
    }

    override fun buildMediaStyle(mediaSession: MediaSessionCompat, serviceClass: Class<out Service>): android.support.v4.media.app.NotificationCompat.MediaStyle {
        val mediaStyle = android.support.v4.media.app.NotificationCompat.MediaStyle()
        mediaStyle.setMediaSession(mediaSession.sessionToken)
        mediaStyle.setShowActionsInCompactView(0, 1, 2) // previous, play/pause, next
        mediaStyle.setShowCancelButton(true)
        mediaStyle.setCancelButtonIntent(createPendingIntent(serviceClass, TextToSpeechService.ACTION_STOP))

        return mediaStyle
    }

    /**
     * Creates a PendingIntent for the given action to the specified service
     *
     * @param action The action to use
     * @param serviceClass The service class to notify of intents
     * @return The resulting PendingIntent
     */
    override fun createPendingIntent(serviceClass: Class<out Service>, action: String): PendingIntent {
        val intent = Intent(context, serviceClass)
        intent.action = action

        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun createClickPendingIntent(): PendingIntent? {
        val item = textToSpeechManager.getCurrentItem() ?: return null

        val contentIntentParams = ContentIntentParams(screenUtil.lastVisibleScreenId, item.contentItemId, item.subItemId, Analytics.Referrer.MEDIA_PLAYBACK_NOTIFICATION)
        val intent = internalIntents.getShowContentIntent(FacebookSdk.getApplicationContext(), contentIntentParams)

        return PendingIntent.getActivity(FacebookSdk.getApplicationContext(), FOREGROUND_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    companion object {
        const val FOREGROUND_REQUEST_CODE = 0
    }
}
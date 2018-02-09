package org.lds.ldssa.media.exomedia.handler

import android.app.Service
import android.content.Context
import com.devbrackets.android.playlistcore.components.audiofocus.AudioFocusProvider
import com.devbrackets.android.playlistcore.components.image.ImageProvider
import com.devbrackets.android.playlistcore.components.mediacontrols.DefaultMediaControlsProvider
import com.devbrackets.android.playlistcore.components.mediacontrols.MediaControlsProvider
import com.devbrackets.android.playlistcore.components.mediasession.DefaultMediaSessionProvider
import com.devbrackets.android.playlistcore.components.mediasession.MediaSessionProvider
import com.devbrackets.android.playlistcore.components.notification.DefaultPlaylistNotificationProvider
import com.devbrackets.android.playlistcore.components.notification.PlaylistNotificationProvider
import com.devbrackets.android.playlistcore.listener.MediaStatusListener
import com.devbrackets.android.playlistcore.listener.ProgressListener
import com.devbrackets.android.playlistcore.listener.ServiceCallbacks
import com.devbrackets.android.playlistcore.manager.BasePlaylistManager
import org.apache.commons.lang3.StringUtils
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.media.exomedia.data.AudioItem
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.ldssa.media.exomedia.helper.GLAudioFocusProvider
import org.lds.ldssa.media.exomedia.service.MediaService
import org.lds.ldssa.model.database.gl.mediaplaybackposition.MediaPlaybackPositionManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.ui.notification.NotificationIds
import org.lds.ldssa.util.AnalyticsUtil
import org.lds.mobile.media.cast.CastManager
import org.lds.mobile.media.handler.LdsPlaylistHandler
import org.lds.mobile.media.player.AudioApi
import java.util.HashMap

class GLPlaylistHandler<I : MediaItem, out M : BasePlaylistManager<I>> constructor(
        context: Context,
        serviceClass: Class<out Service>,
        playlistManager: M,
        imageProvider: ImageProvider<I>,
        notificationProvider: PlaylistNotificationProvider,
        mediaSessionProvider: MediaSessionProvider,
        mediaControlsProvider: MediaControlsProvider,
        audioFocusProvider: AudioFocusProvider<I>,
        listener: Listener<I>?,
        override val castManager: CastManager,
        private val analytics: Analytics,
        private val analyticsUtil: AnalyticsUtil,
        private val mediaPlaybackPositionManager: MediaPlaybackPositionManager
) : LdsPlaylistHandler<I, M>(context, serviceClass, playlistManager, imageProvider, notificationProvider, mediaSessionProvider, mediaControlsProvider, audioFocusProvider, listener, castManager), ProgressListener, MediaStatusListener<I> {

    /**
     * Retrieves the ID to use for the notification and registering this
     * service as Foreground when media is playing
     */
    override val notificationId: Int
        get() = NotificationIds.MEDIA_PLAYBACK.notificationId

    override fun setup(serviceCallbacks: ServiceCallbacks) {
        super.setup(serviceCallbacks)
    }

    override fun tearDown() {
        saveCurrentAudioPosition()
        super.tearDown()
    }

    override fun stop() {
        saveCurrentAudioPosition()
        super.stop()
    }

    override fun next() {
        saveCurrentAudioPosition()
        super.next()
    }

    override fun previous() {
        saveCurrentAudioPosition()
        super.previous()
    }

    override fun play() {
        super.play()
        setupForeground() // TODO this is a workaround until Brian fixes it in PlaylistCore. Remove this when that has been done. (Version TBD)
    }

    override fun pause(transient: Boolean) {
        saveCurrentAudioPosition()
        super.pause(transient)
    }

    private fun saveCurrentAudioPosition() {
        val currentItem = playlistManager.currentItem
        val audioApi = getAudioApi()
        if (audioApi == null || currentItem == null || currentItem.mediaType != BasePlaylistManager.AUDIO) {
            return
        }
        saveCurrentAudioPosition(currentItem, audioApi.currentPosition.toInt(), audioApi.duration.toInt())
    }

    private fun getAudioApi(): AudioApi<I>? {
        return playlistManager.mediaPlayers.firstOrNull { it is AudioApi } as AudioApi?
    }

    /**
     * This method will save the current audio position and then post analytics data
     *
     * When the calling methods have valid data for saving the audio position:
     * 1 - stop() - The normal scenario where a request is made to stop playback of audio.
     * 2 - tearDown() - Will call into this method when the service is destroyed, but usually won't have valid data because onMediaStopped() was called first. Valid data only comes through this method when the app is force-closed.
     * 3 - next() - Called just before the next item in the list is loaded. An onMediaStopped() call is not made in this scenario.
     * 4 - previous() - Called just before the previous item in the list is loaded. An onMediaStopped() call is not made in this scenario.
     * 5 - togglePlayPause() - Called just before the current item is paused. Note that seeking will call performPause(). An onMediaStopped() call is not made in this scenario.
     */
    private fun saveCurrentAudioPosition(playlistItem: MediaItem?, position: Int, duration: Int) {
        var positionToSave = position
        if (playlistItem == null || playlistItem.mediaType != BasePlaylistManager.AUDIO || duration == 0) {
            return
        }

        //If we are near the end of the audio, the next playback should start from the beginning
        if (positionToSave + MediaService.POSITION_SAVE_THRESHOLD >= duration) {
            positionToSave = 0
        }

        mediaPlaybackPositionManager.setPlaybackPosition(playlistItem.contentItemId, playlistItem.subItemId, ItemMediaType.AUDIO, null, positionToSave)
        logAudioAnalytics(playlistItem as AudioItem?, positionToSave.toLong(), duration.toLong())
    }

    private fun logAudioAnalytics(audioItem: AudioItem?, position: Long, duration: Long) {
        if (audioItem == null) {
            return
        }

        var uri = audioItem.downloadedMediaUri
        if (StringUtils.isEmpty(uri)) {
            uri = audioItem.mediaUrl
        }

        val percentageViewed = position.toFloat() / duration
        val sourceType = if (StringUtils.isNotBlank(audioItem.downloadedMediaUri)) Analytics.Value.LOCAL else Analytics.Value.REMOTE

        val contentItemId = audioItem.contentItemId

        val attributes = HashMap<String, String>()
        attributes.put(Analytics.Attribute.CONTENT_LANGUAGE, analyticsUtil.findContentLanguageByContentItemId(contentItemId))
        attributes.put(Analytics.Attribute.URI, analyticsUtil.findSubItemUriBySubItemId(contentItemId, audioItem.subItemId))
        attributes.put(Analytics.Attribute.ITEM_URI, analyticsUtil.findItemUriByContentItemId(contentItemId))
        attributes.put(Analytics.Attribute.CONTENT_URI, uri ?: "")
        attributes.put(Analytics.Attribute.TITLE, audioItem.title ?: "")
        attributes.put(Analytics.Attribute.CONTENT_TYPE, Analytics.Value.AUDIO)
        attributes.put(Analytics.Attribute.CONTENT_GROUP, analyticsUtil.findContentGroupByContentItemId(contentItemId))
        attributes.put(Analytics.Attribute.CONTENT_VERSION, analyticsUtil.findContentVersionByContentItemId(contentItemId))
        attributes.put(Analytics.Attribute.PERCENTAGE_VIEWED, percentageViewed.toString())
        attributes.put(Analytics.Attribute.SOURCE_TYPE, sourceType)
        analytics.postEvent(Analytics.Event.CONTENT_VIEWED, attributes)
    }

    class Builder<I : MediaItem, out M : BasePlaylistManager<I>>(
            private val context: Context,
            private val serviceClass: Class<out Service>,
            private val playlistManager: M,
            private val imageProvider: ImageProvider<I>,
            private val castManager: CastManager,
            private val analytics: Analytics,
            private val analyticsUtil: AnalyticsUtil,
            private val mediaPlaybackPositionManager: MediaPlaybackPositionManager
    ) {
        var notificationProvider: PlaylistNotificationProvider? = null
        var mediaSessionProvider: MediaSessionProvider? = null
        var mediaControlsProvider: MediaControlsProvider? = null
        var audioFocusProvider: AudioFocusProvider<I>? = null
        var listener: Listener<I>? = null

        fun build(): GLPlaylistHandler<I, M> {
            return GLPlaylistHandler(context,
                    serviceClass,
                    playlistManager,
                    imageProvider,
                    notificationProvider ?: DefaultPlaylistNotificationProvider(context),
                    mediaSessionProvider ?: DefaultMediaSessionProvider(context, serviceClass),
                    mediaControlsProvider ?: DefaultMediaControlsProvider(context),
                    audioFocusProvider ?: GLAudioFocusProvider(context),
                    listener,
                    castManager,
                    analytics,
                    analyticsUtil,
                    mediaPlaybackPositionManager)
        }
    }
}
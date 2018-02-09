package org.lds.ldssa.media.exomedia.service

import com.devbrackets.android.playlistcore.components.playlisthandler.PlaylistHandler
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.ldssa.media.exomedia.handler.GLPlaylistHandler
import org.lds.ldssa.media.exomedia.helper.GLAudioApi
import org.lds.ldssa.media.exomedia.helper.GLCastApi
import org.lds.ldssa.media.exomedia.manager.PlaylistManager
import org.lds.ldssa.media.exomedia.notification.MediaNotificationProvider
import org.lds.ldssa.model.database.gl.mediaplaybackposition.MediaPlaybackPositionManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.util.AnalyticsUtil
import org.lds.ldssa.util.ScreenUtil
import org.lds.mobile.media.cast.CastManager
import org.lds.mobile.media.player.AudioApi
import org.lds.mobile.media.player.CastApi
import org.lds.mobile.media.service.LdsMediaService
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * A simple service that extends [BasePlaylistService] in order to provide
 * the application specific information required.
 */
class MediaService : LdsMediaService<MediaItem>() {

    @Inject
    override lateinit var playlistManager: PlaylistManager
    @Inject
    override lateinit var castManager: CastManager
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var analyticsUtil: AnalyticsUtil
    @Inject
    lateinit var mediaPlaybackPositionManager: MediaPlaybackPositionManager
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var screenUtil: ScreenUtil

    override val castApi: CastApi<MediaItem> by lazy {
        return@lazy GLCastApi(applicationContext, castManager)
    }
    override val audioApi: AudioApi<MediaItem> by lazy {
        return@lazy GLAudioApi(applicationContext)
    }

    init {
        Injector.get().inject(this)
    }

    override fun newPlaylistHandler(): PlaylistHandler<MediaItem> {
        val imageProvider = MediaImageProvider(applicationContext, object : MediaImageProvider.OnImageUpdatedListener {
            override fun onImageUpdated() {
                playlistHandler.updateMediaControls()
            }
        })

        val playlistHandler = GLPlaylistHandler.Builder(
                applicationContext,
                javaClass,
                playlistManager,
                imageProvider,
                castManager,
                analytics,
                analyticsUtil,
                mediaPlaybackPositionManager
        )

        playlistHandler.notificationProvider = MediaNotificationProvider(applicationContext, playlistManager, internalIntents, screenUtil)

        return playlistHandler.build()
    }

    override fun onDestroy() {
        castManager.detach()
        super.onDestroy()
    }

    companion object {
        val POSITION_SAVE_THRESHOLD = TimeUnit.SECONDS.toMillis(5)
    }
}
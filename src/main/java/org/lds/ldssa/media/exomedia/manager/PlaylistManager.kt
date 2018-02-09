package org.lds.ldssa.media.exomedia.manager

import android.app.Application
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.ldssa.media.exomedia.service.MediaService
import org.lds.mobile.media.cast.CastManager
import org.lds.mobile.media.manager.LdsPlaylistManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaylistManager @Inject constructor(application: Application, castManager: CastManager): LdsPlaylistManager<MediaItem>(application, castManager, MediaService::class.java) {

    override val castEnabled: Boolean
        get() = true

    var secondaryId: Long = 0

    fun setPlaylistId(playlistId: Long, secondaryId: Long) {
        id = playlistId
        this.secondaryId = secondaryId
    }
}
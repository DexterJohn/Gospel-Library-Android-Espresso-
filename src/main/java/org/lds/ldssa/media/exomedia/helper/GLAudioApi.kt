package org.lds.ldssa.media.exomedia.helper

import android.content.Context
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.ldssa.model.prefs.Prefs
import org.lds.mobile.media.player.AudioApi
import javax.inject.Inject

class GLAudioApi(context: Context): AudioApi<MediaItem>(context) {

    @Inject
    lateinit var prefs: Prefs

    init {
        Injector.get().inject(this)
    }

    override fun play() {
        audioPlayer.setPlaybackSpeed(prefs.audioPlaybackSpeed.playbackSpeedValue)
        super.play()
    }

    override fun playItem(item: MediaItem) {
        super.playItem(item)
        audioPlayer.setPlaybackSpeed(prefs.audioPlaybackSpeed.playbackSpeedValue)
    }

    override fun onCompletion() {
        if (prefs.isAudioContinuousPlay) {
            super.onCompletion()
        } else {
            pause()
            seekTo(0)
        }
    }
}
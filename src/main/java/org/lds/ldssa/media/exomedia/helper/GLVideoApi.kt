package org.lds.ldssa.media.exomedia.helper

import com.devbrackets.android.exomedia.ui.widget.VideoView
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.mobile.media.data.LdsMediaItem
import org.lds.mobile.media.player.VideoApi

class GLVideoApi(videoView: VideoView): VideoApi<MediaItem>(videoView) {

    var onCompletionListener: () -> Unit = {}
    var onErrorListener: () -> Unit = {}

    override fun onCompletion() {
        stop()
        onCompletionListener()
    }

    override fun onError(e: Exception): Boolean {
        onErrorListener()
        return super.onError(e)
    }

    override fun onPlaylistItemChanged(currentItem: LdsMediaItem?, hasNext: Boolean, hasPrevious: Boolean): Boolean {
        val videoControls = videoView.videoControls
        if (videoControls != null && currentItem != null) {
            // Updates the VideoControls button visibilities
            videoControls.setPreviousButtonEnabled(hasPrevious)
            videoControls.setNextButtonEnabled(hasNext)
        }

        return false
    }
}


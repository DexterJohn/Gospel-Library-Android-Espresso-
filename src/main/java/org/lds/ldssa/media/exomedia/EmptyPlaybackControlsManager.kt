package org.lds.ldssa.media.exomedia

import android.view.MenuItem
import org.lds.ldssa.media.MiniPlaybackControlsManager
import org.lds.ldssa.ui.widget.MiniPlaybackControls

class EmptyPlaybackControlsManager : MiniPlaybackControlsManager {
    override var playbackControls: MiniPlaybackControls?
        get() = null
        set(value) {}
    override var onItemChangedListener: (currentItem: MiniPlaybackControlsManager.PlaybackItem?, isNextAvailable: Boolean, isPreviousAvailable: Boolean, MiniPlaybackControlsManager.PlaybackType) -> Unit
        get() = {_, _, _, _ ->  }
        set(value) {}
    override var onProgressChangedListener: (MiniPlaybackControlsManager.Progress, MiniPlaybackControlsManager.PlaybackType) -> Unit
        get() = {_, _ -> }
        set(value) {}
    override var onPlaybackStateChangedListener: (MiniPlaybackControlsManager.PlaybackState, MiniPlaybackControlsManager.PlaybackType) -> Unit
        get() = {_, _ -> }
        set(value) {}

    override fun invokePlayPause() {}
    override fun invokePrevious() {}
    override fun invokeStop() {}
    override fun invokeNext() {}
    override fun invokeSeekStarted() {}
    override fun invokeSeekEnded(seekToPosition: Int) {}
    override fun getCurrentItem() = null
    override fun getCurrentPlaybackState() = MiniPlaybackControlsManager.PlaybackState.STOPPED
    override fun getCurrentProgress() = null
    override fun isPlaying() = false
    override fun isNextAvailable() = false
    override fun isPreviousAvailable() = false
    override fun isReplayAvailable() = false
    override fun isForwardAvailable() = false
    override fun getPlaybackType() = MiniPlaybackControlsManager.PlaybackType.NONE
    override fun controlsCanShow() = false
    override fun onDetachedFromWindow() {}
    override fun formatProgressString(progress: Int) = ""
    override fun formatDurationString(duration: Int) = ""
    override fun onOptionsItemSelected(menuItem: MenuItem) = false
}
package org.lds.ldssa.media

import android.view.MenuItem
import org.lds.ldssa.ui.widget.MiniPlaybackControls

interface MiniPlaybackControlsManager {

    var playbackControls: MiniPlaybackControls?

    // Listeners
    var onItemChangedListener: (currentItem: PlaybackItem?, isNextAvailable: Boolean, isPreviousAvailable: Boolean, PlaybackType) -> Unit
    var onProgressChangedListener: (Progress, PlaybackType) -> Unit
    var onPlaybackStateChangedListener: (PlaybackState, PlaybackType) -> Unit

    // Control Actions
    fun invokePlayPause()
    fun invokeStop()
    fun invokePrevious()
    fun invokeNext()
    fun invokeSeekStarted()
    fun invokeSeekEnded(seekToPosition: Int)

    // Data Requests
    fun getCurrentItem(): PlaybackItem?
    fun getCurrentPlaybackState(): PlaybackState
    fun getCurrentProgress(): Progress?
    fun isPlaying(): Boolean
    fun isNextAvailable(): Boolean
    fun isPreviousAvailable(): Boolean
    fun isReplayAvailable(): Boolean
    fun isForwardAvailable(): Boolean
    fun getPlaybackType(): PlaybackType
    fun controlsCanShow(): Boolean

    // System Actions
    fun onDetachedFromWindow()

    // Other Actions
    fun formatProgressString(progress: Int): String
    fun formatDurationString(duration: Int): String

    // Menu
    fun onOptionsItemSelected(menuItem: MenuItem): Boolean

    // Classes
    class PlaybackItem(var title: String, var subtitle: String)
    class Progress(var currentPosition: Int, var duration: Int, var bufferProgress: Int = 0)

    enum class PlaybackState {
        RETRIEVING,
        PREPARING,
        PLAYING,
        PAUSED,
        SEEKING,
        STOPPED,
        ERROR
    }

    enum class PlaybackType {
        NONE,
        EXOMEDIA,
        TEXT_TO_SPEECH
    }
}

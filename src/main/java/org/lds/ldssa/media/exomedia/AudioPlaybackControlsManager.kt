package org.lds.ldssa.media.exomedia

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.view.MenuItem
import com.devbrackets.android.exomedia.util.TimeFormatUtil
import com.devbrackets.android.playlistcore.data.MediaProgress
import com.devbrackets.android.playlistcore.data.PlaybackState
import com.devbrackets.android.playlistcore.listener.PlaylistListener
import com.devbrackets.android.playlistcore.listener.ProgressListener
import com.devbrackets.android.playlistcore.manager.BasePlaylistManager
import org.lds.ldssa.R
import org.lds.ldssa.download.GLDownloadManager
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.media.MiniPlaybackControlsManager
import org.lds.ldssa.media.MiniPlaybackControlsManager.PlaybackItem
import org.lds.ldssa.media.MiniPlaybackControlsManager.Progress
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.ldssa.media.exomedia.manager.PlaylistManager
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.content.subitem.SubItemManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.ui.dialog.DownloadMediaDialogFragment
import org.lds.ldssa.ui.widget.MiniPlaybackControls
import timber.log.Timber
import javax.inject.Inject

class AudioPlaybackControlsManager(val context: Context) :
        MiniPlaybackControlsManager,
        PlaylistListener<MediaItem>,
        ProgressListener {

    @Inject
    lateinit var playlistManager: PlaylistManager
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var downloadManager: GLDownloadManager
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var itemManager: ItemManager
    @Inject
    lateinit var subItemManager: SubItemManager

    override var playbackControls: MiniPlaybackControls? = null
        set(value) {
            field = value
            value?.castEnabled = true
            value?.downloadEnabled = true
        }
    override var onItemChangedListener: (currentItem: PlaybackItem?, isNextAvailable: Boolean, isPreviousAvailable: Boolean, playbackType: MiniPlaybackControlsManager.PlaybackType) -> Unit = { _, _, _, _ -> }
    override var onProgressChangedListener: (progress: Progress, playbackType: MiniPlaybackControlsManager.PlaybackType) -> Unit = { _, _ -> }
    override var onPlaybackStateChangedListener: (MiniPlaybackControlsManager.PlaybackState, MiniPlaybackControlsManager.PlaybackType) -> Unit = { _, _ -> }

    init {
        Injector.get().inject(this)
        playlistManager.registerPlaylistListener(this)
        playlistManager.registerProgressListener(this)
    }

    override fun invokePlayPause() {
        playlistManager.invokePausePlay()
    }

    override fun invokeStop() {
        playlistManager.invokeStop()
    }

    override fun invokePrevious() {
        playlistManager.invokePrevious()
    }

    override fun invokeNext() {
        playlistManager.invokeNext()
    }

    override fun invokeSeekStarted() {
        playlistManager.invokeSeekStarted()
    }

    override fun invokeSeekEnded(seekToPosition: Int) {
        playlistManager.invokeSeekEnded(seekToPosition.toLong())
    }

    override fun getCurrentItem(): PlaybackItem? {
        val currentItem = playlistManager.currentItem ?: return null
        if (currentItem.mediaType != BasePlaylistManager.AUDIO) {
            return null
        }

        return createPlaybackItem(currentItem)
    }

    override fun getCurrentPlaybackState(): MiniPlaybackControlsManager.PlaybackState {
        return determinePlaybackState(playlistManager.currentPlaybackState)
    }

    override fun getCurrentProgress(): Progress? {
        val currentProgress = playlistManager.currentProgress ?: return null
        return Progress(currentProgress.position.toInt(), currentProgress.duration.toInt(), currentProgress.bufferPercent)
    }

    override fun isPlaying(): Boolean {
        return playlistManager.isPlaying
    }

    override fun isNextAvailable(): Boolean {
        return playlistManager.isNextAvailable
    }

    override fun isPreviousAvailable(): Boolean {
        return playlistManager.isPreviousAvailable
    }

    override fun isReplayAvailable(): Boolean {
        return true
    }

    override fun isForwardAvailable(): Boolean {
        return true
    }

    override fun onDetachedFromWindow() {
        playlistManager.unRegisterPlaylistListener(this)
        playlistManager.unRegisterProgressListener(this)
    }

    override fun formatProgressString(progress: Int): String {
        return TimeFormatUtil.formatMs(progress.toLong())
    }

    override fun formatDurationString(duration: Int): String {
        return TimeFormatUtil.formatMs(duration.toLong())
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_mini_player_settings -> {
                internalIntents.showAudioSettings(context)
                return true
            }
            R.id.menu_mini_player_download -> {
                downloadCurrentlyPlayingAudioItem()
                return true
            }
            else -> return false
        }
    }

    override fun onProgressUpdated(mediaProgress: MediaProgress): Boolean {
        onProgressChangedListener(Progress(mediaProgress.position.toInt(), mediaProgress.duration.toInt(), mediaProgress.bufferPercent), getPlaybackType())
        return false
    }

    override fun onPlaybackStateChanged(playbackState: PlaybackState): Boolean {
        onPlaybackStateChangedListener(determinePlaybackState(playbackState), getPlaybackType())
        return false
    }

    override fun onPlaylistItemChanged(currentItem: MediaItem?, hasNext: Boolean, hasPrevious: Boolean): Boolean {
        if (currentItem == null || currentItem.mediaType == BasePlaylistManager.VIDEO) {
            playbackControls?.hideImmediate()
        } else {
            playbackControls?.show()
        }

        onItemChangedListener(createPlaybackItem(currentItem), hasNext, hasPrevious, getPlaybackType())
        return false
    }

    override fun getPlaybackType(): MiniPlaybackControlsManager.PlaybackType {
        return MiniPlaybackControlsManager.PlaybackType.EXOMEDIA
    }

    override fun controlsCanShow(): Boolean {
        return !(playlistManager.isStopped || playlistManager.currentItem?.mediaType != BasePlaylistManager.AUDIO)
    }

    private fun downloadCurrentlyPlayingAudioItem() {
        val playlistItem = playlistManager.currentItem
        playlistItem?.let {
            if (context is FragmentActivity) {
                DownloadMediaDialogFragment.newInstance(it.contentItemId, it.subItemId, null, ItemMediaType.AUDIO).show(context.supportFragmentManager)
            } else {
                val title = it.title ?: ""
                val tertiaryId = it.tertiaryId ?: ""
                val downloadUrl = it.mediaDownloadUrl ?: ""

                when {
                    downloadUrl.isNullOrBlank() -> Timber.w("Media download url is missing")
                    tertiaryId.isNullOrBlank() -> Timber.w("Media download tertiaryId is missing")
                    else -> downloadManager.downloadMedia(it.contentItemId, it.subItemId, tertiaryId, title, downloadUrl, ItemMediaType.AUDIO)
                }
            }
        }
    }

    private fun createPlaybackItem(currentItem: MediaItem?): PlaybackItem? {
        currentItem?.let {
            val subtitle = itemManager.findTitleById(currentItem.contentItemId)
            return PlaybackItem(it.title ?: "", subtitle)
        }
        return null
    }

    private fun determinePlaybackState(playbackState: PlaybackState): MiniPlaybackControlsManager.PlaybackState {
        when (playbackState) {
            PlaybackState.RETRIEVING -> return MiniPlaybackControlsManager.PlaybackState.RETRIEVING
            PlaybackState.PREPARING -> return MiniPlaybackControlsManager.PlaybackState.PREPARING
            PlaybackState.PLAYING -> return MiniPlaybackControlsManager.PlaybackState.PLAYING
            PlaybackState.PAUSED -> return MiniPlaybackControlsManager.PlaybackState.PAUSED
            PlaybackState.ERROR -> return MiniPlaybackControlsManager.PlaybackState.ERROR
            PlaybackState.SEEKING -> return MiniPlaybackControlsManager.PlaybackState.SEEKING
            else -> return MiniPlaybackControlsManager.PlaybackState.STOPPED
        }
    }
}

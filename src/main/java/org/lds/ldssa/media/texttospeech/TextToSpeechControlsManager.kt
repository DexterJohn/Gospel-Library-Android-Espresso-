package org.lds.ldssa.media.texttospeech

import android.content.Context
import android.view.MenuItem
import kotlinx.coroutines.experimental.launch
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.media.MiniPlaybackControlsManager
import org.lds.ldssa.media.MiniPlaybackControlsManager.PlaybackItem
import org.lds.ldssa.media.MiniPlaybackControlsManager.Progress
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.content.subitem.SubItemManager
import org.lds.ldssa.ui.widget.MiniPlaybackControls
import org.lds.ldssa.util.LanguageUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import javax.inject.Inject

class TextToSpeechControlsManager(val context: Context) : MiniPlaybackControlsManager, TextToSpeechManager.TextToSpeechItemListener, TextToSpeechManager.TextToSpeechProgressListener {

    @Inject
    lateinit var itemManager: ItemManager
    @Inject
    lateinit var subItemManager: SubItemManager
    @Inject
    lateinit var languageUtil: LanguageUtil
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var textToSpeechManager: TextToSpeechManager
    @Inject
    lateinit var cc: CoroutineContextProvider

    override var playbackControls: MiniPlaybackControls? = null
        set(value) {
            field = value
            value?.castEnabled = false
            value?.downloadEnabled = false
        }
    override var onItemChangedListener: (currentItem: PlaybackItem?, isNextAvailable: Boolean, isPreviousAvailable: Boolean, playbackType: MiniPlaybackControlsManager.PlaybackType) -> Unit = { _, _, _, _ -> }
    override var onProgressChangedListener: (progress: Progress, playbackType: MiniPlaybackControlsManager.PlaybackType) -> Unit = { _, _ -> }
    override var onPlaybackStateChangedListener: (MiniPlaybackControlsManager.PlaybackState, MiniPlaybackControlsManager.PlaybackType) -> Unit = { _, _ -> }

    init {
        Injector.get().inject(this)
        textToSpeechManager.registerItemListener(this)
        textToSpeechManager.registerProgressListener(this)
    }

    override fun invokePlayPause() {
        textToSpeechManager.invokePlayPause()
    }

    override fun invokeStop() {
        textToSpeechManager.invokeStop()
        playbackControls?.hideImmediate()
    }

    override fun invokePrevious() {
        textToSpeechManager.invokePrevious()
    }

    override fun invokeNext() {
        textToSpeechManager.invokeNext()
    }

    override fun invokeSeekStarted() {
        textToSpeechManager.invokeSeekStarted()
    }

    override fun invokeSeekEnded(seekToPosition: Int) {
        textToSpeechManager.invokeSeekEnded(seekToPosition)
    }

    override fun getCurrentItem(): PlaybackItem? {
        val currentItem = textToSpeechManager.getCurrentItem() ?: return null
        return PlaybackItem(currentItem.title, currentItem.subtitle)
    }

    override fun getCurrentPlaybackState(): MiniPlaybackControlsManager.PlaybackState {
        return determinePlaybackState(textToSpeechManager.getCurrentPlaybackState())
    }

    override fun getCurrentProgress(): Progress? {
        val currentProgress = textToSpeechManager.getCurrentProgress() ?: return null
        return Progress(currentProgress.position, currentProgress.duration, 0)
    }

    override fun isPlaying(): Boolean {
        return textToSpeechManager.isPlaying()
    }

    override fun isNextAvailable(): Boolean {
        return textToSpeechManager.isNextAvailable()
    }

    override fun isPreviousAvailable(): Boolean {
        return textToSpeechManager.isPreviousAvailable()
    }

    override fun isReplayAvailable(): Boolean {
        return false
    }

    override fun isForwardAvailable(): Boolean {
        return false
    }

    override fun onDetachedFromWindow() {
        textToSpeechManager.unRegisterItemListener(this)
        textToSpeechManager.unRegisterProgressListener(this)
    }

    override fun formatProgressString(progress: Int): String {
        return progress.toString()
    }

    override fun formatDurationString(duration: Int): String {
        return duration.toString()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_mini_player_settings -> {
                internalIntents.showAudioSettings(context)
                return true
            }
            else -> return false
        }
    }

    override fun onItemChanged(currentItem: TextToSpeechManager.TextToSpeechItem, hasNext: Boolean, hasPrevious: Boolean) {
        launch(cc.ui) {
            onItemChangedListener(PlaybackItem(currentItem.title, currentItem.subtitle), hasNext, hasPrevious, getPlaybackType())
        }
    }

    override fun onProgressChanged(ttsProgress: TextToSpeechManager.TextToSpeechProgress) {
        launch(cc.ui) {
            onProgressChangedListener(Progress(ttsProgress.position, ttsProgress.duration), getPlaybackType())
        }
    }

    override fun onPlaybackStateChanged(playbackState: TextToSpeechEngine.PlaybackState) {
        launch(cc.ui) {
            if (playbackState == TextToSpeechEngine.PlaybackState.COMPLETED) {
                playbackCompleted()
            } else {
                onPlaybackStateChangedListener(determinePlaybackState(playbackState), getPlaybackType())
            }
        }
    }

    override fun getPlaybackType(): MiniPlaybackControlsManager.PlaybackType {
        return MiniPlaybackControlsManager.PlaybackType.TEXT_TO_SPEECH
    }

    override fun controlsCanShow(): Boolean {
        return textToSpeechManager.isPlaybackActive()
    }

    private fun playbackCompleted() {
        playbackControls?.updatePlayPauseButton(false)
    }

    private fun determinePlaybackState(playbackState: TextToSpeechEngine.PlaybackState): MiniPlaybackControlsManager.PlaybackState {
        when (playbackState) {
            TextToSpeechEngine.PlaybackState.PREPARING -> return MiniPlaybackControlsManager.PlaybackState.PREPARING
            TextToSpeechEngine.PlaybackState.PLAYING -> return MiniPlaybackControlsManager.PlaybackState.PLAYING
            TextToSpeechEngine.PlaybackState.PAUSED -> return MiniPlaybackControlsManager.PlaybackState.PAUSED
            TextToSpeechEngine.PlaybackState.ERROR -> return MiniPlaybackControlsManager.PlaybackState.ERROR
            else -> return MiniPlaybackControlsManager.PlaybackState.STOPPED
        }
    }
}

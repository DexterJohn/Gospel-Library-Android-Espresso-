package org.lds.ldssa.media.texttospeech

import android.app.Application
import android.content.Intent
import com.devbrackets.android.playlistcore.manager.BasePlaylistManager
import com.google.android.exoplayer2.util.MimeTypes
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.ldssa.model.prefs.Prefs
import java.lang.ref.WeakReference
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextToSpeechManager @Inject constructor(private val application: Application, private val prefs: Prefs) {

    private var textToSpeechEngine: TextToSpeechEngine? = null

    private val itemListeners = ArrayList<WeakReference<TextToSpeechItemListener>>()
    private val progressListeners = ArrayList<WeakReference<TextToSpeechProgressListener>>()
    private var itemListenersLock = ReentrantLock(true)
    private var progressListenersLock = ReentrantLock(true)

    private var itemList: List<TextToSpeechItem>? = null
    private var currentItem: TextToSpeechManager.TextToSpeechItem? = null
    private var currentItemPosition: Int = 0
    private var pausedForSeek = false

    private fun invokePlay(position: Int = 0, playImmediately: Boolean = true) {
        val intent = Intent(application, TextToSpeechService::class.java)
        intent.action = TextToSpeechService.ACTION_PLAY
        intent.putExtra(TextToSpeechService.EXTRA_POSITION, position)
        intent.putExtra(TextToSpeechService.EXTRA_PLAY_IMMEDIATELY, playImmediately)
        application.startService(intent)
    }

    fun invokePlayPause() {
        val intent = Intent(application, TextToSpeechService::class.java)
        intent.action = TextToSpeechService.ACTION_PLAY_PAUSE
        application.startService(intent)
    }

    fun invokeStop() {
        val intent = Intent(application, TextToSpeechService::class.java)
        intent.action = TextToSpeechService.ACTION_STOP
        application.startService(intent)
    }

    fun invokeNext() {
        itemList?.let {
            currentItem = it[++currentItemPosition]
            speak(0, isPlaying())
        }
    }

    fun invokePrevious() {
        itemList?.let {
            currentItem = it[--currentItemPosition]
            speak(0, isPlaying())
        }
    }

    fun invokeSeekStarted() {
        if (isPlaying()) {
            invokePlayPause()
            pausedForSeek = true
        }
    }

    fun invokeSeekEnded(seekToPosition: Int) {
        invokePlay(seekToPosition, pausedForSeek)
        pausedForSeek = false
    }

    fun setTextToSpeechItems(items: List<TextToSpeechItem>, position: Int) {
        currentItemPosition = position
        itemList = items
        itemList?.let {
            currentItem = it[currentItemPosition]
        }
    }

    @JvmOverloads
    fun speak(position: Int = 0, playImmediately: Boolean = false) {
        currentItem?.let {
            postItemChanged(it)
        }
        val intent = Intent(application, TextToSpeechService::class.java)
        intent.action = TextToSpeechService.ACTION_START
        intent.putExtra(TextToSpeechService.EXTRA_POSITION, position)
        intent.putExtra(TextToSpeechService.EXTRA_PLAY_IMMEDIATELY, playImmediately)
        application.startService(intent)
    }

    fun speakCompleted() {
        if (prefs.isAudioContinuousPlay && isNextAvailable()) {
            itemList?.let {
                currentItem = it[++currentItemPosition]
            }
            speak(0, true)
        } else {
            postPlaybackStateChanged(TextToSpeechEngine.PlaybackState.COMPLETED)
        }
    }

    fun isPlaying(): Boolean {
        return textToSpeechEngine?.isPlaying == true
    }

    fun isNextAvailable() = currentItemPosition < (itemList?.size ?: 1) - 1

    fun isPreviousAvailable() = currentItemPosition > 0

    fun registerItemListener(listener: TextToSpeechItemListener) {
        itemListenersLock.lock()
        itemListeners.add(WeakReference<TextToSpeechItemListener>(listener))
        itemListenersLock.unlock()
    }

    fun unRegisterItemListener(listener: TextToSpeechItemListener) {
        itemListenersLock.lock()
        val iterator = itemListeners.iterator()

        while (iterator.hasNext()) {
            val itemListener = iterator.next().get()
            if (itemListener == null || itemListener === listener) {
                iterator.remove()
            }
        }

        itemListenersLock.unlock()
    }

    fun registerProgressListener(listener: TextToSpeechProgressListener) {
        progressListenersLock.lock()
        progressListeners.add(WeakReference<TextToSpeechProgressListener>(listener))
        progressListenersLock.unlock()
    }

    fun unRegisterProgressListener(listener: TextToSpeechProgressListener) {
        progressListenersLock.lock()
        val iterator = progressListeners.iterator()

        while (iterator.hasNext()) {
            val progressListener = iterator.next().get()
            if (progressListener == null || progressListener === listener) {
                iterator.remove()
            }
        }

        progressListenersLock.unlock()
    }

    fun postProgressChanged(progress: TextToSpeechProgress) {
        progressListeners.forEach {
            it.get()?.onProgressChanged(progress)
        }
    }

    private fun postItemChanged(item: TextToSpeechItem) {
        itemListeners.forEach {
            it.get()?.onItemChanged(item, isNextAvailable() , isPreviousAvailable())
        }
    }

    fun postPlaybackStateChanged(playbackState: TextToSpeechEngine.PlaybackState) {
        itemListeners.forEach {
            it.get()?.onPlaybackStateChanged(playbackState)
        }
    }

    fun getItem(position: Int): TextToSpeechItem? {
        return itemList?.get(position)
    }

    fun getCurrentItem(): TextToSpeechItem? {
        return currentItem
    }

    fun getCurrentProgress(): TextToSpeechProgress? {
        val progress = textToSpeechEngine?.currentProgress ?: return null
        return TextToSpeechProgress(progress.position, progress.duration)
    }

    fun getCurrentPlaybackState(): TextToSpeechEngine.PlaybackState {
        return textToSpeechEngine?.currentPlaybackState ?: TextToSpeechEngine.PlaybackState.STOPPED
    }

    fun setTextToSpeechEngine(textToSpeechEngine: TextToSpeechEngine?) {
        this.textToSpeechEngine = textToSpeechEngine
    }

    fun isPlaybackActive(): Boolean {
        return textToSpeechEngine != null
    }

    class TextToSpeechItem(override var contentItemId: Long, override var subItemId: Long, override var title: String, var subtitle: String, override val imageRenditions: String): MediaItem() {
        override val album: String?
            get() = null
        override val artist: String?
            get() = subtitle
        override val artworkUrl: String?
            get() = null
        override val downloaded: Boolean
            get() = false
        override val downloadedMediaUri: String?
            get() = null
        override val id: Long
            get() = contentItemId
        override val mediaType: Int
            get() = BasePlaylistManager.AUDIO
        override val mediaUrl: String?
            get() = null
        override val thumbnailUrl: String?
            get() = null
        override val playlistId: Long
            get() = contentItemId
        override val tertiaryId: String?
            get() = subItemId.toString()
        override val mimeType: String
            get() = MimeTypes.AUDIO_MPEG
        override val mediaDownloadUrl: String?
            get() = null
    }

    class TextToSpeechProgress(var position: Int, var duration: Int)

    interface TextToSpeechItemListener {
        fun onItemChanged(currentItem: TextToSpeechItem, hasNext: Boolean, hasPrevious: Boolean)
        fun onPlaybackStateChanged(playbackState: TextToSpeechEngine.PlaybackState)
    }

    interface TextToSpeechProgressListener {
        fun onProgressChanged(ttsProgress: TextToSpeechProgress)
    }
}
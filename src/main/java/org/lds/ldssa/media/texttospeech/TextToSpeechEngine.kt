package org.lds.ldssa.media.texttospeech

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.support.v4.media.session.MediaSessionCompat
import com.devbrackets.android.playlistcore.components.mediasession.DefaultMediaSessionProvider
import com.devbrackets.android.playlistcore.data.MediaInfo
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.media.exomedia.service.MediaImageProvider
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.gl.mediaplaybackposition.MediaPlaybackPositionManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.ui.notification.NotificationIds
import org.lds.ldssa.ui.web.ContentData
import org.lds.ldssa.util.AnalyticsUtil
import org.lds.ldssa.util.ContentRenderer
import org.lds.ldssa.util.LanguageUtil
import org.lds.ldssa.util.ScreenUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import timber.log.Timber
import java.util.HashMap
import java.util.Locale
import javax.inject.Inject

class TextToSpeechEngine @Inject constructor(
        private val application: Application,
        private val textToSpeechManager: TextToSpeechManager,
        private val contentRenderer: ContentRenderer,
        private val itemManager: ItemManager,
        private val languageUtil: LanguageUtil,
        private val textToSpeechGenerator: TextToSpeechGenerator,
        private val screenUtil: ScreenUtil,
        private val internalIntents: InternalIntents,
        private val mediaPlaybackPositionManager: MediaPlaybackPositionManager,
        private val analytics: Analytics,
        private val analyticsUtil: AnalyticsUtil,
        private val cc: CoroutineContextProvider,
        private val notificationManager: NotificationManager
        ) {

    private lateinit var textToSpeech: TextToSpeech

    var notificationProvider: TextToSpeechNotificationProvider
    var currentPlaybackState = PlaybackState.STOPPED
    var currentProgress = TextToSpeechManager.TextToSpeechProgress(0, 0)
    var isPlaying = false
        private set
    var mediaSessionCompat: MediaSessionCompat

    var notifyServiceSetupAsForeground: () -> Unit = {}
    var notifyServiceStopForeground: () -> Unit = {}
    var notifyServiceRelaxResources: () -> Unit = {}
    var currentParagraphPosition = 0
        private set

    private var paragraphList = ArrayList<TextToSpeechParagraph>()
    private var playRequested = false
    private var shutdownOccurred = false
    private var textToSpeechReady = false

    private var imageProvider: MediaImageProvider

    private val audioFocusProvider: TextToSpeechAudioFocusProvider

    init {
        Injector.get().inject(this)
        notificationProvider = TextToSpeechNotificationProvider(application, textToSpeechManager, internalIntents, screenUtil)
        mediaSessionCompat = DefaultMediaSessionProvider(application, TextToSpeechService::class.java).get()
        imageProvider = MediaImageProvider(application, object : MediaImageProvider.OnImageUpdatedListener {
            override fun onImageUpdated() {
                updateNotification()
            }
        })
        audioFocusProvider = TextToSpeechAudioFocusProvider(application)
        audioFocusProvider.setTextToSpeechEngine(this)
        start()
    }

    fun speak(paragraphPosition: Int, playImmediately: Boolean) {
        if (shutdownOccurred) {
            start()
        }
        if (isPlaying) {
            performStop()
        }
        currentParagraphPosition = paragraphPosition
        loadParagraphs()
        setTextToSpeechLanguage()
        playRequested = playImmediately
        notifyServiceSetupAsForeground()
    }

    fun performPlay(paragraphPosition: Int, playImmediately: Boolean = true) {
        currentParagraphPosition = paragraphPosition
        if (paragraphList.isEmpty()) {
            playRequested = true
        } else if (playImmediately) {
            startTextToSpeechPlayback(currentParagraphPosition)
        }

        requestAudioFocus()
        updateNotification()
    }

    fun performPlayPause() {
        if (isPlaying) {
            performStop()
            abandonAudioFocus()
        } else {
            requestAudioFocus()
            performPlay(currentParagraphPosition)
        }

        updateNotification()
    }

    fun performStop() {
        notifyServiceStopForeground()
        playbackStateChanged(PlaybackState.PAUSED)
        textToSpeech.stop()
        isPlaying = false

        updateNotification()
        saveCurrentAudioPosition()
    }

    fun performNext() {
        textToSpeechManager.invokeNext()
        updateNotification()
    }

    fun performPrevious() {
        textToSpeechManager.invokePrevious()
        updateNotification()
    }

    fun shutdown() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        playbackStateChanged(PlaybackState.STOPPED)
        saveCurrentAudioPosition()
        textToSpeechManager.setTextToSpeechEngine(null)
        paragraphList.clear()

        notifyServiceRelaxResources()

        abandonAudioFocus()

        shutdownOccurred = true
        textToSpeechReady = false
    }

    /**
     * Performs the process to update the playback controls and images in the notification
     * associated with the current playlist item.
     */
    fun updateNotification() {
        notificationManager.notify(NotificationIds.MEDIA_PLAYBACK.notificationId, notificationProvider.buildNotification(getMediaInfo(), mediaSessionCompat, TextToSpeechService::class.java))
    }

    fun getMediaInfo(): MediaInfo {
        val mediaInfo = MediaInfo()

        // Generate the notification state
        mediaInfo.mediaState.isPlaying = isPlaying
        mediaInfo.mediaState.isNextEnabled = textToSpeechManager.isNextAvailable()
        mediaInfo.mediaState.isPreviousEnabled = textToSpeechManager.isPreviousAvailable()

        // Updates the notification information
        mediaInfo.notificationId = NotificationIds.MEDIA_PLAYBACK.notificationId
        mediaInfo.playlistItem = textToSpeechManager.getCurrentItem()

        mediaInfo.appIcon = imageProvider.notificationIconRes
        mediaInfo.artwork = imageProvider.remoteViewArtwork
        mediaInfo.largeNotificationIcon = imageProvider.largeNotificationImage

        return mediaInfo
    }

    /**
     * Performs the process to update the playback controls and the background
     * (artwork) image displayed on the lock screen and other remote views.
     */
    fun updateRemoteViews() {
        val playlistItem = textToSpeechManager.getCurrentItem()
        playlistItem?.let {
            imageProvider.updateImages(playlistItem)
        }
    }

    private fun start() {
        textToSpeechManager.setTextToSpeechEngine(this)
        textToSpeech = TextToSpeech(application, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechReady = true
                setTextToSpeechLanguage()
                if (playRequested) {
                    startTextToSpeechPlayback(currentParagraphPosition)
                }
            } else {
                Timber.e("Text-to-Speech initialization failed [%d]", status)
            }
        })
        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(paragraphId: String) {
                currentParagraphPosition = findPlaybackPositionByParagraphId(paragraphId)
                playbackStateChanged(PlaybackState.PLAYING)
                progressChanged(currentParagraphPosition, determineDuration())
                updateNotification()
            }

            override fun onDone(paragraphId: String) {
                val lastParagraph = paragraphList[paragraphList.size - 1]
                if (lastParagraph.paragraphId == paragraphId) {
                    performSpeakCompleted()
                    isPlaying = false
                }
            }

            override fun onError(paragraphId: String) {
                notifyServiceStopForeground()
                abandonAudioFocus()
                Timber.e("Text-to-Speech synthesis failed")
                isPlaying = false
            }
        })
    }

    private fun startTextToSpeechPlayback(playbackPosition: Int) {
        if (paragraphList.isEmpty()) {
            return
        }

        notifyServiceSetupAsForeground()

        textToSpeech.stop()

        var startPosition = playbackPosition
        if (playbackPosition > paragraphList.size) {
            startPosition = paragraphList.size
        }
        if (playbackPosition < 0) {
            startPosition = 0
        }

        paragraphList.forEach { paragraph ->
            if (paragraph.playbackPosition >= startPosition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(paragraph.paragraphText, TextToSpeech.QUEUE_ADD, null, paragraph.paragraphId)
                } else {
                    val params = HashMap<String, String>()
                    params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, paragraph.paragraphId)
                    textToSpeech.speak(paragraph.paragraphText, TextToSpeech.QUEUE_ADD, params)
                }
                isPlaying = true
            }
        }
    }

    private fun performSpeakCompleted() {
        currentParagraphPosition = 0
        progressChanged(currentParagraphPosition, determineDuration())
        textToSpeechManager.speakCompleted()
    }

    private fun findPlaybackPositionByParagraphId(paragraphId: String): Int {
        paragraphList.forEach {
            if (it.paragraphId == paragraphId) {
                return it.playbackPosition
            }
        }
        return 0
    }

    private fun loadParagraphs() {
        playbackStateChanged(PlaybackState.PREPARING)
        textToSpeechManager.getCurrentItem()?.let {
            launch(cc.ui) {
                val contentData = run(coroutineContext + cc.commonPool) {
                    contentRenderer.generateDefaultHtmlText(it.contentItemId, it.subItemId, null, false)
                }

                generateTextToSpeak(contentData)
            }
        }
    }

    private fun generateTextToSpeak(contentData: ContentData) {
        paragraphList.clear()
        paragraphList.addAll(textToSpeechGenerator.generateTextToSpeak(contentData))

        progressChanged(currentParagraphPosition, determineDuration())

        if (textToSpeechReady && playRequested) {
            startTextToSpeechPlayback(currentParagraphPosition)
        } else {
            playbackStateChanged(PlaybackState.PAUSED)
        }
    }

    private fun setTextToSpeechLanguage() {
        val languageId = itemManager.findLanguageIdById(textToSpeechManager.getCurrentItem()?.contentItemId ?: 1)
        // Set language
        val locale = languageUtil.getLocaleByLanguageId(languageId)
        locale?.let {
            when (locale.language) {
                // The Church uses a non-standard language code for some languages. Pass on the real one instead.
                "zhs" -> textToSpeech.language = Locale.SIMPLIFIED_CHINESE
                "alb" -> textToSpeech.language = Locale("sqi")
                else -> textToSpeech.language = it
            }
        }
    }

    private fun progressChanged(progress: Int, duration: Int) {
        currentProgress = TextToSpeechManager.TextToSpeechProgress(progress, duration)
        textToSpeechManager.postProgressChanged(currentProgress)
    }

    private fun playbackStateChanged(playbackState: PlaybackState) {
        currentPlaybackState = playbackState
        textToSpeechManager.postPlaybackStateChanged(playbackState)
    }

    private fun determineDuration(): Int {
        if (paragraphList.isEmpty()) {
            return 0
        }

        return paragraphList[paragraphList.size - 1].playbackPosition
    }

    private fun requestAudioFocus(): Boolean {
        return audioFocusProvider.requestFocus()
    }

    private fun abandonAudioFocus(): Boolean {
        return audioFocusProvider.abandonFocus()
    }

    private fun saveCurrentAudioPosition() {
        val textToSpeechItem = textToSpeechManager.getCurrentItem() ?: return
        val duration = determineDuration()
        var savePosition = currentParagraphPosition

        //If we are near the end, the next playback should start from the beginning
        if (savePosition + POSITION_SAVE_THRESHOLD >= duration) {
            savePosition = 0
        }

        mediaPlaybackPositionManager.setPlaybackPosition(textToSpeechItem.contentItemId, textToSpeechItem.subItemId, ItemMediaType.TEXT_TO_SPEECH, null, savePosition)
        logTextToSpeechAnalytics(textToSpeechItem, savePosition.toLong(), duration.toLong())
    }

    private fun logTextToSpeechAnalytics(textToSpeechItem: TextToSpeechManager.TextToSpeechItem, position: Long, duration: Long) {
        val percentageViewed = position.toFloat() / duration
        val contentItemId = textToSpeechItem.contentItemId

        val attributes = HashMap<String, String>()
        attributes.put(Analytics.Attribute.CONTENT_LANGUAGE, analyticsUtil.findContentLanguageByContentItemId(contentItemId))
        attributes.put(Analytics.Attribute.URI, analyticsUtil.findSubItemUriBySubItemId(contentItemId, textToSpeechItem.subItemId))
        attributes.put(Analytics.Attribute.ITEM_URI, analyticsUtil.findItemUriByContentItemId(contentItemId))
        attributes.put(Analytics.Attribute.TITLE, textToSpeechItem.title)
        attributes.put(Analytics.Attribute.CONTENT_GROUP, analyticsUtil.findContentGroupByContentItemId(contentItemId))
        attributes.put(Analytics.Attribute.CONTENT_VERSION, analyticsUtil.findContentVersionByContentItemId(contentItemId))
        attributes.put(Analytics.Attribute.PERCENTAGE_VIEWED, percentageViewed.toString())
        analytics.postEvent(Analytics.Event.TEXT_TO_SPEECH_LISTENED, attributes)
    }

    enum class PlaybackState {
        PREPARING,
        PLAYING,
        PAUSED,
        STOPPED,
        COMPLETED,
        ERROR
    }

    companion object {
        private val POSITION_SAVE_THRESHOLD = 1
    }
}
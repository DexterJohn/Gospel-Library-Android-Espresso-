package org.lds.ldssa.media.texttospeech

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.ui.notification.NotificationIds
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextToSpeechService: Service() {

    @Inject
    lateinit var textToSpeechEngine: TextToSpeechEngine

    private var foregroundSetup: Boolean = false

    init {
        Injector.get().inject(this)
        textToSpeechEngine.notifyServiceSetupAsForeground = { setupAsForeground() }
        textToSpeechEngine.notifyServiceStopForeground = { stopForeground() }
        textToSpeechEngine.notifyServiceRelaxResources = { relaxResources() }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null || intent.action == null) {
            return Service.START_NOT_STICKY
        }
        handleAction(intent)
        return Service.START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
        onDestroy()
    }

    /**
     * Sets up the service as a Foreground service only if we aren't already registered as such
     */
    @SuppressLint("NewApi")
    fun setupForeground() {
        if (!foregroundSetup) {
            foregroundSetup = true
            startForeground(NotificationIds.MEDIA_PLAYBACK.notificationId, textToSpeechEngine.notificationProvider.buildNotification(textToSpeechEngine.getMediaInfo(), textToSpeechEngine.mediaSessionCompat, TextToSpeechService::class.java))
        }
    }

    /**
     * If the service is registered as a foreground service then it will be unregistered
     * as such without removing the notification
     */
    private fun stopForeground() {
        if (foregroundSetup) {
            foregroundSetup = false
            stopForeground(false)
        }
    }

    private fun relaxResources() {
        stopForeground(true)

        foregroundSetup = false
    }

    /**
     * Requests the service be transferred to the foreground, initializing the
     * RemoteView and Notification helpers for playback control.
     */
    private fun setupAsForeground() {
        //Starts the service as the foreground audio player
        setupForeground()

        textToSpeechEngine.updateNotification()
        textToSpeechEngine.updateRemoteViews()
    }

    private fun handleAction(intent: Intent?) {
        val action = intent?.action ?: return
        when (action) {
            ACTION_START -> textToSpeechEngine.speak(intent.getIntExtra(EXTRA_POSITION, 0), intent.getBooleanExtra(EXTRA_PLAY_IMMEDIATELY, false))
            ACTION_PLAY -> textToSpeechEngine.performPlay(intent.getIntExtra(EXTRA_POSITION, 0), intent.getBooleanExtra(EXTRA_PLAY_IMMEDIATELY, true))
            ACTION_PLAY_PAUSE -> textToSpeechEngine.performPlayPause()
            ACTION_NEXT -> textToSpeechEngine.performNext()
            ACTION_PREVIOUS -> textToSpeechEngine.performPrevious()
            ACTION_STOP -> shutdown()
        }
    }

    private fun shutdown() {
        textToSpeechEngine.performStop()
        Timber.i("TextToSpeechEngine shutting down")
        stopSelf()
    }

    companion object {
        private val PREFIX = "org.lds.ldssa."
        val ACTION_START = PREFIX + "action_start"
        val ACTION_PLAY = PREFIX + "action_play"
        val ACTION_PLAY_PAUSE = PREFIX + "action_play_pause"
        val ACTION_NEXT = PREFIX + "action_next"
        val ACTION_PREVIOUS = PREFIX + "action_previous"
        val ACTION_STOP = PREFIX + "action_stop"

        val EXTRA_POSITION = "extra_position"
        val EXTRA_PLAY_IMMEDIATELY= "play_immediately"
    }
}
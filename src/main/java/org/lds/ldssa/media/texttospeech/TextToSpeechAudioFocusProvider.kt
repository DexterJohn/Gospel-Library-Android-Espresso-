package org.lds.ldssa.media.texttospeech

import android.content.Context
import android.media.AudioManager
import org.lds.mobile.media.audiofocus.LdsAudioManager

class TextToSpeechAudioFocusProvider(context: Context) : AudioManager.OnAudioFocusChangeListener {

    private var pausedForFocusLoss = false
    private var currentAudioFocus = AUDIOFOCUS_NONE

    private var audioManager = LdsAudioManager(context)
    private var textToSpeechEngine: TextToSpeechEngine? = null

    override fun onAudioFocusChange(focusChange: Int) {
        if (currentAudioFocus == focusChange) {
            return
        }

        currentAudioFocus = focusChange

        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> onFocusGained()
            AudioManager.AUDIOFOCUS_LOSS -> onFocusLoss()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> onFocusLossTransient()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> onFocusLossTransientCanDuck()
        }
    }

    fun setTextToSpeechEngine(engine: TextToSpeechEngine) {
        textToSpeechEngine = engine
    }

    fun requestFocus(): Boolean {
        if (currentAudioFocus == AudioManager.AUDIOFOCUS_GAIN) {
            return true
        }

        val status = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN, true)
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == status
    }

    fun abandonFocus(): Boolean {
        if (currentAudioFocus == AUDIOFOCUS_NONE) {
            return true
        }

        val status = audioManager.abandonAudioFocus(this)
        if (AudioManager.AUDIOFOCUS_REQUEST_GRANTED == status) {
            currentAudioFocus = AUDIOFOCUS_NONE
        }

        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == status
    }

    private fun onFocusGained() {
        textToSpeechEngine?.let { engine ->
            if (pausedForFocusLoss && !engine.isPlaying) {
                pausedForFocusLoss = false
                engine.performPlay(engine.currentParagraphPosition)
            }
        }
    }

    private fun onFocusLoss() {
        textToSpeechEngine?.let { engine ->
            if (engine.isPlaying) {
                pausedForFocusLoss = true
                engine.performStop()
            }
        }
    }

    private fun onFocusLossTransient() {
        textToSpeechEngine?.let { engine ->
            if (engine.isPlaying) {
                pausedForFocusLoss = true
                engine.performStop()
            }
        }
    }

    private fun onFocusLossTransientCanDuck() {
        textToSpeechEngine?.let { engine ->
            if (engine.isPlaying) {
                pausedForFocusLoss = true
                engine.performStop()
            }
        }
    }

    companion object {
        const val AUDIOFOCUS_NONE = 0
    }
}
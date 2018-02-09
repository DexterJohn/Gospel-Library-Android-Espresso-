package org.lds.ldssa.util

import android.view.MenuItem
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.lds.ldssa.model.database.content.navcollection.NavCollection
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.prefs.type.AudioPlaybackVoiceType
import javax.inject.Inject

class RelatedAudioAvailableUtil @Inject constructor(
        private val prefs: Prefs,
        private val relatedAudioItemManager: RelatedAudioItemManager) {

    fun showMenuOptionIfAvailable(contentItemId: Long, navCollectionId: Long, menuItem: MenuItem) {

        launch(UI) {
            val audioDownloadAvailable = run(coroutineContext + CommonPool) {
                var audioVoice = prefs.audioVoice
                if (audioVoice == AudioPlaybackVoiceType.TEXT_TO_SPEECH) {
                    // If the user has Text-to-speech as their default, then allow downloading of the audio default voice (male)
                    audioVoice = AudioPlaybackVoiceType.MALE
                }

                if (navCollectionId != NavCollection.ROOT_NAV_COLLECTION_ID) {
                    return@run relatedAudioItemManager.getItemCountByNavCollectionIdAndVoiceId(contentItemId, navCollectionId, audioVoice.voiceId) > 0
                } else {
                    return@run relatedAudioItemManager.getItemCount(contentItemId, audioVoice.voiceId) > 0
                }
            }

            // Enables the download menu item when available
            menuItem.isVisible = audioDownloadAvailable
        }
    }
}
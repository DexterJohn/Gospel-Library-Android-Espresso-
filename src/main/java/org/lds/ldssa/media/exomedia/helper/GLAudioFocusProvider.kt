package org.lds.ldssa.media.exomedia.helper

import android.content.Context
import com.devbrackets.android.playlistcore.api.PlaylistItem
import org.lds.mobile.media.audiofocus.LdsAudioFocusProvider

class GLAudioFocusProvider<I : PlaylistItem>(context: Context) : LdsAudioFocusProvider<I>(context) {
    override val allowAudioDucking: Boolean
        get() = false // All audio will pause instead of lowering the volume when transient audio focus loss occurs
}
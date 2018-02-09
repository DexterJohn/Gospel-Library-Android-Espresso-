package org.lds.ldssa.media.exomedia.helper

import android.content.Context
import android.net.Uri
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.common.images.WebImage
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.mobile.glide.ImageRenditions
import org.lds.mobile.media.cast.CastManager
import org.lds.mobile.media.data.LdsMediaItem
import org.lds.mobile.media.player.CastApi
import javax.inject.Inject

class GLCastApi @Inject constructor(context: Context, castManager: CastManager): CastApi<MediaItem>(context, castManager) {

    override fun getMediaInfo(mediaItem: LdsMediaItem): MediaInfo {
        val mediaMetadata = MediaMetadata(getMediaMetadataType(mediaItem))

        mediaMetadata.putString(MediaMetadata.KEY_TITLE, mediaItem.title)
        if (mediaItem is MediaItem) {
            mediaMetadata.addImage(WebImage(Uri.parse(getCastRenditionUrl(mediaItem.imageRenditions))))
        }

        val mediaUrl = getMediaUrl(mediaItem)
        return MediaInfo.Builder(mediaUrl)
                .setContentType(mediaItem.mimeType)
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setMetadata(mediaMetadata)
                .build()
    }

    private fun getCastRenditionUrl(renditions: String): String? {
        return ImageRenditions(renditions).getUrl(CAST_ARTWORK_WIDTH, CAST_ARTWORK_HEIGHT)
    }

    companion object {
        const val CAST_ARTWORK_WIDTH = 800
        const val CAST_ARTWORK_HEIGHT = 800
    }
}
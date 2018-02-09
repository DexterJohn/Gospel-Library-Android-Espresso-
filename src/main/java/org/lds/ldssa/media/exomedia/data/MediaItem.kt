package org.lds.ldssa.media.exomedia.data

import org.lds.mobile.media.data.LdsMediaItem

/**
 * An abstract object to represent a media playlistItem
 */
abstract class MediaItem : LdsMediaItem() {

    abstract val playlistId: Long

    abstract val tertiaryId: String?

    abstract val imageRenditions: String

    abstract val contentItemId: Long

    abstract val subItemId: Long

    abstract val mediaDownloadUrl: String?

    override val artworkUrl: String?
        // We use getImageRenditions instead
        get() = null

    override val thumbnailUrl: String?
        // We use getImageRenditions instead
        get() = null
}
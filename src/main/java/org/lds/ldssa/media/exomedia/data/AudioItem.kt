package org.lds.ldssa.media.exomedia.data

import android.text.Html
import com.devbrackets.android.playlistcore.manager.BasePlaylistManager
import com.google.android.exoplayer2.util.MimeTypes
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.content.playlistitemquery.PlaylistItemQuery
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.util.DownloadedMediaUtil
import org.lds.ldssa.util.MimeTypeUtil
import javax.inject.Inject

class AudioItem(private val audioItem: PlaylistItemQuery) : MediaItem() {

    @Inject
    lateinit var downloadedMediaManager: DownloadedMediaManager
    @Inject
    lateinit var downloadedMediaUtil: DownloadedMediaUtil
    @Inject
    lateinit var itemManager: ItemManager

    init {
        Injector.get().inject(this)
    }

    override val id: Long
        get() = audioItem.subitemId.toLong()

    override val playlistId: Long
        get() = contentItemId

    override val mediaType: Int
        get() = BasePlaylistManager.AUDIO

    override val mediaUrl: String?
        get() = audioItem.mediaUrl

    override val downloadedMediaUri: String?
        get() = audioItem.downloadedMediaUrl

    override val title: String?
        get() = Html.fromHtml(audioItem.title).toString()

    override val album: String?
        get() = itemManager.findTitleById(contentItemId)

    override val artist: String?
        get() = null

    override val contentItemId: Long
        get() = audioItem.contentItemId

    override val subItemId: Long
        get() = audioItem.subitemId.toLong()

    override val tertiaryId: String?
        get() = audioItem.mediaUrl

    override val mediaDownloadUrl: String
        get() = audioItem.mediaUrl

    override val downloaded: Boolean
        get() {
            if (!downloadedMediaUri.isNullOrBlank()) {
                return true
            }

            val download = downloadedMediaManager.findByIds(playlistId, id, tertiaryId, ItemMediaType.AUDIO)
            if (!download?.file.isNullOrBlank()) {
                audioItem.downloadedMediaUrl = downloadedMediaUtil.getDownloadedMediaUrl(download, mediaDownloadUrl)
            }

            return !downloadedMediaUri.isNullOrBlank()
        }

    override val imageRenditions: String
        get() = audioItem.artworkRenditions

    override val mimeType: String
        get() = if (downloaded) MimeTypes.AUDIO_MPEG else MimeTypeUtil.getMimeType(mediaUrl)
}
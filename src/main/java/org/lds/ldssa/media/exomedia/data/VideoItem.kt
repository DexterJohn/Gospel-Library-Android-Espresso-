package org.lds.ldssa.media.exomedia.data

import android.text.Html
import com.devbrackets.android.playlistcore.manager.BasePlaylistManager
import com.google.android.exoplayer2.util.MimeTypes
import org.apache.commons.lang3.math.NumberUtils
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.model.webview.content.dto.DtoInlineVideo
import org.lds.ldssa.util.DownloadedMediaUtil
import org.lds.ldssa.util.MimeTypeUtil
import org.lds.ldssa.util.VideoUtil
import javax.inject.Inject

class VideoItem(val video: DtoInlineVideo, override var imageRenditions: String = "", override val contentItemId: Long, private val navItemId: Long): MediaItem() {

    @Inject
    lateinit var videoUtil: VideoUtil
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
        get() = navItemId

    override val playlistId: Long
        get() = contentItemId

    override val mediaType: Int
        get() = BasePlaylistManager.VIDEO

    override val title: String?
        get() = if (video.title != null) Html.fromHtml(video.title).toString() else ""

    override val album: String?
        get() = itemManager.findTitleById(contentItemId)

    override val artist: String?
        get() = null

    override val subItemId: Long
        get() = navItemId

    override val tertiaryId: String?
        get() = video.videoId

    override val downloaded: Boolean
        get() {
            if (!downloadedMediaUri.isNullOrBlank()) {
                return true
            }

            val download = downloadedMediaManager.findByIds(playlistId, id, tertiaryId, ItemMediaType.VIDEO)
            if (!download?.file.isNullOrBlank()) {
                downloadedMediaUri = downloadedMediaUtil.getDownloadedMediaUrl(download, mediaDownloadUrl)
            }

            return !downloadedMediaUri.isNullOrBlank()
        }

    override var downloadedMediaUri: String? = null

    override val mediaDownloadUrl: String?
        get() = videoUtil.getVideoDownloadUrl(video)

    override val mediaUrl: String?
        get() {
            if (selectedQualityIndex == -1) {
                selectedQualityIndex = determineSelectedQualityIndex()
            }

            if (video.sources == null || video.sources.isEmpty()) {
                return null
            }

            return video.sources[selectedQualityIndex].url
        }

    override val mimeType: String
        get() = if (downloaded) MimeTypes.VIDEO_MP4 else MimeTypeUtil.getMimeType(mediaUrl)

    var selectedQualityIndex = -1 // TODO setting this value doesn't seem to have any effect (quality did not change before conversion either)

    private fun determineSelectedQualityIndex(): Int {
        var qualityIndex = 0

        //If the device doesn't support HLS find the first non-HLS video url that isn't HD
        // since the Android VideoView doesn't always work with HD content
        if (!videoUtil.deviceSupportsHLS()) {
            while (qualityIndex < video.sources.size) {
                val source = video.sources[qualityIndex]

                if (NumberUtils.toInt(source.width, 0) < MEDIA_HD && !videoUtil.isSourceHLS(source)) {
                    break
                }
                qualityIndex++
            }
        }

        return qualityIndex
    }

    companion object {
        const val MEDIA_HD = 720 //aka 720p
    }
}
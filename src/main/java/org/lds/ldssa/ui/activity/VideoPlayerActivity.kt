package org.lds.ldssa.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.devbrackets.android.exomedia.listener.VideoControlsVisibilityListener
import com.devbrackets.android.playlistcore.data.PlaybackState
import com.devbrackets.android.playlistcore.data.PlaybackState.PREPARING
import com.devbrackets.android.playlistcore.data.PlaybackState.STOPPED
import com.devbrackets.android.playlistcore.listener.PlaylistListener
import kotlinx.android.synthetic.main.activity_video_player.*
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Boolean
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import me.eugeniomarletti.extras.intent.base.Serializable
import me.eugeniomarletti.extras.intent.base.String
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.download.GLDownloadManager
import org.lds.ldssa.event.download.DownloadCompletedEvent
import org.lds.ldssa.glide.GlideApp
import org.lds.ldssa.glide.GlideRequests
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.ldssa.media.exomedia.data.VideoItem
import org.lds.ldssa.media.exomedia.helper.GLVideoApi
import org.lds.ldssa.media.exomedia.manager.PlaylistManager
import org.lds.ldssa.media.exomedia.service.MediaService
import org.lds.ldssa.model.database.content.navitem.NavItemManager
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager
import org.lds.ldssa.model.database.gl.mediaplaybackposition.MediaPlaybackPositionManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.util.GLFileUtil
import org.lds.ldssa.util.ShareUtil
import org.lds.ldssa.util.VideoUtil
import org.lds.ldssa.ux.content.item.ContentRequestCode
import org.lds.mobile.glide.ImageRenditions
import org.lds.mobile.media.cast.CastManager
import org.lds.mobile.media.cast.LdsCastSessionListener
import org.lds.mobile.media.ui.LdsVideoControls
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.util.LdsDrawableUtil
import pocketbus.Subscribe
import pocketbus.ThreadMode
import timber.log.Timber
import java.util.HashMap
import java.util.HashSet
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class VideoPlayerActivity : BaseActivity(), PlaylistListener<MediaItem> {

    @Inject
    lateinit var castManager: CastManager
    @Inject
    lateinit var shareUtil: ShareUtil
    @Inject
    lateinit var videoUtil: VideoUtil
    @Inject
    lateinit var downloadManager: GLDownloadManager
    @Inject
    lateinit var navItemManager: NavItemManager
    @Inject
    lateinit var fileUtil: GLFileUtil
    @Inject
    lateinit var downloadedMediaManager: DownloadedMediaManager
    @Inject
    lateinit var mediaPlaybackPositionManager: MediaPlaybackPositionManager
    @Inject
    lateinit var playListManager: PlaylistManager

    // Extras
    private var contentItemId: Long = 0
    private var subItemId: Long = 0
    private lateinit var videoId: String
    private lateinit var referrer: Analytics.Referrer

    // SaveState
    private var isFullscreen = AtomicBoolean(false)

    private var selectedVideoQualityIndex = 0

    private lateinit var videoApi: GLVideoApi
    private lateinit var castSessionManagerListener: CastSessionManagerListener
    private lateinit var glideRequests: GlideRequests

    private var pausedInOnPause = false
    private var fullscreenRequested = false
    private var optionsMenuShowing = false
    private val fullscreenMenuHandler = Handler()
    private var startViewingTimeInMs: Long = 0 // Used to track how long the user viewed the content for analytics
    private val controlsVisibilityListener = ControlsVisibilityListener()

    public override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.VIDEO_VIEW
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_video_player
    }

    public override fun onStart() {
        super.onStart()
        startViewingTimeInMs = System.currentTimeMillis()
        playListManager.registerPlaylistListener(this)

        playListManager.addVideoApi(videoApi)

        if (pausedInOnPause && !videoView.isPlaying) {
            videoView.start()
        }

        castManager.registerCastSessionListener(castSessionManagerListener)

        // Acquire WakeLock
        if (castManager.isDisconnected) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun onStop() {
        playListManager.removeVideoApi(videoApi)
        playListManager.unRegisterPlaylistListener(this)
        saveCurrentPosition()

        pausedInOnPause = videoView.isPlaying

        if (videoView.isPlaying) {
            videoView.pause()
        }

        castManager.unregisterCastSessionListener(castSessionManagerListener)

        // Remove WakeLock
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        logAnalytics()
        playListManager.unRegisterPlaylistListener(this)

        fullscreenMenuHandler.removeCallbacksAndMessages(null)

        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        with(IntentOptions) {
            contentItemId = intent.contentItemId
            subItemId = intent.subItemId
            videoId = intent.videoId
            referrer = intent.analyticsReferrer
        }

        videoView.setHandleAudioFocus(false)
        videoApi = GLVideoApi(videoView)
        videoApi.onCompletionListener = { finish() }
        videoApi.onErrorListener = { onMediaError() }

        savedInstanceState?.let {
            restoreState(it)
            referrer = Analytics.Referrer.RESTORE_STATE
        }

        castSessionManagerListener = CastSessionManagerListener()

        glideRequests = GlideApp.with(this)

        setupDownloadedVideo()
        configureVideoView()

        startPlayback()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveState(outState)
    }

    override fun onPlaybackStateChanged(playbackState: PlaybackState): Boolean {
        when (playbackState) {
            PREPARING -> videoView.previewImageView.visibility = View.GONE
            STOPPED -> finish()
            else -> {}
        }

        return false
    }

    override fun onPlaylistItemChanged(currentItem: MediaItem?, hasNext: Boolean, hasPrevious: Boolean): Boolean {
        if (currentItem == null) {
            return false
        }

        title = currentItem.title
        glideRequests.load(ImageRenditions(currentItem.imageRenditions))
                .fitCenter()
                .into(videoView.previewImageView)

        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        exitFullscreenMode()

        // If we aren't casting then stop the service
        if (castManager.isDisconnected) {
            playListManager.invokeStop()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_video, menu)
        LdsDrawableUtil.tintAllMenuIconsForTheme(this, menu, R.attr.themeStyleColorToolbarIcon)
        createVideoQualityMenu(menu.findItem(R.id.menu_item_video_quality))
        castManager.setupCastButton(this, menu, R.id.menu_item_cast)

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val videoPlaylistItem = getVideoItem()
        val isDownloaded = videoPlaylistItem?.downloadedMediaUri != null

        if (isDownloaded) {
            menu.findItem(R.id.menu_item_download).isVisible = false
        }

        // If there aren't any additional video qualities, or it is downloaded then don't show the option to select video quality
        if (isDownloaded || !hasMultipleSources(videoPlaylistItem)) {
            menu.findItem(R.id.menu_item_video_quality).isVisible = false
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPanelClosed(featureId: Int, menu: Menu) {
        super.onPanelClosed(featureId, menu)

        if (optionsMenuShowing) {
            optionsMenuShowing = false
            if (!fullscreenRequested) {
                return
            }

            fullscreenMenuHandler.postDelayed({
                fullscreenRequested = false
                enterFullscreenMode()
            }, HIDE_CONTROLS_DELAY)
        }
    }

    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
        optionsMenuShowing = true
        fullscreenMenuHandler.removeCallbacksAndMessages(null)

        return super.onMenuOpened(featureId, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                castManager.detach()
                finish()
                return true
            }
            R.id.menu_item_share -> {
                showShareDialog()
                return true
            }
            R.id.menu_item_download -> {
                val videoPlaylistItem = getVideoItem()

                videoPlaylistItem?.let { videoItem ->
                    val title = videoItem.title ?: ""
                    val tertiaryId = videoItem.tertiaryId ?: ""
                    val downloadUrl = videoItem.mediaDownloadUrl ?: ""

                    when {
                        downloadUrl.isNullOrBlank() -> Timber.w("Media download url is missing")
                        tertiaryId.isNullOrBlank() -> Timber.w("Media download tertiaryId is missing")
                        else -> downloadManager.downloadMedia(contentItemId, subItemId, tertiaryId, title, downloadUrl, ItemMediaType.VIDEO)
                    }
                }
                return true
            }
            R.id.menu_item_video_quality -> {
                updateVideoQualityMenu(item)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        exitFullscreenMode()
        return super.dispatchTouchEvent(ev)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultIntent)

        if (requestCode != ContentRequestCode.REQUEST_APPLICATION_SHARE.ordinal || resultCode != Activity.RESULT_OK) {
            return
        }

        if (resultIntent != null) {
            shareUtil.processShareRequest(this, resultIntent)
        }
    }

    private fun onMediaError() {
        // Show the error message
        videoView.setControls(null)
        errorView.visibility = View.VISIBLE
    }

    private fun restoreState(bundle: Bundle) {
        with(SaveStateOptions) {
            isFullscreen.set(bundle.isFullscreen ?: false)
        }
    }

    private fun saveState(bundle: Bundle) {
        with(SaveStateOptions) {
            bundle.isFullscreen = isFullscreen.get()
        }
    }

    @Subscribe(ThreadMode.MAIN)
    fun handle(event: DownloadCompletedEvent) {
        if (event.contentItemId == contentItemId && event.subItemId == subItemId && event.isSuccessful) {
            setupDownloadedVideo()
            supportInvalidateOptionsMenu()
        }
    }

    private fun enterFullscreenMode() {
        if (!isFullscreen.compareAndSet(false, true)) {
            return
        }

        // Handle System UI and Actionbar
        var flags = View.SYSTEM_UI_FLAG_LOW_PROFILE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            flags = flags or (View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }
        window.decorView.systemUiVisibility = flags

        setToolbarVisible(false)
    }

    private fun exitFullscreenMode() {
        if (!isFullscreen.compareAndSet(true, false)) {
            return
        }

        // Handle System UI and Actionbar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        setToolbarVisible(true)

        // Handle other components
        val videoControls = videoView.videoControls
        videoControls?.show()
    }

    private fun startPlayback() {
        // Determines the playback position, resuming by default
        getVideoItem()?.let { videoItem ->
            var playbackPosition = mediaPlaybackPositionManager.getPlaybackPosition(contentItemId, subItemId, ItemMediaType.VIDEO, videoItem.video.videoId)
            if (playbackPosition <= MediaService.POSITION_SAVE_THRESHOLD) {
                playbackPosition = 0
            }

            playListManager.play(playbackPosition.toLong(), false)
        }
    }

    private fun setupDownloadedVideo() {
        val downloadedMedia = downloadedMediaManager.findByIds(contentItemId, subItemId, videoId, ItemMediaType.VIDEO)
        var downloadUrl: String? = null
        if (downloadedMedia != null) {
            downloadedMedia.file?.let { downloadedFilename ->
                val downloadedFile = fileUtil.getContentMediaFile(downloadedFilename, ItemMediaType.VIDEO)
                if (downloadedFile.exists()) {
                    downloadUrl = downloadedFile.absolutePath
                }
            }
        }

        Timber.i("VideoPlayer using downloadUrl: [%s]", downloadUrl)
        val currentItem = getVideoItem()
        currentItem?.downloadedMediaUri = downloadUrl
    }

    private fun configureVideoView() {
        val videoControls = LdsVideoControls(this, castManager)
        videoControls.setNextButtonRemoved(true)
        videoControls.setPreviousButtonRemoved(true)
        videoControls.setVisibilityListener(controlsVisibilityListener)
        videoView.setControls(videoControls)
    }

    private fun showShareDialog() {
        val videoPlaylistItem = getVideoItem()

        val navItem = navItemManager.findById(contentItemId, subItemId)

        if (navItem != null) {
            val videoUrl = if (videoPlaylistItem != null) videoUtil.getVideoDownloadUrl(videoPlaylistItem.video) ?: "" else ""
            val videoTitle = navItem.titleHtml
            val description = navItem.preview
            shareUtil.onShowShareDialogForVideo(this, getScreenId(), contentItemId, subItemId, videoUrl, videoTitle, description)
        }
    }

    private fun hasMultipleSources(videoPlaylistItem: VideoItem?): Boolean {
        if (videoPlaylistItem == null) {
            return false
        }

        val sources = videoPlaylistItem.video.sources
        return sources.size > 1
    }

    /**
     * Creates the video quality selection SubMenu

     * @param item The MenuItem to create the video quality SubMenu for
     */
    private fun createVideoQualityMenu(item: MenuItem) {
        val subMenu = item.subMenu

        val videoQualityClickListener = VideoQualityMenuClickListener()
        val usedQualities = HashSet<Int>()

        // Adds all the video qualities to the menu
        val videoPlaylistItem = getVideoItem()

        if (videoPlaylistItem != null) {
            for (i in 0..videoPlaylistItem.video.sources.size - 1) {
                val source = videoPlaylistItem.video.sources[i]

                var title: String? = null
                val sourceHeight = NumberUtils.toInt(source.height, 0)

                // Makes sure we don't list any duplicates
                if (usedQualities.contains(sourceHeight) || !videoUtil.deviceSupportsHLS() && videoUtil.isSourceHLS(source)) {
                    continue
                }

                // Determines the menu item title (e.g. "720p HD")
                if (videoUtil.isSourceHLS(source)) {
                    title = getString(R.string.auto)
                } else if (sourceHeight != 0) {
                    val templateResId = if (sourceHeight >= VideoItem.MEDIA_HD) R.string.video_quality_hd_template else R.string.video_quality_template
                    title = getString(templateResId, sourceHeight)
                }

                // If we have a title then add it to the subMenu
                if (title != null) {
                    val subItem = subMenu.add(VIDEO_QUALITY_GROUP_ID, i, usedQualities.size, title)
                    subItem.setOnMenuItemClickListener(videoQualityClickListener)
                    usedQualities.add(sourceHeight)
                }
            }
        }

        // This needs to be done after the items are added, otherwise the radio buttons won't show up
        subMenu.setGroupCheckable(VIDEO_QUALITY_GROUP_ID, true, true)
    }

    /**
     * Makes sure the video quality SubMenu items have the correct
     * states (checked)

     * @param item The MenuItem that contains the video quality SubMenu
     */
    private fun updateVideoQualityMenu(item: MenuItem) {
        val subMenu = item.subMenu ?: return

        val subItem = subMenu.getItem(selectedVideoQualityIndex)
        subItem.isChecked = true
    }

    private fun getVideoItem(): VideoItem? {
        return when (playListManager.currentItem) {
            is VideoItem -> playListManager.currentItem as VideoItem
            else -> null
        }
    }

    private fun saveCurrentPosition() {
        val videoItem = getVideoItem() ?: return

        var position = videoView.currentPosition

        // If we are near the end of the video, the next playback should start from the beginning
        if (position + MediaService.POSITION_SAVE_THRESHOLD >= videoView.duration) {
            position = 0
        }

        mediaPlaybackPositionManager.setPlaybackPosition(contentItemId, subItemId, ItemMediaType.VIDEO, videoItem.video.videoId, position.toInt())
    }

    private fun setSource(url: String) {
        videoView.reset()
        videoView.setVideoURI(Uri.parse(url))
        errorView.visibility = View.GONE

        val videoPlaylistItem = getVideoItem() ?: return

        // Determines the playback position, resuming by default
        val playbackPosition = mediaPlaybackPositionManager.getPlaybackPosition(contentItemId, subItemId, ItemMediaType.VIDEO, videoPlaylistItem.video.videoId)
        if (playbackPosition > MediaService.POSITION_SAVE_THRESHOLD) {
            videoView.seekTo(playbackPosition.toLong())
        }
    }

    private fun logAnalytics() {
        val videoPlaylistItem = getVideoItem() ?: return

        var uri = videoPlaylistItem.downloadedMediaUri
        if (StringUtils.isEmpty(uri)) {
            uri = videoPlaylistItem.mediaUrl
        }

        val percentageViewed = videoView.currentPosition.toFloat() / videoView.duration
        val sourceType = if (StringUtils.isNotBlank(videoPlaylistItem.downloadedMediaUri)) Analytics.Value.LOCAL else Analytics.Value.REMOTE

        val attributes = HashMap<String, String>()
        attributes.put(Analytics.Attribute.CONTENT_LANGUAGE, analyticsUtil.findContentLanguageByLanguageId(getLanguageId()))
        attributes.put(Analytics.Attribute.URI, analyticsUtil.findSubItemUriBySubItemId(contentItemId, subItemId))
        attributes.put(Analytics.Attribute.ITEM_URI, analyticsUtil.findItemUriByContentItemId(contentItemId))
        attributes.put(Analytics.Attribute.CONTENT_URI, uri ?: "")
        attributes.put(Analytics.Attribute.TITLE, videoPlaylistItem.title ?: "")
        attributes.put(Analytics.Attribute.CONTENT_TYPE, Analytics.Value.VIDEO)
        attributes.put(Analytics.Attribute.CONTENT_GROUP, analyticsUtil.findContentGroupByContentItemId(contentItemId))
        attributes.put(Analytics.Attribute.CONTENT_VERSION, analyticsUtil.findContentVersionByContentItemId(contentItemId))
        attributes.put(Analytics.Attribute.PERCENTAGE_VIEWED, percentageViewed.toString())
        attributes.put(Analytics.Attribute.SECONDS_VIEWED, Analytics.Value.millisToSeconds(System.currentTimeMillis() - startViewingTimeInMs))
        attributes.put(Analytics.Attribute.REFERRER, referrer.value)
        attributes.put(Analytics.Attribute.SOURCE_TYPE, sourceType)
        analytics.postEvent(Analytics.Event.CONTENT_VIEWED, attributes)
    }

    private fun setToolbarVisible(visible: Boolean) {
        getMainToolbar()?.setVisible(visible)
    }

    private inner class CastSessionManagerListener : LdsCastSessionListener() {
        override fun onCastDeviceConnected(resumed: Boolean) {
            exitFullscreenMode()
            supportInvalidateOptionsMenu()

            // Remove WakeLock
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        override fun onCastDeviceDisconnected() {
            enterFullscreenMode()
            supportInvalidateOptionsMenu()

            // Acquire WakeLock
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    private inner class VideoQualityMenuClickListener : MenuItem.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem): Boolean {
            if (item.groupId == VIDEO_QUALITY_GROUP_ID) {
                val videoPlaylistItem = getVideoItem() ?: return false

                mediaPlaybackPositionManager.setPlaybackPosition(contentItemId, subItemId, ItemMediaType.VIDEO, videoPlaylistItem.video.videoId, videoView.currentPosition.toInt())
                videoPlaylistItem.selectedQualityIndex = item.itemId
                setSource(videoPlaylistItem.mediaUrl ?: "")
                selectedVideoQualityIndex = item.order
                return true
            }

            return false
        }
    }

    private inner class ControlsVisibilityListener : VideoControlsVisibilityListener {
        override fun onControlsShown() {
            fullscreenRequested = false
        }

        override fun onControlsHidden() {
            // If the user is interacting (menus) wait until they are finished to enter fullscreen
            if (optionsMenuShowing) {
                fullscreenRequested = true
            } else {
                enterFullscreenMode()
            }
        }

    }

    companion object {
        private val VIDEO_QUALITY_GROUP_ID = 20
        private val HIDE_CONTROLS_DELAY: Long = 100
    }

    object IntentOptions {
        var Intent.contentItemId by IntentExtra.Long(defaultValue = 0L)
        var Intent.subItemId by IntentExtra.Long(defaultValue = 0L)
        var Intent.videoId by IntentExtra.String(defaultValue = "")
        var Intent.analyticsReferrer by IntentExtra.Serializable(defaultValue = Analytics.Referrer.UNKNOWN)
    }

    object SaveStateOptions {
        var Bundle.isFullscreen by BundleExtra.Boolean()
    }
}

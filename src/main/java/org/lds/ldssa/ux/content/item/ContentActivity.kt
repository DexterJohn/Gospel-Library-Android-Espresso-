package org.lds.ldssa.ux.content.item

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.devbrackets.android.playlistcore.data.PlaybackState
import com.devbrackets.android.playlistcore.listener.PlaylistListener
import com.devbrackets.android.playlistcore.manager.BasePlaylistManager
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.coroutines.experimental.launch
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Boolean
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Boolean
import me.eugeniomarletti.extras.intent.base.Int
import me.eugeniomarletti.extras.intent.base.Long
import me.eugeniomarletti.extras.intent.base.Serializable
import me.eugeniomarletti.extras.intent.base.String
import me.eugeniomarletti.extras.intent.base.StringArray
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.download.GLDownloadManager
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.media.MiniPlaybackControlsManager
import org.lds.ldssa.media.exomedia.AudioPlaybackControlsManager
import org.lds.ldssa.media.exomedia.data.AudioItem
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.ldssa.media.exomedia.manager.PlaylistManager
import org.lds.ldssa.media.texttospeech.TextToSpeechControlsManager
import org.lds.ldssa.media.texttospeech.TextToSpeechEngine
import org.lds.ldssa.media.texttospeech.TextToSpeechManager
import org.lds.ldssa.model.GLContentContext
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager
import org.lds.ldssa.model.database.content.availablerelatedtypequery.AvailableRelatedTypeQueryManager
import org.lds.ldssa.model.database.content.playlistitemquery.PlaylistItemQueryManager
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemManager
import org.lds.ldssa.model.database.content.subitem.SubItemManager
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager
import org.lds.ldssa.model.database.gl.history.HistoryManager
import org.lds.ldssa.model.database.gl.mediaplaybackposition.MediaPlaybackPositionManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.types.SnackbarAction
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasContent
import org.lds.ldssa.model.prefs.type.AudioPlaybackVoiceType
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ux.annotations.links.LinksActivity
import org.lds.ldssa.ui.activity.NoteActivity
import org.lds.ldssa.ui.sidebar.SideBar
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.ldssa.util.ScreenUtil
import org.lds.ldssa.util.ShareUtil
import org.lds.ldssa.ux.annotations.notebookselection.NotebookSelectionActivity
import org.lds.ldssa.ux.annotations.tagselection.TagSelectionActivity
import org.lds.mobile.ui.setVisible
import org.lds.mobile.util.LdsNetworkUtil
import timber.log.Timber
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject


class ContentActivity : BaseActivity(),
        ViewPager.OnPageChangeListener,
        ScreenLauncherUtil.ScreenActivity,
        SideBar.ContentDrawerListener,
        SideBar.ScrollPositionRequestListener,
        PlaylistListener<MediaItem>,
        TextToSpeechManager.TextToSpeechItemListener {

    @Inject
    lateinit var shareUtil: ShareUtil
    @Inject
    lateinit var trailQueryManager: NavigationTrailQueryManager
    @Inject
    lateinit var subItemManager: SubItemManager
    @Inject
    lateinit var historyManager: HistoryManager
    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var playlistManager: PlaylistManager
    @Inject
    lateinit var itemManager: ItemManager
    @Inject
    lateinit var mediaPlaybackPositionManager: MediaPlaybackPositionManager
    @Inject
    lateinit var playlistItemQueryManager: PlaylistItemQueryManager
    @Inject
    lateinit var glDownloadManager: GLDownloadManager
    @Inject
    lateinit var textToSpeechManager: TextToSpeechManager
    @Inject
    lateinit var availableRelatedTypeManager: AvailableRelatedTypeQueryManager
    @Inject
    lateinit var downloadedMediaManager: DownloadedMediaManager
    @Inject
    lateinit var networkUtil: LdsNetworkUtil
    @Inject
    lateinit var relatedAudioItemManager: RelatedAudioItemManager

    // ---- Extras ----
    private var contentItemId = 0L
    private var subItemId = 0L
    private var scrollPosition = 0
    private lateinit var scrollToParagraphAid: String
    private var markParagraphAids: Array<String?> = emptyArray()
    private lateinit var findOnPageText: String
    private lateinit var markTextSqliteOffsets: String // search results (from fts sqlite offsets)
    private var markTextSqliteExactPhrase: Boolean = false // search results (from fts sqlite offsets)
    var analyticsReferrer = Analytics.Referrer.UNKNOWN

    // ---- SaveState ----
    private var isFullscreen = AtomicBoolean(false)

    private lateinit var contentPagerAdapter: ContentPagerAdapter
    private var subItemUri = ""
    private lateinit var audioPlaybackControlsManager: AudioPlaybackControlsManager
    private lateinit var textToSpeechControlsManager: TextToSpeechControlsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.get().inject(this)
        super.onCreate(savedInstanceState)
        with(IntentOptions) {
            contentItemId = intent.contentItemId
            subItemId = intent.subItemId
            scrollPosition = intent.scrollPosition
            scrollToParagraphAid = intent.scrollToParagraphAid
            markParagraphAids = intent.markParagraphAids
            findOnPageText = intent.findOnPageText
            markTextSqliteOffsets = intent.markTextSqliteOffsets
            markTextSqliteExactPhrase = intent.markTextSqliteExactPhrase
            analyticsReferrer = intent.analyticsReferrer
        }

        savedInstanceState?.let { restoreState(it) }

        // To support tabs/screens.... be sure that there is a subItemUri
        if (subItemUri.isEmpty()) {
            subItemUri = subItemManager.findUriById(contentItemId, subItemId)
            if (subItemUri.isEmpty()) {
                Timber.e("Cannot find subItemUri for contentItemId: [%d], subItemId: [%d]", contentItemId, subItemId)
            }
        }

        screenUtil.updateLanguage(getScreenId(), itemManager.findLanguageIdById(contentItemId))

        //Pushes the content fragment
        setupPager()
        setupContentDrawer()

        if (savedInstanceState != null) {
            if (isFullscreen.get()) {
                enterFullscreenMode()
            }

            analyticsReferrer = Analytics.Referrer.RESTORE_STATE
        }

        title = subItemManager.findTitleById(contentItemId, subItemId)
        updateNavigationTrail(trailQueryManager.findAllForContent(contentItemId, subItemId))

        setupMediaPlaybackControls()

        contentSideDrawer.onContentChanged(getScreenId(), contentItemId, subItemId)

        mediaFab.setOnClickListener {
            playAudio()
        }
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)

        // restore listeners
        if (fragment is ContentItemFragment) {
            fragment.onStickyTappedListener = { annotationId ->
                onStickyTapped(annotationId)
            }
            fragment.toggleFullScreenModeListener = {
                launch(cc.ui) {
                    if (isFullscreen.get()) {
                        exitFullscreenMode()
                    } else {
                        enterFullscreenMode()
                    }
                }
            }
            fragment.onRefTappedListener = { title, contentItemId, subItemId, refId ->
                launch(cc.ui) {
                    contentSideDrawer.showRelatedContentItem(contentItemId, subItemId, refId, title)
                }
            }
            fragment.onLinkTappedListener = { title, url, newScreen ->
                if (newScreen) {
                    internalIntents.showUriActivity(this@ContentActivity, ScreenUtil.NEW_SCREEN_ID, url, false, true, false, Analytics.Referrer.CONTENT_INLINE_LINK)
                } else {
                    runOnUiThread { contentSideDrawer.showUri(title, url) }
                }
            }
            fragment.onContentSelectionStartListener = {
                contentViewPager.isPagingEnabled = false
            }
            fragment.onContentSelectionEndListener = {
                contentViewPager.isPagingEnabled = true
            }
            fragment.showHideMediaFabListener = { show ->
                showHideMediaFab(show)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Menu items that MUST be handled by this activity (no sense passing it to the Fragment)
        when (item.itemId) {
            R.id.menu_item_related_content -> {
                contentSideDrawer.clearHistory()
                contentSideDrawer.showRelatedContent(contentItemId, subItemId)
                return true
            }
            R.id.menu_item_play_audio -> {
                playAudio()
                return true
            }
        }

        // Activity.onOptionsItemSelected() is called before Fragment.onOptionsItemSelected()... allow ContentItemFragment handle this (especially for bookmarks)
        return false
    }

    override fun onStart() {
        super.onStart()
        playlistManager.registerPlaylistListener(this)
        textToSpeechManager.registerItemListener(this)
        addDisposable(annotationManager.tableChanges().subscribe { _ -> showSignInMessage() })
    }

    override fun onStop() {
        super.onStop()
        playlistManager.unRegisterPlaylistListener(this)
        textToSpeechManager.registerItemListener(this)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Because the drawer layout saves lock modes in the savedInstanceState, make sure the drawer is in the right mode.
        if (contentSideDrawer.isPinned && prefs.isSidebarOpened) {
            contentDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
            contentDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    private fun showSignInMessage() {
        //If the user is not signed in inform them that they will loose annotations on the next update
        if (accountUtil.shouldShowSignInMessage()) {
            accountUtil.showSignInMessage(this@ContentActivity)
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_content
    }

    override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.CONTENT_VIEW
    }

    override fun getUri(): String? {
        return subItemManager.findUriById(contentItemId, subItemId)
    }

    override fun getGlContentContext(): GLContentContext {
        return GLContentContext(0L, contentItemId, 0L, subItemId)
    }

    override fun onPlaybackStateChanged(playbackState: PlaybackState): Boolean {
        return false
    }

    /*
     * Used to update the current content web page to follow along with the audio.
     */
    override fun onPlaylistItemChanged(currentItem: MediaItem?, hasNext: Boolean, hasPrevious: Boolean): Boolean {
        if (currentItem == null || !contentViewPager.isPagingEnabled || contentItemId != currentItem.contentItemId) {
            return false
        }

        if (miniPlaybackControls.playbackManager.getPlaybackType() != MiniPlaybackControlsManager.PlaybackType.EXOMEDIA) {
            miniPlaybackControls.playbackManager = audioPlaybackControlsManager
        }

        // We only update the pager position if the audio moves to the next page
        val currentPosition = contentPagerAdapter.getPositionBySubItemId(subItemId)
        val newPosition = contentPagerAdapter.getPositionBySubItemId(currentItem.subItemId)
        if (Math.abs(newPosition - currentPosition) == 1) {
            contentViewPager.setCurrentItem(newPosition, true)
            analyticsReferrer = Analytics.Referrer.AUDIO_AUTO_ADVANCE
        }

        return false
    }

    override fun onItemChanged(currentItem: TextToSpeechManager.TextToSpeechItem, hasNext: Boolean, hasPrevious: Boolean) {
        if (!contentViewPager.isPagingEnabled || contentItemId != currentItem.contentItemId) {
            return
        }

        if (miniPlaybackControls.playbackManager.getPlaybackType() != MiniPlaybackControlsManager.PlaybackType.TEXT_TO_SPEECH) {
            miniPlaybackControls.playbackManager = textToSpeechControlsManager
        }

        // We only update the pager position if the audio moves to the next page
        val currentPosition = contentPagerAdapter.getPositionBySubItemId(subItemId)
        val newPosition = contentPagerAdapter.getPositionBySubItemId(currentItem.subItemId)
        if (Math.abs(newPosition - currentPosition) == 1) {
            runOnUiThread {
                contentViewPager.setCurrentItem(newPosition, true)
                analyticsReferrer = Analytics.Referrer.AUDIO_AUTO_ADVANCE
            }
        }
    }

    override fun onPlaybackStateChanged(playbackState: TextToSpeechEngine.PlaybackState) {
        // Nothing to do
    }

    /**
     * Performs the initial setup for the content pager, including setting the
     * adapter, and a listener for page changes
     */
    private fun setupPager() {
        // adapter
        contentPagerAdapter = ContentPagerAdapter()

        // pager
        contentViewPager.setAdapter(contentPagerAdapter)
        contentViewPager.currentItem = subItemManager.findPositionById(contentItemId, subItemId)
        contentViewPager.addOnPageChangeListener(this)
    }

    private fun setupMediaPlaybackControls() {
        audioPlaybackControlsManager = AudioPlaybackControlsManager(this)
        textToSpeechControlsManager = TextToSpeechControlsManager(this)

        setPlaybackControlsManager()
    }

    private fun setPlaybackControlsManager() {
        if (textToSpeechManager.isPlaybackActive()) {
            miniPlaybackControls.playbackManager = textToSpeechControlsManager
        } else {
            miniPlaybackControls.playbackManager = audioPlaybackControlsManager
        }
    }

    private fun setupContentDrawer() {
        contentDrawerLayout.setScrimColor(ContextCompat.getColor(this, R.color.drawer_scrim))
        contentSideDrawer.setContentDrawerListener(this)
        contentSideDrawer.setScrollPositionRequestListener(this)

        if (contentSideDrawer.isPinned && contentSideDrawer.canShowPinned() && prefs.isSidebarOpened) {
            pinSidebar()
        } else {
            unpinSidebar(false)
        }
    }

    private fun pinSidebar() {
        relatedContentDrawerContainer.removeAllViews()
        relatedContentPinnedContainer.removeAllViews()
        relatedContentPinnedContainer.addView(contentSideDrawer)
        relatedContentPinnedContainer.visibility = View.VISIBLE
        contentDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            sideBarLegacySeparator.visibility = View.VISIBLE
        }

        prefs.isSidebarOpened = true
        contentSideDrawer.updatePinMenuOption()
    }

    private fun unpinSidebar(openDrawer: Boolean) {
        relatedContentDrawerContainer.removeAllViews()
        relatedContentPinnedContainer.removeAllViews()
        relatedContentDrawerContainer.addView(contentSideDrawer)
        relatedContentPinnedContainer.visibility = View.GONE
        sideBarLegacySeparator.visibility = View.GONE
        contentDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        if (openDrawer) {
            onShowContentDrawerRequested()
        }

        prefs.isSidebarOpened = false
        contentSideDrawer.updatePinMenuOption()
    }

    fun isSidebarPinned() = contentSideDrawer.isPinned

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveState(outState)
    }

    private fun restoreState(bundle: Bundle) {
        with(SaveStateOptions) {
            isFullscreen.set(bundle.isFullscreen == true)
        }
    }

    private fun saveState(bundle: Bundle) {
        with(SaveStateOptions) {
            bundle.isFullscreen = isFullscreen.get()
        }
    }

    public override fun onResume() {
        super.onResume()
        contentViewPager.keepScreenOn = true
        contentViewPager.onActivityResume()

        setPlaybackControlsManager()
    }

    override fun onPause() {
        super.onPause()
        contentViewPager.onActivityPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultIntent: Intent?) {
        if (resultCode != RESULT_OK || resultIntent == null) {
            super.onActivityResult(requestCode, resultCode, resultIntent)
            return
        }

        when (ContentRequestCode.getCode(requestCode)) {
            ContentRequestCode.REQUEST_APPLICATION_SHARE -> shareUtil.processShareRequest(this, resultIntent)

            // side-bar resultCodes
            ContentRequestCode.REQUEST_NOTE -> {
                analyticsReferrer = Analytics.Referrer.EXIT_NOTE_MODE
                onAnnotationUpdated(resultIntent.getLongExtra(NoteActivity.EXTRA_ANNOTATION_ID, 0L))
            }
            ContentRequestCode.REQUEST_TAG -> {
                analyticsReferrer = Analytics.Referrer.EXIT_TAG_MODE
                with(TagSelectionActivity.IntentOptions) {
                    onAnnotationUpdated(resultIntent.annotationId)
                }
            }
            ContentRequestCode.REQUEST_LINK -> {
                analyticsReferrer = Analytics.Referrer.EXIT_LINK_MODE
                onAnnotationUpdated(resultIntent.getLongExtra(LinksActivity.EXTRA_ANNOTATION_ID, 0L))
            }
            ContentRequestCode.REQUEST_NOTEBOOK_SELECTION -> {
                analyticsReferrer = Analytics.Referrer.EXIT_NOTEBOOK_MODE
                with(NotebookSelectionActivity.IntentOptions) {
                    onAnnotationUpdated(resultIntent.annotationId)
                }
            }

            // other
            else -> super.onActivityResult(requestCode, resultCode, resultIntent)
        }
    }

    private fun onAnnotationUpdated(annotationId: Long) {
        contentSideDrawer?.updateAnnotation(annotationId)
    }

    override fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem): Boolean {
        if (screenHistoryItem.sourceType == ScreenSourceType.CONTENT) {
            val extras = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasContent::class.java)
            return extras.contentItemId == contentItemId && subItemUri == extras.subItemUri
        }
        return false
    }

    override fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem) {
        screenHistoryItem.sourceType = ScreenSourceType.CONTENT
        screenHistoryItem.title = subItemManager.findTitleById(contentItemId, subItemId)

        val docId = subItemManager.findDocIdById(contentItemId, subItemId)
        if (docId != null) {
            screenHistoryItem.description = itemManager.findTitleByDocId(docId)
        }

        // extras
        screenHistoryItem.setExtras(gson, ScreenHistoryExtrasContent(contentItemId, subItemUri, getScrollPosition()))

        // history
        historyManager.add(contentItemId, subItemUri, screenHistoryItem.title, screenHistoryItem.description, getScrollPosition())
    }

    override fun onBackPressed() {
        if (contentDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            onCloseContentDrawerRequested()
        } else {
            super.onBackPressed()
        }
    }

    private fun playAudio() {
        if (shouldPlayTextToSpeech()) {
            playTextToSpeech()
            return
        }

        // Determines the playback position, resuming by default
        var playbackPosition = mediaPlaybackPositionManager.getPlaybackPosition(contentItemId, subItemId, ItemMediaType.AUDIO, null)
        if (playbackPosition <= MEDIA_POSITION_THRESHOLD) {
            playbackPosition = 0
        }

        // If the playlist is already correct, only update the playing audio item
        val currentItem = playlistManager.currentItem
        if (playlistManager.id == contentItemId && currentItem != null && currentItem.mediaType == BasePlaylistManager.AUDIO) {
            if (currentItem.id != subItemId) {
                playlistManager.setCurrentItem(subItemId)
                playlistManager.play(playbackPosition.toLong(), true)
            }

            return
        }

        // Converts the list of audioItems to PlaylistItems
        val audioItems = playlistItemQueryManager.findAllByContentItemIdAndType(contentItemId, subItemManager.findTypeById(contentItemId, subItemId))
        val playlistItems = ArrayList<MediaItem>(audioItems.size)
        audioItems.forEach {
            playlistItems.add(AudioItem(it))
        }

        if (playlistItems.isEmpty()) {
            // Don't start playback if there is nothing to play.
            showHideMediaFab(false) // The media fab should not be visible. Hide it.
            return
        }

        // Updates the playlistManager and starts the audio
        playlistManager.setParameters(playlistItems, 0)
        playlistManager.setPlaylistId(contentItemId, -1)
        playlistManager.setCurrentItem(subItemId)
        playlistManager.play(playbackPosition.toLong(), true)
    }

    private fun playTextToSpeech() {
        // Determines the playback position, resuming by default
        val playbackPosition = mediaPlaybackPositionManager.getPlaybackPosition(contentItemId, subItemId, ItemMediaType.TEXT_TO_SPEECH, null)

        // Build list of items to play
        val itemList = ArrayList<TextToSpeechManager.TextToSpeechItem>()
        val subtitle = itemManager.findTitleById(contentItemId)
        val imageRenditions = itemManager.findImageCoverRenditionsById(contentItemId)

        var startPosition = 0
        val subItems = subItemManager.findAllByContentItemId(contentItemId)
        for (i in subItems.indices) {
            val subItem = subItems[i]
            val textToSpeechItem = TextToSpeechManager.TextToSpeechItem(contentItemId, subItem.id, subItem.title, subtitle, imageRenditions)
            itemList.add(textToSpeechItem)
            if (subItem.id == subItemId) {
                startPosition = i
            }
        }

        // Start Playback
        textToSpeechManager.setTextToSpeechItems(itemList, startPosition)
        textToSpeechManager.speak(playbackPosition)
        miniPlaybackControls.playbackManager = textToSpeechControlsManager
        miniPlaybackControls.show()
    }

    private fun isRelatedAudioAvailable() = availableRelatedTypeManager.findBySubItemId(contentItemId, subItemId)?.audioAvailable == true

    private fun shouldPlayTextToSpeech(): Boolean {
        // Reasons Text-to-speech should be used:
        // 1. The default audio voice is set to Text-to-speech
        // 2. There is no available audio
        if (prefs.audioVoice == AudioPlaybackVoiceType.TEXT_TO_SPEECH || !isRelatedAudioAvailable()) {
            return true
        }

        // 3. There is no network connection and no downloaded audio for the selected voice
        // 4. Data is limited when not on wifi, the device is not connected to wifi, and audio for the item in the selected voice is not downloaded
        // TODO when GLA-2126 has been implemented, switch this logic to use the new column to determine if a voice is downloaded.
        if (!networkUtil.isConnected() || prefs.isMobileNetworkLimited && !networkUtil.isConnected(false)) {
            val downloadedMediaList = downloadedMediaManager.findAllByContentItemAndPage(contentItemId, subItemId)
            for (downloadedMedia in downloadedMediaList) {
                val relatedAudioItem = relatedAudioItemManager.findBySubItemIdAndVoiceId(contentItemId, subItemId, prefs.audioVoice.voiceId.toLong())
                if (relatedAudioItem != null && relatedAudioItem.mediaUrl == downloadedMedia.tertiaryId) {
                    // Downloaded media for this voice type exists
                    return false
                }
            }

            // No available downloads exist for this voice type
            return true
        }

        // Audio is available and no network restrictions are in place
        return false
    }

    private fun onStickyTapped(annotationId: Long) {
        launch(cc.ui) {
            contentSideDrawer.showAnnotation(annotationId, true)
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

        // Handle other components
        miniPlaybackControls.hideImmediate()
        mediaFab.setFullscreenEnabled(true)

        //Makes sure to teach the user how to exit fullscreen
        if (!prefs.trainingFullscreenHelpShown) {
            Snackbar.make(mediaFab, R.string.double_tap_fullscreen_training, Snackbar.LENGTH_LONG).show()
            prefs.trainingFullscreenHelpShown = true
        }
    }

    private fun exitFullscreenMode() {
        if (!isFullscreen.compareAndSet(true, false)) {
            return
        }

        // Handle System UI and Actionbar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        setToolbarVisible(true)

        // Handle other components
        miniPlaybackControls.show()
        mediaFab.setFullscreenEnabled(false)
    }

    private fun setToolbarVisible(visible: Boolean) {
        val mainToolbar = getMainToolbar()
        mainToolbar?.setVisible(visible)
    }

    fun getScrollPosition(): Int {
        val webView = getCurrentWebView()
        return webView?.scrollPositionPercentage ?: 0
    }

    private fun getCurrentFragment() = contentPagerAdapter.getContentFragment(contentViewPager.currentItem)

    private fun getCurrentWebView() = getCurrentFragment()?.getContentWebView()

    override fun onPageSelected(position: Int) {
        analyticsReferrer = Analytics.Referrer.SWIPE
        subItemId = contentPagerAdapter.getSubItemId(position)
        subItemUri = subItemManager.findUriById(contentItemId, subItemId)
        if (subItemUri.isEmpty()) {
            Timber.e("Cannot find subItemUri for contentItemId: [%d], subItemId: [%d]", contentItemId, subItemId)
        }

        title = subItemManager.findTitleById(contentItemId, subItemId)

        //Hide the FAB until we have verified there is audio.
        getCurrentFragment()?.let { currentFragment ->
            if (!currentFragment.isMarkTextPopupVisible) {
                mediaFab.setScrollVisibility(false)
            }
        }

        contentSideDrawer.onContentChanged(getScreenId(), contentItemId, subItemId)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // do nothing
    }

    override fun onPageScrollStateChanged(state: Int) {
        // do nothing
    }

    override fun onCloseContentDrawerRequested() {
        if (contentSideDrawer.isPinned && prefs.isSidebarOpened) {
            unpinSidebar(false)
            return
        }

        runOnUiThread { contentDrawerLayout.closeDrawer(GravityCompat.END) }
    }

    override fun onSidebarPinned() {
        pinSidebar()
    }

    override fun onSidebarUnpinned(requestUnpinnedUi: Boolean) {
        unpinSidebar(requestUnpinnedUi)
    }

    override fun onCurrentScrollPositionAidRequested() {
        requestCurrentScrollPositionAid()
    }

    override fun onShowContentDrawerRequested() {
        if (contentSideDrawer.isPinned && contentSideDrawer.canShowPinned()) {
            pinSidebar()
            return
        }

        runOnUiThread { contentDrawerLayout.openDrawer(GravityCompat.END) }
    }

    private fun showHideMediaFab(show: Boolean) {
        getCurrentFragment()?.let { currentFragment ->
            if (!currentFragment.isMarkTextPopupVisible && !miniPlaybackControls.isVisible()) {
                mediaFab.setScrollVisibility(show)
            }
        }
    }

    override fun showActionSnackbar(message: CharSequence, action: SnackbarAction, duration: Int) {
        showActionSnackbar(mediaFab, message, action, duration)
    }

    fun scrollSidebarToParagraphAid(paragraphAid: String) {
        runOnUiThread { contentSideDrawer.scrollToParagraphAid(paragraphAid) }
    }

    private fun requestCurrentScrollPositionAid() {
        getCurrentWebView()?.requestAidFromScrollPosition()
    }

    inner class ContentPagerAdapter : FragmentStatePagerAdapter(supportFragmentManager) {
        private val subItemIds: List<Long> = subItemManager.findAllIds(contentItemId)
        private val pages = arrayOfNulls<ContentItemFragment>(subItemIds.size)
        private val extraBottomMargin: Int = 0

        // State
        private var initialItemsSet = false // only send mark text and scroll to AID the to the first initialSubItemId

        override fun getItem(position: Int): Fragment {
            // Return the cached fragment if it exists
            val contentItemFragment = pages[position]
            if (contentItemFragment != null) {
                return contentItemFragment
            }

            val pagerItemSubItemId = subItemIds[position]

            val fragment: ContentItemFragment
            if (subItemId == pagerItemSubItemId && !initialItemsSet) {
                // primary subitem
                fragment = ContentItemFragment.newInstance(getScreenId(), contentItemId, subItemId, position,
                        scrollToParagraphAid,
                        scrollPosition,
                        markParagraphAids,
                        findOnPageText,
                        markTextSqliteOffsets,
                        markTextSqliteExactPhrase
                )

                initialItemsSet = true
            } else {
                // other items
                fragment = ContentItemFragment.newInstance(getScreenId(), contentItemId, pagerItemSubItemId, position)
            }

            // cache the fragment
            pages[position] = fragment

            //Makes sure the bottom margin is correctly sized
            fragment.updateWebViewBottomMargin(extraBottomMargin)

            return fragment
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
            pages[position] = null
        }

        override fun getCount(): Int {
            return subItemManager.findCount(contentItemId).toInt()
        }

        fun getContentFragment(position: Int): ContentItemFragment? {
            if (position < 0 || position >= pages.size) {
                return null
            }

            return pages[position]
        }

        fun getPositionBySubItemId(subItemId: Long): Int {
            return subItemIds.indices.firstOrNull { subItemIds[it] == subItemId } ?: 0
        }

        fun getSubItemId(position: Int): Long {
            return subItemIds[position]
        }
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, ContentActivity::class) {
        private val MEDIA_POSITION_THRESHOLD = TimeUnit.SECONDS.toMillis(5)
    }

    object IntentOptions {
        var Intent.contentItemId by IntentExtra.Long(defaultValue = 0L)
        var Intent.subItemId by IntentExtra.Long(defaultValue = 0L)

        // Optional
        var Intent.scrollPosition by IntentExtra.Int(defaultValue = 0)
        var Intent.scrollToParagraphAid by IntentExtra.String(defaultValue = "") // what page gets the mark(s) (if any)
        var Intent.markParagraphAids by IntentExtra.StringArray(defaultValue = emptyArray()) // flag to Mark/Highlight the given Paragraph ids
        var Intent.findOnPageText by IntentExtra.String(defaultValue = "")
        var Intent.markTextSqliteOffsets by IntentExtra.String(defaultValue = "") // mark text by sqlite fts offsets and scroll to first item
        var Intent.markTextSqliteExactPhrase by IntentExtra.Boolean(defaultValue = false) // mark text by sqlite fts offsets and scroll to first item (true if results are exact phrase)
        var Intent.analyticsReferrer by IntentExtra.Serializable(defaultValue = Analytics.Referrer.UNKNOWN)
    }

    object SaveStateOptions {
        var Bundle.isFullscreen by BundleExtra.Boolean()
    }
}

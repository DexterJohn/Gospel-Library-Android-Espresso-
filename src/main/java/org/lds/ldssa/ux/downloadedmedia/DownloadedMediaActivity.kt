package org.lds.ldssa.ux.downloadedmedia

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_downloaded_media.*
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Long
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.media.exomedia.AudioPlaybackControlsManager
import org.lds.ldssa.media.exomedia.data.AudioItem
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.ldssa.media.exomedia.data.VideoItem
import org.lds.ldssa.media.exomedia.manager.PlaylistManager
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.content.playlistitemquery.PlaylistItemQueryManager
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMedia
import org.lds.ldssa.model.database.gl.downloadedmediacollection.DownloadedMediaCollection
import org.lds.ldssa.model.webview.content.dto.DtoInlineVideo
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.dialog.DeleteAllMediaDialogFragment
import org.lds.ldssa.util.GLFileUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ui.setVisible
import java.util.ArrayList
import java.util.LinkedList
import javax.inject.Inject

/**
 * Displays a list of the books that have downloaded media.  If a book is
 * selected, either through this Activity or passed in as an Intent Extra,
 * then a list of downloaded media for that book will be displayed.
 */
class DownloadedMediaActivity : BaseActivity() {

    @Inject
    lateinit var fileUtil: GLFileUtil
    @Inject
    lateinit var itemManager: ItemManager
    @Inject
    lateinit var playlistManager: PlaylistManager
    @Inject
    lateinit var playlistItemQueryManager: PlaylistItemQueryManager
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var coroutineContextProvider: CoroutineContextProvider

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DownloadedMediaViewModel::class.java) }

    private lateinit var downloadedMediaAdapter: DownloadedMediaAdapter
    private lateinit var downloadedCollectionsAdapter: DownloadedMediaCollectionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.get().inject(this)
        super.onCreate(savedInstanceState)

        setupAdapters()

        recyclerView.layoutManager = LinearLayoutManager(this)
        updateAdapter()

        miniPlaybackControls.playbackManager = AudioPlaybackControlsManager(this)

        setupViewModelObservers()

        with(SaveStateOptions) {
            viewModel.setContentItemId(savedInstanceState?.contentItemId ?: INVALID_CONTENT_ITEM_ID)
            viewModel.setSortBySize(prefs.generalDisplayMediaSortBySize)
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_downloaded_media
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(SaveStateOptions) {
            outState.contentItemId = viewModel.getContentItemId()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_media_manager, menu)

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val deleteAllItem = menu.findItem(R.id.menu_item_delete_all)
        deleteAllItem.isVisible = downloadedMediaAdapter.itemCount > 0
        updateSortMenuItem(menu.findItem(R.id.menu_item_sort))

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_sort -> {
                viewModel.setSortBySize(!viewModel.getSortBySize())
                prefs.generalDisplayMediaSortBySize = viewModel.getSortBySize()
                updateSortMenuItem(item)
            }
            R.id.menu_item_delete_all -> deleteAll(viewModel.getContentItemId())
            R.id.menu_item_media_current_downloads -> internalIntents.showCurrentDownloads(this, getScreenId())
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    override fun onBackPressed() {
        if (isShowingCollections()) {
            super.onBackPressed()
            return
        }

        viewModel.setContentItemId(INVALID_CONTENT_ITEM_ID)
        updateAdapter()
        dismissSnackbar()
        updateToolbar()
    }

    public override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.DOWNLOADED_MEDIA
    }

    private fun playAudio(downloadedMedia: DownloadedMedia) {
        val items = LinkedList<MediaItem>()

        // TODO: this call is not guaranteed to play the downloaded file if the file is of a different voice than what is saved in prefs
        val contentItemId = viewModel.getContentItemId()
        val audioItem = playlistItemQueryManager.find(contentItemId, downloadedMedia.subItemId)
        audioItem?.let {
            items.add(AudioItem(it))
        }

        playlistManager.setParameters(items, 0)
        playlistManager.setPlaylistId(contentItemId, -1)
        playlistManager.play(0, false)
    }

    private fun playVideo(downloadedMedia: DownloadedMedia) {
        val inlineVideo = DtoInlineVideo()
        inlineVideo.sources = ArrayList()
        inlineVideo.title = downloadedMedia.title

        val contentItemId = viewModel.getContentItemId()
        val coverRenditions = itemManager.findImageCoverRenditionsById(contentItemId)

        val items = LinkedList<MediaItem>()
        items.add(VideoItem(inlineVideo, coverRenditions, contentItemId, downloadedMedia.subItemId))

        playlistManager.setParameters(items, 0)
        playlistManager.setPlaylistId(contentItemId, -1)
        internalIntents.showVideoPlayer(this, getScreenId(), contentItemId, downloadedMedia.subItemId,
                downloadedMedia.tertiaryId, Analytics.Referrer.DOWNLOADED_MEDIA)
    }

    private fun updateSortMenuItem(item: MenuItem) {
        item.setTitle(if (viewModel.getSortBySize()) R.string.sort_by_item else R.string.sort_by_size)
    }

    private fun deleteAll(contentId: Long) {
        val dialog = DeleteAllMediaDialogFragment.newInstance(contentId)
        dialog.show(supportFragmentManager)
    }

    private fun delete(downloadedMedia: DownloadedMedia) {
        currentSnackbar?.dismiss()
        viewModel.removeDownloadedMedia(downloadedMedia.id)
        showUndoSnackbar(getString(R.string.deleted, downloadedMedia.title), Runnable {
            // Re-adds the removed item
            viewModel.undoRemoveDownloadedMedia(downloadedMedia.id)
        }, Runnable { onDelete(downloadedMedia) })
    }

    private fun onDelete(downloadedMedia: DownloadedMedia) {
        val downloadedFilename = downloadedMedia.file
        downloadedFilename ?: return

        val file = fileUtil.getContentMediaFile(downloadedFilename, downloadedMedia.type)

        // Delete the file if it exists
        if (file.exists()) {
            file.delete()
        }

        // Remove the stored download and inform the listener
        viewModel.deleteDownloadedMedia(downloadedMedia.id)
    }

    private fun updateToolbar() {
        title = getString(R.string.downloaded_media)
        setSubtitle(if (!isShowingCollections()) itemManager.findTitleById(viewModel.getContentItemId()) else "")
    }

    private fun updateAdapter() {
        if (isShowingCollections()) {
            recyclerView.adapter = downloadedCollectionsAdapter
        } else {
            recyclerView.adapter = downloadedMediaAdapter
        }

        updateToolbar()
    }

    private fun isShowingCollections() = viewModel.getContentItemId() == INVALID_CONTENT_ITEM_ID

    private fun setupAdapters() {
        downloadedMediaAdapter = DownloadedMediaAdapter(coroutineContextProvider).apply {
            itemClickListener = { downloadedMedia: DownloadedMedia ->
                if (downloadedMedia.type === org.lds.ldssa.model.database.types.ItemMediaType.VIDEO) {
                    playVideo(downloadedMedia)
                } else {
                    playAudio(downloadedMedia)
                }
            }
            deleteClickListener = { downloadedMedia: DownloadedMedia ->
                delete(downloadedMedia)
            }
        }

        downloadedCollectionsAdapter = DownloadedMediaCollectionsAdapter().apply {
            itemClickListener = { downloadedMediaCollection: DownloadedMediaCollection ->
                viewModel.setContentItemId(downloadedMediaCollection.id)
                updateAdapter()
            }
            deleteClickListener = { downloadedMediaCollection: DownloadedMediaCollection ->
                deleteAll(downloadedMediaCollection.id)
            }
        }
    }

    private fun setupViewModelObservers() {
        viewModel.downloadedMediaList.observeNotNull { downloadedMediaList ->
            downloadedMediaAdapter.items = downloadedMediaList
            emptyListIndicator.setVisible(isShowingCollections().not() && downloadedMediaList.isEmpty())
        }

        viewModel.downloadedCollectionsList.observeNotNull { downloadedCollectionsList ->
            downloadedCollectionsAdapter.list = downloadedCollectionsList
            emptyListIndicator.setVisible(isShowingCollections() && downloadedCollectionsList.isEmpty())
        }
    }

    companion object {
        val INVALID_CONTENT_ITEM_ID = -1L
    }

    object SaveStateOptions {
        var Bundle.contentItemId by BundleExtra.Long()
    }
}
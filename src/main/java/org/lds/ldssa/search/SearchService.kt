package org.lds.ldssa.search

import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.util.DefaultNamedThreadFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class SearchService : Service() {

    private var searchCountExecutor: ExecutorService? = null
    private var searchNotePreviewExecutor: ExecutorService? = null
    private var searchContentItemPreviewExecutor: ExecutorService? = null

    @Inject
    lateinit var searchUtil: SearchUtil
    @Inject
    lateinit var searchEngineLocal: SearchEngineLocal

    init {
        Injector.get().inject(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleCommand(intent)
        return START_NOT_STICKY
    }

    private fun handleCommand(intent: Intent?) {
        intent ?: return

        when (intent.action) {
            ACTION_SEARCH_COUNT ->  executeContentCountSearch(intent)
            ACTION_SEARCH_PREVIEW_CONTENT -> executeContentPreviewSearch(intent)
            ACTION_SEARCH_PREVIEW_NOTE -> executeNotePreviewSearch(intent)
        }
    }

    private fun getTabIdFromIntent(intent: Intent): Long {
        val screenId = intent.getLongExtra(EXTRA_SCREEN_ID, 0L)
        if (screenId <= 0L) {
            throw IllegalStateException("tab id required for Search Service")
        }

        return screenId
    }

    private fun executeContentCountSearch(intent: Intent) {
        val searchFilter = SearchEngineLocal.SearchFilter(intent)
        val screenId = getTabIdFromIntent(intent)

        // create new executor
        searchCountExecutor?.shutdownNow()
        searchCountExecutor = Executors.newSingleThreadExecutor(DefaultNamedThreadFactory("SearchService"))

        // for count... also shutdown content executor
        searchContentItemPreviewExecutor?.shutdownNow()


        // start search in background
        searchCountExecutor?.let {
            it.submit {
                searchEngineLocal.findAllContentCount(screenId, searchFilter)
            }

            it.shutdown()
        }
    }

    private fun executeContentPreviewSearch(intent: Intent) {
        val filter = SearchEngineLocal.SearchFilter(intent)
        val screenId = getTabIdFromIntent(intent)

        // create new executor
        searchContentItemPreviewExecutor?.shutdownNow()
        searchContentItemPreviewExecutor = Executors.newSingleThreadExecutor()


        // start search in background
        searchContentItemPreviewExecutor?.let {
            it.submit {
                searchEngineLocal.findContentPreview(screenId, filter)
            }

            it.shutdown()
        }
    }

    private fun executeNotePreviewSearch(intent: Intent) {
        val searchText = intent.getStringExtra(EXTRA_SEARCH_TEXT) ?: return
        val screenId = getTabIdFromIntent(intent)

        // create new executor
        searchNotePreviewExecutor?.shutdownNow()
        searchNotePreviewExecutor = Executors.newSingleThreadExecutor()


        // start search in background
        searchNotePreviewExecutor?.let {
            it.submit {
                searchEngineLocal.findNotePreview(screenId, searchText)
            }

            it.shutdown()
        }
    }

    companion object {
        const val ACTION_SEARCH_COUNT = "ACTION_SEARCH_COUNT"
        const val ACTION_SEARCH_PREVIEW_CONTENT = "ACTION_SEARCH_PREVIEW_CONTENT"
        const val ACTION_SEARCH_PREVIEW_NOTE = "ACTION_SEARCH_PREVIEW_NOTE"

        const val EXTRA_SCREEN_ID = "EXTRA_SCREEN_ID"
        const val EXTRA_SEARCH_TEXT = "EXTRA_SEARCH_TEXT"
        const val EXTRA_FILTER_LIBRARY_COLLECTION_ID = "EXTRA_FILTER_LIBRARY_COLLECTION_ID"
        const val EXTRA_FILTER_CONTENT_ITEM_ID = "EXTRA_FILTER_CONTENT_ITEM_ID"
        const val EXTRA_FILTER_NAV_COLLECTION_ID = "EXTRA_FILTER_NAV_COLLECTION_ID"
        const val EXTRA_FILTER_SUB_ITEM_ID = "EXTRA_FILTER_SUB_ITEM_ID"
    }
}

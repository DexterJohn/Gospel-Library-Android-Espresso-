package org.lds.ldssa.ux.locations.bookmarks

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import butterknife.BindView
import com.afollestad.materialdialogs.MaterialDialog
import com.devbrackets.android.recyclerext.adapter.helper.SimpleElevationItemTouchHelperCallback
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.ExternalIntents
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.bookmark.Bookmark
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager
import org.lds.ldssa.model.database.userdata.bookmarkquery.BookmarkQuery
import org.lds.ldssa.model.database.userdata.bookmarkquery.BookmarkQueryManager
import org.lds.ldssa.sync.AnnotationSync
import org.lds.ldssa.ui.fragment.BaseFragment
import org.lds.ldssa.util.CitationUtil
import org.lds.ldssa.util.ToastUtil
import org.lds.ldssa.util.annotations.BookmarkUtil
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.widget.EmptyStateIndicator
import pocketknife.BindArgument
import pocketknife.NotRequired
import javax.inject.Inject

class BookmarksFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var bookmarkManager: BookmarkManager
    @Inject
    lateinit var bookmarkQueryManager: BookmarkQueryManager
    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var bookmarkUtil: BookmarkUtil
    @Inject
    lateinit var externalIntents: ExternalIntents
    @Inject
    lateinit var citationUtil: CitationUtil
    @Inject
    lateinit var annotationSync: AnnotationSync
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var toastUtil: ToastUtil

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(BookmarksViewModel::class.java) }

    @BindView(R.id.swipeRefreshLayout)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.bookmarks_fragment_recycler)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.emptyStateIndicator)
    lateinit var emptyStateIndicator: EmptyStateIndicator

    @NotRequired
    @BindArgument(ARG_REFERENCE_CONTENT_ITEM_ID)
    var referenceContentItemId: Long = 0
    @NotRequired
    @BindArgument(ARG_REFERENCE_SUBITEM_ID)
    var referenceSubItemId: Long = 0
    @NotRequired
    @BindArgument(ARG_REFERENCE_PARAGRAPH_AID)
    var referenceParagraphAid: String? = null

    private lateinit var adapter: BookmarksAdapter

    override fun getLayoutResourceId() = R.layout.fragment_bookmarks

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Injector.get().inject(this)

        setupRecyclerView()
        swipeRefreshLayout.setOnRefreshListener(this)
        setupViewModelObservers()
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        launch(cc.ui) {
            run(cc.commonPool) {
                annotationSync.sync()
            }
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupViewModelObservers() {
        viewModel.bookmarkList.observeNotNull { bookmarkList ->
            emptyStateIndicator.setVisible(bookmarkList.isEmpty())
            adapter.items = bookmarkList
        }
    }

    fun onNewClick() = showAddBookmarkDialog()

    private fun onMenuItemSelected(bookmarkQuery: BookmarkQuery, menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_popup_update -> updateBookmark(bookmarkQuery)
            R.id.menu_popup_rename -> showRenameBookmarkDialog(bookmarkQuery)
            R.id.menu_popup_delete -> {
                deleteBookmark(bookmarkQuery)
                return true
            }
        }

        return false
    }

    private fun setupRecyclerTouchHelper() {
        val dragDirections = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val dragElevationChange = context?.resources?.getDimensionPixelSize(R.dimen.drag_elevation_increase)?.toFloat() ?: 0f

        val itemTouchHelper = ItemTouchHelper(object : SimpleElevationItemTouchHelperCallback(dragDirections, 0, dragElevationChange) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val originalPosition = viewHolder.adapterPosition
                val endPosition = target.adapterPosition
                if (originalPosition == RecyclerView.NO_POSITION || endPosition == RecyclerView.NO_POSITION) {
                    return false
                }
                adapter.move(originalPosition, endPosition)
                updateBookmarks(adapter.items, { true })
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //Swipe not supported
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun showAddBookmarkDialog() {
        activity?.let {
            MaterialDialog.Builder(it)
                    .title(R.string.add_bookmark)
                    .positiveText(R.string.add)
                    .negativeText(R.string.cancel)
                    .inputRange(0, Bookmark.NAME_MAX_LENGTH)
                    .input(null, citationUtil.createCitationText(referenceContentItemId, referenceSubItemId, referenceParagraphAid)) { _, input -> addBookmark(input.toString()) }
                    .show()
        }
    }

    private fun addBookmark(name: String) {
        if (name.isNotBlank()) {
            updateBookmarks(worker = {
                bookmarkUtil.add(name, referenceContentItemId, referenceSubItemId, referenceParagraphAid)
                true
            }, success = {
                toastUtil.showShort(R.string.bookmark_added)
            })
        }
    }

    private fun updateBookmark(bookmarkQueryItem: BookmarkQuery) = updateBookmarks(adapter.items, {
        val bookmarkId = bookmarkQueryItem.id
        val succeeded = bookmarkUtil.update(bookmarkId, referenceContentItemId, referenceSubItemId, referenceParagraphAid)
        if (succeeded) {
            bookmarkQueryManager.findByBookmarkId(bookmarkId)?.let { updatedBookmarkQueryItem ->
                // update the existing item in the list
                bookmarkQueryItem.updateFromOtherItem(updatedBookmarkQueryItem)
            }
        }
        succeeded
    }, {
        // notify the user of the update
        toastUtil.showShort(R.string.bookmark_updated)
        activity?.finish()
    })

    private fun showRenameBookmarkDialog(bookmarkQueryItem: BookmarkQuery) {
        activity?.let {
            MaterialDialog.Builder(it)
                    .title(R.string.rename_bookmark)
                    .positiveText(R.string.ok)
                    .negativeText(R.string.cancel)
                    .inputRange(0, Bookmark.NAME_MAX_LENGTH)
                    .input(null, bookmarkQueryItem.name) { _, input ->
                        updateBookmarks(adapter.items, {
                            bookmarkUtil.rename(bookmarkQueryItem.id, input.toString())
                            true
                        })
                    }
                    .show()
        }
    }

    private fun setupRecyclerView() {
        adapter = BookmarksAdapter(!referenceParagraphAid.isNullOrBlank(), cc).also {
            it.clickListener = { bookmarkId ->
                internalIntents.showContentFromBookmark(activity, screenId, bookmarkId, false)
            }
            it.menuItemClickListener = { bookmarkQuery, menuItem ->
                onMenuItemSelected(bookmarkQuery, menuItem)
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        setupRecyclerTouchHelper()
    }

    private fun deleteBookmark(bookmarkQuery: BookmarkQuery) {
        activity?.let {
            MaterialDialog.Builder(it)
                    .title(R.string.delete)
                    .content(getString(R.string.delete_collection_item_title, bookmarkQuery.name))
                    .positiveText(R.string.delete)
                    .negativeText(R.string.cancel)
                    .onPositive { _, _ ->
                        updateBookmarks(worker = {
                            bookmarkUtil.delete(bookmarkQuery.id)
                            true
                        })
                    }.show()
        }
    }

    private fun updateBookmarks(updatedList: List<BookmarkQuery>? = null, worker: suspend () -> Boolean, success: suspend () -> Unit = {}) = launch(cc.ui) {
        var succeeded = false
        run(cc.commonPool) {
            succeeded = bookmarkUtil.updateBookmarks(updatedList, worker)
        }
        externalIntents.updateBookmarkWidget(activity)
        if (succeeded) {
            success()
        }
    }

    companion object {
        const val ARG_REFERENCE_CONTENT_ITEM_ID = "ARG_REFERENCE_CONTENT_ITEM_ID"
        const val ARG_REFERENCE_SUBITEM_ID = "ARG_REFERENCE_SUBITEM_ID"
        const val ARG_REFERENCE_PARAGRAPH_AID = "ARG_REFERENCE_PARAGRAPH_AID"

        fun newInstance(screenId: Long, referenceContentItemId: Long, referenceSubItemId: Long, referenceParagraphAid: String): BookmarksFragment {
            val fragment = BookmarksFragment()

            val args = BaseFragment.getBaseBundle(screenId)
            args.putLong(ARG_REFERENCE_CONTENT_ITEM_ID, referenceContentItemId)
            args.putLong(ARG_REFERENCE_SUBITEM_ID, referenceSubItemId)
            args.putString(ARG_REFERENCE_PARAGRAPH_AID, referenceParagraphAid)

            fragment.arguments = args

            return fragment
        }
    }
}

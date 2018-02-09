package org.lds.ldssa.ux.annotations

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.devbrackets.android.recyclerext.adapter.helper.SimpleElevationItemTouchHelperCallback
import kotlinx.android.synthetic.main.fragment_note_collection_all_annotations.*
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Long
import me.eugeniomarletti.extras.bundle.base.String
import org.lds.ldssa.R
import org.lds.ldssa.event.download.DownloadCompletedEvent
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadata
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.ui.fragment.BaseFragment
import org.lds.ldssa.util.getScrollPosition
import org.lds.mobile.ui.setVisible
import pocketbus.Subscribe
import pocketbus.ThreadMode
import javax.inject.Inject

/**
 * Fragment used to show a list of annotations
 * - List of annotations for a specific Tag
 * - List of annotations for a specific Notebook
 */
class AnnotationsFragment : BaseFragment() {

    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var notebookManager: NotebookManager
    @Inject
    lateinit var subItemMetadataManager: SubItemMetadataManager
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(AnnotationsViewModel::class.java) }

    private lateinit var adapter: AnnotationsAdapter
    private var annotationOrderingUpdated = false

    private var notebookId = 0L
    private var tagText = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Injector.get().inject(this)

        arguments?.let {
            with(ArgumentOptions) {
                notebookId = it.notebookId
                tagText = it.tagText
            }
        }

        setupRecyclerView()

        //Updates the empty list indicator title when in a notebook
        if (notebookId > 0L) {
            emptyStateIndicator.setTitle(resources.getString(R.string.no_notes_in_notebook))
        }

        setupViewModelObservers()

        when {
            notebookId > 0L -> viewModel.setNotebookId(notebookId)
            tagText.isNotBlank() ->  viewModel.setTagText(tagText)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.scrollPosition = allAnnotationsRecyclerView.getScrollPosition()
        super.onSaveInstanceState(outState)
    }

    override fun hasBusSubscriptions() = true

    override fun onStop() {
        if (notebookId != 0L && annotationOrderingUpdated) {
            notebookManager.updateAnnotationOrdering(notebookId, adapter.items)
        }
        super.onStop()
    }

    override fun getLayoutResourceId() = R.layout.fragment_note_collection_all_annotations

    // todo remove (use LiveData)
    @Subscribe(ThreadMode.MAIN)
    fun handle(event: DownloadCompletedEvent) {
        val annotations = adapter.items
        if (event.type !== ItemMediaType.CONTENT || annotations.isEmpty() || !event.isSuccessful) {
            return
        }

        val start = (allAnnotationsRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val end = allAnnotationsRecyclerView.layoutManager.childCount + start

        //Checks all the ATTACHED views to see if we need to update them
        var i = start
        while (i in 0..(end - 1)) {
            val annotation = annotations[i]

            var subItemMetadata: SubItemMetadata? = null
            annotation.docId?.let {
                subItemMetadata = subItemMetadataManager.findByDocId(it)
            }

            //If the item was updated, notify the data was changed to update the display
            if (subItemMetadata != null && subItemMetadata?.itemId == event.contentItemId) {
                adapter.notifyItemChanged(i)
            }
            i++
        }
    }

    private fun setupViewModelObservers() {
        viewModel.annotationList.observeNotNull { annotationList ->
            allAnnotationsLoadingView.setVisible(false)

            emptyStateIndicator.setVisible(annotationList.isEmpty())

            adapter.items = annotationList
            allAnnotationsRecyclerView.scrollToPosition(viewModel.scrollPosition)
        }
    }

    private fun setupRecyclerView() {
        // don't show notebooks if we are already in a notebook (only show in tags)
        val showNotebooksInAnnotationView = notebookId <= 0

        adapter = AnnotationsAdapter(cc, screenId, showNotebooksInAnnotationView)

        allAnnotationsRecyclerView.adapter = adapter
        allAnnotationsRecyclerView.layoutManager = LinearLayoutManager(activity)

        setupRecyclerTouchHelper()
    }

    private fun setupRecyclerTouchHelper() {
        // only allow dragging in Notebooks
        if (notebookId == 0L) {
            return
        }

        val dragDirections = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val dragElevationChange = context?.resources?.getDimensionPixelSize(R.dimen.drag_elevation_increase)?.toFloat() ?: 0f

        val touchHelper = ItemTouchHelper(object : SimpleElevationItemTouchHelperCallback(dragDirections, 0, dragElevationChange) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val originalPosition = viewHolder.adapterPosition
                val endPosition = target.adapterPosition
                if (originalPosition == RecyclerView.NO_POSITION || endPosition == RecyclerView.NO_POSITION) {
                    return false
                }
                adapter.move(originalPosition, endPosition)
                annotationOrderingUpdated = true
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //Swipe not supported
            }
        })

        touchHelper.attachToRecyclerView(allAnnotationsRecyclerView)
    }

    companion object {
        fun newInstance(tabId: Long, tagText: String = "", notebookId: Long = 0L): AnnotationsFragment {
            val fragment = AnnotationsFragment()

            val args = BaseFragment.getBaseBundle(tabId)
            with(ArgumentOptions) {
                args.notebookId = notebookId
                args.tagText = tagText
            }

            fragment.arguments = args
            return fragment
        }
    }

    object ArgumentOptions {
        var Bundle.notebookId by BundleExtra.Long(defaultValue = 0L)
        var Bundle.tagText by BundleExtra.String(defaultValue = "")
    }
}
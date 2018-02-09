package org.lds.ldssa.ux.annotations.allannotations

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_note_collection_all_annotations.*
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.ui.fragment.BaseFragment
import org.lds.mobile.ui.setVisible
import javax.inject.Inject

/**
 * Fragment used to show a list of annotations
 * - All annotations ("All" section in "Notes")
 */
class AllAnnotationsFragment : BaseFragment() {

    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var notebookManager: NotebookManager
    @Inject
    lateinit var subItemMetadataManager: SubItemMetadataManager
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(AllAnnotationsViewModel::class.java) }

    private lateinit var adapter: AllAnnotationsAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Injector.get().inject(this)

        setupRecyclerView()
        setupViewModelObservers()
    }

    override fun getLayoutResourceId() = R.layout.fragment_note_collection_all_annotations

    private fun setupRecyclerView() {
        adapter = AllAnnotationsAdapter(screenId)

        allAnnotationsRecyclerView.adapter = adapter
        allAnnotationsRecyclerView.layoutManager = LinearLayoutManager(activity)
    }
    private fun setupViewModelObservers() {
        viewModel.allAnnotationList.observeNotNull { annotationList ->
            allAnnotationsLoadingView.setVisible(false)

            emptyStateIndicator.setVisible(annotationList.isEmpty())

            adapter.setList(annotationList)
        }
    }

    companion object {
        fun newInstance(screenId: Long): AllAnnotationsFragment {
            val fragment = AllAnnotationsFragment()

            val args = getBaseBundle(screenId)

            fragment.arguments = args
            return fragment
        }
    }
}
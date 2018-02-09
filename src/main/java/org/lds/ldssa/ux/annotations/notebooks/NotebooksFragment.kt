package org.lds.ldssa.ux.annotations.notebooks

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.fragment_note_collection_notebooks.*
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.database.userdata.notebookview.NotebookView
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.prefs.type.NotebookSortType
import org.lds.ldssa.sync.AnnotationSync
import org.lds.ldssa.ui.actionmode.NotesActionModeCallback
import org.lds.ldssa.ui.animation.CollapseAnimation
import org.lds.ldssa.ui.animation.ExpandAnimation
import org.lds.ldssa.ui.fragment.BaseFragment
import org.lds.ldssa.util.NotebookUtil
import org.lds.ldssa.util.ScrollViewHider
import org.lds.ldssa.ux.annotations.notes.NotesActivity
import org.lds.mobile.ui.setVisible
import javax.inject.Inject

class NotebooksFragment : BaseFragment() {

    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var notebookUtil: NotebookUtil
    @Inject
    lateinit var annotationSync: AnnotationSync
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(NotebooksViewModel::class.java) }

    private lateinit var adapter: NotebooksAdapter

    private var actionMode: ActionMode? = null
    private lateinit var notesActionModeCallback: NotesActionModeCallback

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Injector.get().inject(this)

        notesActionModeCallback = NotesActionModeCallback(activity).apply {
            actionModeStarted = { onActionModeStarted() }
            actionModeEnded = { onActionModeEnded() }
            actionModeMenuItemSelected = { menuItem -> onActionModeMenuItemSelected(menuItem) }
            actionModeSelectionCount = { adapter.getSelectedCount() }
        }

        adapter = NotebooksAdapter(cc).apply {
            actionImageItemClickListener = { onItemActionClick() }
            itemClickListener = { notebookView -> internalIntents.showNotebook(activity, screenId, notebookView.id) }
            menuItemClickListener = { notebookView, menuItem -> onMenuItemSelected(notebookView, menuItem) }
            actionModeVisible = { notesActionModeCallback.actionModeVisible }
        }

        notebooksRecyclerView.layoutManager = LinearLayoutManager(activity)
        notebooksRecyclerView.adapter = adapter

        ScrollViewHider(notebooksRecyclerView, filterContainer)
        notebooksListFilterEditText.onMenuClickListener = { view -> onSortClick(view) }

        swipeRefreshLayout.setOnRefreshListener { onSwipeRefresh() }

        setupViewModelObservers()
    }

    override fun getLayoutResourceId() = R.layout.fragment_note_collection_notebooks

    private fun setupViewModelObservers() {
        notebooksListFilterEditText.textWatcherLiveData.observeNotNull { filterText ->
            viewModel.setFilterText(filterText.toString())
        }

        viewModel.notebookList.observeNotNull { notebookViewList ->
            notesLoadingView.setVisible(false)

            val hasFilterText = notebooksListFilterEditText.getText().toString().isNotBlank()
            emptyStateIndicator.setVisible(notebookViewList.isEmpty() && hasFilterText.not())

            adapter.items = notebookViewList
        }
    }

    private fun onSwipeRefresh() {
        swipeRefreshLayout.isRefreshing = true
        launch(cc.ui) {
            run(cc.commonPool) {
                annotationSync.sync()
            }
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun onItemActionClick() {
        // start actionMode (if needed)
        if (notesActionModeCallback.actionModeVisible.not()) {
            val activity = activity as AppCompatActivity
            actionMode = activity.startSupportActionMode(notesActionModeCallback)
        }

        // update count on the action mode bar
        val selectionCount = adapter.getSelectedCount()
        notesActionModeCallback.updateMenu()

        if (selectionCount > 0) {
            actionMode?.title = context?.resources?.getQuantityString(R.plurals.num_items, selectionCount, selectionCount) ?: ""
        } else {
            actionMode?.finish()
        }
    }

    private fun onActionModeStarted() {
        val animation = CollapseAnimation(filterContainer, notebooksRecyclerView)
        filterContainer.startAnimation(animation)
        informActivityOfActionModeChange(true)
    }

    private fun onActionModeEnded() {
        adapter.clearSelection(notebooksRecyclerView)

        val animation = ExpandAnimation(filterContainer, notebooksRecyclerView)
        filterContainer.startAnimation(animation)
        informActivityOfActionModeChange(false)
    }

    private fun informActivityOfActionModeChange(actionModeStarted: Boolean) {
        val activity = activity
        (activity as? NotesActivity)?.actionModeChanged(actionModeStarted)
    }

    private fun onActionModeMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_item_delete ->
                notebookUtil.promptDelete(activity, adapter.selectedNotebookIds.toList()) {
                    actionMode?.finish()
                }
            R.id.menu_item_merge -> {
                notebookUtil.promptNotebookMerge(activity, adapter.selectedNotebookIds.toList())
                actionMode?.finish()
            }
        }

        return false
    }

    private fun onMenuItemSelected(notebookView: NotebookView, menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_popup_rename -> {
                notebookUtil.promptEdit(activity, notebookView.id)
                return true
            }
            R.id.menu_popup_delete -> {
                notebookUtil.promptDelete(activity, listOf(notebookView.id))
                return true
            }
        }
        return false
    }

    private fun onSortClick(view: View) {
        val context = activity
        context ?: return

        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.menu_note_collection_sort, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            item.isChecked = true
            val selectedSortType = NotebookSortType.getSortTypeFromMenuId(item.itemId)

            if (selectedSortType != prefs.notebookSortType) {
                // remove all data from the list to force the new sort to be scrolled to the top of the list
                adapter.items = emptyList()

                prefs.notebookSortType = NotebookSortType.getSortTypeFromMenuId(item.itemId)
                viewModel.setSortType(prefs.notebookSortType)
            }

            return@setOnMenuItemClickListener true
        }

        //sets the selected item
        popupMenu.menu.findItem(NotebookSortType.getSortMenuItemId(prefs.notebookSortType)).isChecked = true
        popupMenu.show()
    }

    companion object {
        fun newInstance(screenId: Long): NotebooksFragment {
            val args = BaseFragment.getBaseBundle(screenId)
            val fragment = NotebooksFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
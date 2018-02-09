package org.lds.ldssa.ux.annotations.tags

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.fragment_note_collection_tags.*
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.tagview.TagView
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.prefs.type.TagSortType
import org.lds.ldssa.sync.AnnotationSync
import org.lds.ldssa.ui.actionmode.NotesActionModeCallback
import org.lds.ldssa.ui.animation.CollapseAnimation
import org.lds.ldssa.ui.animation.ExpandAnimation
import org.lds.ldssa.ui.fragment.BaseFragment
import org.lds.ldssa.util.ScrollViewHider
import org.lds.ldssa.util.annotations.TagUtil
import org.lds.ldssa.ux.annotations.notes.NotesActivity
import org.lds.mobile.ui.setVisible
import javax.inject.Inject

class TagsFragment : BaseFragment() {

    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var tagUtil: TagUtil
    @Inject
    lateinit var annotationSync: AnnotationSync
    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(TagsViewModel::class.java) }

    private lateinit var adapter: TagsAdapter

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

        adapter = TagsAdapter(cc).apply {
            actionImageItemClickListener = { onItemActionClick() }
            itemClickListener = { tagView -> internalIntents.showTags(activity, screenId, tagView.name) }
            menuItemClickListener = { tagView, menuItem -> onMenuItemSelected(tagView, menuItem) }
            actionModeVisible = { notesActionModeCallback.actionModeVisible }
        }

        tagsRecyclerView.layoutManager = LinearLayoutManager(activity)
        tagsRecyclerView.adapter = adapter

        ScrollViewHider(tagsRecyclerView, filterContainer)

        swipeRefreshLayout.setOnRefreshListener { onSwipeRefresh() }

        setupViewModelObservers()
    }

    override fun getLayoutResourceId() = R.layout.fragment_note_collection_tags

    private fun setupViewModelObservers() {
        viewModel.tagList.observeNotNull { tagViewList ->
            tagsLoadingView.setVisible(false)

            val hasFilterText = tagsListFilterEditText.getText().toString().isNotBlank()
            emptyStateIndicator.setVisible(tagViewList.isEmpty() && hasFilterText.not())

            adapter.items = tagViewList
        }

        tagsListFilterEditText.textWatcherLiveData.observeNotNull { filterText ->
            viewModel.setFilterText(filterText.toString())
        }
        tagsListFilterEditText.onMenuClickListener = { view -> onSortClick(view) }
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
        val animation = CollapseAnimation(filterContainer, tagsRecyclerView)
        filterContainer.startAnimation(animation)
        informActivityOfActionModeChange(true)
    }

    private fun onActionModeEnded() {
        adapter.clearSelection(tagsRecyclerView)

        val animation = ExpandAnimation(filterContainer, tagsRecyclerView)
        filterContainer.startAnimation(animation)
        informActivityOfActionModeChange(false)
    }

    private fun informActivityOfActionModeChange(actionModeStarted: Boolean) {
        val activity = activity
        (activity as? NotesActivity)?.actionModeChanged(actionModeStarted)
    }

    private fun onActionModeMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_item_delete -> {
                tagUtil.promptDelete(activity, adapter.selectedTagNames.toList())
                actionMode?.finish()
            }
            R.id.menu_item_merge -> {
                tagUtil.promptMerge(activity, adapter.selectedTagNames.toList())
                actionMode?.finish()
            }
        }

        return false
    }

    private fun onMenuItemSelected(tagView: TagView, menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_popup_rename -> {
                tagUtil.promptEdit(activity, tagView.name)
                return true
            }
            R.id.menu_popup_delete -> {
                tagUtil.promptDelete(activity, tagView.name)
                return true
            }
        }
        return false
    }

    private fun onSortClick(view: View) {
        val context = activity
        context ?: return
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.menu_tag_sort, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            item.isChecked = true
            val selectedSortType = TagSortType.getSortTypeFromMenuId(item.itemId)

            if (selectedSortType != prefs.tagSortType) {
                // remove all data from the list to force the new sort to be scrolled to the top of the list
                adapter.items = emptyList()

                prefs.tagSortType = selectedSortType
                viewModel.setSortType(prefs.tagSortType)
            }

            return@setOnMenuItemClickListener true
        }

        //sets the selected item
        popupMenu.menu.findItem(TagSortType.getSortMenuItemId(prefs.tagSortType)).isChecked = true
        popupMenu.show()
    }

    companion object {
        fun newInstance(tabId: Long): TagsFragment {
            val args = BaseFragment.getBaseBundle(tabId)
            val fragment = TagsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

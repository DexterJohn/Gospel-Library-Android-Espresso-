package org.lds.ldssa.ux.annotations.notebooks

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.notebookview.NotebookView
import org.lds.ldssa.model.database.userdata.notebookview.NotebookViewManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.prefs.type.NotebookSortType
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.livedata.AbsentLiveData
import javax.inject.Inject

class NotebooksViewModel
@Inject constructor(
        private val notebookManager: NotebookManager,
        private val notebookViewManager: NotebookViewManager,
        private val annotationManager: AnnotationManager,
        private val prefs: Prefs,
        private val cc: CoroutineContextProvider
) : ViewModel() {

    private val notebookListFilterData = MutableLiveData<NotebooksListFilterData>()

    val notebookList: LiveData<List<NotebookView>>

    init {
        notebookList = AbsentLiveData.switchMap(notebookListFilterData) {
            loadNotebooks(it)
        }

        // since this is a full list... give an initial value
        notebookListFilterData.value = NotebooksListFilterData(sortType = prefs.notebookSortType)
    }

    fun setFilterText(filterText: String) {
        if (notebookListFilterData.value?.filterText != filterText) {
            notebookListFilterData.value = notebookListFilterData.value?.copy(filterText = filterText) ?: NotebooksListFilterData(filterText = filterText)
        }
    }

    fun setSortType(sortType: NotebookSortType) {
        if (notebookListFilterData.value?.sortType != sortType) {
            notebookListFilterData.value = notebookListFilterData.value?.copy(sortType = sortType) ?: NotebooksListFilterData(sortType = sortType)
            prefs.notebookSortType = sortType
        }
    }

    private fun loadNotebooks(notebooksListFilterData: NotebooksListFilterData): LiveData<List<NotebookView>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, listOf(notebookManager, annotationManager)) {
            val filterText = notebooksListFilterData.filterText

            return@toLiveData notebookViewManager.findAllFilter(filterText, notebooksListFilterData.sortType)
        }
    }

    data class NotebooksListFilterData(val filterText: String = "", val sortType: NotebookSortType = NotebookSortType.MOST_RECENT)
}
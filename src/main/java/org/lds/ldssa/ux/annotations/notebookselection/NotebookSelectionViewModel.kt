package org.lds.ldssa.ux.annotations.notebookselection

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager
import org.lds.ldssa.model.database.userdata.notebookview.NotebookView
import org.lds.ldssa.model.database.userdata.notebookview.NotebookViewManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.prefs.type.NotebookSortType
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.livedata.AbsentLiveData
import javax.inject.Inject

class NotebookSelectionViewModel
@Inject constructor(
        private val notebookViewManager: NotebookViewManager,
        private val annotationManager: AnnotationManager,
        private val notebookManager: NotebookManager,
        private val notebookAnnotationManager: NotebookAnnotationManager,
        private val prefs: Prefs,
        private val cc: CoroutineContextProvider
) : ViewModel() {

    private val notebookSelectionData = MutableLiveData<NotebookSelectionData>()

    val notebookList: LiveData<List<NotebookView>>

    init {
        notebookList = AbsentLiveData.switchMap(notebookSelectionData) {
            when (it.annotationId) {
                0L -> AbsentLiveData.create()
                else -> loadNotebooksForAnnotation(it)
            }
        }
    }

    fun setAnnotationId(annotationId: Long) {
        if (notebookSelectionData.value?.annotationId != annotationId) {
            notebookSelectionData.value = notebookSelectionData.value?.copy(annotationId = annotationId) ?: NotebookSelectionData(annotationId, sortType = prefs.notebookSortType)
        }
    }

    fun setSortType(sortType: NotebookSortType) {
        if (notebookSelectionData.value?.sortType != sortType) {
            notebookSelectionData.value = notebookSelectionData.value?.copy(sortType = sortType) ?: NotebookSelectionData(sortType = sortType)
            prefs.notebookSortType = sortType
        }
    }

    fun addToNotebook(notebookId: Long) {
        val annotationId = notebookSelectionData.value?.annotationId
        annotationId ?: return

        annotationManager.addToNotebook(annotationId, notebookId)
    }

    fun removeFromNotebook(notebookId: Long) {
        val annotationId = notebookSelectionData.value?.annotationId
        annotationId ?: return

        annotationManager.removeFromNotebook(annotationId, notebookId)
    }

    private fun loadNotebooksForAnnotation(selectionData: NotebookSelectionData): LiveData<List<NotebookView>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, listOf(notebookAnnotationManager, notebookManager)) {
            return@toLiveData notebookViewManager.findAllOrderBy(selectionData.sortType, selectionData.annotationId)
        }
    }

    data class NotebookSelectionData(val annotationId: Long = 0L, val sortType: NotebookSortType = NotebookSortType.MOST_RECENT)
}
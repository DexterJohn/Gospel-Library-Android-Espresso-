package org.lds.ldssa.ux.annotations

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.livedata.AbsentLiveData
import javax.inject.Inject

class AnnotationsViewModel
@Inject constructor(
        private val annotationManager: AnnotationManager,
        private val cc: CoroutineContextProvider
) : ViewModel() {

    private val annotationListData = MutableLiveData<AnnotationsListData>()

    val annotationList: LiveData<List<Annotation>>

    var scrollPosition = 0

    init {
        annotationList = AbsentLiveData.switchMap(annotationListData) {
            loadAnnotations(it)
        }
    }

    fun setTagText(tagText: String) {
        if (annotationListData.value?.tagText != tagText) {
            annotationListData.value = annotationListData.value?.copy(tagText = tagText) ?: AnnotationsListData(tagText = tagText)
        }
    }

    fun setNotebookId(notebookId: Long) {
        if (annotationListData.value?.notebookId != notebookId) {
            annotationListData.value = annotationListData.value?.copy(notebookId = notebookId) ?: AnnotationsListData(notebookId = notebookId)
        }
    }

    private fun loadAnnotations(annotationsListData: AnnotationsListData): LiveData<List<Annotation>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, listOf(annotationManager)) {
            when {
                annotationsListData.notebookId > 0L -> annotationManager.findAllByNotebook(annotationsListData.notebookId)
                else -> annotationManager.findAllByTag(annotationsListData.tagText)
            }
        }
    }

    data class AnnotationsListData(val tagText: String = "", val notebookId: Long = 0L)
}
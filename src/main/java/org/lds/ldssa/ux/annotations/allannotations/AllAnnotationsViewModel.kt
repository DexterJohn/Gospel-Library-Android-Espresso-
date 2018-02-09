package org.lds.ldssa.ux.annotations.allannotations

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import javax.inject.Inject

class AllAnnotationsViewModel
@Inject constructor(annotationManager: AnnotationManager) : ViewModel() {

    val allAnnotationList: LiveData<PagedList<Annotation>>

    init {
        val pageListConfig = PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setPrefetchDistance(PREFETCH_DISTANCE)
                .build()

        allAnnotationList = annotationManager.findAllLivePagedListProvider().create(INITIAL_LOAD_POSITION, pageListConfig)
    }

    companion object {
        private const val INITIAL_LOAD_POSITION = 0
        private const val PAGE_SIZE = 25
        private const val PREFETCH_DISTANCE = 25
    }
}
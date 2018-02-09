package org.lds.ldssa.ux.annotations.tags

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.tag.TagManager
import org.lds.ldssa.model.database.userdata.tagview.TagView
import org.lds.ldssa.model.database.userdata.tagview.TagViewManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.prefs.type.TagSortType
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.livedata.AbsentLiveData
import javax.inject.Inject

class TagsViewModel
@Inject constructor(
        private val tagManager: TagManager,
        private val tagViewManager: TagViewManager,
        private val annotationManager: AnnotationManager,
        private val prefs: Prefs,
        private val cc: CoroutineContextProvider
) : ViewModel() {

    private val tagListFilterData = MutableLiveData<TagListFilterData>()

    val tagList: LiveData<List<TagView>>

    init {
        tagList = AbsentLiveData.switchMap(tagListFilterData) {
            loadTags(it)
        }

        // since this is a full list... give an initial value
        tagListFilterData.value = TagListFilterData(sortType = prefs.tagSortType)

    }

    fun setFilterText(filterText: String) {
        if (tagListFilterData.value?.filterText != filterText) {
            tagListFilterData.value = tagListFilterData.value?.copy(filterText = filterText) ?: TagListFilterData(filterText = filterText)
        }
    }

    fun setSortType(sortType: TagSortType) {
        if (tagListFilterData.value?.sortType != sortType) {
            tagListFilterData.value = tagListFilterData.value?.copy(sortType = sortType) ?: TagListFilterData(sortType = sortType)
            prefs.tagSortType = sortType
        }
    }

    private fun loadTags(tagListFilterData: TagListFilterData): LiveData<List<TagView>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, listOf(tagManager, annotationManager)) {
            val filterText = tagListFilterData.filterText

            if (filterText.isBlank()) {
                return@toLiveData tagViewManager.findAllOrderBy(tagListFilterData.sortType)
            } else {
                return@toLiveData tagViewManager.findAllFilter(filterText, tagListFilterData.sortType).toMutableList()
            }
        }
    }

    data class TagListFilterData(val filterText: String = "", val sortType: TagSortType = TagSortType.MOST_RECENT)
}
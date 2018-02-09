package org.lds.ldssa.ux.annotations.tagselection

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.database.userdata.tag.Tag
import org.lds.ldssa.model.database.userdata.tag.TagManager
import org.lds.ldssa.model.database.userdata.tagview.TagView
import org.lds.ldssa.model.database.userdata.tagview.TagViewManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.prefs.type.TagSortType
import org.lds.ldssa.util.annotations.TagUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.livedata.AbsentLiveData
import javax.inject.Inject

class TagSelectionViewModel
@Inject constructor(
        private val tagManager: TagManager,
        private val tagViewManager: TagViewManager,
        private val tagUtil: TagUtil,
        private val prefs: Prefs,
        private val cc: CoroutineContextProvider
) : ViewModel() {

    private val annotationId = MutableLiveData<Long>()
    private val tagSelectionData = MutableLiveData<TagSelectionData>()

    val tagsForAnnotationList: LiveData<List<Tag>>
    val tagsAvailableList: LiveData<List<TagView>>

    init {
        tagsForAnnotationList = AbsentLiveData.switchMap(annotationId, 0L) {
            loadTagsForAnnotation(it)
        }
        tagsAvailableList = AbsentLiveData.switchMap(tagSelectionData) {
            when (it.annotationId) {
                0L -> AbsentLiveData.create()
                else -> loadTagsAvailable(it)
            }
        }
    }

    fun setAnnotationId(annotationId: Long) {
        if (this.annotationId.value != annotationId) {
            this.annotationId.value = annotationId
        }

        if (tagSelectionData.value?.annotationId != annotationId) {
            tagSelectionData.value = tagSelectionData.value?.copy(annotationId = annotationId) ?: TagSelectionData(annotationId, sortType = prefs.tagSortType)
        }
    }

    fun setFilterText(filterText: String) {
        if (tagSelectionData.value?.filterText != filterText) {
            tagSelectionData.value = tagSelectionData.value?.copy(filterText = filterText) ?: TagSelectionData(filterText = filterText)
        }
    }

    fun setSortType(sortType: TagSortType) {
        if (tagSelectionData.value?.sortType != sortType) {
            tagSelectionData.value = tagSelectionData.value?.copy(sortType = sortType) ?: TagSelectionData(sortType = sortType)
            prefs.tagSortType = sortType
        }
    }

    fun addTag(name: String) {
        val formattedTagText = name.trim { it <= ' ' }

        //Makes sure the new tag is not blank
        if (formattedTagText.isBlank()) {
            return
        }

        tagSelectionData.value?.let {
            tagUtil.add(it.annotationId, name)
        }
    }

    fun removeTag(name: String) {
        tagSelectionData.value?.let {
            tagUtil.delete(it.annotationId, name)
        }
    }

    private fun loadTagsForAnnotation(annotationId: Long): LiveData<List<Tag>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, tagManager) {
            tagManager.findAllByAnnotationId(annotationId)
        }
    }

    private fun loadTagsAvailable(tagSelectionData: TagSelectionData): LiveData<List<TagView>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, tagManager) {
            val filterText = tagSelectionData.filterText
            if (filterText.isBlank()) {
                return@toLiveData tagViewManager.findAllOrderBy(tagSelectionData.sortType, tagSelectionData.annotationId)
            } else {
                val list = tagViewManager.findAllFilter(filterText, tagSelectionData.sortType, tagSelectionData.annotationId).toMutableList()
                val tagNameExists = tagViewManager.findTagNameExists(filterText)

                // add a "Create Tag" item in position 0 if needed
                if (!tagNameExists) {
                    list.add(0, TagView(true).apply {
                        name = tagSelectionData.filterText
                    })
                }

                return@toLiveData list
            }
        }
    }

    data class TagSelectionData(val annotationId: Long = 0L, val filterText: String = "", val sortType: TagSortType = TagSortType.ALPHABETICAL)
}
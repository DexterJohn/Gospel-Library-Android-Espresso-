package org.lds.ldssa.ux.annotations.links

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.launch
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.GLContentContext
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadata
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager
import org.lds.ldssa.model.database.content.navsection.NavSectionManager
import org.lds.ldssa.model.database.content.subitem.SubItemManager
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestion
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestionManager
import org.lds.ldssa.model.database.userdata.link.Link
import org.lds.ldssa.model.database.userdata.link.LinkManager
import org.lds.ldssa.util.annotations.LinkUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.livedata.AbsentLiveData
import org.lds.mobile.livedata.SingleLiveEvent
import javax.inject.Inject

class LinksViewModel
@Inject constructor(
        private val linkManager: LinkManager,
        private val linkUtil: LinkUtil,
        private val navSectionManager: NavSectionManager,
        private val searchSuggestionManager: SearchSuggestionManager,
        private val subItemManager: SubItemManager,
        private val subItemMetadataManager: SubItemMetadataManager,
        private val cc: CoroutineContextProvider
) : ViewModel() {

    val linksForAnnotation: LiveData<List<Link>>
    val suggestions = MutableLiveData<List<SearchSuggestion>>()

    val showLinkContent = SingleLiveEvent<ShowLinkTagEvent>()

    private val annotationId = MutableLiveData<Long>()

    init {
        linksForAnnotation = AbsentLiveData.switchMap(annotationId, 0L) { loadLinksForAnnotation(it) }
    }

    fun setAnnotationId(annotationId: Long) {
        if (annotationId != this.annotationId.value) {
            this.annotationId.value = annotationId
        }
    }

    fun setSearchText(searchText: String, languageId: Long) = launch(cc.commonPool) {
        val list = if (searchText.isEmpty()) {
            emptyList()
        } else {
            searchSuggestionManager.findSuggestions(languageId, searchText, GLContentContext())
        }
        suggestions.postValue(list)
    }

    fun addLink(contentItemId: Long, subItemId: Long, paragraphAid: String, searchTitle: String) = launch(cc.commonPool) {
        val docId = subItemManager.findDocIdById(contentItemId, subItemId)
        annotationId.value?.let {
            linkUtil.add(it, docId, paragraphAid, searchTitle)
        }
    }

    fun deleteLink(linkId: Long) = launch(cc.commonPool) {
        annotationId.value?.let {
            linkUtil.delete(it, linkId)
        }
    }

    fun showLink(linkId: Long) = launch(cc.commonPool) {
        val link = linkManager.findByRowId(linkId)
        link?.docId?.let { docId ->
            subItemMetadataManager.findByDocId(docId)?.let { subItemMetadata ->
                showLinkContent.postValue(ShowLinkTagEvent(linkId, subItemMetadata))
            }
        }
    }

    // find ALL nav items for the given contentItemId
    fun searchCollection(contentItemId: Long) = launch(cc.commonPool) {
        suggestions.postValue(searchSuggestionManager.findSuggestionsByContentItemId(contentItemId, 0L))
    }

    // a specific subItem could not be identified, so find subItems for given collection
    fun searchSubItems(contentItemId: Long, searchSuggestionId: Long) = launch(cc.commonPool) {
        val navSectionId = navSectionManager.findIdByCollectionId(contentItemId, searchSuggestionId)
        suggestions.postValue(searchSuggestionManager.findSuggestionsByContentItemId(contentItemId, navSectionId))
    }

    private fun loadLinksForAnnotation(annotationId: Long): LiveData<List<Link>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, linkManager) {
            linkManager.findAllByAnnotationId(annotationId)
        }
    }

    class ShowLinkTagEvent(val linkId: Long, val subItemMetadata: SubItemMetadata)
}
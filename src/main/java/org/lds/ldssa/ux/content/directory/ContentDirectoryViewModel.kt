package org.lds.ldssa.ux.content.directory

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.experimental.launch
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.GLContentContext
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQuery
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager
import org.lds.ldssa.model.database.content.contentdirectoryitemquery.ContentDirectoryItemQuery
import org.lds.ldssa.model.database.content.contentdirectoryitemquery.ContentDirectoryItemQueryManager
import org.lds.ldssa.model.database.content.navcollection.NavCollection
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager
import org.lds.ldssa.model.database.content.navcollectionindexentry.NavCollectionIndexEntry
import org.lds.ldssa.model.database.content.navcollectionindexentry.NavCollectionIndexEntryManager
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasContentDirectory
import org.lds.ldssa.util.ScreenUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.livedata.AbsentLiveData
import javax.inject.Inject

class ContentDirectoryViewModel
@Inject constructor(
        private val gson: Gson,
        private val contentDirectoryItemQueryManager: ContentDirectoryItemQueryManager,
        private val navCollectionManager: NavCollectionManager,
        private val navCollectionIndexEntryManager: NavCollectionIndexEntryManager,
        private val itemManager: ItemManager,
        private val screenUtil: ScreenUtil,
        private val trailQueryManager: NavigationTrailQueryManager,
        private val cc: CoroutineContextProvider
) : ViewModel() {

    private val contentDirectoryData = MutableLiveData<ContentDirectoryData>()

    // observable live data items
    val contentDirectoryDirectoryList: LiveData<List<ContentDirectoryItemQuery>>
    val fastScrollIndexEntriesList: LiveData<List<NavCollectionIndexEntry>>
    val navigationTrailList: LiveData<List<NavigationTrailQuery>>
    val contentDirectoryTitle: LiveData<ContentDirectoryTitle>

    // Todo.... move more code from activity to ViewModel to no longer need these to be accessed from the Activity
    // data from intents
    val screenId: Long
        get() {
            return contentDirectoryData.value?.screenId ?: 0L
        }
    val contentItemId: Long
        get() {
            return contentDirectoryData.value?.contentItemId ?: 0L
        }
    val navCollectionId: Long
        get() {
            return contentDirectoryData.value?.navCollectionId ?: 0L
        }

    var navCollectionUri = ""
        private set

    // save state
    var lastScrollPosition = 0

    init {
        contentDirectoryDirectoryList = AbsentLiveData.switchMap(contentDirectoryData) {
            loadContentDirectoryList(it)
        }

        fastScrollIndexEntriesList = AbsentLiveData.switchMap(contentDirectoryData) {
            loadFastScrollIndexData(it)
        }

        navigationTrailList = AbsentLiveData.switchMap(contentDirectoryData) {
            loadNavigationTrail(it)
        }

        contentDirectoryTitle = AbsentLiveData.switchMap(contentDirectoryData) {
            loadTitle(it)
        }
    }

    fun setContentDirectoryData(screenId: Long, contentItemId: Long, navCollectionId: Long) {
        val newData = ContentDirectoryData(screenId, contentItemId, navCollectionId)
        if (contentDirectoryData.value != newData) {
            navCollectionUri = when (navCollectionId) {
                NavCollection.ROOT_NAV_COLLECTION_ID -> navCollectionManager.findRootCollectionUri(contentItemId)
                else -> navCollectionManager.findUriById(contentItemId, navCollectionId)
            }

            contentDirectoryData.value = newData

            launch(cc.commonPool) {
                screenUtil.updateLanguage(screenId, itemManager.findLanguageIdById(contentItemId))
            }
        }
    }

    fun reloadContentDirectory() {
        contentDirectoryData.value = contentDirectoryData.value
    }

    private fun loadTitle(contentDirectoryData: ContentDirectoryData): LiveData<ContentDirectoryTitle> {
        return DBToolsLiveData.toLiveData(cc.commonPool) {
            var titleNavCollectionId = contentDirectoryData.navCollectionId
            if (titleNavCollectionId == NavCollection.ROOT_NAV_COLLECTION_ID) {
                titleNavCollectionId = navCollectionManager.findRootCollectionId(contentDirectoryData.contentItemId)
            }

            val title = navCollectionManager.findCollectionTitleById(contentDirectoryData.contentItemId, titleNavCollectionId)

            return@toLiveData if (title.contains(':')) {
                val parts = title.split(":", limit = 2)
                ContentDirectoryTitle(parts[1], parts[0])
            } else {
                ContentDirectoryTitle(title, "")
            }
        }
    }

    private fun loadContentDirectoryList(contentDirectoryData: ContentDirectoryData): LiveData<List<ContentDirectoryItemQuery>> {
        return DBToolsLiveData.toLiveData(cc.commonPool) {
            if (contentDirectoryData.navCollectionId == 0L) {
                contentDirectoryItemQueryManager.findAllByContentItemId(contentDirectoryData.contentItemId)
            } else {
                contentDirectoryItemQueryManager.findAllByCollectionId(contentDirectoryData.contentItemId, contentDirectoryData.navCollectionId)
            }
        }
    }

    private fun loadFastScrollIndexData(contentDirectoryData: ContentDirectoryData): LiveData<List<NavCollectionIndexEntry>> {
        return DBToolsLiveData.toLiveData(cc.commonPool) {
            val itemCollectionId = if (contentDirectoryData.navCollectionId > NavCollection.ROOT_NAV_COLLECTION_ID) contentDirectoryData.navCollectionId else NavCollection.ROOT_NAV_COLLECTION_ID
            return@toLiveData navCollectionIndexEntryManager.findAllById(contentDirectoryData.contentItemId, itemCollectionId)
        }
    }

    private fun loadNavigationTrail(contentDirectoryData: ContentDirectoryData): LiveData<List<NavigationTrailQuery>> {
        return DBToolsLiveData.toLiveData(cc.commonPool) {
            return@toLiveData trailQueryManager.findDefaultAllForContentDirectory(contentDirectoryData.contentItemId, contentDirectoryData.navCollectionId)
        }
    }

    fun getGlContentContext(): GLContentContext {
        return GLContentContext(0L, contentItemId, navCollectionId, 0L)
    }

    fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem): Boolean {
        if (screenHistoryItem.sourceType === ScreenSourceType.CONTENT_DIRECTORY) {
            val extras = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasContentDirectory::class.java)
            return extras.contentItemId == contentItemId && navCollectionUri == extras.navCollectionUri
        }
        return false
    }

    fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem, currentScrollPosition: Int) {
        screenHistoryItem.sourceType = ScreenSourceType.CONTENT_DIRECTORY

        val tabTitleNavId = if (navCollectionId != NavCollection.ROOT_NAV_COLLECTION_ID) navCollectionId else navCollectionManager.findRootCollectionId(contentItemId)
        val description = when (navCollectionId) {
            NavCollection.ROOT_NAV_COLLECTION_ID -> navCollectionManager.findCollectionTitleById(contentItemId, tabTitleNavId)
            else -> navCollectionManager.findCollectionParentTitleById(contentItemId, tabTitleNavId)
        }

        val title = navCollectionManager.findCollectionTitleById(contentItemId, tabTitleNavId)

        screenHistoryItem.title = title
        screenHistoryItem.description = description

        screenHistoryItem.setExtras(gson, ScreenHistoryExtrasContentDirectory(contentItemId, navCollectionUri, currentScrollPosition))
    }

    data class ContentDirectoryTitle(val title: String, val subTitle: String)
    data class ContentDirectoryData(val screenId: Long, val contentItemId: Long, val navCollectionId: Long)
}
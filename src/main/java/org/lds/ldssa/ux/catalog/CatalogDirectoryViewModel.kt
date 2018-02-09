package org.lds.ldssa.ux.catalog

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.R
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQuery
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQueryManager
import org.lds.ldssa.model.database.catalog.language.LanguageManager
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager
import org.lds.ldssa.model.database.tips.tip.TipManager
import org.lds.ldssa.model.database.types.CatalogItemQueryType
import org.lds.ldssa.model.database.types.LibraryCollectionType
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.util.LanguageUtil
import org.lds.ldssa.util.ScreenUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.livedata.AbsentLiveData
import org.lds.mobile.ui.recyclerview.ListItemWithHeader
import org.lds.mobile.ui.toListItemsWithHeader
import javax.inject.Inject

class CatalogDirectoryViewModel
@Inject constructor(
        val application: Application, // todo remove and change usages to resource ids (better for testing)
        private val tipManager: TipManager,
        private val languageUtil: LanguageUtil,
        private val catalogQueryManager: CatalogItemQueryManager,
        private val languageManager: LanguageManager,
        private val prefs: Prefs,
        private val screenUtil: ScreenUtil,
        private val downloadedItemManager: DownloadedItemManager,
        private val customCollectionManager: CustomCollectionManager,
        private val cc: CoroutineContextProvider
) : ViewModel() {

    val catalogDirectoryList: LiveData<List<ListItemWithHeader<CatalogItemQuery, String>>>
    var lastScrollPosition = 0
    var restoreScrollPosition = true


    private val catalogData = MutableLiveData<CatalogData>()

    init {
        catalogDirectoryList = AbsentLiveData.switchMap(catalogData) {
            loadCatalogDirectory(it.collectionId, it.screenId)
        }
    }

    fun reloadCatalog() {
        catalogData.value = catalogData.value
    }

    fun setCatalogData(collectionId: Long, screenId: Long) {
        val data = CatalogData(collectionId, screenId)
        if (catalogData.value != data) {
            catalogData.value = data
        }
    }

    private fun loadCatalogDirectory(collectionId: Long, screenId: Long): LiveData<List<ListItemWithHeader<CatalogItemQuery, String>>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, listOf(downloadedItemManager, customCollectionManager)) {
            // list of all items in collection
            val collectionItems = catalogQueryManager.findCatalogListView(collectionId, screenUtil.getLanguageIdForScreen(screenId), prefs.isObsoleteContentEnabled).toMutableList()

            // list of contentIds, if there are any in the collection
            val contentItemIds = collectionItems.filter { it.type == CatalogItemQueryType.COLLECTION_CONTENT_ITEM }.map { it.id }

            // identify if content items are installed
            if (contentItemIds.isNotEmpty()) {
                val downloadedContentIdSet = downloadedItemManager.findContentItemIdsInstalled(contentItemIds).toHashSet()

                collectionItems.filter { it.type == CatalogItemQueryType.COLLECTION_CONTENT_ITEM && downloadedContentIdSet.contains(it.id) }
                        .forEach { it.installed = true }
            }

            if (languageManager.isRootCollection(collectionId)) {
                val includeStudyPlans = collectionItems.any { it.id == STUDY_PLANS_ID_KEY }
                addRootCatalogs(collectionItems, includeStudyPlans)
            }

            // add headers in if needed
            if (prefs.generalDisplayAsList) {
                return@toLiveData collectionItems.toListItemsWithHeader({ it.sectionId }, { it.sectionTitle })
            } else {
                // items without headers
                return@toLiveData collectionItems.map { ListItemWithHeader<CatalogItemQuery, String>(it) }.toMutableList()
            }
        }
    }

    private fun createNotesCatalogItem(): CatalogItemQuery {
        val notesCatalogItem = CatalogItemQuery()

        val myCollectionsSectionTitle = application.getString(R.string.my_collections)
        notesCatalogItem.title = application.getString(R.string.notes)
        notesCatalogItem.sectionId = 0
        notesCatalogItem.type = CatalogItemQueryType.NOTES
        notesCatalogItem.sectionTitle = myCollectionsSectionTitle

        return notesCatalogItem
    }

    private fun createTipsCatalogItem(): CatalogItemQuery {
        val tipsCatalogItem = CatalogItemQuery()
        tipsCatalogItem.title = application.getString(R.string.tips)
        tipsCatalogItem.sectionId = 0
        tipsCatalogItem.type = CatalogItemQueryType.TIPS
        tipsCatalogItem.sectionTitle = application.getString(R.string.tips)
        return tipsCatalogItem
    }

    private fun createStudyPlansCatalogItem() = CatalogItemQuery().apply {
        title = application.getString(R.string.study_plans)
        sectionId = 0
        type = CatalogItemQueryType.STUDY_PLANS
        sectionTitle = application.getString(R.string.study_plans)
    }

    /**
     * When viewing the root Catalog we also need to display:
     * - Notes
     * - Custom Collections
     * - Tips
     */
    private fun addRootCatalogs(items: MutableList<CatalogItemQuery>, includeStudyPlans: Boolean): MutableList<CatalogItemQuery> {
        val noteQuery = createNotesCatalogItem()

        var insertIndex = getScriptureItemCount(items)
        items.add(insertIndex++, noteQuery)
        if (includeStudyPlans) {
            val studyPlans = createStudyPlansCatalogItem()
            items.add(insertIndex++, studyPlans)
        }
        items.addAll(insertIndex, getCustomCollections(noteQuery.sectionTitle))

        // only add tips if there are tips for this language
        val tipsCountForTabLanguage = tipManager.findCountByLanguageId(languageUtil.deviceLanguageId)
        if (tipsCountForTabLanguage > 0) {
            items.add(createTipsCatalogItem())
        }

        return items
    }

    private fun getCustomCollections(sectionTitle: String?): List<CatalogItemQuery> {
        val collections = catalogQueryManager.findAllCustomCollections()
        for (collection in collections) {
            collection.sectionId = 0
            collection.sectionTitle = sectionTitle
        }

        return collections
    }

    private fun getScriptureItemCount(items: List<CatalogItemQuery>): Int {
        //Determines the sectionId for scriptures
        val scripturesSectionId: Long = items.firstOrNull { it.collectionType === LibraryCollectionType.SCRIPTURES }?.sectionId ?: -1

        //Determines the item count for scriptures
        return items.takeWhile { it.sectionId == scripturesSectionId }.count()
    }

    data class CatalogData(val collectionId: Long, val screenId: Long)

    companion object {
        const val STUDY_PLANS_ID_KEY = 71402L
    }
}
package org.lds.ldssa.ux.customcollection.collections

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQuery
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQueryManager
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionManager
import org.lds.mobile.coroutine.CoroutineContextProvider
import javax.inject.Inject

class CustomCollectionsViewModel
@Inject constructor(private val catalogItemQueryManager: CatalogItemQueryManager,
                    private val customCollectionManager: CustomCollectionManager,
                    private val cc: CoroutineContextProvider) : ViewModel() {

    val customCollectionList: LiveData<List<CatalogItemQuery>>

    init {
        customCollectionList = loadCustomCollections()
    }

    private fun loadCustomCollections(): LiveData<List<CatalogItemQuery>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, listOf(customCollectionManager)) {
            return@toLiveData catalogItemQueryManager.findAllCustomCollections()
        }
    }
}
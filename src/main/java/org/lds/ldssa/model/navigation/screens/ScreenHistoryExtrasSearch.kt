package org.lds.ldssa.model.navigation.screens

import org.lds.ldssa.model.GLContentContext
import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras
import org.lds.ldssa.ux.search.SearchMode
import java.util.ArrayList

class ScreenHistoryExtrasSearch() : NavigationHistoryItemExtras() {

    var searchText = ""
    var mode = SearchMode.HISTORY
    var modeCollectionId = 0L
    var modeContentItemId = 0L
    var glContentContext = GLContentContext()
    var scrollPosition = 0

    constructor(searchText: String,
                mode: SearchMode,
                modeCollectionId: Long,
                modeContentItemId: Long,
                glContentContext: GLContentContext,
                scrollPosition: Int) : this() {
        this.searchText = searchText
        this.mode = mode
        this.modeCollectionId = modeCollectionId
        this.modeContentItemId = modeContentItemId
        this.glContentContext = glContentContext
        this.scrollPosition = scrollPosition
    }

    override fun getExtras(): List<DtoNavigationHistoryItemExtra> {
        verifyRequiredExtras()

        val extrasList = ArrayList<DtoNavigationHistoryItemExtra>()
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_MODE, mode.ordinal))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_MODE_COLLECTION_ID, modeCollectionId))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_MODE_CONTENT_ITEM_ID, modeContentItemId))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_CONTEXT_LIBRARY_COLLECTION_ID, glContentContext.libraryCollectionId))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_CONTEXT_CONTENT_ITEM_ID, glContentContext.contentItemId))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_CONTEXT_NAV_COLLECTION_ID, glContentContext.navCollectionId))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_CONTEXT_SUB_ITEM_ID, glContentContext.subItemId))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_SEARCH_TEXT, searchText))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_SCROLL_POSITION, scrollPosition))

        return extrasList
    }

    override fun setExtras(extrasList: List<DtoNavigationHistoryItemExtra>) {
        for (dtoNavigationHistoryItemExtra in extrasList) {
            when (dtoNavigationHistoryItemExtra.key) {
                EXTRA_KEY_MODE -> mode = SearchMode.values()[dtoNavigationHistoryItemExtra.valueAsInt]
                EXTRA_KEY_MODE_COLLECTION_ID -> modeCollectionId = dtoNavigationHistoryItemExtra.valueAsLong
                EXTRA_KEY_MODE_CONTENT_ITEM_ID -> modeContentItemId = dtoNavigationHistoryItemExtra.valueAsLong
                EXTRA_KEY_CONTEXT_LIBRARY_COLLECTION_ID -> glContentContext.libraryCollectionId = dtoNavigationHistoryItemExtra.valueAsLong
                EXTRA_KEY_CONTEXT_CONTENT_ITEM_ID -> glContentContext.contentItemId = dtoNavigationHistoryItemExtra.valueAsLong
                EXTRA_KEY_CONTEXT_NAV_COLLECTION_ID -> glContentContext.navCollectionId = dtoNavigationHistoryItemExtra.valueAsLong
                EXTRA_KEY_CONTEXT_SUB_ITEM_ID -> glContentContext.subItemId = dtoNavigationHistoryItemExtra.valueAsLong
                EXTRA_KEY_SEARCH_TEXT -> searchText = dtoNavigationHistoryItemExtra.value
                EXTRA_KEY_SCROLL_POSITION -> scrollPosition = dtoNavigationHistoryItemExtra.valueAsInt
            }
        }

        verifyRequiredExtras()
    }

    private fun verifyRequiredExtras() {}

    companion object {
        private val EXTRA_KEY_MODE = "mode"
        private val EXTRA_KEY_MODE_COLLECTION_ID = "modeCollectionId"
        private val EXTRA_KEY_MODE_CONTENT_ITEM_ID = "modeContentItemId"

        private val EXTRA_KEY_CONTEXT_LIBRARY_COLLECTION_ID = "contextLibraryCollectionId"
        private val EXTRA_KEY_CONTEXT_CONTENT_ITEM_ID = "contextContentItemId"
        private val EXTRA_KEY_CONTEXT_NAV_COLLECTION_ID = "contextNavCollectionId"
        private val EXTRA_KEY_CONTEXT_SUB_ITEM_ID = "contextSubItemId"

        private val EXTRA_KEY_SEARCH_TEXT = "searchText"
        private val EXTRA_KEY_SCROLL_POSITION = "scrollPosition"
    }
}

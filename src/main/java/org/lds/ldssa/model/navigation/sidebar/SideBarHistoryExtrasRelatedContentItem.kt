package org.lds.ldssa.model.navigation.sidebar

import com.google.gson.Gson

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras
import org.lds.ldssa.model.navigation.InvalidExtraException

import java.util.ArrayList

class SideBarHistoryExtrasRelatedContentItem : NavigationHistoryItemExtras {

    var contentItemId: Long = 0
    var subItemId: Long = 0
    var title: String = ""
    var refId: String = ""

    constructor(contentItemId: Long, subItemId: Long, title: String, refId: String) {
        this.contentItemId = contentItemId
        this.subItemId = subItemId
        this.title = title
        this.refId = refId
    }

    constructor(gson: Gson, extrasJson: String) {
        deserialize(gson, extrasJson)
    }

    override fun getExtras(): List<DtoNavigationHistoryItemExtra> {
        verifyRequiredExtras()

        val extrasList = ArrayList<DtoNavigationHistoryItemExtra>()
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_CONTENT_ITEM_ID, contentItemId))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_SUB_ITEM_ID, subItemId))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_TITLE, title))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_REF_ID, refId))

        return extrasList
    }

    override fun setExtras(extrasList: List<DtoNavigationHistoryItemExtra>) {
        for (dtoNavigationHistoryItemExtra in extrasList) {
            when (dtoNavigationHistoryItemExtra.key) {
                EXTRA_KEY_CONTENT_ITEM_ID -> contentItemId = dtoNavigationHistoryItemExtra.valueAsLong
                EXTRA_KEY_SUB_ITEM_ID -> subItemId = dtoNavigationHistoryItemExtra.valueAsLong
                EXTRA_KEY_TITLE -> title = dtoNavigationHistoryItemExtra.value
                EXTRA_KEY_REF_ID -> refId = dtoNavigationHistoryItemExtra.value
            }
        }

        verifyRequiredExtras()
    }

    private fun verifyRequiredExtras() {
        if (contentItemId <= 0) {
            throw InvalidExtraException(EXTRA_KEY_CONTENT_ITEM_ID, contentItemId)
        }
        if (subItemId <= 0) {
            throw InvalidExtraException(EXTRA_KEY_SUB_ITEM_ID, subItemId)
        }
    }

    companion object {
        private const val EXTRA_KEY_CONTENT_ITEM_ID = "contentItemId"
        private const val EXTRA_KEY_SUB_ITEM_ID = "subItemId"
        private const val EXTRA_KEY_TITLE = "title"
        private const val EXTRA_KEY_REF_ID = "refId"
    }
}

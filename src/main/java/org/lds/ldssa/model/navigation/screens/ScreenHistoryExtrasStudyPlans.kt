package org.lds.ldssa.model.navigation.screens

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra
import org.lds.ldssa.model.navigation.InvalidExtraException
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras

import java.util.ArrayList

class ScreenHistoryExtrasStudyPlans : NavigationHistoryItemExtras {

    var selectedTabId = 0
    var scrollPosition = 0

    constructor() {
        // used by TabHistoryItem.getExtras()
    }

    constructor(selectedTabId: Int, scrollPosition: Int) {
        this.selectedTabId = selectedTabId
        this.scrollPosition = scrollPosition
    }

    override fun getExtras(): List<DtoNavigationHistoryItemExtra> {
        verifyRequiredExtras()

        val extrasList = ArrayList<DtoNavigationHistoryItemExtra>()
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_SELECTED_TAB_ID, selectedTabId))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_SCROLL_POSITION, scrollPosition))

        return extrasList
    }

    override fun setExtras(extrasList: List<DtoNavigationHistoryItemExtra>) {
        for (dtoNavigationHistoryItemExtra in extrasList) {
            when (dtoNavigationHistoryItemExtra.key) {
                EXTRA_KEY_SELECTED_TAB_ID -> selectedTabId = dtoNavigationHistoryItemExtra.valueAsInt
                EXTRA_KEY_SCROLL_POSITION -> scrollPosition = dtoNavigationHistoryItemExtra.valueAsInt
            }// ignore extra extras
        }

        verifyRequiredExtras()
    }

    private fun verifyRequiredExtras() {
        if (selectedTabId < 0) {
            throw InvalidExtraException(EXTRA_KEY_SELECTED_TAB_ID, selectedTabId)
        }
        if (scrollPosition < 0) {
            throw InvalidExtraException(EXTRA_KEY_SCROLL_POSITION, scrollPosition)
        }
    }

    companion object {
        private val EXTRA_KEY_SELECTED_TAB_ID = "selectedTabId"
        private val EXTRA_KEY_SCROLL_POSITION = "scrollPosition"
    }
}

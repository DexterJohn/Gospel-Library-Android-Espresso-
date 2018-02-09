package org.lds.ldssa.model.navigation.screens

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra
import org.lds.ldssa.model.navigation.InvalidExtraException
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras
import java.util.ArrayList

class ScreenHistoryExtrasStudyItems : NavigationHistoryItemExtras {

    var studyPlanId = 0L
    var scrollPosition = 0

    constructor() {
        // used by ScreenHistoryItem.getExtras()
    }

    constructor(studyPlanId: Long, scrollPosition: Int) {
        this.studyPlanId = studyPlanId
        this.scrollPosition = scrollPosition
    }

    override fun getExtras(): List<DtoNavigationHistoryItemExtra> {
        verifyRequiredExtras()

        val extrasList = ArrayList<DtoNavigationHistoryItemExtra>()
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_STUDY_PLAN_ID, studyPlanId))
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_SCROLL_POSITION, scrollPosition))

        return extrasList
    }

    override fun setExtras(extrasList: List<DtoNavigationHistoryItemExtra>) {
        for (dtoNavigationHistoryItemExtra in extrasList) {
            when (dtoNavigationHistoryItemExtra.key) {
                EXTRA_KEY_STUDY_PLAN_ID -> studyPlanId = dtoNavigationHistoryItemExtra.valueAsLong
                EXTRA_KEY_SCROLL_POSITION -> scrollPosition = dtoNavigationHistoryItemExtra.valueAsInt
            }// ignore extra extras
        }

        verifyRequiredExtras()
    }

    private fun verifyRequiredExtras() {
        if (studyPlanId <= 0L) {
            throw InvalidExtraException(EXTRA_KEY_STUDY_PLAN_ID, studyPlanId)
        }
        if (scrollPosition < 0) {
            throw InvalidExtraException(EXTRA_KEY_SCROLL_POSITION, scrollPosition)
        }
    }

    companion object {
        private val EXTRA_KEY_STUDY_PLAN_ID = "studyPlanId"
        private val EXTRA_KEY_SCROLL_POSITION = "scrollPosition"
    }
}
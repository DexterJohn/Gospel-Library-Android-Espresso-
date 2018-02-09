package org.lds.ldssa.model.navigation.screens

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra
import org.lds.ldssa.model.navigation.InvalidExtraException
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras
import java.util.ArrayList

class ScreenHistoryExtrasAnnotationView : NavigationHistoryItemExtras {
    var annotationId: Long = 0
        private set

    @Suppress("unused")
    constructor() {
        // used by ScreenHistoryItem.getExtras()
    }

    constructor(annotationId: Long) {
        this.annotationId = annotationId
    }

    override fun getExtras(): List<DtoNavigationHistoryItemExtra> {
        verifyRequiredExtras()

        val extrasList = ArrayList<DtoNavigationHistoryItemExtra>()
        extrasList.add(DtoNavigationHistoryItemExtra(EXTRA_KEY_ANNOTATION_ID, annotationId))

        return extrasList
    }

    override fun setExtras(extrasList: List<DtoNavigationHistoryItemExtra>) {
        for (dtoNavigationHistoryItemExtra in extrasList) {
            when (dtoNavigationHistoryItemExtra.key) {
                EXTRA_KEY_ANNOTATION_ID -> annotationId = dtoNavigationHistoryItemExtra.valueAsLong
            }
        }

        verifyRequiredExtras()
    }

    private fun verifyRequiredExtras() {
        if (annotationId <= 0) {
            throw InvalidExtraException(EXTRA_KEY_ANNOTATION_ID, annotationId)
        }
    }

    companion object {
        private const val EXTRA_KEY_ANNOTATION_ID = "annotationId"
    }
}

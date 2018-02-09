package org.lds.ldssa.ux.tips.pages

import android.arch.lifecycle.ViewModel
import org.lds.ldssa.model.database.types.TipType
import org.lds.ldssa.util.AnalyticsUtil
import javax.inject.Inject

class TipsPagerViewModel
@Inject constructor(
        private val analyticsUtil: AnalyticsUtil
) : ViewModel() {

    var tipIdList: List<Long> = emptyList()
    var tipType = TipType.REGULAR

    fun logAnalyticsTipViewed(position: Int) {
        analyticsUtil.logTipViewed(tipIdList[position])
    }
}
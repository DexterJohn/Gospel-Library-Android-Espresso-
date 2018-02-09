package org.lds.ldssa.ux.welcome

import android.arch.lifecycle.ViewModel
import org.lds.ldssa.util.AnalyticsUtil
import javax.inject.Inject

class WelcomeViewModel
@Inject constructor(
        private val analyticsUtil: AnalyticsUtil
) : ViewModel() {

    var tipIdList: List<Long> = emptyList()

    fun logAnalyticsTipViewed(position: Int) {
        if (position >= tipIdList.size) {
            // Don't need to log the sign-in fragment
            return
        }

        analyticsUtil.logTipViewed(tipIdList[position])
    }
}
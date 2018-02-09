package org.lds.ldssa.ux.study.plans

import android.support.annotation.StringRes
import org.lds.ldssa.R

// The order of these controls the order of the tabs created
enum class StudyPlanTab(@StringRes val titleId: Int) {
    MY_PLANS_TAB(R.string.my_plans), FEATURED_TAB(R.string.featured);

    companion object {
        val DEFAULT_TAB = FEATURED_TAB
    }
}

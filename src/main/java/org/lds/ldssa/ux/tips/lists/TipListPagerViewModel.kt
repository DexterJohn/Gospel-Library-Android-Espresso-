package org.lds.ldssa.ux.tips.lists

import android.arch.lifecycle.ViewModel
import javax.inject.Inject

class TipListPagerViewModel
@Inject constructor(
) : ViewModel() {

    var scrollPosition = 0
}
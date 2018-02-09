package org.lds.ldssa.ux.tips.pages.tip

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.launch
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.database.gl.tipviewed.TipViewedManager
import org.lds.ldssa.model.database.tips.tip.Tip
import org.lds.ldssa.model.database.tips.tip.TipManager
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.livedata.AbsentLiveData
import javax.inject.Inject

class TipViewModel
@Inject constructor(
        private val tipManager: TipManager,
        private val tipViewedManager: TipViewedManager,
        private val cc: CoroutineContextProvider
) : ViewModel() {

    var isTablet = false
    var currentPosition = 0L

    private val tipId = MutableLiveData<Long>()

    val tip: LiveData<Tip?>

    init {
        tip = AbsentLiveData.switchMap(tipId) {
            loadTip(it)
        }
    }

    fun setTipId(tipId: Long) {
        if (tipId != this.tipId.value) {
            this.tipId.value = tipId
        }
    }

    private fun loadTip(tipId: Long): LiveData<Tip?> {
        return DBToolsLiveData.toLiveData(cc.commonPool, listOf(tipManager)) {
            return@toLiveData tipManager.findByRowId(tipId)
        }
    }

    fun saveTipViewed() = launch(cc.commonPool) {
        tipId.value?.let { id ->
            tipViewedManager.saveTipViewed(id)
        }
    }

    fun hasVideo() = tip.value?.hasVideo(isTablet) ?: false
}
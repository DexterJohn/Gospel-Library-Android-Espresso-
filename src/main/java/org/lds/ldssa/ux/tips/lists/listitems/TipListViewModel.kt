package org.lds.ldssa.ux.tips.lists.listitems

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.database.tips.tipquery.TipQuery
import org.lds.ldssa.model.database.tips.tipquery.TipQueryManager
import org.lds.ldssa.model.database.types.TipType
import org.lds.ldssa.util.LanguageUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.livedata.AbsentLiveData
import org.lds.mobile.ui.recyclerview.ListItemWithHeader
import org.lds.mobile.ui.toListItemsWithHeader
import javax.inject.Inject

class TipListViewModel
@Inject constructor(
        private val cc: CoroutineContextProvider,
        private val languageUtil: LanguageUtil,
        private val tipQueryManager: TipQueryManager
) : ViewModel() {

    val tipsList: LiveData<List<ListItemWithHeader<TipQuery, String>>>

    private val tipsListData = MutableLiveData<TipListData>()

    init {
        tipsList = AbsentLiveData.switchMap(tipsListData) {
            loadTipList(it)
        }
    }

    fun setTipListData(byName: Boolean) {
        val tipType = if (byName) TipType.REGULAR else TipType.WELCOME
        val newTipListData = TipListData(languageUtil.deviceLanguageId, byName, tipType)
        if (newTipListData != tipsListData.value) {
            tipsListData.value = newTipListData
        }
    }

    private fun loadTipList(tipListData: TipListData): LiveData<List<ListItemWithHeader<TipQuery, String>>> {
        return DBToolsLiveData.toLiveData(cc.commonPool) {
            val tipsList = tipQueryManager.findTipsByType(tipListData.byName, tipListData.languageId, tipListData.tipType)

            // add headers in if needed
            if (tipListData.byName) {
                // items without headers
                return@toLiveData tipsList.map { ListItemWithHeader<TipQuery, String>(it) }.toMutableList()
            } else {
                return@toLiveData tipsList.toListItemsWithHeader({ it.versionId }, { it.versionName })
            }
        }
    }

    fun getTipType() = tipsListData.value?.tipType ?: TipType.REGULAR

    data class TipListData(val languageId: Long, val byName: Boolean = false, val tipType: TipType = TipType.REGULAR)
}
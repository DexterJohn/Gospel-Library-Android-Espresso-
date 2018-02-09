package org.lds.ldssa.ux.tips.lists

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.Menu
import kotlinx.android.synthetic.main.activity_tips.*
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Int
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasTips
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.ldssa.ux.tips.lists.listitems.TipListFragment
import javax.inject.Inject

class TipListPagerActivity : BaseActivity(), ScreenLauncherUtil.ScreenActivity {

    @Inject
    lateinit var navigationTrailQueryManager: NavigationTrailQueryManager
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: TipListPagerViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(TipListPagerViewModel::class.java) }

    override fun getLayoutResourceId() = R.layout.activity_tips

    override fun getAnalyticsScreenName() = Analytics.Screen.TIPS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)
        setupViewPager()
        setTitle(R.string.tips)

        updateNavigationTrail(navigationTrailQueryManager.findDefaultAllForNotesOrTips(screenUtil.getLanguageIdForScreen(getScreenId())))

        getToolbarExpand()?.setStickyView(tabLayoutContainer)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        commonMenu.onCreateOptionsMenu(this, menu, menuInflater)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.scrollPosition = getScrollPosition()
        super.onSaveInstanceState(outState)
    }

    override fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem) = screenHistoryItem.sourceType === ScreenSourceType.TIPS

    override fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem) {
        screenHistoryItem.sourceType = ScreenSourceType.TIPS
        screenHistoryItem.title = getString(R.string.tips)
        screenHistoryItem.setExtras(gson, ScreenHistoryExtrasTips(viewPager.currentItem, getScrollPosition()))
    }

    private fun setupViewPager() {
        with (IntentOptions) {
            viewPager.adapter = TipListPagerAdapter(this@TipListPagerActivity, supportFragmentManager, getScreenId(), intent.scrollPosition, intent.selectedTab)
            tabLayout.setupWithViewPager(viewPager)
            viewPager.currentItem = intent.selectedTab
        }
    }

    private fun getScrollPosition(): Int {
        val adapter = viewPager.adapter as TipListPagerAdapter
        val fragment = adapter.getItem(viewPager.currentItem)

        return fragment.getScrollPosition()
    }

    object IntentOptions {
        var Intent.selectedTab by IntentExtra.Int(0)
        var Intent.scrollPosition by IntentExtra.Int(0)
    }

    class TipListPagerAdapter(
            val context: Context, fm: FragmentManager,
            private val screenId: Long,
            private val scrollPosition: Int,
            private val selectedPosition: Int
    ) : FragmentPagerAdapter(fm) {

        override fun getCount() = 2

        override fun getItem(position: Int) = TipListFragment.newInstance(context, screenId, position == 0, if (position == selectedPosition) scrollPosition else 0)

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                1 -> return context.getString(R.string.whats_new)
                else -> return context.getString(R.string.tips)
            }
        }
    }
}

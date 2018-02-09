package org.lds.ldssa.ux.study.plans

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Menu
import kotlinx.android.synthetic.main.activity_study_plans.*
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Int
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Int
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasStudyPlans
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.util.ScreenLauncherUtil
import javax.inject.Inject

class StudyPlansActivity : BaseActivity(), ScreenLauncherUtil.ScreenActivity {
    @Inject
    lateinit var navigationTrailQueryManager: NavigationTrailQueryManager
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(StudyPlansViewModel::class.java) }

    override fun getLayoutResourceId() = R.layout.activity_study_plans
    override fun getAnalyticsScreenName() = Analytics.Screen.STUDY_PLANS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        with(IntentOptions) {
            viewModel.scrollPosition = intent.scrollPosition
            viewModel.selectedTab = StudyPlanTab.values()[intent.selectedTab]
        }
        savedInstanceState?.let { bundle ->
            with(SaveStateOptions) {
                viewModel.scrollPosition = bundle.scrollPosition
                viewModel.selectedTab = StudyPlanTab.values()[bundle.selectedTab]
            }
        }

        setTitle(R.string.study_plans)
        setupViewPager()

        updateNavigationTrail(navigationTrailQueryManager.findDefaultAllForNotesOrTips(screenUtil.getLanguageIdForScreen(getScreenId())))

        getToolbarExpand()?.setStickyView(tabLayoutContainer)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        commonMenu.onCreateOptionsMenu(this, menu, menuInflater)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        with(SaveStateOptions) {
            outState.scrollPosition = viewModel.scrollPosition
        }
        super.onSaveInstanceState(outState)
    }

    override fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem) = screenHistoryItem.sourceType === ScreenSourceType.STUDY_PLANS

    override fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem) {
        screenHistoryItem.apply {
            sourceType = ScreenSourceType.STUDY_PLANS
            title = getString(R.string.study_plans)
            setExtras(gson, ScreenHistoryExtrasStudyPlans(viewPager.currentItem, viewModel.scrollPosition))
        }
    }

    private fun setupViewPager() {
        viewPager.adapter = StudyPlansPagerAdapter(supportFragmentManager, getScreenId())
        tabLayout.setupWithViewPager(viewPager)
        viewPager.currentItem = viewModel.selectedTab.ordinal

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // ignore
            }

            override fun onPageSelected(position: Int) {
                prefs.studyPlansPagerPosition = position
                //todo logAnalytics(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                // ignore
            }
        })
    }

    object IntentOptions {
        var Intent.scrollPosition by IntentExtra.Int(0)
        var Intent.selectedTab by IntentExtra.Int(StudyPlanTab.DEFAULT_TAB.ordinal)
    }

    object SaveStateOptions {
        var Bundle.scrollPosition by BundleExtra.Int(0)
        var Bundle.selectedTab by BundleExtra.Int(StudyPlanTab.DEFAULT_TAB.ordinal)
    }

    companion object {
        const val TAB_POSITION_STUDY_PLANS = 2
    }
}

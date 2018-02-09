package org.lds.ldssa.ux.study.items

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_study_items.*
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Int
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Int
import me.eugeniomarletti.extras.intent.base.Long
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.ContentIntentParams
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasStudyItems
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.util.LanguageUtil
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.ldssa.util.ToastUtil
import org.lds.ldssa.util.UriUtil
import org.threeten.bp.LocalDate
import javax.inject.Inject

class StudyItemsActivity: BaseActivity(), ScreenLauncherUtil.ScreenActivity {
    @Inject
    lateinit var languageUtil: LanguageUtil
    @Inject
    lateinit var navigationTrailQueryManager: NavigationTrailQueryManager
    @Inject
    lateinit var toastUtil: ToastUtil
    @Inject
    lateinit var uriUtil: UriUtil
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapter: StudyItemsAdapter
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(StudyItemsViewModel::class.java) }

    override fun getLayoutResourceId() = R.layout.activity_study_items
    override fun getAnalyticsScreenName() = Analytics.Screen.STUDY_PLAN_ITEMS

    override fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem) = with(screenHistoryItem) {
        if (sourceType !== ScreenSourceType.STUDY_PLAN_ITEMS) {
            return@with false
        }
        true
    }

    override fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem) {
        screenHistoryItem.apply {
            sourceType = ScreenSourceType.STUDY_PLAN_ITEMS
            title = viewModel.livePlanTitle.value ?: getString(R.string.study_plans)
            setExtras(gson, ScreenHistoryExtrasStudyItems(viewModel.studyPlan!!.id, viewModel.scrollPosition))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        intent?.let { intent ->
            with(IntentOptions) {
                viewModel.scrollPosition = intent.scrollPosition
                viewModel.init(intent.studyPlanId)
            }
        }
        savedInstanceState?.let { bundle ->
            with(SaveStateOptions) {
                viewModel.scrollPosition = bundle.scrollPosition
            }
        }

        viewModel.livePlanTitle.observe { title = it }
        viewModel.liveStudyLessonList.observeNotNull { lessonList ->
            adapter.setLessons(lessonList)
            itemsRecycler.scrollToPosition(viewModel.scrollPosition)
        }

        setupRecyclerView()
        updateNavigationTrail(navigationTrailQueryManager.findDefaultAllForStudyPlans(screenUtil.getLanguageIdForScreen(getScreenId())))
    }

    private fun setupRecyclerView() {
        adapter = StudyItemsAdapter()
        adapter.viewClickListener = { showPrinciple(it) }
        adapter.doneClickListener = { toggleDone(it) }
        itemsRecycler.adapter = adapter
        itemsRecycler.layoutManager = LinearLayoutManager(this)
    }

    private fun showPrinciple(studyPrinciple: StudyPrincipleDto) {
        val studyPlan = viewModel.studyPlan
        val uriString = studyPrinciple.contentUri
        if (uriString.isNotBlank()) {
            internalIntents.showUriActivity(this, getScreenId(), uriString, true, false, false, Analytics.Referrer.ITEM_NAVIGATION)
        } else if (studyPlan.contentItemId > 0L && studyPrinciple.subItemId > 0L) {
            internalIntents.showContent(this, ContentIntentParams(getScreenId(), studyPlan.contentItemId, studyPrinciple.subItemId, Analytics.Referrer.ITEM_NAVIGATION))
        } else {
            toastUtil.showShort("contentItemId, subItemId and contentUri missing")
        }
    }

    private fun toggleDone(studyPrinciple: StudyPrincipleDto) {
        studyPrinciple.completionDate = when (studyPrinciple.completionDate) {
            null -> LocalDate.now()
            else -> null
        }
    }

    object IntentOptions {
        var Intent.scrollPosition by IntentExtra.Int(0)
        var Intent.studyPlanId by IntentExtra.Long(0L)
    }

    object SaveStateOptions {
        var Bundle.scrollPosition by BundleExtra.Int(0)
    }
}

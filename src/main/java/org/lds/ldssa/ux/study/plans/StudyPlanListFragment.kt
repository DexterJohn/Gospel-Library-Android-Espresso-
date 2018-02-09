package org.lds.ldssa.ux.study.plans

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_study_plan_list.*
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Int
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.ui.fragment.BaseFragment
import org.lds.ldssa.util.ToastUtil
import org.lds.ldssa.util.getScrollPosition
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit
import javax.inject.Inject

class StudyPlanListFragment : BaseFragment() {
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var toastUtil: ToastUtil

    private lateinit var adapter: StudyPlansAdapter
    private var tabType: StudyPlanTab = StudyPlanTab.DEFAULT_TAB
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(StudyPlansViewModel::class.java) }

    override fun getLayoutResourceId() = R.layout.fragment_study_plan_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)
        arguments?.let { arguments ->
            with(ArgumentOptions) {
                tabType = StudyPlanTab.values()[arguments.tabTypeId]
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        val liveData = when (tabType) {
            StudyPlanTab.FEATURED_TAB -> viewModel.featuredStudyPlans
            StudyPlanTab.MY_PLANS_TAB -> viewModel.myStudyPlans
        }
        liveData.observe(this, Observer { studyPlans ->
            studyPlans ?: return@Observer
            adapter.items = studyPlans
            if (viewModel.selectedTab == tabType) {
                studyPlanRecyclerView.scrollToPosition(viewModel.scrollPosition)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (viewModel.selectedTab == tabType) {
            viewModel.scrollPosition = studyPlanRecyclerView.getScrollPosition()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.selectedTab = tabType
    }

    private fun setupRecyclerView() {
        adapter = StudyPlansAdapter(tabType)
        adapter.clickListener = { onClick(it) }
        studyPlanRecyclerView.adapter = adapter
        studyPlanRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    /**
     * If the user is enrolled, show the lessons scrolled to the current lesson. If not enrolled, show the enrollment wizard.
     */
    private fun onClick(studyPlan: StudyPlanDto) {
        val subscription = studyPlan.subscription
        when (subscription) {
            null -> {
                toastUtil.showLong("Study Plan Enrollment Wizard is missing")   //todo go to enrollment wizard
            }
            else -> {
                val principlesPerWeek = Math.ceil(studyPlan.totalPrinciples.toDouble() / studyPlan.totalLessons.toDouble()).toInt()
                val itemsPerWeek = 1 + principlesPerWeek
                val scrollPosition = when (studyPlan.pace) {
                    StudyPlanPace.TIME_DRIVEN -> {
                        val weeks = ChronoUnit.WEEKS.between(subscription.subscribedDate, LocalDate.now()).toInt()
                        limitBetween(0, weeks, studyPlan.totalLessons - 1) * itemsPerWeek
                    }
                    StudyPlanPace.USER_DRIVEN -> {
                        subscription.lastCompletedItem / principlesPerWeek * itemsPerWeek
                    }
                }
                internalIntents.showStudyItems(activity!!, screenId, studyPlan.id, scrollPosition)
            }
        }
    }

    private fun limitBetween(min: Int, value: Int, max: Int) = Math.min(Math.max(value, min), max) //todo move to LDSCommons

    companion object {

        fun newInstance(screenId: Long, tabTypeId: Int): StudyPlanListFragment {
            val bundle = BaseFragment.getBaseBundle(screenId)
            with(ArgumentOptions) {
                bundle.tabTypeId = tabTypeId
            }
            return StudyPlanListFragment().apply {
                arguments = bundle
            }
        }
    }

    object ArgumentOptions {
        var Bundle.tabTypeId by BundleExtra.Int(StudyPlanTab.DEFAULT_TAB.ordinal)
    }
}

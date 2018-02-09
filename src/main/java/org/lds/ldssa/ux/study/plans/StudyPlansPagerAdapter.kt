package org.lds.ldssa.ux.study.plans

import android.app.Application
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.lds.ldssa.inject.Injector
import javax.inject.Inject

class StudyPlansPagerAdapter(fm: FragmentManager, private val screenId: Long) : FragmentPagerAdapter(fm) {

    @Inject
    lateinit var application: Application

    init { Injector.get().inject(this) }

    override fun getCount() = StudyPlanTab.values().size
    override fun getItem(position: Int): Fragment = StudyPlanListFragment.newInstance(screenId, position)
    override fun getPageTitle(position: Int): CharSequence = application.getString(StudyPlanTab.values()[position].titleId)
}

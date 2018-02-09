package org.lds.ldssa.ux.study.plans

import android.app.Application
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import org.lds.ldssa.R
import org.lds.ldssa.glide.GlideApp
import org.lds.ldssa.inject.Injector
import org.lds.mobile.ui.widget.media.MediaTileVertical
import javax.inject.Inject

class StudyPlansAdapter(private val tabType: StudyPlanTab) : RecyclerView.Adapter<StudyPlansAdapter.StudyPlanViewHolder>() {

    @Inject
    lateinit var application: Application

    var items: List<StudyPlanDto> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var clickListener: (studyPlan: StudyPlanDto) -> Unit = {}
    var menuOptionListener: (studyPlan: StudyPlanDto, menu: MenuItem) -> Boolean = {_, _ -> false }

    init { Injector.get().inject(this) }

    override fun onBindViewHolder(viewHolder: StudyPlanViewHolder, position: Int) {
        items[position].let { item ->
            viewHolder.mediaTile.apply {
                setTitle(item.title)
                when (tabType) {
                    StudyPlanTab.FEATURED_TAB -> {
                        setSubtitle1(null)
                        setSubtitle2(null)
                    }
                    StudyPlanTab.MY_PLANS_TAB -> {
                        setSubtitle1(application.getString(R.string.plan_weeks, item.totalLessons))
                        setSubtitle2(application.getString(R.string.plan_items, item.totalPrinciples))
                    }
                }
                item.imageName?.let { imageName ->
                    GlideApp.with(this).load("file:///android_asset/images/$imageName").into(imageView)
                }
            }
            viewHolder.itemView.setOnClickListener { clickListener.invoke(item) }
        }
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudyPlanViewHolder(parent)

    class StudyPlanViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_study_plan, parent, false)) {
        val mediaTile = itemView as MediaTileVertical
    }
}

package org.lds.ldssa.ux.study.items

import android.app.Application
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_study_combined.view.*
import kotlinx.android.synthetic.main.list_item_study_lesson.view.*
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.mobile.ui.util.LdsDrawableUtil
import org.lds.mobile.ui.widget.list.LineItem
import javax.inject.Inject

class StudyItemsAdapter : RecyclerView.Adapter<StudyItemsAdapter.StudyItemViewHolder>() {

    @Inject
    lateinit var application: Application

    private var items: List<StudyItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var combineLessonAndPrinciple = false

    var doneClickListener: (studyPrinciple: StudyPrincipleDto) -> Unit = {}
    var viewClickListener: (studyPrinciple: StudyPrincipleDto) -> Unit = {}

    fun setLessons(lessonList: List<StudyLessonDto>) {
        val items = ArrayList<StudyItem>()
        combineLessonAndPrinciple = lessonList.first().principleList?.size == 1
        lessonList.forEach { lesson ->
            if (combineLessonAndPrinciple) {
                lesson.principleList?.first()?.let { principle ->
                    items.add(StudyItem(StudyHolderType.COMBINED, lesson.title, principle = principle))
                }
            } else {
                items.add(StudyItem(StudyHolderType.LESSON, lesson.title))
                lesson.principleList?.forEach { principle ->
                    items.add(StudyItem(StudyHolderType.PRINCIPLE, lesson.title, principle = principle))
                }
            }
        }
        this.items = items
        notifyDataSetChanged()
    }

    init { Injector.get().inject(this) }

    override fun onBindViewHolder(viewHolder: StudyItemViewHolder, position: Int) {
        val item = items[position]
        when (item.type) {
            StudyHolderType.LESSON -> bindLesson(viewHolder, item)
            StudyHolderType.PRINCIPLE -> bindPrinciple(viewHolder, item)
            StudyHolderType.COMBINED -> bindCombined(viewHolder, item)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType) {
        StudyHolderType.COMBINED.ordinal -> StudyItemViewHolder(StudyHolderType.COMBINED, parent, R.layout.list_item_study_combined)
        StudyHolderType.LESSON.ordinal -> StudyItemViewHolder(StudyHolderType.LESSON, parent, R.layout.list_item_study_lesson)
        else -> StudyItemViewHolder(StudyHolderType.PRINCIPLE, parent, R.layout.list_item_study_combined)
    }

    private fun bindCombined(viewHolder: StudyItemViewHolder, item: StudyItem) {
        bindLesson(viewHolder, item)
        bindPrinciple(viewHolder, item)
    }

    private fun bindLesson(viewHolder: StudyItemViewHolder, item: StudyItem) {
        viewHolder.setLessonTitle(item.title)
    }

    private fun bindPrinciple(viewHolder: StudyItemViewHolder, item: StudyItem) {
        viewHolder.apply {
            item.principle?.let { principle ->
                setPrincipleTitle(principle.title)
                updatePrinciple(principle, viewHolder)
                if (itemView is LineItem) {
                    itemView.itemClickListener = { viewClickListener(principle) }
                    itemView.actionItemClickListener = {
                        doneClickListener(principle)
                        updatePrinciple(principle, viewHolder)
                    }
                }
            }
        }
    }

    private fun updatePrinciple(principle: StudyPrincipleDto, viewHolder: StudyItemViewHolder) = viewHolder.apply {
        val drawableId = if (principle.isComplete()) R.drawable.ic_check_circle_black_24dp else R.drawable.ic_radio_button_unchecked_black_24dp
        val tint = LdsDrawableUtil.resolvedColorIntResourceId(viewHolder.itemView.context, if (principle.isComplete()) R.attr.colorAccent else R.attr.themeStyleColorHighlightGrayUnderline)
        viewHolder.setActionImage(drawableId, tint)
    }

    class StudyItemViewHolder(val type: StudyHolderType, parent: ViewGroup, @LayoutRes layoutId: Int) :
            RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)) {
        fun setLessonTitle(title: String) {
            when (type) {
                StudyHolderType.LESSON -> itemView.sectionHeader?.setLabel(title)
                else -> itemView.lineItem?.setTitle(title)
            }
        }

        fun setPrincipleTitle(title: String) = itemView.lineItem?.let { lineItem ->
            when (type) {
                StudyHolderType.PRINCIPLE -> lineItem.setTitle(title)
                else -> lineItem.setSubtitle(title)
            }
        }

        fun setActionImage(@DrawableRes drawableId: Int, @ColorInt tint: Int) = itemView.lineItem?.let { lineItem ->
            lineItem.actionIconResId = drawableId
            lineItem.actionIconTint = tint
        }
    }

    enum class StudyHolderType {
        LESSON, PRINCIPLE, COMBINED
    }

    private class StudyItem(val type: StudyHolderType, val title: String, var principle: StudyPrincipleDto? = null)
}

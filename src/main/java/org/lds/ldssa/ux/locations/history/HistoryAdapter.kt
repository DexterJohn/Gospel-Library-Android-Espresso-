package org.lds.ldssa.ux.locations.history

import android.app.Application
import android.content.Context
import android.text.format.DateUtils
import android.view.ViewGroup

import com.devbrackets.android.recyclerext.adapter.RecyclerHeaderListAdapter
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder

import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.gl.history.History
import org.lds.ldssa.ui.adapter.viewholder.ImageActionTwoRowViewHolder
import org.lds.ldssa.ui.adapter.viewholder.StandardListHeaderViewHolder
import org.lds.ldssa.util.ThreeTenUtil
import org.lds.mobile.ui.util.LdsDrawableUtil
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

import javax.inject.Inject

class HistoryAdapter
    : RecyclerHeaderListAdapter<StandardListHeaderViewHolder, ImageActionTwoRowViewHolder, History>(), ClickableViewHolder.OnClickListener {

    @Inject
    lateinit var application: Application

    private var clickListener: ClickableViewHolder.OnClickListener? = null

    init {
        Injector.get().inject(this)
    }

    override fun onCreateHeaderViewHolder(viewGroup: ViewGroup, viewType: Int) = StandardListHeaderViewHolder.newInstance(viewGroup)

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): ImageActionTwoRowViewHolder {
        return ImageActionTwoRowViewHolder.newInstance(parent).apply {
            setOnClickListener(this@HistoryAdapter)
            setUnSelectedImage(LdsDrawableUtil.tintDrawableForTheme(parent.context, R.drawable.ic_lds_history_24dp, R.attr.themeStyleColorTextItemSubTitle))
        }
    }

    override fun onBindHeaderViewHolder(holder: StandardListHeaderViewHolder, firstChildPosition: Int) {
        items?.get(firstChildPosition)?.let {
            holder.setText(formatDate(holder.itemView.context, it.time))
        }
    }

    override fun onBindChildViewHolder(holder: ImageActionTwoRowViewHolder, childPosition: Int) {
        items?.get(childPosition)?.let {
            holder.setTitle(it.title)
            holder.setSubTitle(it.description)
        }
    }

    override fun getHeaderId(childPosition: Int): Long {
        items?.get(childPosition)?.let {
            return ThreeTenUtil.toMillis(it.time) / org.apache.commons.lang3.time.DateUtils.MILLIS_PER_DAY
        }
        return 0L
    }

    override fun onClick(viewHolder: ClickableViewHolder) {
        clickListener?.onClick(viewHolder)
    }

    fun setOnClickListener(listener: ClickableViewHolder.OnClickListener?) {
        clickListener = listener
    }

    private fun formatDate(context: Context, date: LocalDateTime): String {
        val daysDiff = ChronoUnit.DAYS.between(date, LocalDateTime.now())

        return when (daysDiff) {
            0L -> context.getString(R.string.today)
            1L -> context.getString(R.string.yesterday)
            else -> {
                val ms = ThreeTenUtil.toMillis(date)
                DateUtils.formatDateTime(application, ms, DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY)
            }
        }
    }
}

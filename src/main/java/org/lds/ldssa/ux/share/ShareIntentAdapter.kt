package org.lds.ldssa.ux.share

import android.app.Activity
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.list_item_application.view.*
import org.lds.ldssa.R

class ShareIntentAdapter(context: Activity) : ArrayAdapter<ResolveInfo>(context, 0) {

    var itemSelectedListener: (ResolveInfo) -> Unit = {}

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: ViewHolder(parent).view
        bindView(view, position)
        return view
    }

    private fun bindView(view: View, position: Int) {
        val viewHolder = view.tag as ViewHolder
        getItem(position)?.apply {
            viewHolder.view.iconImageView.setImageDrawable(activityInfo.applicationInfo.loadIcon(context.packageManager))
            viewHolder.view.labelTextView.text = activityInfo.applicationInfo.loadLabel(context.packageManager)
        }
        view.setOnClickListener({ itemSelectedListener(getItem(position)) })
    }

    private inner class ViewHolder(parent: ViewGroup) {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_application, parent, false)

        init {
            view.tag = this
        }
    }
}

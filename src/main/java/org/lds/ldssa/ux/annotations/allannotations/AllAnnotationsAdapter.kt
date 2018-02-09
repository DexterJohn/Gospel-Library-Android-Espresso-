package org.lds.ldssa.ux.annotations.allannotations

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.DiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder
import kotlinx.android.synthetic.main.annotation_list_item.view.*
import org.lds.ldssa.R
import org.lds.ldssa.model.database.userdata.annotation.Annotation

class AllAnnotationsAdapter(private val screenId: Long) : PagedListAdapter<Annotation, AllAnnotationsAdapter.AnnotationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnotationViewHolder {
        return AnnotationViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AnnotationViewHolder, position: Int) {
        val annotation = getItem(position)
        if (annotation != null) {
            holder.itemView.annotationView.loadUi(screenId, annotation, true, true)
            holder.itemView.annotationView.setMenuButton(holder.itemView.annotationMenuImageButton)
        }
    }

    class AnnotationViewHolder(parent: ViewGroup) : ClickableViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.annotation_list_item_card, parent, false))

    companion object {
        val DIFF_CALLBACK = object : DiffCallback<Annotation>() {
            override fun areContentsTheSame(oldItem: Annotation, newItem: Annotation): Boolean {
                return oldItem.compare(newItem) && oldItem.lastModified == newItem.lastModified
            }

            override fun areItemsTheSame(oldItem: Annotation, newItem: Annotation) = oldItem.id == newItem.id
        }
    }
}
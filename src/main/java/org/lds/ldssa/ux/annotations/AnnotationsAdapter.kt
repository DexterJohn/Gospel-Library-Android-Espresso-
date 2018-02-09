package org.lds.ldssa.ux.annotations

import android.view.LayoutInflater
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder
import kotlinx.android.synthetic.main.annotation_list_item.view.*
import org.lds.ldssa.R
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ui.recyclerview.RecyclerViewDiffAdapter

class AnnotationsAdapter(
        cc: CoroutineContextProvider,
        private val tabId: Long,
        private val showNotebooks: Boolean
) : RecyclerViewDiffAdapter<Annotation, AnnotationsAdapter.AnnotationViewHolder>(cc) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnotationViewHolder {
        return AnnotationViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AnnotationViewHolder, position: Int) {
        val annotation = items[position]
        holder.itemView.annotationView.loadUi(tabId, annotation, true, showNotebooks)
        holder.itemView.annotationView.setMenuButton(holder.itemView.annotationMenuImageButton)
    }

    override fun areContentsTheSame(oldItem: Annotation, newItem: Annotation): Boolean {
        return oldItem.compare(newItem) && oldItem.lastModified == newItem.lastModified
    }

    override fun areItemsTheSame(oldItem: Annotation, newItem: Annotation) = oldItem.id == newItem.id

    class AnnotationViewHolder(parent: ViewGroup) : ClickableViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.annotation_list_item_card, parent, false))
}
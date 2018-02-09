package org.lds.ldssa.ux.annotations.notebookselection


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_notebook_checkbox.view.*
import org.lds.ldssa.R
import org.lds.ldssa.model.database.userdata.notebookview.NotebookView
import org.lds.mobile.ui.ext.executeOnValidPosition

class NotebookSelectionAdapter : RecyclerView.Adapter<NotebookSelectionAdapter.ViewHolder>() {
    var list: List<NotebookView> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var notebookCheckChangedListener: (notebookId: Long, checked: Boolean) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NotebookSelectionAdapter.ViewHolder {
        return NotebookSelectionAdapter.ViewHolder(parent)
    }

    override fun onBindViewHolder(viewHolder: NotebookSelectionAdapter.ViewHolder, position: Int) {
        val notebookView = list[position]
        viewHolder.itemView.notebookNameTextView.text = notebookView.name
        viewHolder.itemView.notebookCountTextView.text = viewHolder.parent.resources.getQuantityString(R.plurals.num_items, notebookView.count, notebookView.count)
        viewHolder.itemView.notebookCheckBox.isChecked = notebookView.selected

        viewHolder.itemClickListener = { holder, checked -> holder.executeOnValidPosition { notebookCheckChangedListener(list[it].id, checked) } }
    }

    override fun getItemCount() = list.size

    class ViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_notebook_checkbox, parent, false)) {
        var itemClickListener: (holder: ViewHolder, checked: Boolean) -> Unit = { _, _ -> }

        init {
            itemView.setOnClickListener {
                itemView.notebookCheckBox.isChecked = !itemView.notebookCheckBox.isChecked
                itemClickListener(this, itemView.notebookCheckBox.isChecked)
            }
        }
    }
}

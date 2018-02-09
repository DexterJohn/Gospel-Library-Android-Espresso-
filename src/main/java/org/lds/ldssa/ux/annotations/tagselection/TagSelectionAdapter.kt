package org.lds.ldssa.ux.annotations.tagselection

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder
import kotlinx.android.synthetic.main.list_item_tag_checkbox.view.*
import kotlinx.android.synthetic.main.list_item_tag_create_new.view.*
import org.lds.ldssa.R
import org.lds.ldssa.model.database.userdata.tagview.TagView
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.getString
import org.lds.mobile.ui.util.LdsDrawableUtil

class TagSelectionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: List<TagView> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var tagCheckChangedListener: (tagText: String, checked: Boolean) -> Unit = { _, _ -> }
    var createNewClickedListener: () -> Unit = { }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CREATE_NEW) {
            ImageOneRowViewHolder(viewGroup)
        } else {
            ListItemCheckboxViewHolder(viewGroup)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tag = list[position]

        if (getItemViewType(position) == VIEW_TYPE_CREATE_NEW) {
            val imageOneRowViewHolder = holder as ImageOneRowViewHolder
            imageOneRowViewHolder.setImage(LdsDrawableUtil.tintDrawableForTheme(holder.itemView.context, R.drawable.ic_lds_action_add_24dp, R.attr.colorAccent))
            imageOneRowViewHolder.setText(imageOneRowViewHolder.getString(R.string.create_new_tag, tag.name))
            imageOneRowViewHolder.setOnClickListener {
                createNewClickedListener()
            }

            return
        }

        val listItemCheckboxViewHolder = holder as ListItemCheckboxViewHolder

        listItemCheckboxViewHolder.itemView.tagNameTextView.text = tag.name
        listItemCheckboxViewHolder.itemView.tagCountTextView.text = listItemCheckboxViewHolder.parent.resources.getQuantityString(R.plurals.num_items, tag.count, tag.count)
        listItemCheckboxViewHolder.itemView.tagCheckBox.isChecked = tag.selected

        listItemCheckboxViewHolder.itemClickListener = { holderView, checked -> holderView.executeOnValidPosition { onCheckChanged(it, checked) } }
    }

    override fun getItemViewType(position: Int): Int {
        val tag = list[position]

        return if (tag.createTagCustomItem) {
            VIEW_TYPE_CREATE_NEW
        } else {
            VIEW_TYPE_TAG
        }
    }

    override fun getItemCount() = list.size

    private fun onCheckChanged(position: Int, checked: Boolean) {
        val tagQuery = list[position]
        tagCheckChangedListener(tagQuery.name, checked)
    }

    class ImageOneRowViewHolder(parent: ViewGroup) : ClickableViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_tag_create_new, parent, false)) {
        fun setText(text: CharSequence) {
            itemView.listItemTextView.text = text
        }

        fun setImage(drawable: Drawable) {
            itemView.listItemImageView.setImageDrawable(drawable)
        }
    }

    class ListItemCheckboxViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_tag_checkbox, parent, false)) {
        var itemClickListener: (holder: ListItemCheckboxViewHolder, checked: Boolean) -> Unit = { _, _ -> }

        init {
            itemView.setOnClickListener {
                itemView.tagCheckBox.isChecked = !itemView.tagCheckBox.isChecked
                itemClickListener(this, itemView.tagCheckBox.isChecked)
            }
        }
    }

    companion object {
        private val VIEW_TYPE_TAG = 0
        private val VIEW_TYPE_CREATE_NEW = 1
    }
}

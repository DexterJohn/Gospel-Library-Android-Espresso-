package org.lds.ldssa.ux.locations.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.text.Html
import android.text.Spanned
import android.text.SpannedString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.RecyclerListAdapter
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder
import com.devbrackets.android.recyclerext.adapter.viewholder.MenuViewHolder
import kotlinx.android.synthetic.main.grid_item_card_screen.view.*
import org.apache.commons.lang3.StringUtils
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.userdata.screen.Screen
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemManager
import org.lds.ldssa.util.GLFileUtil
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.util.LdsDrawableUtil
import timber.log.Timber
import javax.inject.Inject

class ScreensAdapter(private val screenId: Long) : RecyclerListAdapter<ScreensAdapter.ViewHolder, Screen>(), ClickableViewHolder.OnClickListener {
    @Inject
    lateinit var fileUtil: GLFileUtil
    @Inject
    lateinit var screenHistoryItemManager: ScreenHistoryItemManager

    private var addedScreenId: Long = 0

    var clickListener: (screenId: Long) -> Unit = { }
    var menuItemSelectedListener: MenuViewHolder.OnMenuItemSelectedListener? = null

    init {
        Injector.get().inject(this)
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder.newInstance(parent)

        holder.setOnClickListener(this)
        holder.setOnMenuItemSelectedListener(menuItemSelectedListener)

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val screen = getItem(position) ?: return

        val title: Spanned
        val subTitle: Spanned

        val screenHistoryItem = screenHistoryItemManager.findCurrentScreenHistoryItemByScreenId(screen.id)
        title = if (StringUtils.isNotBlank(screen.name)) {
            Html.fromHtml(screen.name)
        } else if (screenHistoryItem != null) {
            Html.fromHtml(screenHistoryItem.title)
        } else {
            SpannedString(holder.itemView.context.resources.getString(R.string.library))
        }

        subTitle = if (screenHistoryItem != null) {
            Html.fromHtml(screenHistoryItem.description)
        } else {
            SpannedString("")
        }

        holder.setCurrentTab(screen.id == screenId)
        holder.setTitle(title)
        holder.setSubTitle(subTitle)
        holder.setThumbnail(getBitmap(fileUtil.getThumbsFile(screen.id).absolutePath))

        if (addedScreenId > 0 && addedScreenId == screen.id) {
            addedScreenId = 0
        }
    }

    override fun onClick(viewHolder: ClickableViewHolder) = viewHolder.executeOnValidPosition { position ->
        items?.let { items ->
            clickListener(items[position].id)
        }
    }

    fun setOnTabMenuListener(listener: MenuViewHolder.OnMenuItemSelectedListener?) {
        this.menuItemSelectedListener = listener
    }

    fun setNewItem(id: Long) {
        addedScreenId = id
    }

    private fun getBitmap(filename: String): Bitmap? {
        var bitmap: Bitmap? = null
        if (filename.isNotEmpty()) {
            try {
                bitmap = BitmapFactory.decodeFile(filename)
            } catch (e: Exception) {
                Timber.e(e, "Error decoding bitmap: %s", filename)
            }

        } else {
            Timber.e("Bitmap filename is empty")
        }
        return bitmap
    }

    class ViewHolder(view: View) : MenuViewHolder(view) {

        override fun getMenuViewId() = R.id.screen_menu

        override fun getMenuResourceId() = R.menu.menu_popup_tab

        fun setTitle(title: CharSequence) {
            itemView.screenTitleTextView.text = title
            itemView.screenImageView.contentDescription = title
        }

        fun setCurrentTab(currentTab: Boolean) {
            itemView.currentScreenLineView.setVisible(currentTab)
        }

        fun setSubTitle(subTitle: CharSequence) {
            itemView.screenSubTitleTextView.text = subTitle
        }

        fun setThumbnail(bitmap: Bitmap?) {
            if (bitmap != null) {
                itemView.screenImageView.setImageBitmap(bitmap)
            } else {
                val colorInt = LdsDrawableUtil.resolvedColorIntResourceId(itemView.context, R.attr.themeStyleColorBackgroundCardTab)
                itemView.screenImageView.setImageDrawable(ColorDrawable(colorInt))
            }
        }

        companion object {
            fun newInstance(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_card_screen, parent, false)
                return ViewHolder(view)
            }
        }
    }
}

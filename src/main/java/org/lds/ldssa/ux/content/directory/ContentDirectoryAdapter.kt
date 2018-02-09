package org.lds.ldssa.ux.content.directory

import android.content.Context
import android.support.annotation.IntRange
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.RecyclerHeaderListAdapter
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder
import com.devbrackets.android.recyclerext.widget.FastScroll
import org.lds.ldssa.R
import org.lds.ldssa.glide.GlideApp
import org.lds.ldssa.glide.GlideRequests
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.content.contentdirectoryitemquery.ContentDirectoryItemQuery
import org.lds.ldssa.model.database.content.navcollectionindexentry.NavCollectionIndexEntry
import org.lds.ldssa.model.database.types.QueryItemType
import org.lds.ldssa.ui.adapter.viewholder.ContentDirectoryViewHolder
import org.lds.ldssa.ui.adapter.viewholder.StandardListHeaderViewHolder
import org.lds.ldssa.util.UriUtil
import org.lds.mobile.ui.ext.executeOnValidPosition
import javax.inject.Inject

/**
 * The Adapter that handles displaying of [ContentDirectoryItemQuery]'s
 */
class ContentDirectoryAdapter(
        context: Context,
        private var rootCollection: Boolean
) : RecyclerHeaderListAdapter<StandardListHeaderViewHolder, ContentDirectoryViewHolder, ContentDirectoryItemQuery>(), FastScroll.PopupCallbacks, ClickableViewHolder.OnClickListener {
    @Inject
    lateinit var uriUtil: UriUtil

    var clickListener: (QueryItemType, Long) -> Unit = { _, _ -> }

    private val inflater: LayoutInflater
    private var displayList = false // True if we should display a list, false for a grid
    private var currentIndexedPosition = 0 // Used to optimize displaying the quick scroll bubble text

    private var indexedEntries: List<NavCollectionIndexEntry>? = null
    private val glideRequests: GlideRequests

    init {
        Injector.get().inject(this)

        inflater = LayoutInflater.from(context)
        glideRequests = GlideApp.with(context)
    }

    override fun onCreateChildViewHolder(viewGroup: ViewGroup, viewType: Int): ContentDirectoryViewHolder {
        val holder = ContentDirectoryViewHolder.newInstance(viewGroup, displayList)
        holder.setOnClickListener(this)

        return holder
    }

    override fun onCreateHeaderViewHolder(viewGroup: ViewGroup, viewType: Int): StandardListHeaderViewHolder {
        val view = inflater.inflate(R.layout.list_header_standard, viewGroup, false)
        return StandardListHeaderViewHolder(view)
    }

    override fun onBindChildViewHolder(holder: ContentDirectoryViewHolder, position: Int) {
        val item = getItem(position) ?: error("Item $position not found")

        showTitle(item, holder)
        holder.setIndentationLevel(item.sectionIndentLevel)
        holder.setSubTitleText(item.subtitle)
        holder.setPreviewText(item.preview)
        holder.setImage(glideRequests, item.imageRenditions)
    }

    override fun onBindHeaderViewHolder(holder: StandardListHeaderViewHolder, firstChildPosition: Int) {
        val item = getItem(firstChildPosition)
        holder.setText(Html.fromHtml(item?.sectionTitle ?: ""))
    }

    override fun getHeaderId(childPosition: Int): Long {
        // We won't have headers when displaying a grid
        if (!displayList) {
            return RecyclerView.NO_ID
        }

        // Works around a content issue where the title can be blank
        val item = getItem(childPosition)
        return if (item?.sectionTitle.isNullOrBlank()) {
            RecyclerView.NO_ID
        } else item?.sectionId ?: error("null id")

    }

    override fun getSectionId(@IntRange(from = 0L) position: Int): Long {
        return getCorrectIndexedEntryPosition(currentIndexedPosition, position).toLong()
    }

    override fun getPopupText(position: Int, sectionId: Long): String {
        return indexedEntries?.let {
            if (it.isEmpty()) {
                ""
            } else {
                currentIndexedPosition = getCorrectIndexedEntryPosition(currentIndexedPosition, position)
                it[currentIndexedPosition].title
            }
        } ?: ""
    }

    override fun onClick(viewHolder: ClickableViewHolder) {
        viewHolder.executeOnValidPosition {
            val position = getChildPosition(it)
            val item = getItem(position) ?: return@executeOnValidPosition
            clickListener(item.type, item.id)
        }
    }

    fun setIndexedEntries(entries: List<NavCollectionIndexEntry>?) {
        indexedEntries = entries
    }

    /**
     * Sets if the Adapter should handle displaying a list of items
     * (with Sticky Headers) or a grid of items.
     *
     * @param displayList True if the adapter should display lists
     */
    fun setDisplayList(displayList: Boolean) {
        if (this.displayList == displayList) {
            return
        }

        this.displayList = displayList
        notifyDataSetChanged()
    }

    fun setIsRootCollection(rootCollection: Boolean) {
        this.rootCollection = rootCollection
    }

    protected fun showTitle(item: ContentDirectoryItemQuery, holder: ContentDirectoryViewHolder) {
        val titleText = item.titleHtml
        val uri = item.uri

        // only determine chapterNumber if it is NOT a root collection
        val chapterNum = if (rootCollection) 0 else uriUtil.standardWorkChapterNum(uri)
        if (chapterNum > 0 && !displayList) {
            holder.setChapterText(chapterNum)
        } else {
            holder.setTitleText(titleText)
        }
    }

    private fun getCorrectIndexedEntryPosition(indexPositionVal: Int, listPosition: Int): Int {
        var indexPosition = indexPositionVal
        val entries = indexedEntries!!
        val checkNextPositions = entries[indexPosition].listIndex <= listPosition

        // Iterates over the indexes until we find the correct one
        while (indexPosition >= 0 && indexPosition < entries.size) {
            var handled = false

            if (checkNextPositions) {
                if (indexPosition < entries.size - 1 && entries[indexPosition + 1].listIndex <= listPosition) {
                    indexPosition++
                    handled = true
                }
            } else {
                if (indexPosition > 0 && entries[indexPosition].listIndex > listPosition) {
                    indexPosition--
                    handled = true
                }
            }

            if (!handled) {
                break
            }
        }

        return indexPosition
    }
}

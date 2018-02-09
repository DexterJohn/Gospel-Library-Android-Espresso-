package org.lds.ldssa.ui.widget

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import org.lds.ldssa.R
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.ui.web.ContentWebView
import org.lds.ldssa.util.HighlightColor
import org.lds.ldssa.util.ImageUtil
import org.lds.mobile.ui.ext.setBackgroundCompat
import org.lds.mobile.ui.util.LdsDrawableUtil

class ContentHighlightPopupMenu(private val contentWebView: ContentWebView,
                                private val imageUtil: ImageUtil) : PopupWindow(contentWebView.context) {

    private val markHighlightMenuTextView: TextView
    private val defineHighlightMenuTextView: TextView
    private val removeHighlightMenuTextView: TextView

    private val measuredWidth: Int
    private val measuredHeight: Int

    private var highlightTopY = 0
    private var highlightBottomY = 0
    private var leftHandleX = 0
    private var leftHandleY = 0
    private var rightHandleX = 0
    private var rightHandleY = 0

    var onHighlightMarkListener: () -> Unit = {}
    var onHighlightStyleListener: () -> Unit = {}
    var onHighlightNoteListener: () -> Unit = {}
    var onHighlightTagListener: () -> Unit = {}
    var onHighlightAddToListener: () -> Unit = {}
    var onHighlightLinkListener: () -> Unit = {}
    var onHighlightCopyListener: () -> Unit = {}
    var onHighlightShareListener: () -> Unit = {}
    var onHighlightDefineListener: () -> Unit = {}
    var onHighlightSearchListener: () -> Unit = {}
    var onHighlightDeleteListener: () -> Unit = {}

    init {
        val context = contentWebView.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.popup_highlight_menu, null)

        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
        measuredWidth = view.measuredWidth
        measuredHeight = view.measuredHeight

        contentView = view

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            @Suppress("DEPRECATION")
            setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        markHighlightMenuTextView = view.findViewById(R.id.markHighlightMenuTextView)
        markHighlightMenuTextView.setOnClickListener {
            val selectedAnnotation = contentWebView.getSelectedAnnotation()

            if (selectedAnnotation == null || selectedAnnotation.isNewRecord) {
                onHighlightMarkListener()
                switchMarkToStyleButton()
            } else {
                onHighlightStyleListener()
            }
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // 4.x devices won't show a shadow on the popup.... so brute force it with a 9 patch
            val popupLayout: LinearLayout = view.findViewById(R.id.popupLayout)
            setBackgroundDrawable(LdsDrawableUtil.getDrawable(context, R.drawable.popup_shadow))
            popupLayout.setBackgroundCompat(ColorDrawable(LdsDrawableUtil.resolvedColorIntResourceId(contentWebView.context, R.attr.themeStyleColorBackgroundDialog)))
        } else {
            setBackgroundDrawable(ColorDrawable(LdsDrawableUtil.resolvedColorIntResourceId(contentWebView.context, R.attr.themeStyleColorBackgroundDialog)))
        }

        val noteHighlightMenuTextView: TextView = view.findViewById(R.id.noteHighlightMenuTextView)
        setButtonIcon(context, noteHighlightMenuTextView, R.drawable.ic_lds_action_note_24dp)
        noteHighlightMenuTextView.setOnClickListener { onHighlightNoteListener() }

        val tagHighlightMenuTextView: TextView = view.findViewById(R.id.tagHighlightMenuTextView)
        setButtonIcon(context, tagHighlightMenuTextView, R.drawable.ic_lds_tag_24dp)
        tagHighlightMenuTextView.setOnClickListener { onHighlightTagListener() }

        val addToHighlightMenuTextView: TextView = view.findViewById(R.id.addToHighlightMenuTextView)
        setButtonIcon(context, addToHighlightMenuTextView, R.drawable.ic_lds_notebook_24dp)
        addToHighlightMenuTextView.setOnClickListener { onHighlightAddToListener() }

        val linkHighlightMenuTextView: TextView = view.findViewById(R.id.linkHighlightMenuTextView)
        setButtonIcon(context, linkHighlightMenuTextView, R.drawable.ic_lds_link_24dp)
        linkHighlightMenuTextView.setOnClickListener { onHighlightLinkListener() }

        val copyHighlightMenuTextView: TextView = view.findViewById(R.id.copyHighlightMenuTextView)
        setButtonIcon(context, copyHighlightMenuTextView, R.drawable.ic_lds_action_copy_24dp)
        copyHighlightMenuTextView.setOnClickListener { onHighlightCopyListener() }

        val shareHighlightMenuTextView: TextView = view.findViewById(R.id.shareHighlightMenuTextView)
        setButtonIcon(context, shareHighlightMenuTextView, R.drawable.ic_lds_action_share_24dp)
        shareHighlightMenuTextView.setOnClickListener { onHighlightShareListener() }

        defineHighlightMenuTextView = view.findViewById(R.id.defineHighlightMenuTextView)
        setButtonIcon(context, defineHighlightMenuTextView, DEFINE_ICON_RES)
        defineHighlightMenuTextView.setOnClickListener { onHighlightDefineListener() }

        val searchHighlightMenuTextView: TextView = view.findViewById(R.id.searchHighlightMenuTextView)
        setButtonIcon(context, searchHighlightMenuTextView, R.drawable.ic_lds_action_search_24dp)
        searchHighlightMenuTextView.setOnClickListener { onHighlightSearchListener() }

        removeHighlightMenuTextView = view.findViewById(R.id.removeHighlightMenuTextView)
        setButtonIcon(context, removeHighlightMenuTextView, REMOVE_ICON_RES)
        removeHighlightMenuTextView.setOnClickListener { onHighlightDeleteListener() }
    }

    private fun enableButton(context: Context, textView: TextView, @DrawableRes iconResId: Int, enabled: Boolean) {
        val drawable = LdsDrawableUtil.getDrawable(context, iconResId)

        when {
            !enabled -> {
                textView.isEnabled = false
                drawable.alpha = ALPHA_DISABLED_ICON
            }
            enabled -> {
                textView.isEnabled = true
                drawable.alpha = ALPHA_ENABLED_ICON
            }
        }

        setButtonIcon(context, textView, drawable)
    }

    private fun setButtonIcon(context: Context, textView: TextView, @DrawableRes iconResId: Int) {
        setButtonIcon(context, textView, LdsDrawableUtil.getDrawable(context, iconResId))
    }

    private fun setButtonIcon(context: Context, textView: TextView, drawable: Drawable) {
        val tintedDrawable = LdsDrawableUtil.tintDrawableForTheme(context, drawable, R.attr.themeStyleColorToolbarActionModeIcon)
        textView.setCompoundDrawablesWithIntrinsicBounds(null, tintedDrawable, null, null)
    }

    /**
     * Show Popup
     *
     * Should only be called with startSelectMode(...) to initialize the popup
     */
    fun show(highlightTopY: Int, highlightBottomY: Int, leftHandleX: Int, leftHandleY: Int, rightHandleX: Int, rightHandleY: Int) {
        this.highlightTopY = highlightTopY
        this.highlightBottomY = highlightBottomY
        this.leftHandleX = leftHandleX
        this.leftHandleY = leftHandleY
        this.rightHandleX = rightHandleX
        this.rightHandleY = rightHandleY
        show()
    }

    /**
     * Show Popup
     *
     * Should only be called only after show(...) with initialize params is called
     */
    fun show() {
        // update the first button to either "Mark" or "Style"
        contentWebView.getSelectedAnnotation()?.let { selectedAnnotation ->
            if (selectedAnnotation.isNewRecord) {
                markHighlightMenuTextView.setText(R.string.mark)
                setButtonIcon(contentWebView.context, markHighlightMenuTextView, R.drawable.ic_lds_annotation_mark_24dp)
            } else {
                setHighlightButtonColor(selectedAnnotation)
                markHighlightMenuTextView.setText(R.string.style)
            }

            enableButton(contentWebView.context, removeHighlightMenuTextView, REMOVE_ICON_RES, !selectedAnnotation.isNewRecord)
        }

        // disable the "Define" button as needed
        val defineButtonEnabled = !contentWebView.selectedAnnotationText.contains(" ")
        enableButton(contentWebView.context, defineHighlightMenuTextView, DEFINE_ICON_RES, defineButtonEnabled)

        val contentWebViewLocRect = Rect()
        contentWebView.getWindowVisibleDisplayFrame(contentWebViewLocRect)

        val contentWebViewScreenLoc = IntArray(2)
        contentWebView.getLocationOnScreen(contentWebViewScreenLoc)
        val webViewY = contentWebViewScreenLoc[1]
        val webViewCenterXPos = (contentWebView.width / 2) - (measuredWidth / 2) // half screen - half popup

        val scrollBounds = Rect()
        contentWebView.getHitRect(scrollBounds)

        val popupTopPadding = contentWebView.context.resources.getDimensionPixelSize(R.dimen.highlight_popup_menu_top_y_padding)
        val popupBottomPadding = contentWebView.context.resources.getDimensionPixelSize(R.dimen.highlight_popup_menu_bottom_y_padding)

        val webViewScrollPosition = contentWebView.scrollY

        val totalAboveSpaceNeeded = measuredHeight
        val highlightTopVisibleY = (highlightTopY - webViewScrollPosition)
        val highlightBottomVisibleY = (highlightBottomY - webViewScrollPosition)

        val isRoomAvailableAbove = totalAboveSpaceNeeded < highlightTopVisibleY

        val totalBelowSpaceNeeded = measuredHeight
        val isRoomAvailableBelow = (highlightBottomVisibleY + totalBelowSpaceNeeded) < contentWebView.height

        when {
            isRoomAvailableAbove -> {
                showAtLocation(contentWebView, Gravity.NO_GRAVITY, webViewCenterXPos, highlightTopVisibleY - measuredHeight - popupTopPadding + webViewY) // +webViewY to adjust for any visible toolbar (showAtLocation(...) uses screen location)
            }
            isRoomAvailableBelow -> {
                showAtLocation(contentWebView, Gravity.NO_GRAVITY, webViewCenterXPos, highlightBottomVisibleY + popupBottomPadding + webViewY) // +webViewY to adjust for any visible toolbar (showAtLocation(...) uses screen location)
            }
            else -> {
                showAtLocation(contentWebView, Gravity.CENTER, 0, 0)
            }
        }
    }

    private fun switchMarkToStyleButton() {
        contentWebView.getSelectedAnnotation()?.let {
            setHighlightButtonColor(it)
            markHighlightMenuTextView.setText(R.string.style)
        }
    }

    private fun setHighlightButtonColor(selectedAnnotation: Annotation) {
        selectedAnnotation.getFirstHighlight()?.let { highlight ->
            val style = HighlightAnnotationStyle[highlight.style]
            val color = HighlightColor.get(highlight.color)
            setHighlightButtonColor(color, style)
        }
    }

    private fun setHighlightButtonColor(color: HighlightColor, style: HighlightAnnotationStyle) {
        val context = contentWebView.context

        if (style.hasDrawableRes()) {
            val colorInt = if (style === HighlightAnnotationStyle.FILL) color.getColorFill(context) else color.getColorUnderline(context)
            val drawables = arrayOfNulls<Drawable>(style.drawableRes.size)
            drawables[0] = imageUtil.filterColor(LdsDrawableUtil.getDrawable(context, style.drawableRes[0]), colorInt, style)

            // We only wanted to tint the first one
            // If there are more drawables then just add them on
            for (index in 1 until style.drawableRes.size) {
                drawables[index] = LdsDrawableUtil.getDrawable(context, style.drawableRes[index])
            }

            markHighlightMenuTextView.setCompoundDrawablesWithIntrinsicBounds(null, LayerDrawable(drawables), null, null)
        }
    }

    companion object {
        const val ALPHA_DISABLED_ICON = 100
        const val ALPHA_ENABLED_ICON = 255
        const val DEFINE_ICON_RES = R.drawable.ic_lds_action_define_24dp
        const val REMOVE_ICON_RES = R.drawable.ic_lds_action_delete_24dp
    }
}
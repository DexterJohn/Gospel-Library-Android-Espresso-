package org.lds.ldssa.ui.widget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import org.lds.ldssa.R
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle
import org.lds.ldssa.util.HighlightColor
import org.lds.mobile.ui.setVisible

class HighlightStyleIndicator : FrameLayout {

    private lateinit var highlightStyleImageView: ImageView
    private lateinit var backgroundView: View
    private lateinit var underlineView: View

    var color = HighlightColor.YELLOW
        private set

    var style = HighlightAnnotationStyle.FILL
        private set

    fun setColorStyle(color: HighlightColor, style: HighlightAnnotationStyle) {
        when (style) {
            HighlightAnnotationStyle.FILL -> {
                backgroundView.setVisible(true)
                underlineView.setVisible(false)
                backgroundView.setBackgroundColor(color.getColorFill(context))
            }
            HighlightAnnotationStyle.UNDERLINE -> {
                backgroundView.setVisible(false)
                underlineView.setVisible(true)
                underlineView.setBackgroundColor(color.getColorUnderline(context))
            }
        }
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @Suppress("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        if (isInEditMode) {
            return
        }
        LayoutInflater.from(context).inflate(R.layout.widget_highlight_style_indicator, this, true)

        highlightStyleImageView = findViewById(R.id.highlightStyleImageView)
        backgroundView = findViewById(R.id.backgroundView)
        underlineView = findViewById(R.id.underlineView)
    }
}

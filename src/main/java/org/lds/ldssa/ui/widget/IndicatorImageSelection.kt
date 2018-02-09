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

class IndicatorImageSelection : FrameLayout {

    private lateinit var indicatorHighlightStyle: HighlightStyleIndicator
    private lateinit var indicatorImageView: ImageView

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @Suppress("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.widget_image_selection_indicator, this, true)
        indicatorHighlightStyle = findViewById(R.id.indicatorHighlightStyle)
        indicatorImageView = findViewById(R.id.indicatorImageView)
    }

    fun setIndicatorVisible(visible: Boolean) {
        indicatorImageView.setVisible(visible, View.INVISIBLE)
    }

    fun setColorStyle(color: HighlightColor, style: HighlightAnnotationStyle) {
        indicatorHighlightStyle.setColorStyle(color, style)
    }
}

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
import org.lds.ldssa.util.HighlightColor
import org.lds.mobile.ui.setVisible

class HighlightColorIndicator : FrameLayout {

    private lateinit var backgroundView: View
    private lateinit var checkmarkImageView: ImageView
    var color: HighlightColor = HighlightColor.YELLOW
        set(value) {
            field = value
            backgroundView.setBackgroundColor(color.getColorUnderline(context))
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
        LayoutInflater.from(context).inflate(R.layout.widget_highlight_color_indicator, this, true)

        checkmarkImageView = findViewById(R.id.checkmarkImageView)
        backgroundView = findViewById(R.id.backgroundView)
    }

    fun setChecked(checked: Boolean) {
        checkmarkImageView.setVisible(checked)
    }
}

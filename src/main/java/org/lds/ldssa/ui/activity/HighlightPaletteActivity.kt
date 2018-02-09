package org.lds.ldssa.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_highlight_palette.*
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Serializable
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.prefs.model.HighlightHistoryItem
import org.lds.ldssa.ui.widget.HighlightColorIndicator
import org.lds.ldssa.ui.widget.HighlightStyleIndicator
import org.lds.ldssa.util.HighlightColor
import org.lds.ldssa.util.annotations.HighlightUtil
import org.lds.mobile.ui.util.LdsDrawableUtil
import javax.inject.Inject

class HighlightPaletteActivity : BaseActivity() {

    private var annotationId = 0L
    private var selectedColor = DEFAULT_COLOR
    private var selectedStyle = HighlightAnnotationStyle.FILL
    private val colorIndicators = ArrayList<HighlightColorIndicator>()

    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var highlightUtil: HighlightUtil

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Updates the home icon to be a check mark
        getMainToolbar()?.navigationIcon = LdsDrawableUtil.tintDrawableForTheme(this, R.drawable.ic_lds_action_done_24dp, R.attr.colorAccent)

        setTitle(R.string.highlight_style)

        fillIndicator.setOnClickListener {
            if (selectedColor == HighlightColor.CLEAR) {
                selectedColor = DEFAULT_COLOR
            }
            setSelectedStyle(HighlightAnnotationStyle.FILL, selectedColor)
        }
        underlineIndicator.setOnClickListener {
            if (selectedColor == HighlightColor.CLEAR) {
                selectedColor = DEFAULT_COLOR
            }
            setSelectedStyle(HighlightAnnotationStyle.UNDERLINE, selectedColor)
        }
        clearIndicator.setOnClickListener {
            setSelectedStyle(HighlightAnnotationStyle.FILL, HighlightColor.CLEAR)
        }

        setupColorButton(redColorIndicator, HighlightColor.RED)
        setupColorButton(orangeColorIndicator, HighlightColor.ORANGE)
        setupColorButton(yellowColorIndicator, HighlightColor.YELLOW)
        setupColorButton(greenColorIndicator, HighlightColor.GREEN)
        setupColorButton(blueColorIndicator, HighlightColor.BLUE)

        setupColorButton(darkBlueColorIndicator, HighlightColor.DARK_BLUE)
        setupColorButton(purpleColorIndicator, HighlightColor.PURPLE)
        setupColorButton(pinkColorIndicator, HighlightColor.PINK)
        setupColorButton(brownColorIndicator, HighlightColor.BROWN)
        setupColorButton(grayColorIndicator, HighlightColor.GRAY)

        val recentHighlights = prefs.contentDisplayRecentHighlights
        if (recentHighlights.size == 5) {
            setupRecentButton(recent1ColorIndicator, prefs.contentDisplayRecentHighlights[0])
            setupRecentButton(recent2ColorIndicator, prefs.contentDisplayRecentHighlights[1])
            setupRecentButton(recent3ColorIndicator, prefs.contentDisplayRecentHighlights[2])
            setupRecentButton(recent4ColorIndicator, prefs.contentDisplayRecentHighlights[3])
            setupRecentButton(recent5ColorIndicator, prefs.contentDisplayRecentHighlights[4])
        }

        with(IntentOptions) {
            val annotation = intent.annotation
            annotation ?: return@with

            annotationId = annotation.id
            annotation.getFirstHighlight()?.let { highlight ->
                setSelectedStyle(HighlightAnnotationStyle[highlight.style], HighlightColor.get(highlight.color))
            }
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_highlight_palette
    }

    override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.HIGHLIGHT_PALETTE
    }

    private fun setupColorButton(indicator: HighlightColorIndicator, color: HighlightColor) {
        indicator.color = color
        indicator.setOnClickListener {
            setSelectedStyle(selectedStyle, color)
        }

        colorIndicators.add(indicator)
    }

    private fun setupRecentButton(indicator: HighlightStyleIndicator, highlightHistoryItem: HighlightHistoryItem) {
        indicator.setColorStyle(highlightHistoryItem.color, highlightHistoryItem.style)
        indicator.setOnClickListener {
            setSelectedStyle(highlightHistoryItem.style, highlightHistoryItem.color)
            finish()
        }
    }

    /**
     * Updates the selected style indicators, and changes the color selection
     * availability.
     *
     * @param style The style to use for setting the indicators and color availability
     * @param color The color to use to determine if the clear "style" is selected
     *              (actually a clear color displayed as a style)
     */
    private fun setSelectedStyle(style: HighlightAnnotationStyle, color: HighlightColor) {
        selectedStyle = style
        selectedColor = color

        colorIndicators.forEach {
            it.setChecked(it.color == color)
        }

        // Updates the indicator
        when {
            color == HighlightColor.CLEAR -> {
                fillIndicator.setIndicatorVisible(false)
                underlineIndicator.setIndicatorVisible(false)
                clearIndicator.setIndicatorVisible(true)
            }
            style == HighlightAnnotationStyle.FILL -> {
                fillIndicator.setIndicatorVisible(true)
                underlineIndicator.setIndicatorVisible(false)
                clearIndicator.setIndicatorVisible(false)
            }
            style == HighlightAnnotationStyle.UNDERLINE -> {
                fillIndicator.setIndicatorVisible(false)
                underlineIndicator.setIndicatorVisible(true)
                clearIndicator.setIndicatorVisible(false)
            }
        }

        // update the style indicator colors... make sure fill and underline have a visible color
        var styleColor = selectedColor
        if (styleColor == HighlightColor.CLEAR) {
            styleColor = DEFAULT_COLOR
        }

        fillIndicator.setColorStyle(styleColor, HighlightAnnotationStyle.FILL)
        underlineIndicator.setColorStyle(styleColor, HighlightAnnotationStyle.UNDERLINE)
    }

    override fun finish() {
        val intent = Intent()
        intent.putExtra(EXTRA_RESULT_ANNOTATION_ID, annotationId)
        intent.putExtra(EXTRA_RESULT_HIGHLIGHT_STYLE, selectedStyle)
        intent.putExtra(EXTRA_RESULT_HIGHLIGHT_COLOR, selectedColor)

        // save the annotation
        val loadedAnnotation = annotationManager.findFullAnnotationByRowId(annotationId)
        loadedAnnotation?.let { annotation ->

            annotation.setAllHighlightColors(selectedColor, selectedStyle)
            annotationManager.save(annotation)

            // Updates the preferences and the UI
            val highlightHistoryItem = HighlightHistoryItem(selectedColor, selectedStyle)
            prefs.contentDisplayRecentHighlights = highlightUtil.getUpdatedHighlightHistory(highlightHistoryItem)

            // analytics
            analyticsUtil.logContentAnnotated(annotation, Analytics.AnnotationType.MARK, Analytics.ChangeType.UPDATE)

            setResult(Activity.RESULT_OK, intent)
        }

        super.finish()
    }

    object IntentOptions {
        var Intent.annotation by IntentExtra.Serializable<Annotation>()
    }

    companion object {
        private val DEFAULT_COLOR = HighlightColor.YELLOW

        // Result Extras
        const val EXTRA_RESULT_ANNOTATION_ID = "EXTRA_RESULT_ANNOTATION_ID"
        const val EXTRA_RESULT_HIGHLIGHT_COLOR = "EXTRA_RESULT_HIGHLIGHT_COLOR"
        const val EXTRA_RESULT_HIGHLIGHT_STYLE = "EXTRA_RESULT_HIGHLIGHT_STYLE"
    }
}
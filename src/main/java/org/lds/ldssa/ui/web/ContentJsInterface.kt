package org.lds.ldssa.ui.web

import android.webkit.JavascriptInterface
import com.google.gson.Gson
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.highlight.Highlight
import org.lds.ldssa.model.webview.content.dto.DtoHighlightInfo
import org.lds.ldssa.model.webview.content.dto.DtoImages
import org.lds.ldssa.model.webview.content.dto.DtoInlineVideos
import org.lds.ldssa.model.webview.content.dto.DtoParagraphAidPosition
import org.lds.ldssa.model.webview.content.dto.DtoWebAnnotationProperties
import org.lds.ldssa.model.webview.content.dto.DtoWebTouch
import org.lds.mobile.util.LdsUiUtil
import timber.log.Timber
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import javax.inject.Inject

class ContentJsInterface @Inject
constructor(private val uiUtil: LdsUiUtil, private val gson: Gson) : BaseJsInterface() {

    private lateinit var contentWebView: ContentWebView

    @JavascriptInterface
    fun jsFinishedRendering(density: String) {
        Timber.d("==== JS Finished Rendering ====")

        // send callback on main thread (this is the 'JavaBridge' thread)
        contentWebView.post { contentWebView.onHtmlRenderingFinished() }
    }

    @JavascriptInterface
    fun jsContentSingleClick(json: String) {
        try {
            val dtoWebTouch = gson.fromJson(json, DtoWebTouch::class.java)
//            Timber.d("==== JS Single Click DTO ==== %s", dtoWebTouch.toText())
            contentWebView.onSingleTap(dtoWebTouch)
        } catch (e: IOException) {
            Timber.e(e, "Failed to parse jsContentSingleClick json")
        }

    }

    @JavascriptInterface
    fun jsContentLongClick(json: String) {
        try {
            val dtoWebTouch = gson.fromJson(json, DtoWebTouch::class.java)
//            Timber.d("==== JS Long Click DTO ==== %s", dtoWebTouch.toText())
            contentWebView.onLongTap(dtoWebTouch)
        } catch (e: IOException) {
            Timber.e(e, "Failed to parse jsContentLongClick json")
        }
    }

    @JavascriptInterface
    fun jsReportHighlightSelectionInfo(json: String, forNote: Boolean) {
        try {
            val highlightInfo = ArrayList(Arrays.asList(*gson.fromJson(json, Array<DtoHighlightInfo>::class.java)))
            if (forNote) {
                contentWebView.onMultipleStickyTappedListener(highlightInfo)
            } else {
                contentWebView.onMultipleHighlightsTappedListener(highlightInfo)
            }
        } catch (e: Exception) {
            Timber.e(e, "jsReportHighlightSelectionInfo ERROR: %s", json)
        }
    }

    /**
     * @param selectAnnotation true if this annotation should start selection mode with this annotation
     * @param selectedText if selectAnnotation == true: text that is in the highlighted OR "" if selectAnnotation == false
     */
    @JavascriptInterface
    fun jsReportHighlightData(json: String, selectedText: String, selectAnnotation: Boolean) {
        val density = uiUtil.getDeviceDisplayDensity(contentWebView.context)
        try {
            val annotationProperties = gson.fromJson(json, DtoWebAnnotationProperties::class.java)
            val uniqueId = annotationProperties.uniqueId

            // make sure we have an annotation
            var annotation = contentWebView.getAnnotationForUniqueId(annotationProperties.uniqueId)
            if (annotation == null) {
                annotation = Annotation()
                if (uniqueId.isNotEmpty()) {
                    annotation.uniqueId = uniqueId
                }
            }

            var leftX: Double? = null
            var leftY: Double? = null
            var rightX: Double? = null
            var rightY: Double? = null
            var lineHeight: Double? = null

            var highlightTopY = 0.0
            var highlightBottomY = 0.0

            // Remove all existing highlights for this annotation and replace with newly provided highlights
            val highlights = ArrayList<Highlight>()
            for (dtoHighlight in annotationProperties.highlights) {
                val highlight = Highlight()
                dtoHighlight.applyToHighlight(highlight)
                highlights.add(highlight)
            }

            var firstRect = true
            for (rect in annotationProperties.rects) {
                val bottom = rect.bottom
                val height = rect.height
                if (leftY == null || bottom <= leftY) {
                    leftY = bottom
                    leftX = rect.left
                }

                if (rightY == null || bottom >= rightY) {
                    rightY = bottom
                    rightX = rect.right
                }

                if (lineHeight == null || lineHeight <= height) {
                    lineHeight = height
                }

                if (firstRect) {
                    highlightTopY = rect.top

                    firstRect = false
                }

                highlightBottomY = rect.bottom
            }

            // identify if the highlights changed, prior to assigning the new highlights to the annotation
            val highlightsChanged = highlightsChanged(annotation.highlights, highlights)

            // remove and replace the highlights for this annotation
            annotation.removeAllHighlights()
            annotation.highlights = highlights

            if (selectAnnotation) {
                // make sure the annotation is set and the selectedText is updated
                contentWebView.setSelectedAnnotation(annotation, selectedText)

                if (!annotation.isNewRecord && highlightsChanged) {
                    // since "selectAnnotation" == true, then the user may have made changes, so save the changes
                    contentWebView.saveSelectedAnnotationFromHandleMove()
                }

                var leftHandleX = 0
                var leftHandleY = 0
                var rightHandleX = 0
                var rightHandleY = 0

                val lineAdjustment = 0.15f
                if (leftX != null && leftY != null && lineHeight != null) {
                    val adjustY = lineHeight * lineAdjustment.toDouble() * density.toDouble() + contentWebView.paddingTop
                    val adjustX = contentWebView.leftHandleImageView.width.toDouble() / 2.0f
                    leftHandleX = (leftX * density - adjustX).toInt()
                    leftHandleY = (leftY * density - adjustY).toInt()
                }
                if (rightX != null && rightY != null && lineHeight != null) {
                    val adjustY = lineHeight * lineAdjustment.toDouble() * density.toDouble() + contentWebView.paddingTop
                    val adjustX = contentWebView.rightHandleImageView.width.toDouble() / 2.0f
                    rightHandleX = (rightX * density - adjustX).toInt()
                    rightHandleY = (rightY * density - adjustY).toInt()
                }

                val topOfHighlightYDensity = (highlightTopY * density).toInt()
                val bottomOfHighlightYDensity = (highlightBottomY * density).toInt()

                contentWebView.startSelectMode(leftHandleX, leftHandleY, rightHandleX, rightHandleY, topOfHighlightYDensity, bottomOfHighlightYDensity)
            }

            // save rects to help with future touch events
            contentWebView.updateAnnotationProperties(annotationProperties)
        } catch (e: Exception) {
            Timber.e(e, "JSON Exception")
        }
    }

    private fun highlightsChanged(existingHighlights: MutableList<Highlight>, newHighlights: ArrayList<Highlight>): Boolean {
        if (existingHighlights.size != newHighlights.size) {
            return true
        }

        existingHighlights.forEachIndexed { index, existingHighlight ->
            val newHighlight = newHighlights[index]

            if (!existingHighlight.sameLocationsAndStyle(newHighlight)) {
                return true
            }
        }

        return false
    }

    @JavascriptInterface
    fun jsReportSelectionProblem() {
        Timber.w("Fine-grained text selection not supported on this device")
    }

    @JavascriptInterface
    fun jsReportInlineVideoInfo(json: String) {
        try {
            contentWebView.inlineVideos = gson.fromJson(json, DtoInlineVideos::class.java)
        } catch (e: Exception) {
            Timber.e(e, "Failed to parse videos json ")
        }

    }

    @JavascriptInterface
    fun jsReportImageInfo(json: String) {
        try {
            contentWebView.inlineImages = gson.fromJson(json, DtoImages::class.java)
        } catch (e: Exception) {
            Timber.e(e, "Failed to parse image json ")
        }

    }

    @JavascriptInterface
    fun jsReportParagraphAidPositions(json: String) {
        try {
            val paragraphAidLocations = ArrayList(Arrays.asList(*gson.fromJson(json, Array<DtoParagraphAidPosition>::class.java)))
            contentWebView.setParagraphAidLocations(paragraphAidLocations)
        } catch (e: IOException) {
            Timber.e(e, "Failed to parse ParagraphAidPositions ")
        }

    }

    @JavascriptInterface
    fun jsReportAidForTapPosition(aid: String) {
        if (aid.isNotEmpty()) {
            contentWebView.onParagraphTapped(aid)
        }
    }

    @JavascriptInterface
    fun jsReportAidForScrollPosition(aid: String?) {
        aid ?: return

        if (aid.isNotEmpty()) {
            contentWebView.onNotifyScrollPositionAid(aid)
        }
    }

    fun init(view: ContentWebView): ContentJsInterface {
        this.contentWebView = view
        return this
    }
}
package org.lds.ldssa.ui.web

import com.google.gson.Gson
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.webview.content.dto.DtoWebAnnotation
import org.lds.ldssa.model.webview.content.dto.DtoWebAnnotationList
import org.lds.ldssa.model.webview.content.dto.DtoWebAnnotationProperties
import org.lds.ldssa.util.HighlightColor
import org.lds.ldssa.util.annotations.AnnotationListUtil
import org.lds.mobile.util.DensityPosition
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentJsInvoker @Inject
constructor(private val annotationListUtil: AnnotationListUtil, private val gson: Gson) {

    fun updateBottomMargin(webView: ContentWebView, deltaMargin: Int) {
        webView.loadUrl("javascript:LDS.util.updateBottomMargin($deltaMargin)")
    }

    fun touchDown(webView: ContentWebView, position: DensityPosition) {
        webView.loadUrl("""javascript:LDS.annotation.onTouchDown(${position.x}, ${position.y})""")
    }

    fun onTouchUp(webView: ContentWebView) {
        webView.loadUrl("javascript:LDS.annotation.onTouchUp()")
    }

    fun onTouchMove(webView: ContentWebView, position: DensityPosition) {
        webView.loadUrl("""javascript:LDS.annotation.onTouchMove(${position.x}, ${position.y})""")
    }

    fun enterSelectModeFromLongPress(webView: ContentWebView, x: Int, y: Int) {
        try {
            webView.loadUrl("""javascript:LDS.annotation.enterSelectModeFromLongPress('${newAnnotationJson()}', $x, $y)""")
        } catch (e: IOException) {
            Timber.e(e, "Failure with enterSelectModeFromLongPress")
        }

    }

    fun enterSelectModeFromHandle(webView: ContentWebView, annotation: Annotation, x: Int, y: Int, right: Boolean, offsetPosition: DensityPosition) {
        try {
            webView.loadUrl("javascript:LDS.annotation.enterSelectModeFromHandle(\'" +
                    gson.toJson(DtoWebAnnotation(annotation)) + "\', " +
                    x + ", " +
                    y + ", " +
                    right + ", " +
                    offsetPosition.x + ", " +
                    offsetPosition.y + ")")
        } catch (e: IOException) {
            Timber.e(e, "Failure with enterSelectModeFromLongPress")
        }

    }

    @Throws(IOException::class)
    private fun newAnnotationJson(): String {
        val annotation = Annotation()
        val dtoAnnotation = DtoWebAnnotation(annotation)
        dtoAnnotation.colorName = HighlightColor.SELECTION.htmlName

        return gson.toJson(dtoAnnotation)
    }

    // Content

    /**
     * Make modifications to images, videos
     */
    fun runHtmlCustomizations(webView: ContentWebView) {
        webView.loadUrl("javascript:LDS.main.createContentHtmlCustomizations();")
    }

    fun removeAnnotationSelectionDivs(webView: ContentWebView) {
        webView.loadUrl("""javascript:LDS.annotation.removeDivsForAnnotationId("")""")
    }

    fun removeAnnotationDivs(webView: ContentWebView, uniqueId: String) {
        webView.loadUrl("""javascript:LDS.annotation.removeDivsForAnnotationId("$uniqueId")""")
    }

    /**
     * Shows / Updates highlight
     */
    fun showHighlight(webView: ContentWebView, annotation: Annotation, selectAnnotation: Boolean) {
        try {
            webView.loadUrl("""javascript:LDS.annotation.showHighlight('${gson.toJson(DtoWebAnnotation(annotation))}', $selectAnnotation)""")
        } catch (e: Exception) {
            Timber.e(e, "Failure with showHighlight")
        }

    }

    fun showBookmarkIndicator(webView: ContentWebView, annotation: Annotation) {
        val bookmark = annotation.bookmark
        if (bookmark != null) {
            webView.loadUrl("javascript:LDS.annotation.showBookmarkIndicator('${annotation.uniqueId}', '${bookmark.paragraphAid}')")
        }
    }

    // Annotations

    fun setSelectedAnnotationIdOnHighlights(webView: ContentWebView, uniqueId: String) {
        webView.loadUrl("""javascript:LDS.annotation.setSelectedAnnotationIdOnHighlights("$uniqueId")""")
    }

    fun requestAnnotationPropertiesForAnnotationList(webView: ContentWebView, annotations: List<Annotation>, annotationPropertiesList: List<DtoWebAnnotationProperties>, forNote: Boolean) {
        val list = DtoWebAnnotationList()
        for (annotationProperties in annotationPropertiesList) {
            val annotation = annotationListUtil.findAnnotationByUniqueId(annotations, annotationProperties.uniqueId)
            if (annotation != null) {
                list.annotations.add(DtoWebAnnotation(annotation))
            }
        }

        try {
            webView.loadUrl("""javascript:LDS.annotation.requestAnnotationPropertiesForAnnotationList('${gson.toJson(list)}', $forNote)""")
        } catch (e: IOException) {
            Timber.e(e, "Failure with requestAnnotationPropertiesForAnnotationList")
        }

    }

    // Cleanup
    fun clearAnnotations(webView: ContentWebView) {
        webView.loadUrl("javascript:LDS.annotation.clearAnnotations()")
    }

    fun scrollToParagraphByAid(webView: ContentWebView, aid: String) {
        webView.loadUrl("javascript:LDS.main.scrollToParagraphByAid('$aid')")
    }

    fun scrollToParagraphByAid(webView: ContentWebView, aid: String, highlightParagraphAids: Array<String>) {
        webView.loadUrl("""javascript:LDS.main.scrollAndMarkParagraphsByAid('$aid', '${highlightParagraphAids.joinToString(",")}')""")
    }

    fun requestScrollToMark(webView: ContentWebView, position: Int) {
        webView.loadUrl("javascript:LDS.main.scrollToMark($position)")
    }

    fun requestRemoveAllMarks(webView: ContentWebView) {
        webView.loadUrl("javascript:LDS.main.removeAllMarks()")
    }

    fun requestParagraphAidPositions(webView: ContentWebView) {
        webView.loadUrl("javascript:LDS.annotation.requestAllParagraphAidPositions()")
    }

    fun requestAidFromTapPosition(webView: ContentWebView, xPos: Int, yPos: Int) {
        webView.loadUrl("javascript:LDS.main.getAidForTapPosition($xPos, $yPos)")
    }

    fun requestAidFromScrollPosition(webView: ContentWebView, xPos: Int, yPos: Int) {
        webView.loadUrl("javascript:LDS.main.getAidForScrollPosition($xPos, $yPos)")
    }

    fun markParagraphByAid(webView: ContentWebView, paragraphAid: String) {
        webView.loadUrl("javascript:LDS.main.markParagraphByAid($paragraphAid)")
    }

    fun removeParagraphMarkByAid(webView: ContentWebView, paragraphAid: String) {
        webView.loadUrl("javascript:LDS.main.removeParagraphMarkByAid($paragraphAid)")
    }

    fun removeAllParagraphMarks(webView: ContentWebView) {
        webView.loadUrl("javascript:LDS.main.removeAllParagraphMarks()")
    }
}

package org.lds.ldssa.util.annotations

import android.content.Context
import android.graphics.Point
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.BackgroundColorSpan
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadata
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.highlight.Highlight
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.prefs.PrefsConst
import org.lds.ldssa.model.prefs.model.HighlightHistoryItem
import org.lds.ldssa.util.ContentParagraphUtil
import org.lds.ldssa.util.HighlightColor
import java.util.ArrayList
import java.util.Collections
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A Utility for standardizing the way that highlighting is done
 * on TextViews.
 * **NOTE:** This is not how highlighting is done in the content
 * itself, that is performed by javascript and css
 */
@Singleton
class HighlightUtil @Inject
constructor(private val prefs: Prefs,
            private val contentParagraphUtil: ContentParagraphUtil) {

    /**
     * Retrieves an updated list of the most recent highlight colors and styles.
     *
     * @param mostRecent The new item to be at the top of the list
     * @return The updated highlight color and styles list
     */
    fun getUpdatedHighlightHistory(mostRecent: HighlightHistoryItem): List<HighlightHistoryItem> {
        val items = prefs.contentDisplayRecentHighlights
        var updated = false

        //If the mostRecent is already stored, just move it to the top
        for (item in items) {
            if (item == mostRecent) {
                items.remove(item)
                items.add(0, item)
                updated = true
                break
            }
        }

        if (!updated) {
            items.add(0, mostRecent)
        }

        //Makes sure we only store 5 items
        for (i in PrefsConst.RECENT_HIGHLIGHT_HISTORY_ITEM_COUNT until items.size) {
            items.removeAt(i)
        }

        return items
    }

    fun createHighlightSpannableString(context: Context, content: String, color: String, style: String): SpannableString {
        val spannableString = SpannableString(Html.fromHtml(content))
        return applyHighlight(context, spannableString, color, style, 0, content.length)
    }

    fun applyHighlight(context: Context, spannable: SpannableString, color: String?, style: String?, start: Int, end: Int): SpannableString {
        if (start > end || start < 0 || end > spannable.length) {
            return spannable
        }

        val isUnderline = HighlightAnnotationStyle.isUnderline(style)

        //Retrieves the correct color for the highlight type
        val colorInt = if (isUnderline) HighlightColor[color].getColorUnderline(context) else HighlightColor[color].getColorFill(context)

        //Performs the actual highlighting
        @Suppress("IMPLICIT_CAST_TO_ANY")
        val span = if (isUnderline) UnderlineColorSpan(colorInt) else BackgroundColorSpan(colorInt)
        spannable.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannable
    }

    /**
     * Retrieves the text paragraphs containing the highlighted text; correctly formatting the
     * highlights and stripping out any unused information for display (such as links).
     *
     * @param annotation The annotation specifying the highlight information
     * @param metadata The metadata containing document specific information
     * @return The correctly styled paragraphs containing the highlighted text
     */
    fun getHighlightedAnnotationParagraphs(context: Context, annotation: Annotation, metadata: SubItemMetadata?): SpannableString? {
        val highlights = annotation.highlights
        if (highlights.isEmpty() || metadata == null) {
            return null
        }

        //Retrieves the highlight paragraphs
        val content = contentParagraphUtil.getParagraphs(contentParagraphUtil.getParagraphAids(annotation), metadata.itemId, metadata.subitemId) ?: return null

        //Sorts the highlights by aId
        Collections.sort(highlights) { lhs, rhs -> (java.lang.Long.parseLong(lhs.paragraphAid) - java.lang.Long.parseLong(rhs.paragraphAid)).toInt() }

        //split paragraphs based on "<p" keeping the split
        val regex = """(?=[\.\s]<p)"""
        val paragraphs = content.split(regex.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        //Verifies we have the same sizes
        if (paragraphs.size != highlights.size) {
            return null
        }

        var highlightedParagraphs: SpannableString? = null
        for (index in paragraphs.indices) {
            val highlight = highlights[index]
            val paragraph = paragraphs[index]
            highlightedParagraphs = mergeSpannableStrings(highlightedParagraphs, applyHighlight(context, paragraph, highlight))
        }

        return highlightedParagraphs
    }

    /**
     * Retrieves the correctly styled paragraph text using the specified spannable as a basis.
     *
     * @param paragraph The content paragraph to highlight with
     * @param highlight The Highlight to use for the content
     * @return The correctly highlighted content
     */
    private fun applyHighlight(context: Context, paragraph: String, highlight: Highlight): SpannableString {
        var formattedParagraph = paragraph
        val offsets = getCharacterOffsets(formattedParagraph, highlight.offsetStart, highlight.offsetEnd)

        //Removes the Links (e.g. Exodus 20:5), and any superscript items from the html
        formattedParagraph = formattedParagraph.replace(REGEX_STRIP_HTML_SUP.toRegex(), "")

        //Strips out the link formatting
        var spannable = SpannableString(Html.fromHtml(formattedParagraph))
        spannable = SpannableString(spannable.toString().replace("[\n]+".toRegex(), "\n").trim { it <= ' ' })

        //If we don't have a valid highlight range then return the current spannable
        return if (offsets.x > offsets.y) {
            spannable
        } else applyHighlight(context, spannable, highlight.color, highlight.style, offsets.x, offsets.y)

    }

    /**
     * Determines the character offsets using the same mechanism that the javascript does.  This means
     * that each separate span (links, text, etc.) have their text split by whitespace and hyphens to find
     * the word offset.
     *
     * Additionally, we decrement the amount of characters for the `sup` tags since those will be
     * removed.
     *
     * @param paragraphs The paragraph text to use for finding the start and end offsets
     * @param startWordOffset The word offset for the start of the highlight
     * @param endWordOffset The word offset for the end of the highlight
     * @return The character offsets found with the `startWordOffset` and `endWordOffset`
     */
    private fun getCharacterOffsets(paragraphs: String, startWordOffset: Int, endWordOffset: Int): Point {
        if (startWordOffset > endWordOffset && endWordOffset != -1) {
            return Point(Integer.MAX_VALUE, -1)
        }

        var characterCount = 0
        var currentWordCount = 0
        var currentSuperscriptCharacterCount = 0

        var startCharacterOffset = 0
        var endCharacterOffset = 0

        var finished = false
        val nodeIterator = NodeIterator(Jsoup.parse(paragraphs))

        while (!finished && nodeIterator.hasNext()) {
            val node = nodeIterator.next()
            var nodeText = ""
            if (node is Element) {
                nodeText = node.text()
            } else if (node is TextNode) {
                nodeText = node.text()
            }

            val words = nodeText.split(REGEX_WORD_SPLIT.toRegex()).toTypedArray()

            for (index in words.indices) {
                val word = words[index]
                var wordCharacterCount = word.length

                characterCount += wordCharacterCount

                //We discount the superscript characters because they will be removed
                if (node is Element && SUPER_SCRIPT_TAG == node.tagName()) {
                    currentSuperscriptCharacterCount += wordCharacterCount
                }

                //Add 1 for the delimiter (space, hyphen, etc.) only if we aren't the last word in the element
                if (index != words.size - 1) {
                    characterCount++
                    wordCharacterCount++
                }

                if (word.trim { it <= ' ' }.isEmpty()) {
                    continue
                }

                currentWordCount++

                if (currentWordCount == startWordOffset) {
                    startCharacterOffset = characterCount - wordCharacterCount - currentSuperscriptCharacterCount
                }

                if (currentWordCount == endWordOffset) {
                    endCharacterOffset = characterCount - currentSuperscriptCharacterCount
                    finished = true
                }
            }
        }

        //Makes sure to use the correct end offset
        if (endWordOffset == -1) {
            endCharacterOffset = characterCount - currentSuperscriptCharacterCount
        }

        return Point(startCharacterOffset, endCharacterOffset)
    }

    /**
     * Merges the two specified SpannableStrings in to a single one
     *
     * @param left The SpannableString that will come first in the merged version
     * @param right The SpannableString that will come second in the merged version
     * @return The merged SpannableString
     */
    private fun mergeSpannableStrings(left: SpannableString?, right: SpannableString?): SpannableString? {
        if (left == null && right == null) {
            return null
        }

        if (left == null) {
            return right
        }

        return if (right == null) {
            left
        } else {
            SpannableString(TextUtils.concat(left, "\n\n", right))
        }
    }

    /**
     * An Iterator for a JSoup Document used to iterate over nodes in the
     * document in depth first order
     */
    private class NodeIterator(document: Document) : Iterator<Node> {
        private var currentIndex = -1
        private val nodes = ArrayList<Node>()

        init {
            init(document)
        }

        override fun hasNext(): Boolean {
            return currentIndex < nodes.size - 1
        }

        override fun next(): Node {
            return nodes[++currentIndex]
        }

        private fun init(node: Node) {
            if (node.childNodeSize() == 0 || node.childNodeSize() == 1 && node.childNode(0) is TextNode) {
                nodes.add(node)
                return
            }

            for (child in node.childNodes()) {
                init(child)
            }
        }
    }

    /**
     * A Span that will correctly set the color for the underline
     * text in a Spannable.
     */
    private class UnderlineColorSpan(private val color: Int) : CharacterStyle(), UpdateAppearance {

        override fun updateDrawState(paint: TextPaint) {
            try {
                //NOTE: setUnderlineText is a public method, but is hidden for some reason
                val method = TextPaint::class.java.getMethod("setUnderlineText", Integer.TYPE, java.lang.Float.TYPE)
                method.invoke(paint, color, UNDERLINE_HEIGHT)
            } catch (e: Exception) {
                paint.isUnderlineText = true
            }

        }

        companion object {
            private val UNDERLINE_HEIGHT = 4.0f
        }
    }

    companion object {
        private const val REGEX_STRIP_HTML_SUP = """<sup [\s\S]+?</sup>"""
        //NOTE: the unicode characters are pulled from the javascript [April 25, 2016] and represent different hyphens/dashes
        private const val REGEX_WORD_SPLIT = """[\s\u002D\u058A\u05BE\u1400\u1806\u2010-\u2015\u2053\u207B\u208B\u2212\u2E17\u2E1A\u2E3A-\u301C\u3030\u30A0\uFE31\uFE32\uFE58\uFE63\uFF0D]""" //NOSONAR
        private const val SUPER_SCRIPT_TAG = "sup"
    }
}

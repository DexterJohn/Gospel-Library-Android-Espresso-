package org.lds.ldssa.search

import android.support.annotation.Size
import org.apache.commons.lang3.StringUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist
import org.lds.ldssa.model.database.catalog.stopword.StopWordManager
import org.lds.ldssa.model.database.search.searchcollection.SearchCollectionManager
import org.lds.ldssa.model.database.search.searchcontentcollectionmap.SearchContentCollectionMapManager
import org.lds.ldssa.model.database.search.searchcountallnotes.SearchCountAllNotesManager
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContentManager
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNoteManager
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitemManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.util.LanguageUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.ArrayList
import java.util.Arrays
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SearchUtil @Inject constructor(
        val prefs: Prefs,
        val languageUtil: LanguageUtil,
        private val searchCollectionManager: SearchCollectionManager,
        private val searchCountAllNotesManager: SearchCountAllNotesManager,
        private val searchCountContentManager: SearchCountContentManager,
        private val searchContentCollectionMapManager: SearchContentCollectionMapManager,
        private val searchPreviewNoteManager: SearchPreviewNoteManager,
        private val searchPreviewSubitemManager: SearchPreviewSubitemManager,
        private val stopWordManager: StopWordManager) {


    fun clearSearch(screenId: Long) {
        searchCollectionManager.inTransaction {
            // counts
            searchCountAllNotesManager.deleteAllByTabId(screenId)
            searchCollectionManager.deleteAllByTabId(screenId)
            searchCountContentManager.deleteAllByTabId(screenId)
            searchContentCollectionMapManager.deleteAllByTabId(screenId)

            // previews
            searchPreviewNoteManager.deleteAllByScreenId(screenId)
            searchPreviewSubitemManager.deleteAllByTabId(screenId)

            true // success
        }
    }

    /**
     * Prepare the sql fts query phrase
     * @param searchText The original query string
     * *
     * @return The return value is an instance of SearchTextRequest that contains two values but it can also be one value.
     * * If the user is performing a regular query then the result could contain keywords and a phrase if a phrase is detected
     * * or just keywords if only keywords are detected.
     * * If the user is performing an advanced query then the result will only have keywords.
     */
    fun createSearchRequest(searchText: String, languageId: Long = prefs.getLastSelectedLanguageId(languageUtil), forceExactSearchOnly: Boolean = false): SearchTextRequest? {
        if (searchText.isBlank()) {
            return null
        }

        var formattedSearchText = searchText

        // if the searchText is surrounded in quotes, remove the quotes
        val exactSearchOnly = forceExactSearchOnly || isExactPhraseSearchText(searchText)
        if (exactSearchOnly) {
            formattedSearchText = removeSearchQuotes(searchText)
        }

        formattedSearchText = removePunctuation(formattedSearchText)
        if (formattedSearchText.isBlank()) {
            return null
        }

        val keywordList: List<String>
        if (exactSearchOnly) {
            keywordList = emptyList()
        } else {
            // find all keywords (without stop words)
            keywordList = createSearchKeywords(languageId, formattedSearchText)
            if (keywordList.isEmpty()) {
                return null
            }
        }

        return SearchTextRequest(formattedSearchText, keywordList, exactSearchOnly)
    }

    fun removePunctuation(searchText: String): String {
        return StringUtils.normalizeSpace(searchText.replace(REMOVE_ALL_PUNCTUATION.toRegex(), " "))
    }

    /**
     * Returns a list of unique keywords that are not "stop words"
     */
    fun createSearchKeywords(languageId: Long, searchText: String): List<String> {
        // lowercase
        var formattedSearchText = searchText.toLowerCase().trim { it <= ' ' }

        // remove parens
        formattedSearchText = removeParenthesis(formattedSearchText)

        // split search terms
        val searchTextKeywords = formattedSearchText.split(" ".toRegex())

        // find stop words
        val stopWords = stopWordManager.filterListOfWordsByLanguageId(languageId, searchTextKeywords)

        // add non-stop words to a new list
        val newSearchTextKeywords = ArrayList<String>()
        if (searchTextKeywords.isNotEmpty()) {
            searchTextKeywords
                    .filter { it.isNotEmpty() && !stopWords.contains(it) && !newSearchTextKeywords.contains(it) } // if this word is NOT a stop word and this term is NOT already in the keywords... add to the list
                    .forEach { newSearchTextKeywords.add(it) }
        }
        return newSearchTextKeywords
    }

    fun getFtsCount(matchInfoXBlob: ByteArray): Long {
        var total = 0L
        if (matchInfoXBlob.size % HITS_LENGTH == 0) {
            val hits = matchInfoXBlob.size / HITS_LENGTH
            for (x in 0 until hits) {
                total += getTitleCount(matchInfoXBlob, x) + getContentCount(matchInfoXBlob, x)
            }
        }
        return total
    }

    private fun getContentCount(@Size(min = HITS_LENGTH.toLong()) blob: ByteArray, index: Int): Int {
        return parseFourByteArrayToInt(Arrays.copyOfRange(blob, index * HITS_LENGTH + HITS_SIZE, index * HITS_LENGTH + HITS_LENGTH))
    }

    private fun getTitleCount(@Size(min = HITS_LENGTH.toLong()) blob: ByteArray, index: Int): Int {
        return parseFourByteArrayToInt(Arrays.copyOfRange(blob, index * HITS_LENGTH, index * HITS_LENGTH + HITS_SIZE))
    }

    /**
     * Convert byte[] into an int
     */
    private fun parseFourByteArrayToInt(data: ByteArray): Int {
        if (data.size == FOUR_BYTE_ARRAY_LENGTH) {
            val byteBuffer = ByteBuffer.allocateDirect(FOUR_BYTE_ARRAY_LENGTH)
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
            byteBuffer.put(data[FOUR_BYTE_ARRAY_POS_0])
            byteBuffer.put(data[FOUR_BYTE_ARRAY_POS_1])
            byteBuffer.put(data[FOUR_BYTE_ARRAY_POS_2])
            byteBuffer.put(data[FOUR_BYTE_ARRAY_POS_3])
            byteBuffer.flip()
            return byteBuffer.short.toInt()
        }
        return -1
    }

    fun removeParenthesis(text: String): String {
        return text.trim { it <= ' ' }.replace("[()]".toRegex(), "")
    }

    // todo needed?  (was in SearchPreviewCallable)
    fun cleanupPreview(previewText: String): String {
        // check for a verse number span that doesn't have an opening tag
        val preview = StringBuilder(previewText)
        val spanCheckClose = StringUtils.indexOf(preview, SPAN_CLOSE)
        val spanCheckOpen = StringUtils.indexOf(preview, SPAN_OPEN)
        if (spanCheckClose < spanCheckOpen) {
            preview.insert(0, SPAN_ADD_VERSE_NUMBER)
        }

        // remove all study markers
        val doc = Jsoup.parse(preview.toString())
        removeTags(doc, SUP_STUDY_MARKER)
        removeTags(doc, LDS_META_TAG)
        removeTags(doc, TITLE_NUMBER_TAG)

        // add a space after the verse numbers
        for (verseNumber in doc.select(SPAN_VERSE_NUMBER)) {
            verseNumber.appendText(" ")
        }

        // remove all tags except for bold
        val whitelist = Whitelist()
        whitelist.addTags(BOLD)

        //        return new SearchPreviewResult(queryItem, Jsoup.clean(doc.body().html(), whitelist), searchUtil.getFtsCount(queryItem.getMatchInfo()));
        return ""
    }

    private fun removeTags(doc: Document, tag: String) {
        for (element in doc.select(tag)) {
            element.remove()
        }
    }

    fun isExactPhraseSearchText(searchText: String): Boolean {
        return searchText.startsWith("\"")
    }

    fun removeSearchQuotes(exactMatchSearchText: String): String {
        var formattedText: String = exactMatchSearchText
        if (formattedText.startsWith("\"")) {
            formattedText = formattedText.substring(1)
        }
        if (formattedText.endsWith("\"")) {
            formattedText = formattedText.substring(0, (formattedText.length - 1))
        }

        return formattedText
    }


    companion object {
        const val ELLIPSE_DIVIDER = " <b>...</b>"

        const val SNIPPET_LENGTH = 34

        private const val FOUR_BYTE_ARRAY_LENGTH = 4
        private const val FOUR_BYTE_ARRAY_POS_0 = 0
        private const val FOUR_BYTE_ARRAY_POS_1 = 1
        private const val FOUR_BYTE_ARRAY_POS_2 = 2
        private const val FOUR_BYTE_ARRAY_POS_3 = 3

        private const val HITS_LENGTH = 8
        private const val HITS_SIZE = 4

        private const val REMOVE_ALL_PUNCTUATION = "[^()\\p{L}\\p{Nd}]+"

        // preview cleanup
        private const val SUP_STUDY_MARKER = "sup.marker"
        private const val SPAN_VERSE_NUMBER = "span.verse-number"
        private const val SPAN_ADD_VERSE_NUMBER = "<span class=\"verse-number\">"
        private const val LDS_META_TAG = "lds|meta"
        private const val TITLE_NUMBER_TAG = "p.title-number"
        private const val SPAN_OPEN = "<span"
        private const val SPAN_CLOSE = "</span"
        private const val BOLD = "b"
    }
}

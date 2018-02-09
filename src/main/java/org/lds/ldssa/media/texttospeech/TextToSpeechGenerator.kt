package org.lds.ldssa.media.texttospeech

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.lds.ldssa.ui.web.ContentData
import javax.inject.Inject

class TextToSpeechGenerator @Inject constructor() {

    fun generateTextToSpeak(contentData: ContentData): List<TextToSpeechParagraph> {
        return generateParagraphs(contentData)
    }

    private fun generateParagraphs(contentData: ContentData): List<TextToSpeechParagraph> {

        val document = Jsoup.parse(contentData.content)
        removeTags(document, "lds:meta")
        removeTags(document, "sup")
        removeTags(document, "img")
        removeSurroundingTags(document, "span")

        val paragraphList = ArrayList<TextToSpeechParagraph>()

        val elements = Jsoup.parse(document.html()).select("h1, h2, h3, p")
        var currentParagraphPosition = 0
        for (element in elements) {
            if (element.hasAttr("id")) {
                if (element.hasClass("reference") || element.hasClass("short-reference") || element.hasClass("reference-info")) {
                    continue
                }
                val paragraphId = element.attr("id")
                if (paragraphId.contains(PARAGRAPH_REGEX)) {
                    val position = paragraphId.takeLastWhile(Char::isDigit).toInt()
                    currentParagraphPosition = if (position > currentParagraphPosition) position else currentParagraphPosition + 1
                }
                paragraphList.add(TextToSpeechParagraph(currentParagraphPosition, paragraphId, element.text()))
            }
        }

        return paragraphList
    }

    private fun removeTags(document: Document, tag: String) {
        val elements = document.getElementsByTag(tag)
        elements.forEach(Element::remove)
    }

    private fun removeSurroundingTags(document: Document, tag: String) {
        val elements = document.getElementsByTag(tag)
        elements.forEach { element ->
            var text = element.text()
            if (element.hasClass(VERSE_NUMBER_CLASS)) {
                text = ""
            }
            element.before(text)
            element.remove()
        }
    }

    companion object {
        val VERSE_NUMBER_CLASS = "verse-number"
        val PARAGRAPH_REGEX = Regex("p\\d")
    }
}
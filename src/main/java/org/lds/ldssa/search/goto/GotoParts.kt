package org.lds.ldssa.search.goto

import android.app.Application
import org.apache.commons.lang3.math.NumberUtils
import org.lds.ldssa.R
import org.lds.ldssa.util.CitationUtil

class GotoParts {

    var bookNumber = 0
    var bookName = ""
    var chapter = ""
    var verse: String = ""
    private val application: Application?

    constructor(application: Application?) {
        this.application = application
    }

    constructor(application: Application?, bookNumber: Int, bookName: String, chapter: String, verse: String) {
        this.bookNumber = bookNumber
        this.bookName = bookName
        this.chapter = chapter
        this.verse = verse
        this.application = application
    }

    constructor(application: Application?, chapter: String, verse: String) {
        this.chapter = chapter
        this.verse = verse
        this.application = application
    }

    constructor(application: Application?, gotoParts: GotoParts) {
        this.bookNumber = gotoParts.bookNumber
        this.bookName = gotoParts.bookName
        this.chapter = gotoParts.chapter
        this.verse = gotoParts.verse
        this.application = application
    }

    val isComplete: Boolean
        get() = !bookName.isEmpty() && !chapter.isEmpty() && !verse.isEmpty()

    val bookSearchText: String
        get() {
            if (bookNumber > 0) {
                return bookNumber.toString() + " " + bookName
            } else {
                return bookName
            }
        }

    val isChapterDefined: Boolean
        get() = chapter.isNotBlank()

    // note: Xiaomi devices don't support isCreatable(...)
    val chapterValue: Int
        get() {
            if (NumberUtils.isNumber(chapter)) {
                return Integer.parseInt(chapter)
            } else {
                return 0
            }
        }

    val isVerseDefined = verse.isNotBlank()

    val verseDefined: Boolean
        get() {
            return verse.isNotBlank()
        }

    // note: Xiaomi devices don't support isCreatable(...)
    val verseValue: Int
        get() {
            if (NumberUtils.isNumber(verse)) {
                return Integer.parseInt(verse)
            } else {
                return 0
            }
        }

    fun shiftLastChapterNumberToVerse(): Boolean {
        // chapter must have at least 2 characters
        if (chapter.length <= 1) {
            return false
        }

        // remove last character from chapter
        val lastChar = chapter[chapter.length - 1]

        chapter = chapter.dropLast(1)

        // make sure there is room in the verse
        if (verse.length >= MAX_VERSE_LENGTH) {
            verse = verse.dropLast(1)
        }

        // insert character at that beginning of the verse
        if (verse.isBlank()) {
            verse = lastChar.toString()
        } else {
            verse = lastChar + verse
        }

        return true
    }

    /**
     * Fix the chapter to not exceed the max chapter count
     * @return true if adjustments were made to fix the chapter correctly
     */
    fun setMaxChapterCount(chapterCount: Int): Boolean {
        if (chapter.isBlank()) {
            return false
        }

        val originalChapter = chapter
        val originalVerse = verse

        while (chapterValue > chapterCount || verse.startsWith("0")) {
            if (!shiftLastChapterNumberToVerse()) {
                break
            }
        }

        // if the verse is left with a leading 0, then just restore to the original
        if (verse.startsWith("0")) {
            chapter = originalChapter
            verse = originalVerse
            return false
        }

        return chapterValue <= chapterCount
    }

    // if there is no chapter, then just return
    // parse the int to remove leading zeros
    fun getFormattedChapterVerse(): String {
        var formattedText = ""

        if (chapter.isNotBlank()) {
            formattedText += " " + chapter
        } else {
            return ""
        }

        if (verse.isNotBlank()) {
            formattedText += getChapterVerseSeparator() + Integer.parseInt(verse)
        }

        return formattedText
    }

    // note: unit tests may have a mocked application... which will return a null value
    fun getChapterVerseSeparator(): String {
        val separatorText = application?.getString(R.string.citation_chapter_verse_separator) ?: ":"
        return if (separatorText.isNotBlank()) separatorText else CitationUtil.DEFAULT_CHAPTER_VERSE_SEPARATOR
    }

    fun containsChapterAndVerse(): Boolean {
        return chapter.isNotBlank() && verse.isNotBlank()
    }

    companion object {
        val MAX_VERSE_LENGTH = 3
    }
}

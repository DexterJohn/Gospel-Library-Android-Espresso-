package org.lds.ldssa.search.goto

import android.app.Application

object GotoTextParser {

    private val MAX_DIGITS = 10
    private val VERSE_CHAPTER_MAX_DIGITS = 4 // use 4 so that dc1112 will be parsed with the options of 111:2, 11:12, and 1:112

    @JvmStatic
    fun parseGoto(searchText: String): GotoParts {
        return parseGoto(null, searchText)
    }

    @JvmStatic
    fun parseGoto(application: Application?, searchText: String): GotoParts {
        var bookNameLeadingNumberComplete = false
        var bookNameComplete = false
        var chapterComplete = false
        var verseComplete = false

        val bookName = StringBuilder()

        val gotoParts = GotoParts(application)

        val searchTextChars = searchText.toCharArray()
        var i = 0
        while (i < searchTextChars.size && !gotoParts.isComplete) {
            val c = searchTextChars[i]
            if (c == ' ') {
                if (!bookNameComplete && bookName.isNotEmpty()) {
                    bookName.append(c)
                }

                i++
            } else if (c == ':' || c == '.' || c == ';' || c == '-' || c == '_') {
                // ignore
                i++
            } else if (Character.isDigit(c)) {
                if (!bookNameComplete && bookName.isNotEmpty()) {
                    bookNameComplete = true
                }

                val numberText = getNumber(searchTextChars, i, VERSE_CHAPTER_MAX_DIGITS)

                if (!bookNameComplete && !bookNameLeadingNumberComplete) {
                    gotoParts.bookNumber = Integer.parseInt(numberText)
                    bookNameLeadingNumberComplete = true
                } else if (!chapterComplete) {
                    gotoParts.chapter = numberText
                    chapterComplete = true
                } else if (!verseComplete) {
                    gotoParts.verse = numberText
                    verseComplete = true
                }

                i += numberText.length
            } else if (Character.isLetter(c)) {
                val wordText = getWord(searchTextChars, i)

                if (!bookNameComplete) {
                    bookName.append(wordText)
                    gotoParts.bookName = bookName.toString()
                }

                i += wordText.length
            } else {
                // anything else (punctuation, etc), bail
                break
            }
        }

        // make sure the book name is set if there is text and it hasn't yet been set
        if (!bookNameComplete && bookName.isNotEmpty()) {
            gotoParts.bookName = bookName.toString()
        }

        return gotoParts
    }

    @JvmStatic
    @JvmOverloads
    fun getNumber(chars: CharArray, currentIndex: Int, maxDigits: Int = MAX_DIGITS): String {
        var numberText = ""
        var i = currentIndex
        while (i < chars.size && numberText.length < maxDigits) {
            val c = chars[i]

            if (Character.isDigit(c)) {
                numberText += c
            } else {
                break
            }
            i++
        }

        return numberText
    }

    @JvmStatic
    fun getWord(chars: CharArray, currentIndex: Int): String {
        var text = ""
        (currentIndex..chars.size - 1)
                .map { chars[it] }
                .takeWhile { Character.isLetter(it) || it == '&' } // allow & to support d&c
                .forEach { text += it }

        return text
    }
}

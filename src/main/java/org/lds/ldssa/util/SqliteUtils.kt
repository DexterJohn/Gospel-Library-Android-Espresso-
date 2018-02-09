package org.lds.ldssa.util

import org.lds.ldssa.search.FtsOffsetItem
import timber.log.Timber

object SqliteUtils {
    private const val TOTAL_ITEMS_PER_OFFSET: Int = 4

    fun parseOffsetResultDataOffsetOrder(offsetResults: String, exactPhraseResults: Boolean): List<FtsOffsetItem> {
        return parseOffsetResultData(offsetResults, exactPhraseResults).sortedBy { it.termByteOffsetInContent }
    }

    fun parseOffsetResultData(offsetResults: String, exactPhraseResults: Boolean): List<FtsOffsetItem> {
        Timber.d("sqlite offset data [%s]", offsetResults)

        val ftsOffsetItemList = ArrayList<FtsOffsetItem>()
        var currentPosition = 0
        val offsetItemData = IntArray(TOTAL_ITEMS_PER_OFFSET)
        offsetResults.split(" ").forEach {
            offsetItemData[currentPosition] = it.toIntOrNull() ?: 0
            currentPosition++

            if (currentPosition == TOTAL_ITEMS_PER_OFFSET) {
                ftsOffsetItemList.add(FtsOffsetItem(
                        columnIndex = offsetItemData[0],
                        searchTextTermIndex = offsetItemData[1],
                        termByteOffsetInContent = offsetItemData[2],
                        termSize = offsetItemData[3]
                ))

                currentPosition = 0
            }
        }

        if (exactPhraseResults) {
            return combinePhraseOffsets(ftsOffsetItemList)
        } else {
            return ftsOffsetItemList
        }
    }

    private fun combinePhraseOffsets(ftsOffsetItemList: ArrayList<FtsOffsetItem>): ArrayList<FtsOffsetItem> {
        val combinedList = ArrayList<FtsOffsetItem>()

        ftsOffsetItemList.forEachIndexed { _, offsetItem ->
            if (combinedList.isNotEmpty() && offsetItem.searchTextTermIndex > 0) {
                // add this offsetItem into the previous item, then discard (don't add to combined list)
                combinedList.last().combine(offsetItem)
            } else {
                combinedList.add(offsetItem)
            }
        }

        return combinedList
    }
}
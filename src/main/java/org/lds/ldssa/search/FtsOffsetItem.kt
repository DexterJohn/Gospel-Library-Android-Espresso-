package org.lds.ldssa.search

class FtsOffsetItem(var columnIndex: Int = 0, var searchTextTermIndex: Int = 0, var termByteOffsetInContent: Int = 0, var termSize: Int = 0) {
    fun combine(otherOffsetItem: FtsOffsetItem) {
        termSize = (otherOffsetItem.termByteOffsetInContent + otherOffsetItem.termSize) - termByteOffsetInContent
    }
}
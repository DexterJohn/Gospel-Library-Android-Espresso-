package org.lds.ldssa.util

import org.junit.Assert.assertEquals
import org.junit.Test

class SqliteUtilsTest {
    @Test
    fun parseOffsetResultData() {
        val offsetItems1 = SqliteUtils.parseOffsetResultData("1 0 10732 5 1 1 10738 2 1 2 10741 3", true) // 1 result "faith in god"
        assertEquals(1, offsetItems1.size)
        offsetItems1[0].let { offsetItem ->
            assertEquals(10732, offsetItem.termByteOffsetInContent)
            assertEquals(12, offsetItem.termSize)
        }


        val offsetItems2 = SqliteUtils.parseOffsetResultData("1 0 5340 3 1 1 5345 5 1 0 9682 3 1 1 9687 5", true) // 2 results "now behold"
        assertEquals(2, offsetItems2.size)
        offsetItems2[0].let { offsetItem ->
            assertEquals(5340, offsetItem.termByteOffsetInContent)
            assertEquals(10, offsetItem.termSize)
        }
        offsetItems2[1].let { offsetItem ->
            assertEquals(9682, offsetItem.termByteOffsetInContent)
            assertEquals(10, offsetItem.termSize)
        }

    }

}
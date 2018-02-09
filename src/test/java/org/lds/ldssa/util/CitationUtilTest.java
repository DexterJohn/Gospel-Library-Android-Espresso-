package org.lds.ldssa.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class CitationUtilTest {
    @Test
    public void createVerseRangeText() throws Exception {
        CitationUtil citationUtil = spy(new CitationUtil(null, null, null, null, null, null, null));
        doReturn(":").when(citationUtil).getChapterVerseSeparator();
        doReturn(",").when(citationUtil).getRangeGroupSeparator();
        doReturn("-").when(citationUtil).getRangeSeparator(true);

        assertEquals("", citationUtil.createVerseRangeText(new ArrayList<String>()));
        assertEquals("1", citationUtil.createVerseRangeText(Arrays.asList("1")));
        assertEquals("1-2", citationUtil.createVerseRangeText(Arrays.asList("1", "2")));
        assertEquals("1,3", citationUtil.createVerseRangeText(Arrays.asList("1", "3")));
        assertEquals("1-3", citationUtil.createVerseRangeText(Arrays.asList("1", "2", "3")));
        assertEquals("1-3,5", citationUtil.createVerseRangeText(Arrays.asList("1", "2", "3", "5")));
        assertEquals("1-3,5-6", citationUtil.createVerseRangeText(Arrays.asList("1", "2", "3", "5", "6")));
        assertEquals("1-3,5,8", citationUtil.createVerseRangeText(Arrays.asList("1", "2", "3", "5", "8")));
        assertEquals("1-3,5,8-11", citationUtil.createVerseRangeText(Arrays.asList("1", "2", "3", "5", "8", "9", "10", "11")));
        assertEquals("1-3,5,7,10-12", citationUtil.createVerseRangeText(Arrays.asList("1", "2", "3", "5", "7", "10", "11", "12")));

        assertEquals("1a", citationUtil.createVerseRangeText(Arrays.asList("1a")));
        assertEquals("1a,2b", citationUtil.createVerseRangeText(Arrays.asList("1a", "2b")));
        assertEquals("1a,2b,3c", citationUtil.createVerseRangeText(Arrays.asList("1a", "2b", "3c")));

        assertEquals("1,2b", citationUtil.createVerseRangeText(Arrays.asList("1", "2b")));
        assertEquals("1,2b,3c", citationUtil.createVerseRangeText(Arrays.asList("1", "2b", "3c")));
        assertEquals("1-2,3c", citationUtil.createVerseRangeText(Arrays.asList("1", "2", "3c")));
        assertEquals("1,2b,3", citationUtil.createVerseRangeText(Arrays.asList("1", "2b", "3")));
        assertEquals("1a,2-3", citationUtil.createVerseRangeText(Arrays.asList("1a", "2", "3")));
        assertEquals("1a,1b,1c", citationUtil.createVerseRangeText(Arrays.asList("1a", "1b", "1c")));
    }

}
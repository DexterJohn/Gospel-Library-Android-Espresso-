package org.lds.ldssa.model.database.search.searchsuggestion;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.lds.ldssa.TestFilesystem;

import java.util.List;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SearchSuggestionManagerTest {

    @Inject
    SearchSuggestionManager searchSuggestionManager;

    @Before
    public void setUp() throws Exception {
        TestFilesystem.deleteFilesystem();

        SearchSuggestionManagerTestComponent component = DaggerSearchSuggestionManagerTestComponent.builder().searchSuggestionManagerTestModule(new SearchSuggestionManagerTestModule()).build();
        component.inject(this);
    }

    @Test
    public void parseGoto() throws Exception {

        String htmlTitle = "1 Nephi";
        String formatted = htmlTitle.trim().replace("\u00A0", " ");


        assertEquals("1 Nephi", formatted);
        System.out.println("Char1: " + ((int) ' '));
        System.out.println("Char2: " + ((int) ' '));
    }

    // todo restore test (catalog 488 is busted)
    // @Test
    public void findGotoSuggestions() throws Exception {
        // chapters only
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "1 N", 5), "1 Nephi");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "1n", 5), "1 Nephi");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "1nephi", 5), "1 Nephi");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "1 nephi", 5), "1 Nephi");

        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "n", 5), "1 Nephi", "2 Nephi", "3 Nephi", "4 Nephi", "Numbers");


        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "n1", 5), "1 Nephi 1", "2 Nephi 1", "3 Nephi 1", "4 Nephi 1", "Numbers 1");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "n3", 5), "1 Nephi 3", "2 Nephi 3", "3 Nephi 3", "Numbers 3", "Nehemiah 3"); // 4th Nephi does NOT have 3 chapters
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "1n3", 5), "1 Nephi 3");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "n3:7", 5), "1 Nephi 3:7", "2 Nephi 3:7", "3 Nephi 3:7", "Numbers 3:7", "Nehemiah 3:7");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "n37", 5), "1 Nephi 3:7", "2 Nephi 3:7", "3 Nephi 3:7", "Numbers 3:7", "Nehemiah 3:7");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "n2 8", 5), "1 Nephi 2:8", "2 Nephi 2:8", "3 Nephi 2:8", "Numbers 2:8", "Nehemiah 2:8");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "n2:8", 5), "1 Nephi 2:8", "2 Nephi 2:8", "3 Nephi 2:8", "Numbers 2:8", "Nehemiah 2:8");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "n2.8", 5), "1 Nephi 2:8", "2 Nephi 2:8", "3 Nephi 2:8", "Numbers 2:8", "Nehemiah 2:8");


        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "n28", 10), "1 Nephi 2:8", "2 Nephi 28", "2 Nephi 2:8", "3 Nephi 28", "3 Nephi 2:8", "Numbers 28", "Numbers 2:8", "Nehemiah 2:8", "Nahum 2:8"); // some chapters have more than 28 chapters
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "n22", 10), "1 Nephi 22", "1 Nephi 2:2", "2 Nephi 22", "2 Nephi 2:2", "3 Nephi 22", "3 Nephi 2:2", "Numbers 22", "Numbers 2:2", "Nehemiah 2:2", "Nahum 2:2"); // 1 nephi has exactly 22 chapters
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "1n28", 5), "1 Nephi 2:8");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "1n22", 5), "1 Nephi 22", "1 Nephi 2:2"); // 1 nephi has exactly 22 chapters
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "1n2 8", 5), "1 Nephi 2:8");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "1n2 2", 5), "1 Nephi 2:2");

        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "d", 5), "Doctrine and Covenants", "Deuteronomy", "Daniel", "96. Dearest Children, God Is Near You", "127. Does the Journey Seem Long?");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "d 2", 5), "Doctrine and Covenants 2", "Deuteronomy 2", "Daniel 2");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "d2", 5), "Doctrine and Covenants 2", "Deuteronomy 2", "Daniel 2");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "d111", 5), "Doctrine and Covenants 111", "Doctrine and Covenants 11:1", "Doctrine and Covenants 1:11", "Deuteronomy 11:1", "Deuteronomy 1:11");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "d1112", 6), "Doctrine and Covenants 111:2", "Doctrine and Covenants 11:12", "Doctrine and Covenants 1:112", "Deuteronomy 11:12", "Deuteronomy 1:112", "Daniel 11:12");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "d11 12", 3), "Doctrine and Covenants 11:12", "Deuteronomy 11:12", "Daniel 11:12");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "d125", 10), "Doctrine and Covenants 125", "Doctrine and Covenants 12:5", "Doctrine and Covenants 1:25", "Deuteronomy 12:5", "Deuteronomy 1:25", "Daniel 12:5", "Daniel 1:25");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "d 12 5", 5), "Doctrine and Covenants 12:5", "Deuteronomy 12:5", "Daniel 12:5"); // also additional suggestions are ignored, since the chapter and verse are defined

        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "b", 6), "Brief Explanation about the Book of Mormon", "Book of Mormon Pronunciation Guide", "44. Beautiful Zion, Built Above", "54. Behold, the Mountain of the Lord", "60. Battle Hymn of the Republic", "124. Be Still, My Soul");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "m", 10), "Mosiah", "Mormon", "Moroni", "Moses", "Matthew", "Mark", "Micah", "Malachi", "105. Master, the Tempest Is Raging", "131. More Holiness Give Me");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "mo", 7), "Mosiah", "Mormon", "Moroni", "Moses", "131. More Holiness Give Me", "Moab", "Mocking, Mock, Mocker, Mockery");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "mo213", 10), "Mosiah 21:3", "Mosiah 2:13", "Mormon 2:13", "Moroni 2:13", "Moses 2:13");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "mo105", 10), "Mosiah 10:5", "Moroni 10:5"); // make sure it is NOT 1:05
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "w", 3), "Words of Mormon", "11. What Was Witnessed in the Heavens?", "16. What Glorious Scenes Mine Eyes Behold");

        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "1 Nephi ", 10), "1 Nephi"); // handle space at the end
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, " 1 Nephi", 10), "1 Nephi"); // handle leading spaces

        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "n41", 4), "1 Nephi 4:1", "2 Nephi 4:1", "3 Nephi 4:1", "Numbers 4:1"); // make sure that we get 4 result items (4th nephi will be held out)
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "n30", 5), "2 Nephi 30", "3 Nephi 30", "Numbers 30"); // the last 0 should NOT be a verse number (1 Nephi 3:0)

        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "g0", 5)); // there is no such thing as a 0 chapter... nothing should show
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "g1 0", 5)); // there is no such thing as a 0 verse... nothing should show

        // SPECIAL
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "Hope", 5), "259. Hope of Israel", "Hope", "Hope");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "Hope1", 5)); // Hope does not have verses.... don't show result
//        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "tg Hope", 5), "Hope");
//        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "bd Hope", 5)); // there is no item "Hope" in Bible Dictionary
//        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "gs Hope", 5), "Hope");
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "Hope", 5), "259. Hope of Israel", "Hope", "Hope"); // one for tg and one for gs


        // special case for English D&C ("dc" gets changed to "d")
        assertSearchSuggestion(searchSuggestionManager.findGotoSuggestions(1, "dc125", 10), "Doctrine and Covenants 125", "Doctrine and Covenants 12:5", "Doctrine and Covenants 1:25", "Deuteronomy 12:5", "Deuteronomy 1:25", "Daniel 12:5", "Daniel 1:25");

    }

    private void assertSearchSuggestion(List<SearchSuggestion> searchSuggestions, String... expectedTitles) {
        String allTitles = "";
        for (SearchSuggestion searchSuggestion : searchSuggestions) {
            if (StringUtils.isNotBlank(allTitles)) {
                allTitles += ", ";
            }
            allTitles += searchSuggestion.getTitle();
        }
        allTitles = "[" + allTitles + "]";

        assertEquals("Suggestions and titles size the same " + allTitles, expectedTitles.length, searchSuggestions.size());

        for (int i = 0; i < expectedTitles.length; i++) {
            String expectedTitle = expectedTitles[i];
            assertEquals("Title compare failure for items (position: " + (i + 1) + "): " + allTitles, expectedTitle, searchSuggestions.get(i).getTitle());
        }
    }

    @Test
    public void findBookSuggestions() throws Exception {
        assertEquals(0, searchSuggestionManager.findContentItemSuggestions(1, "", 1).size());

        // test limit
        assertEquals(5, searchSuggestionManager.findContentItemSuggestions(1, "Tr", 5).size());

        // test variations
        assertNotEquals("True to the Faith", searchSuggestionManager.findContentItemSuggestions(1, "T", 1).get(0).getTitle()); // there are other titles that should take priority here
        assertEquals("True to the Faith", searchSuggestionManager.findContentItemSuggestions(1, "True", 1).get(0).getTitle());
        assertEquals("True to the Faith", searchSuggestionManager.findContentItemSuggestions(1, "True to", 1).get(0).getTitle());
        assertEquals("True to the Faith", searchSuggestionManager.findContentItemSuggestions(1, "True to the", 1).get(0).getTitle());
        assertEquals("True to the Faith", searchSuggestionManager.findContentItemSuggestions(1, "True to the Faith", 1).get(0).getTitle());
        assertEquals("True to the Faith", searchSuggestionManager.findContentItemSuggestions(1, "to the Faith", 1).get(0).getTitle());

        // test results for "Oct"
        // TODO Is there a way to write this test where the results don't change when catalog updates occur?
//        List<SearchSuggestion> octBookSuggestions = searchSuggestionManager.findContentItemSuggestions(1, "Oct", 10);
//        assertEquals("October 2016", octBookSuggestions.get(0).getTitle());
//        assertEquals("General Conference", octBookSuggestions.get(0).getSubTitle()); // 2016
//        assertEquals("Ensign", octBookSuggestions.get(1).getSubTitle());
//        assertEquals("New Era", octBookSuggestions.get(2).getSubTitle());
//        assertEquals("Friend", octBookSuggestions.get(3).getSubTitle());
//        assertEquals("Liahona", octBookSuggestions.get(4).getSubTitle());
//
//        assertEquals("October 2015", octBookSuggestions.get(5).getTitle());

        // make sure scripture books show up in first positions
        assertEquals("Book of Mormon", searchSuggestionManager.findContentItemSuggestions(1, "b", 1).get(0).getTitle());
        assertEquals("Old Testament", searchSuggestionManager.findContentItemSuggestions(1, "o", 1).get(0).getTitle());
        assertEquals("New Testament", searchSuggestionManager.findContentItemSuggestions(1, "n", 1).get(0).getTitle());
        assertEquals("Doctrine and Covenants", searchSuggestionManager.findContentItemSuggestions(1, "d", 1).get(0).getTitle());
        assertEquals("Pearl of Great Price", searchSuggestionManager.findContentItemSuggestions(1, "p", 1).get(0).getTitle());
    }

}
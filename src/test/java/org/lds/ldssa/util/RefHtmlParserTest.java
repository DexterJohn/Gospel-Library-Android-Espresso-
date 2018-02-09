package org.lds.ldssa.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RefHtmlParserTest {

    public static final String SCRIPTURE_SINGLE = "<p data-aid=\"7639793\" id=\"note5_p1\"><a class=\"scripture-ref\" href=\"gospellibrary://content/scriptures/bofm/3-ne/11?verse=32#p32\">3 Nephi 11:32</a>.</p>";
    public static final String SCRIPTURE_MULTIPLE = "<p data-aid=\"19062525\" id=\"note17b_p1\">\n" +
            "    <a class=\"scripture-ref\" href=\"gospellibrary://content/scriptures/bofm/1-ne/6?verse=1&amp;context=1-3#p1\">1 Ne. 6:1(1–3)</a>;\n" +
            "    <a class=\"scripture-ref\" href=\"gospellibrary://content/scriptures/bofm/1-ne/8?verse=29&amp;context=29-30#p29\">8:29 (29–30)</a>;\n" +
            "    <a class=\"scripture-ref\" href=\"gospellibrary://content/scriptures/bofm/1-ne/19?verse=1&amp;context=1-6#p1\">19:1 (1–6)</a>.\n" +
            "</p>";
    public static final String CONFERENCE = "<p data-aid=\"7640659\" id=\"note14_p1\">R. Scott Lloyd, “God Wants His Children to Return to Him, Elder Nelson Teaches,” Church News section of LDS.org, Jan. 28, 2014,\n" +
            "    <a href=\"https://www.lds.org/church/news/god-wants-his-children-to-return-to-him-elder-nelson-teaches\">lds.org/church/news/god-wants-his-children-to-return-to-him-elder-nelson-teaches</a>.\n" +
            "</p>";

    public static final String CONFERENCE_TITLE_ONLY = "<p data-aid=\"7639925\" id=\"note1_p1\">“The Family Is of God,” in <em>Families Are Forever: 2014 Outline for Sharing Time</em> (2013), 28–29. </p>";

    @Test
    public void testScriptureSingle() {
        RefHtmlParser parser = new RefHtmlParser(SCRIPTURE_SINGLE);

        assertEquals("HtmlTitle", "", parser.getHtmlTitle());

        List<RefHtmlParser.ScriptureRef> scriptureRefList = parser.getScriptureRefList();
        assertEquals("scriptureRefList size", 1, scriptureRefList.size());

        RefHtmlParser.ScriptureRef item1 = scriptureRefList.get(0);
        assertEquals("scriptureRefList item1 title", "3 Nephi 11:32", item1.getTitle());
        assertEquals("scriptureRefList item1 uri", "gospellibrary://content/scriptures/bofm/3-ne/11?verse=32#p32", item1.getUri());
    }

    @Test
    public void testScriptureMultiple() {
        RefHtmlParser parser = new RefHtmlParser(SCRIPTURE_MULTIPLE);

        List<RefHtmlParser.ScriptureRef> scriptureRefList = parser.getScriptureRefList();
        assertEquals("scriptureRefList size", 3, scriptureRefList.size());

        RefHtmlParser.ScriptureRef item1 = scriptureRefList.get(0);
        assertEquals("scriptureRefList item1 title", "1 Ne. 6:1(1–3)", item1.getTitle());
        assertEquals("scriptureRefList item1 uri", "gospellibrary://content/scriptures/bofm/1-ne/6?verse=1&context=1-3#p1", item1.getUri());

        RefHtmlParser.ScriptureRef item2 = scriptureRefList.get(1);
        assertEquals("scriptureRefList item1 title", "8:29 (29–30)", item2.getTitle());
        assertEquals("scriptureRefList item1 uri", "gospellibrary://content/scriptures/bofm/1-ne/8?verse=29&context=29-30#p29", item2.getUri());

        RefHtmlParser.ScriptureRef item3 = scriptureRefList.get(2);
        assertEquals("scriptureRefList item1 title", "19:1 (1–6)", item3.getTitle());
        assertEquals("scriptureRefList item1 uri", "gospellibrary://content/scriptures/bofm/1-ne/19?verse=1&context=1-6#p1", item3.getUri());
    }

    @Test
    public void testConference() {
        RefHtmlParser parser = new RefHtmlParser(CONFERENCE);

        assertEquals("HtmlTitle", "R.&nbsp;Scott Lloyd, “God Wants His Children to Return to Him, Elder Nelson Teaches,” Church News section of LDS.org, Jan. 28, 2014, <a href=\"https://www.lds.org/church/news/god-wants-his-children-to-return-to-him-elder-nelson-teaches\">lds.org/church/news/god-wants-his-children-to-return-to-him-elder-nelson-teaches</a>", parser.getHtmlTitle());

        List<RefHtmlParser.ScriptureRef> scriptureRefList = parser.getScriptureRefList();
        assertEquals("scriptureRefList size", 0, scriptureRefList.size());
    }

    @Test
    public void testConferenceTitleOnly() {
        RefHtmlParser parser = new RefHtmlParser(CONFERENCE_TITLE_ONLY);

        assertEquals("HtmlTitle", "“The Family Is of God,” in <em>Families Are Forever: 2014 Outline for Sharing Time</em> (2013), 28–29.", parser.getHtmlTitle());

        List<RefHtmlParser.ScriptureRef> scriptureRefList = parser.getScriptureRefList();
        assertEquals("scriptureRefList size", 0, scriptureRefList.size());
    }

}
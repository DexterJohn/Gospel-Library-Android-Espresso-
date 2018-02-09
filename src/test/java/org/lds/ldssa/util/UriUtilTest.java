package org.lds.ldssa.util;

import org.junit.Before;
import org.junit.Test;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.webservice.catalog.CatalogService;
import org.lds.ldssa.model.webservice.catalog.RoleCatalogService;
import org.lds.ldssa.model.webservice.rolecontent.RoleBasedContentService;
import org.lds.mobile.log.JavaTree;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class UriUtilTest {

    private static final String URI_1 = "gospellibrary://content/scriptures/nt/matt/19?verse=16-21#p16";
    private static final long URI_1_SUBITEM_ID = 1L;

    private static final String URI_2 = "gospellibrary://content/scriptures/pgp/abr/3?verse=25#p25";
    private static final String URI_3 = "gospellibrary://content/scriptures/bofm/1-ne/16?verse=18-25#p18";
    private static final String URI_4 = "gospellibrary://content/scriptures/dc-testament/dc/132?verse=37#p37";
    private static final String URI_5 = "gospellibrary://content/scriptures/ot/josh/24?verse=14-15#p14";
    private static final String URI_6 = "gospellibrary://content/scriptures/nt/john/14?verse=15,21-24#p15";
    private static final String URI_7 = "gospellibrary://content/scriptures/nt/acts/4?span=4:32-5:10#p32"; // cross chapters
    private static final String URI_8 = "gospellibrary://content/scriptures/bofm/moro/10?verse=32#p32";
    private static final String URI_9 = "gospellibrary://content/scriptures/nt/matt/13?verse=24-26,30#p24";
    private static final String URI_10 = "gospellibrary://content/scriptures/nt/matt/13?verse=1,3-5,10#p24";

    private static final String REF_URI_11 = "gospellibrary://content/scriptures/bofm/1-ne/3?verse=24&context=12,19-24#p24";

    private static final String BAD_URI_1 = "gospellibrary://content/scriptures/bad/matt/99?verse=24-30#p24"; // no matching content
    private static final String BAD_URI_2 = "gospellibrary://content/scriptures";
    private static final String BAD_URI_3 = "gospellibrary://content";
    private static final String BAD_URI_4 = "";
//    private static final String BAD_URI_5 = null; // non URI is not supported (@Nonnull)


    @Inject
    UriUtil uriUtil;
    @Inject
    ParagraphMetadataManager paragraphMetadataManager;

    @Before
    public void setUp() throws Exception {
        UriUtilTestComponent component = DaggerUriUtilTestComponent.builder().uriUtilTestModule(new UriUtilTest.UriUtilTestModule()).build();
        component.inject(this);

        Timber.plant(new JavaTree());

        uriUtil = spy(uriUtil);
    }

    @Test
    public void testParseUri() throws Exception {
        parseUri(URI_1);

    }

    @Test
    public void testFindContentItemIdByUri() throws Exception {
        long languageId = 1;
        assertEquals(201392132L, uriUtil.findContentItemIdByUri(languageId, new URI(URI_1)));
        assertEquals(201392132L, uriUtil.findContentItemIdByUri(languageId, URI_1));
        assertEquals(201392135L, uriUtil.findContentItemIdByUri(languageId, URI_2));

        assertEquals(-1L, uriUtil.findContentItemIdByUri(languageId, BAD_URI_1));
        assertEquals(-1L, uriUtil.findContentItemIdByUri(languageId, BAD_URI_2));
        assertEquals(-1L, uriUtil.findContentItemIdByUri(languageId, BAD_URI_3));
        assertEquals(-1L, uriUtil.findContentItemIdByUri(languageId, BAD_URI_4));
//        assertEquals(-1L, uriUtil.findContentItemIdByUri(languageId, BAD_URI_5));
    }

    @Test
    public void testGetParagraphsFromUri() throws Exception {
        checkParagraphs(findParagraphIdsFromUri(URI_1, true), "p16", "p17", "p18", "p19", "p20", "p21");
        checkParagraphs(findParagraphIdsFromUri(URI_2, true), "p25");
        checkParagraphs(findParagraphIdsFromUri(URI_3, true), "p18", "p19", "p20", "p21", "p22", "p23", "p24", "p25");
        checkParagraphs(findParagraphIdsFromUri(URI_4, true), "p37");
        checkParagraphs(findParagraphIdsFromUri(URI_5, true), "p14", "p15");
        checkParagraphs(findParagraphIdsFromUri(URI_6, true), "p15", "p21", "p22", "p23", "p24");
//        checkParagraphs(getParagraphsFromUri(URI_7), "p", "p", "p", "p", "p", "p", "p", "p");
        checkParagraphs(findParagraphIdsFromUri(URI_8, true), "p32");
        checkParagraphs(findParagraphIdsFromUri(URI_9, true), "p24", "p25", "p26", "p30");
        checkParagraphs(findParagraphIdsFromUri(URI_10, true), "p1", "p3", "p4", "p5", "p10");

        checkParagraphs(findParagraphIdsFromUri(REF_URI_11, true), "p12", "p19", "p20", "p21", "p22", "p23", "p24");

        checkParagraphs(findParagraphIdsFromUri(BAD_URI_2, true));
        checkParagraphs(findParagraphIdsFromUri(BAD_URI_3, true));
        checkParagraphs(findParagraphIdsFromUri(BAD_URI_4, true));
//        checkParagraphs(uriUtil.getParagraphsFromUri(BAD_URI_5));
    }

    private void checkParagraphs(Set<String> paragraphs, String... items) {
        assertEquals(items.length, paragraphs.size());
        for (String item : items) {
            assertTrue(paragraphs.contains(item));
        }
    }

    @Test
    public void testGetScrollToParagraphFromUri() {
        assertEquals("p16", uriUtil.getScrollToParagraphFromUri(URI_1));
        assertEquals("p25", uriUtil.getScrollToParagraphFromUri(URI_2));
        assertEquals("p18", uriUtil.getScrollToParagraphFromUri(URI_3));
        assertEquals("p37", uriUtil.getScrollToParagraphFromUri(URI_4));
        assertEquals("p14", uriUtil.getScrollToParagraphFromUri(URI_5));
        assertEquals("p15", uriUtil.getScrollToParagraphFromUri(URI_6));
        assertEquals("p32", uriUtil.getScrollToParagraphFromUri(URI_7));
        assertEquals("p32", uriUtil.getScrollToParagraphFromUri(URI_8));
        assertEquals("p24", uriUtil.getScrollToParagraphFromUri(URI_9));
        assertEquals("p24", uriUtil.getScrollToParagraphFromUri(URI_10));

        assertEquals("p24", uriUtil.getScrollToParagraphFromUri(BAD_URI_1));
        assertEquals(null, uriUtil.getScrollToParagraphFromUri(BAD_URI_2));
        assertEquals(null, uriUtil.getScrollToParagraphFromUri(BAD_URI_3));
        assertEquals(null, uriUtil.getScrollToParagraphFromUri(BAD_URI_4));
    }

    @Test
    public void testparseParagraphNumber() {
        assertEquals(uriUtil.parseParagraphNumber("p1"), 1);
        assertEquals(uriUtil.parseParagraphNumber("p10"), 10);
        assertEquals(uriUtil.parseParagraphNumber("p100"), 100);
        assertEquals(uriUtil.parseParagraphNumber("p1000"), 1000);

        assertEquals(uriUtil.parseParagraphNumber(""), -1);
        assertEquals(uriUtil.parseParagraphNumber("p"), -1);
        assertEquals(uriUtil.parseParagraphNumber("1"), -1);
        assertEquals(uriUtil.parseParagraphNumber("10"), -1);
        assertEquals(uriUtil.parseParagraphNumber("100"), -1);
    }

    @Test
    public void testFindAidsByUri() {
        doReturn(URI_1_SUBITEM_ID).when(uriUtil).findBaseSubItemId(anyLong(), any(URI.class));

        Answer<List<String>> findAllAidsAnswer = new Answer<List<String>>() {
            @Override
            public List<String> answer(InvocationOnMock invocation) throws Throwable {
                List<String> aidList = new ArrayList<>();
                Set<String> paragraphIds = invocation.getArgument(2);

                for (String paragraphId : paragraphIds) {
                    aidList.add("AID_" + paragraphId);
                }

                return aidList;
            }
        };

        doAnswer(findAllAidsAnswer).when(paragraphMetadataManager).findAllAidsBySubItemAndParagraphIds(anyLong(), anyLong(), any(Set.class));


        long languageId = 0;
        checkAids(uriUtil.findAidsByUri(languageId, URI_1, true), "AID_p16", "AID_p17", "AID_p18", "AID_p19", "AID_p20", "AID_p21");
        checkAids(uriUtil.findAidsByUri(languageId, URI_2, true), "AID_p25");
        checkAids(uriUtil.findAidsByUri(languageId, URI_3, true), "AID_p18", "AID_p19", "AID_p20", "AID_p21", "AID_p22", "AID_p23", "AID_p24", "AID_p25");
        checkAids(uriUtil.findAidsByUri(languageId, URI_4, true), "AID_p37");
        checkAids(uriUtil.findAidsByUri(languageId, URI_5, true), "AID_p14", "AID_p15");
        checkAids(uriUtil.findAidsByUri(languageId, URI_6, true), "AID_p15", "AID_p21", "AID_p22", "AID_p23", "AID_p24");
//        checkAids(uriUtil.findAidsByUri(languageId, URI_7), "p", "p", "p", "p", "p", "p", "p", "p");
        checkAids(uriUtil.findAidsByUri(languageId, URI_8, true), "AID_p32");
        checkAids(uriUtil.findAidsByUri(languageId, URI_9, true), "AID_p24", "AID_p25", "AID_p26", "AID_p30");
        checkAids(uriUtil.findAidsByUri(languageId, URI_10, true), "AID_p1", "AID_p3", "AID_p4", "AID_p5", "AID_p10");

        checkAids(uriUtil.findAidsByUri(languageId, REF_URI_11, true), "AID_p12", "AID_p19", "AID_p20", "AID_p21", "AID_p22", "AID_p23", "AID_p24");
        checkAids(uriUtil.findAidsByUri(languageId, REF_URI_11, false), "AID_p24");

        checkAids(uriUtil.findAidsByUri(languageId, BAD_URI_2, true));
        checkAids(uriUtil.findAidsByUri(languageId, BAD_URI_3, true));
        checkAids(uriUtil.findAidsByUri(languageId, BAD_URI_4, true));
    }

    private void checkAids(Collection<String> paragraphs, String... items) {
        assertEquals("Wrong number of Aids", items.length, paragraphs.size());
        for (String item : items) {
            assertTrue("Paragraphs do NOT contain: " + item, paragraphs.contains(item));
        }
    }

    @Nonnull
    public Set<String> findParagraphIdsFromUri(String uriText, boolean includeContextItems) throws URISyntaxException {
        Set<String> paragraphItems = new HashSet<>();

        URI uri = new URI(uriText);
        Map<String, String> queryPairs = uriUtil.splitQuery(uri);

        for (Map.Entry<String, String> queryPair : queryPairs.entrySet()) {
            switch (queryPair.getKey()) {
                case "context":
                    if (!includeContextItems) {
                        break;
                    }
                case "verse":
                    paragraphItems = uriUtil.parseVerseParagraphRange(queryPair.getValue());
                    break;
                case "span":
                    break;
            }
        }

        return paragraphItems;
    }

    @Nonnull
    public List<String> parseUri(String fullUri) {
        List<String> uris = new ArrayList<>();

        try {
            URI uri = new URI(fullUri);

            System.out.println("authority = " + uri.getAuthority());
            System.out.println("host = " + uri.getHost());
            System.out.println("port = " + uri.getPort());
            System.out.println("path = " + uri.getPath());
            System.out.println("query = " + uri.getQuery());
            System.out.println("fragment = " + uri.getFragment());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uris;
    }

    @Module
    public class UriUtilTestModule {
        @Provides
        @Singleton
        CatalogService provideCatalogService() {
            return mock(CatalogService.class);
        }

        @Provides
        @Singleton
        RoleCatalogService provideRoleCatalogService() {
            return mock(RoleCatalogService.class);
        }

        @Provides
        @Singleton
        RoleBasedContentService provideRoleContentService() {
            return mock(RoleBasedContentService.class);
        }

        @Provides
        @Singleton
        ParagraphMetadataManager provideParagraphMetadataManager(DatabaseManager databaseManager, ContentItemUtil contentItemUtil) {
            return spy(new ParagraphMetadataManager(databaseManager, contentItemUtil));
        }
    }
}
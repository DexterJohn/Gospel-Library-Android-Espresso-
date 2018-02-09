package org.lds.ldssa.util;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.crashlytics.android.Crashlytics;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.model.database.catalog.item.Item;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.language.Language;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadata;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadata;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.userdata.annotation.Annotation;
import org.lds.ldssa.model.database.userdata.highlight.Highlight;
import org.lds.mobile.util.LdsStringUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class UriUtil {

    public static final String LEGACY_VERSE_SEPARATOR = ".";

    private static final String BASE_LDS_SCHEME = "https";
    private static final String BASE_LDS_AUTHORITY = "www.lds.org";
    private static final String SCRIPTURES_URI_PREFIX = "/scriptures/";
    private static final String URI_QUERY_PARAM_CONTEXT = "context";
    private static final String URI_QUERY_PARAM_VERSE = "verse";
    private static final String URI_QUERY_PARAM_SPAN = "span";
    private static final String URI_QUERY_PARAM_PARA = "para";
    private static final String URI_QUERY_PARAM_LANGUAGE = "lang";
    private static final String URI_QUERY_FRAGMENT_PARAGRAPH = "p";

    private ItemManager itemManager;
    private SubItemManager subItemManager;
    private ParagraphMetadataManager paragraphMetadataManager;
    private SubItemMetadataManager subItemMetadataManager;
    private LanguageManager languageManager;
    private CitationUtil citationUtil;
    private final ContentItemUtil contentItemUtil;

    /*
     * Group1: Start Range
     * Group2: End Range
     */
    private static final Pattern REGEX_VERSE_PATTERN = Pattern.compile("(\\d+)(?:-?(\\d+))?(?:,|$)");
    private static final int REGEX_VERSE_START_GROUP = 1;
    private static final int REGEX_VERSE_END_GROUP = 2;

    /*
     * Group1: Start Range
     * Group2: End Range
     */
    private static final Pattern REGEX_PARA_PATTERN = Pattern.compile("(p\\d+)(?:-?(p\\d+))?(?:,|$)");
    private static final int REGEX_PARA_START_GROUP = 1;
    private static final int REGEX_PARA_END_GROUP = 2;

    /*
     * Group1: Start Chapter
     * Group2: Start Range
     * Group3: End Chapter Range
     * Group4: End Range
     */
    private static final Pattern REGEX_SPAN_PATTERN = Pattern.compile("(?:(\\d+):)?(\\d+)(?:-(?:(\\d+):)?(\\d+))?(?:,|$)");
    private static final int REGEX_SPAN_CHAPTER_START_GROUP = 1;
    private static final int REGEX_SPAN_START_GROUP = 2;
    private static final int REGEX_SPAN_CHAPTER_END_GROUP = 3;
    private static final int REGEX_SPAN_END_GROUP = 4;


    @Inject
    public UriUtil(ItemManager itemManager, SubItemManager subItemManager, ParagraphMetadataManager paragraphMetadataManager,
                   SubItemMetadataManager subItemMetadataManager, LanguageManager languageManager, CitationUtil citationUtil,
                   ContentItemUtil contentItemUtil) {
        this.itemManager = itemManager;
        this.subItemManager = subItemManager;
        this.paragraphMetadataManager = paragraphMetadataManager;
        this.subItemMetadataManager = subItemMetadataManager;
        this.languageManager = languageManager;
        this.citationUtil = citationUtil;
        this.contentItemUtil = contentItemUtil;
    }

    /**
     * @param intentUri uri from intent
     * @return id > 0 if the
     */
    public long findContentItemIdByUri(long languageId, @Nonnull String intentUri) {
        try {
            return findContentItemIdByUri(languageId, new URI(intentUri));
        } catch (URISyntaxException e) {
            Timber.e(e, "Failed to parse uri [%s]", intentUri);
        }

        return -1;
    }

    public long findContentItemIdByUri(long languageId, @Nonnull URI uri) {
        // get the path and search for the contentItemId by Uri
        String uriPath = uri.getPath();
        if (uriPath == null) {
            return -1;
        }

        long contentItemId;
        do {
            contentItemId = itemManager.findIdByUri(languageId, uriPath);

            if (contentItemId > 0) {
                return contentItemId;
            }

            // shorten the uriPath and search again
            uriPath = StringUtils.substringBeforeLast(uriPath, "/");
        } while (uriPath.length() > 0 && contentItemId <= 0);

        return -1;
    }

    @Nonnull
    public Set<String> findAidsByUri(long languageId, @Nonnull String uri, boolean includeContextItems) {
        try {
            return findAidsByUri(languageId, new URI(uri), includeContextItems);
        } catch (URISyntaxException e) {
            Timber.e(e, "Failed to parse uri [%s]", uri);
        }

        return new HashSet<>();
    }

    @Nonnull
    public Set<String> findAidsByUri(long languageId, @Nonnull URI uri, boolean includeContextItems) {
        return findAidsByContentItemIdAndUri(findContentItemIdByUri(languageId, uri), uri, includeContextItems);
    }

    @Nonnull
    public Set<String> findAidsByContentItemIdAndUri(long contentItemId, @Nonnull URI uri, boolean includeContextItems) {
        // find the the subItemId for the base URI path
        long baseSubItemId = findBaseSubItemId(contentItemId, uri);

        return findAidsByUri(contentItemId, baseSubItemId, uri, includeContextItems, false);
    }

    public long findBaseSubItemId(long contentItemId, @NonNull URI uri) {
        return subItemManager.findIdByUri(contentItemId, uri.getPath());
    }

    @Nonnull
    public Set<String> findAidsByUri(long contentItemId, long baseSubItemId, @Nonnull String uri, boolean includeContextItems, boolean oneSubItemOnly) {
        try {
            return findAidsByUri(contentItemId, baseSubItemId, new URI(uri), includeContextItems, oneSubItemOnly);
        } catch (URISyntaxException e) {
            Timber.e(e, "Failed to parse uri [%s]", uri);
        }

        return new HashSet<>();
    }

    @Nonnull
    public Set<String> findAidsByUri(long contentItemId, long baseSubItemId, @Nonnull URI uri, boolean includeContextItems, boolean oneSubItemOnly) {
        Set<String> aids = new HashSet<>();

        Map<String, String> queryPairs = splitQuery(uri);

        for (Map.Entry<String, String> queryPair : queryPairs.entrySet()) {
            switch (queryPair.getKey()) {
                case URI_QUERY_PARAM_CONTEXT:
                    if (!includeContextItems) {
                        break;
                    }
                case URI_QUERY_PARAM_VERSE:
                    Set<String> verseParagraphIds = parseVerseParagraphRange(queryPair.getValue());
                    if (!verseParagraphIds.isEmpty()) {
                        aids.addAll(paragraphMetadataManager.findAllAidsBySubItemAndParagraphIds(contentItemId, baseSubItemId, verseParagraphIds));
                    }
                    break;
                case URI_QUERY_PARAM_SPAN:
                    aids.addAll(findSpanAidsFromUri(contentItemId, uri.getPath(), queryPair.getValue(), oneSubItemOnly));
                    break;
                case URI_QUERY_PARAM_PARA:
                    Set<String> paraParagraphIds = parseParaParagraphRange(queryPair.getValue());
                    if (!paraParagraphIds.isEmpty()) {
                        aids.addAll(paragraphMetadataManager.findAllAidsBySubItemAndParagraphIds(contentItemId, baseSubItemId, paraParagraphIds));
                    }
                    break;
            }
        }

        if (aids.isEmpty() && uri.getPath().contains(LEGACY_VERSE_SEPARATOR)) {
            // Handle old url style
            String path = uri.getPath();
            path = path.substring(path.indexOf(LEGACY_VERSE_SEPARATOR) + 1, path.length());
            Set<String> paragraphIds = parseVerseParagraphRange(path);
            aids.addAll(paragraphMetadataManager.findAllAidsBySubItemAndParagraphIds(contentItemId, baseSubItemId, paragraphIds));
        }

        return aids;
    }

    public Map<String, String> splitQuery(@Nonnull URI uri) {
        Map<String, String> queryPairs = new LinkedHashMap<>();
        String query = uri.getQuery();

        if (query == null) {
            return queryPairs;
        }

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            try {
                queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            } catch (Exception e) {
                Crashlytics.log(1, "uri", uri.toString());
                Crashlytics.log(1, "error", e.getMessage());
                Timber.e(e, "Failed to decode uri query");
            }
        }
        return queryPairs;
    }

    @Nonnull
    public Set<String> parseParaParagraphRange(@Nonnull String range) {
        Set<String> paragraphItems = new HashSet<>();

        Matcher matcher = REGEX_PARA_PATTERN.matcher(range);

        while (matcher.find()) {
            int start = Integer.parseInt(matcher.group(REGEX_PARA_START_GROUP).replace("p", ""));
            int end = matcher.group(REGEX_PARA_END_GROUP) != null ? Integer.parseInt(matcher.group(REGEX_PARA_END_GROUP).replace("p", "")) : 0;

            // just paragraphs for this chapter
            generateAndAddParagraphIdsForRange(paragraphItems, start, end);
        }

        return paragraphItems;
    }


    @Nonnull
    public Set<String> parseVerseParagraphRange(@Nonnull String range) {
        Set<String> paragraphItems = new HashSet<>();

        Matcher matcher = REGEX_VERSE_PATTERN.matcher(range);

        while (matcher.find()) {
            int start = Integer.parseInt(matcher.group(REGEX_VERSE_START_GROUP));
            int end = matcher.group(REGEX_VERSE_END_GROUP) != null ? Integer.parseInt(matcher.group(REGEX_VERSE_END_GROUP)) : 0;

            // just paragraphs for this chapter
            generateAndAddParagraphIdsForRange(paragraphItems, start, end);
        }

        return paragraphItems;
    }

    private void generateAndAddParagraphIdsForRange(@Nonnull Set<String> paragraphItems, int start, int end) {
        if (end > start) {
            for (int i = start; i <= end; i++) {
                paragraphItems.add("p" + i);
            }
        } else {
            paragraphItems.add("p" + start);
        }
    }

    @Nonnull
    public List<String> findSpanAidsFromUri(long contentItemId, @Nonnull String path, @Nonnull String range, boolean oneSubItemOnly) {
        List<String> aids = new ArrayList<>();

        Matcher matcher = REGEX_SPAN_PATTERN.matcher(range);

        if (!matcher.find()) {
            return aids;
        }

        // start
        int startChapter = Integer.parseInt(matcher.group(REGEX_SPAN_CHAPTER_START_GROUP));
        int startParagraph = Integer.parseInt(matcher.group(REGEX_SPAN_START_GROUP));

        // end
        int endChapter;
        int endParagraph;
        if (oneSubItemOnly) {
            endChapter = startChapter;
            endParagraph = 0; // ignore
        } else {
            endChapter = Integer.parseInt(matcher.group(REGEX_SPAN_CHAPTER_END_GROUP));
            endParagraph = Integer.parseInt(matcher.group(REGEX_SPAN_END_GROUP));
        }

        // cycle through ids
        String pathWithoutChapter = StringUtils.substringBeforeLast(path, "/");

        for (int chapter = startChapter; chapter <= endChapter; chapter++) {
            String chapterUri = pathWithoutChapter + "/" + chapter;
            long chapterSubItemId = subItemManager.findIdByUri(contentItemId, chapterUri);

            if (chapter == startChapter) {
                aids.addAll(findAllParagraphAIds(contentItemId, chapterSubItemId, startParagraph, true));
            } else if (chapter == endChapter) {
                aids.addAll(findAllParagraphAIds(contentItemId, chapterSubItemId, endParagraph, false));
            } else {
                // add ALL paragraph Aids for this chapter (start and end chapters are not a part of this)
                aids.addAll(paragraphMetadataManager.findAllAidsBySubItemIdAndIsParagraph(contentItemId, chapterSubItemId));
            }
        }

        return aids;
    }

    public int parseParagraphNumber(@Nonnull String paragraphId) {
        if (paragraphId.length() <= 1) {
            return -1;
        }

        if (!paragraphId.startsWith("p")) {
            return -1;
        }

        try {
            return Integer.parseInt(paragraphId.substring(1));
        } catch (Exception e) {
            return -1;
        }
    }

    @Nonnull
    public List<String> findAllParagraphAIds(long contentItemId, long subItemId, long segParagraphNumber, boolean startSeg) {
        List<String> paragraphAIdList = new ArrayList<>();
        List<ParagraphMetadata> paragraphMetadataList = paragraphMetadataManager.findAllBySubItemIdAndIsParagraph(contentItemId, subItemId);

        // filter paragraphs
        for (ParagraphMetadata paragraphMetadata : paragraphMetadataList) {
            int paragraphNumber = parseParagraphNumber(paragraphMetadata.getParagraphId());
            if (startSeg) {
                if (paragraphNumber >= segParagraphNumber) { // start
                    paragraphAIdList.add(paragraphMetadata.getParagraphAid());
                }
            } else {
                if (paragraphNumber <= segParagraphNumber) { // end
                    paragraphAIdList.add(paragraphMetadata.getParagraphAid());
                }
            }
        }

        return paragraphAIdList;
    }

    @Nullable
    public String getScrollToParagraphFromUri(@Nonnull String intentUri) {
        try {
            return getScrollToParagraphFromUri(new URI(intentUri));
        } catch (URISyntaxException e) {
            Timber.e(e, "Failed to parse uri [%s]", intentUri);
        }

        return null;
    }

    @Nullable
    public String getScrollToParagraphFromUri(@Nonnull URI uri) {
        return uri.getFragment();
    }

    @Nonnull
    public String getScrollToParagraphAidFromUri(long contentItemId, long subItemId, @Nonnull String uri) {
        try {
            return getScrollToParagraphAidFromUri(contentItemId, subItemId, new URI(uri));
        } catch (URISyntaxException e) {
            Timber.e(e, "Failed to parse uri [%s]", uri);
            return "";
        }
    }

    @Nonnull
    public String getScrollToParagraphAidFromUri(long contentItemId, long subItemId, @Nonnull URI uri) {
        String paragraphId = getScrollToParagraphFromUri(uri);

        // If the paragraphId contains only a verse number then add the fragment prefix
        if (StringUtils.isNumeric(paragraphId)) {
            paragraphId = URI_QUERY_FRAGMENT_PARAGRAPH + paragraphId;
        }
        return paragraphMetadataManager.findAid(contentItemId, subItemId, paragraphId);
    }

    public boolean isScripturesUri(String uri) {
        return uri != null && uri.startsWith(SCRIPTURES_URI_PREFIX);
    }

    public int standardWorkChapterNum(String uri) {
        if (!isScripturesUri(uri)) {
            return 0;
        }

        int slashIndex = uri.lastIndexOf('/');
        if (slashIndex == -1 || slashIndex >= uri.length()) {
            return 0;
        }

        String doc = uri.substring(slashIndex + 1);
        if (StringUtils.isNumeric(doc)) {
            return Integer.valueOf(doc);
        }
        return 0;
    }

    public boolean isFullContentReference(@Nonnull URI uri) {
        // check for any known query params
        return !LdsStringUtil.containsAny(uri.getQuery(), URI_QUERY_PARAM_CONTEXT, URI_QUERY_PARAM_VERSE, URI_QUERY_PARAM_SPAN, URI_QUERY_PARAM_PARA);
    }

    /**
     * Creates a uri that can be used to share the specified annotation.
     *
     * @param annotation The annotation to generate a URI for
     * @return The uri that references the specified annotation
     */
    @Nonnull
    public String getSharableUri(@Nullable Annotation annotation) {
        if (annotation == null) {
            return "";
        }

        // Load the subitem metadata
        SubItemMetadata subItemMetadata = null;
        if (annotation.getDocId() != null) {
            subItemMetadata = subItemMetadataManager.findByDocId(annotation.getDocId());
        }

        if (subItemMetadata == null || !contentItemUtil.isItemDownloadedAndOpen(subItemMetadata.getItemId())) {
            return "";
        }

        // Build the base Uri
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(BASE_LDS_SCHEME);
        uriBuilder.authority(BASE_LDS_AUTHORITY);
        String path = subItemManager.findUriById(subItemMetadata.getItemId(), subItemMetadata.getSubitemId());
        uriBuilder.path(path);

        // Add the verse query and paragraph fragment
        List<String> paragraphAidList = new ArrayList<>();
        for (Highlight highlight : annotation.getHighlights()) {
            paragraphAidList.add(highlight.getParagraphAid());
        }

        List<String> verseNumbers = paragraphMetadataManager.findVerseNumbers(subItemMetadata.getItemId(), subItemMetadata.getSubitemId(), paragraphAidList);
        // remove any null values from the list
        verseNumbers.removeAll(Collections.singleton(null));

        if (!verseNumbers.isEmpty() && path.contains(SCRIPTURES_URI_PREFIX)) {
            uriBuilder.appendQueryParameter(URI_QUERY_PARAM_VERSE, citationUtil.createVerseRangeText(verseNumbers, false));
        }

        // Add the language query
        Item item = itemManager.findByRowId(subItemMetadata.getItemId());
        Language language = null;
        if (item != null) {
            language = languageManager.findByRowId(item.getLanguageId());
        }
        if (language != null) {
            uriBuilder.appendQueryParameter(URI_QUERY_PARAM_LANGUAGE, language.getIso6393());
        }

        // Add the paragraph fragment
        String paragraphId = paragraphMetadataManager.findParagraphIdByAid(subItemMetadata.getItemId(), subItemMetadata.getSubitemId(), paragraphAidList.get(0));
        uriBuilder.fragment(paragraphId);

        //TODO: when the website is updated to HTML5, determine the format for paragraph scrolling and highlighting

        // Build the full uri
        return uriBuilder.build().toString();
    }

    public long findLanguageIdFromUri(@Nonnull Uri uri) {
        String locale = uri.getQueryParameter(URI_QUERY_PARAM_LANGUAGE);
        if (locale == null) {
            locale = Locale.getDefault().getCountry();
        }
        return languageManager.findIdByLocale(locale);
    }
}

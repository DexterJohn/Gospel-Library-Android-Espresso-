package org.lds.ldssa.util;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.R;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadata;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.userdata.annotation.Annotation;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.highlight.Highlight;
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CitationUtil {
    public static final String DEFAULT_CHAPTER_VERSE_SEPARATOR = ":";
    public static final String DEFAULT_RANGE_SEPARATOR = "-";
    public static final String DEFAULT_GROUP_SEPARATOR = ", ";

    private final Application application;
    private final SubItemManager subItemManager;
    private final ParagraphMetadataManager paragraphMetadataManager;
    private final SubItemMetadataManager subItemMetadataManager;
    private final ContentItemUtil contentItemUtil;
    private final AnnotationManager annotationManager;
    private final HighlightManager highlightManager;

    @Inject
    public CitationUtil(Application application, SubItemManager subItemManager, ParagraphMetadataManager paragraphMetadataManager, SubItemMetadataManager subItemMetadataManager,
                        ContentItemUtil contentItemUtil, AnnotationManager annotationManager, HighlightManager highlightManager) {
        this.application = application;
        this.subItemManager = subItemManager;
        this.paragraphMetadataManager = paragraphMetadataManager;
        this.subItemMetadataManager = subItemMetadataManager;
        this.contentItemUtil = contentItemUtil;
        this.annotationManager = annotationManager;
        this.highlightManager = highlightManager;
    }

    @Nonnull
    public String createCitationText(String docId, @Nullable String paragraphAid) {
        SubItemMetadata subItemMetadata = subItemMetadataManager.findByDocId(docId);

        // if it is not downloaded... move on....
        if (subItemMetadata == null || !contentItemUtil.isItemDownloadedAndOpen(subItemMetadata.getItemId())) {
            return "";
        }

        long contentItemId = subItemMetadata.getItemId();
        long subItemId = subItemMetadata.getSubitemId();
        return createCitationText(contentItemId, subItemId, paragraphAid);
    }

    @Nonnull
    public String createCitationText(long annotationId) {
        String docId = annotationManager.findDocIdById(annotationId);

        SubItemMetadata subItemMetadata = null;
        if (docId != null) {
            subItemMetadata = subItemMetadataManager.findByDocId(docId);
        }

        if (subItemMetadata == null) {
            return "";
        }

        List<String> paragraphIds = highlightManager.findAllParagraphIdsByAnnotationId(annotationId);

        return createCitationText(subItemMetadata.getItemId(), subItemMetadata.getSubitemId(), paragraphIds);
    }

    @Nonnull
    public String createCitationText(long contentItemId, long annotationId) {
        if (!contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            return "";
        }

        String docId = annotationManager.findDocIdById(annotationId);
        if (docId == null) {
            return "";
        }

        long subItemId = subItemManager.findIdByDocId(contentItemId, docId);
        List<String> paragraphIds = highlightManager.findAllParagraphIdsByAnnotationId(annotationId);
        return createCitationText(contentItemId, subItemId, paragraphIds);
    }

    @Nonnull
    public String createCitationText(Annotation annotation) {
        SubItemMetadata subItemMetadata = null;
        if (annotation.getDocId() != null) {
            subItemMetadata = subItemMetadataManager.findByDocId(annotation.getDocId());
        }

        if (subItemMetadata == null) {
            return "";
        }

        List<String> paragraphAidList = new ArrayList<>();
        for (Highlight highlight : annotation.getHighlights()) {
            paragraphAidList.add(highlight.getParagraphAid());
        }
        return createCitationText(subItemMetadata.getItemId(), subItemMetadata.getSubitemId(), paragraphAidList);
    }


    @NonNull
    public String createCitationText(long contentItemId, long subItemId, @NonNull Collection<String> paragraphAids) {
        return createCitationText(contentItemId, subItemId, paragraphAids.toArray(new String[paragraphAids.size()]));
    }

    @NonNull
    public String createCitationText(long contentItemId, long subItemId, @NonNull String... paragraphAids) {
        if (!contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            return "";
        }

        String title = subItemManager.findTitleById(contentItemId, subItemId);
        if (paragraphAids.length == 0) {
            return title;
        } else {
            final List<String> verseList = paragraphMetadataManager.findVerseNumbers(contentItemId, subItemId, Arrays.asList(paragraphAids));

            // remove any null values from the list
            verseList.removeAll(Collections.singleton(null));

            if (verseList.isEmpty()) {
                return title;
            } else {
                return title + getChapterVerseSeparator() + createVerseRangeText(verseList);
            }
        }
    }

    @Nonnull
    public String createVerseRangeText(@Nonnull List<String> verseList) {
        return createVerseRangeText(verseList, true);
    }

    @Nonnull
    public String createVerseRangeText(@Nonnull List<String> verseList, boolean forDisplay) {
        switch (verseList.size()) {
            case 0:
                return "";
            case 1:
                return verseList.get(0);
            default:
                // handle in upcoming loop
        }

        StringBuilder text = new StringBuilder();
        int rangeCount = 0;
        int previousVerseNumber = -1;
        for (String verse : verseList) {
            if (!StringUtils.isNumeric(verse)) {
                // take care of any unresolved ranges
                if (rangeCount > 0) {
                    text.append(getRangeSeparator(forDisplay)).append(previousVerseNumber);
                }
                rangeCount = 0;

                if (text.length() > 0) {
                    text.append(getRangeGroupSeparator());
                }
                text.append(verse);
                continue;
            }

            int verseNumber = Integer.parseInt(verse);
            int delta = verseNumber - previousVerseNumber;
            if (delta == 1) {
                previousVerseNumber = verseNumber;
                rangeCount++;
            } else {
                if (rangeCount > 0) {
                    text.append(getRangeSeparator(forDisplay)).append(previousVerseNumber);

                    // reset
                    rangeCount = 0;
                }

                if (text.length() > 0) {
                    text.append(getRangeGroupSeparator());
                }
                text.append(verseNumber);

                previousVerseNumber = verseNumber;
            }
        }

        // take care of any unresolved ranges
        if (rangeCount > 0) {
            text.append(getRangeSeparator(forDisplay)).append(previousVerseNumber);
        }

        return text.toString();
    }

    public String getChapterVerseSeparator() {
        String separatorText = application.getString(R.string.citation_chapter_verse_separator);

        // note: unit tests may have a mocked application... which will return a null value
        return StringUtils.isNotBlank(separatorText) ?  separatorText : DEFAULT_CHAPTER_VERSE_SEPARATOR;
    }

    public String getRangeSeparator() {
        return getRangeSeparator(true);
    }

    public String getRangeSeparator(boolean forDisplay) {
        if (forDisplay) {
            String rangeText = application.getString(R.string.citation_range_separator);

            if (StringUtils.isNotBlank(rangeText)) {
                return rangeText;
            }
        }
        return DEFAULT_RANGE_SEPARATOR;
    }

    public String getRangeGroupSeparator() {
        String groupSeparatorText = application.getString(R.string.citation_range_group_separator);

        return StringUtils.isNotBlank(groupSeparatorText) ? groupSeparatorText : DEFAULT_GROUP_SEPARATOR;
    }
}

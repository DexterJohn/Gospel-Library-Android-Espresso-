package org.lds.ldssa.util;

import android.graphics.Point;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadata;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.database.content.subitemcontent.SubItemContentManager;
import org.lds.ldssa.model.database.userdata.annotation.Annotation;
import org.lds.ldssa.model.database.userdata.highlight.Highlight;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * A utility to abstract the retrieval of the content paragraphs
 * for use with the highlight utils, etc.
 */
@Singleton
public class ContentParagraphUtil {
    private final ParagraphMetadataManager paragraphMetadataManager;
    private final SubItemContentManager subItemContentManager;

    @Inject
    public ContentParagraphUtil(ParagraphMetadataManager paragraphMetadataManager, SubItemContentManager subItemContentManager) {
        this.paragraphMetadataManager = paragraphMetadataManager;
        this.subItemContentManager = subItemContentManager;
    }

    /**
     * Retrieves the paragraphs contained in the <code>paragraphAids</code>
     *
     * @param paragraphAids The list of Aids to retrieve the paragraphs for
     * @param contentItemId The id for the content item to retrieve the paragraphs for
     * @param subItemId the id for the sub item to retrieve the paragraphs for
     * @return The paragraphs contained in the <code>paragraphAids</code>
     */
    @Nullable
    public String getParagraphs(@NonNull List<String> paragraphAids, long contentItemId, long subItemId) {
        //Retrieves the range for the paragraphs to use
        Point paragraphRange = getParagraphsRange(paragraphAids, contentItemId, subItemId);
        if (paragraphRange == null) {
            Timber.d("Unable to determine paragraph range for %d : %d", contentItemId, subItemId);
            return null;
        }

        String htmlContent = subItemContentManager.findContentById(contentItemId, subItemId);

        //Fixes the offsets to be on paragraph tags
        int startIndex = findMatchingIndex("<p", paragraphRange.x, htmlContent, false, true);
        if (startIndex == paragraphRange.x) {
            startIndex = findMatchingIndex("<h", paragraphRange.x, htmlContent, false, true);
        }
        paragraphRange.x = startIndex;
        paragraphRange.y = findMatchingIndex("</p>", paragraphRange.y, htmlContent, true, true);

        try {
            return htmlContent.substring(paragraphRange.x, paragraphRange.y);
        } catch (Exception e) {
            Crashlytics.log(1, "getParagraphs", "contentItemId: [" + contentItemId + "]  subItemId [" + subItemId + "]  paragraphRange.x: [" + paragraphRange.x + "]  paragraphRange.y: [" + paragraphRange.y + "]");
            Crashlytics.log(1, "error", e.getMessage());
            Timber.e(e, "ContentParagraphUtil - getParagraphs");

            return "";
        }
    }

    /**
     * Retrieves the list of paragraph Aids associated with the highlight in the specified
     * annotation.
     *
     * @param annotation The Annotation containing the highlight
     * @return The paragraph Aids associated with the highlight
     */
    public List<String> getParagraphAids(@NonNull Annotation annotation) {
        List<String> aids = new LinkedList<>();
        if (annotation.getHighlights().isEmpty()) {
            return aids;
        }

        for (Highlight highlight : annotation.getHighlights()) {
            if (StringUtils.isNotBlank(highlight.getParagraphAid())) {
                aids.add(highlight.getParagraphAid());
            }
        }

        return aids;
    }

    /**
     * Retrieves the start and end index that contains all paragraphs contained in the <code>paragraphAids</code>
     *
     * @param paragraphAids The aIds for the paragraphs to include
     * @param contentItemId The id for the content item containing the content
     * @param subItemId The id for the sub item pointing to the specific piece of content
     * @return A Pair with x representing start and y representing end
     */
    @Nullable
    private Point getParagraphsRange(@NonNull List<String> paragraphAids, long contentItemId, long subItemId) {
        int start = Integer.MAX_VALUE;
        int end = -1;

        for (String aid: paragraphAids) {
            ParagraphMetadata paragraphMetaData = paragraphMetadataManager.findByContentItemAndSubItemAndParagraphAid(contentItemId, subItemId, aid);

            if (paragraphMetaData != null) {
                start = Math.min(start, paragraphMetaData.getStartIndex());
                end = Math.max(end, paragraphMetaData.getEndIndex());
            }
        }

        boolean invalid = start > end || start < 0;
        return invalid ? null : new Point(start, end);
    }

    /**
     * Find the index for the closest matching tag starting from the <code>startIndex</code> in the
     * specified direction.
     *
     * @param tag The tag to find
     * @param startIndex The index to start at
     * @param content The content to look through for the specified tag
     * @param includeTagLength True if the resulting index should include the length of the <code>tag</code>
     * @param walkBackwards True if we should walk backwards until we reach the correct tag
     *
     * @return The index at the start of the specified tag, or the <code>startIndex</code> if it can't be found
     */
    public int findMatchingIndex(String tag, int startIndex, String content, boolean includeTagLength, boolean walkBackwards) {
        if (startIndex < 0 || startIndex > content.length() || StringUtils.isBlank(tag) || StringUtils.isBlank(content)) {
            return content.length();
        }

        //Iterates towards the start of the string
        if (walkBackwards) {
            for (int i = startIndex; i >= 0; i--) {
                if (content.regionMatches(true, i, tag, 0, tag.length())) {
                    return i + (includeTagLength ? tag.length() : 0);
                }
            }

            return startIndex;
        }

        //Walk forwards (towards the end of the string)
        for (int i = startIndex; i < content.length(); i++) {
            if (content.regionMatches(true, i, tag, 0, tag.length())) {
                return i + (includeTagLength ? tag.length() : 0);
            }
        }

        return startIndex;
    }
}

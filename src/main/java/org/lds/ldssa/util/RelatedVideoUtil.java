package org.lds.ldssa.util;

import org.lds.ldssa.model.database.content.relatedvideoitem.RelatedVideoItem;
import org.lds.ldssa.model.database.content.relatedvideoitem.RelatedVideoItemManager;
import org.lds.ldssa.model.database.content.relatedvideoitemsource.RelatedVideoItemSource;
import org.lds.ldssa.model.database.content.relatedvideoitemsource.RelatedVideoItemSourceManager;

import java.util.List;

import javax.inject.Inject;

public class RelatedVideoUtil {

    private static final String POSTER_ATTRIBUTE = "poster";
    private static final String VIDEO_ID_ATTRIBUTE = "data-video-id";
    private static final String TITLE_ATTRIBUTE = "data-video-title";
    private static final String SRC_ATTRIBUTE = "src";
    private static final String TYPE_ATTRIBUTE = "type";
    private static final String WIDTH_ATTIBUTE = "data-width";
    private static final String HEIGHT_ATTRIBUTE = "data-height";
    private static final String FILE_SIZE_ATTRIBUTE = "data-file-size";
    private static final String END_TAG = ">";
    private static final String END_SOURCE_TAG = "</source-no-preload>";
    private static final String ATTRIBUTE_WRAP_START = "=\"";
    private static final String ATTRIBUTE_WRAP_END = "\" ";

    private RelatedVideoItemManager relatedVideoItemManager;
    private RelatedVideoItemSourceManager relatedVideoItemSourceManager;

    @Inject
    public RelatedVideoUtil(RelatedVideoItemManager relatedVideoItemManager,
                            RelatedVideoItemSourceManager relatedVideoItemSourceManager) {
        this.relatedVideoItemManager = relatedVideoItemManager;
        this.relatedVideoItemSourceManager = relatedVideoItemSourceManager;
    }

    public String generateRelatedVideoHtmlText(long contentItemId, long subItemId) {
        StringBuilder htmlText = new StringBuilder("");

        List<RelatedVideoItem> relatedVideoItemList = relatedVideoItemManager.findAllBySubitem(contentItemId, subItemId);
        if (relatedVideoItemList.isEmpty()) {
            return htmlText.toString();
        }

        // Currently there is only one related video item attached to the content
        RelatedVideoItem relatedVideoItem = relatedVideoItemList.get(0);
        List<RelatedVideoItemSource> relatedVideoItemSources = relatedVideoItemSourceManager.findAllByRelatedVideoItem(contentItemId, relatedVideoItem.getId());

        htmlText.append(ContentRenderer.VIDEO_WRAPPER_TOP_REPLACE);
        htmlText.append(POSTER_ATTRIBUTE).append(wrapAttributeValue(relatedVideoItem.getPosterUrl()));
        htmlText.append(VIDEO_ID_ATTRIBUTE).append(wrapAttributeValue(relatedVideoItem.getVideoId()));
        htmlText.append(TITLE_ATTRIBUTE).append(wrapAttributeValue(relatedVideoItem.getTitle()));
        htmlText.append(END_TAG);

        for (RelatedVideoItemSource source : relatedVideoItemSources) {
            htmlText.append(ContentRenderer.SOURCE_REPLACE);
            htmlText.append(SRC_ATTRIBUTE).append(wrapAttributeValue(source.getMediaUrl()));
            htmlText.append(TYPE_ATTRIBUTE).append(wrapAttributeValue(source.getType()));
            htmlText.append(WIDTH_ATTIBUTE).append(wrapAttributeValue(Integer.toString(source.getWidth() != null ? source.getWidth() : 0)));
            htmlText.append(HEIGHT_ATTRIBUTE).append(wrapAttributeValue(Integer.toString(source.getHeight() != null ? source.getHeight() : 0)));
            htmlText.append(FILE_SIZE_ATTRIBUTE).append(wrapAttributeValue(Long.toString(source.getFileSize() != null ? source.getFileSize() : 0)));
            htmlText.append(END_SOURCE_TAG);
        }

        htmlText.append(ContentRenderer.VIDEO_WRAPPER_BOTTOM_REPLACE);

        return htmlText.toString();
    }

    private String wrapAttributeValue(String value) {
        return ATTRIBUTE_WRAP_START + value + ATTRIBUTE_WRAP_END;
    }
}

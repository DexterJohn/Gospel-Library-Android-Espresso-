package org.lds.ldssa.event.content;

import java.util.List;

public class ContentLinkedEvent {
    private long screenId;
    private long annotationId;
    private long contentItemId;
    private long subItemId;
    private List<String> paragraphAids;

    public ContentLinkedEvent(long screenId, long annotationId, long contentItemId, long subItemId, List<String> paragraphAids) {
        this.screenId = screenId;
        this.annotationId = annotationId;
        this.contentItemId = contentItemId;
        this.subItemId = subItemId;
        this.paragraphAids = paragraphAids;
    }

    public long getScreenId() {
        return screenId;
    }

    public long getAnnotationId() {
        return annotationId;
    }

    public long getSubItemId() {
        return subItemId;
    }

    public long getContentItemId() {
        return contentItemId;
    }

    public List<String> getParagraphAids() {
        return paragraphAids;
    }
}

package org.lds.ldssa.event.download;

import org.lds.ldssa.model.database.types.ItemMediaType;

public class DownloadCompletedEvent {
    private final long androidDownloadId;
    private final long contentItemId;
    private final long subItemId;
    private final ItemMediaType type;
    private final boolean successful;

    public DownloadCompletedEvent(long contentItemId, boolean successful, long androidDownloadId) {
        this(androidDownloadId, contentItemId, -1, ItemMediaType.CONTENT, successful);
    }

    public DownloadCompletedEvent(long androidDownloadId, long contentItemId, long subItemId, ItemMediaType type, boolean successful) {
        this.androidDownloadId = androidDownloadId;
        this.contentItemId = contentItemId;
        this.subItemId = subItemId;
        this.type = type;
        this.successful = successful;
    }

    public long getAndroidDownloadId() {
        return androidDownloadId;
    }

    public long getContentItemId() {
        return contentItemId;
    }

    public long getSubItemId() {
        return subItemId;
    }

    public ItemMediaType getType() {
        return type;
    }

    public boolean isSuccessful() {
        return successful;
    }
}

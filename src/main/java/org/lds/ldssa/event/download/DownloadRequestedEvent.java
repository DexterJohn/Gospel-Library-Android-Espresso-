package org.lds.ldssa.event.download;

import org.lds.ldssa.model.database.types.ItemMediaType;

public class DownloadRequestedEvent {
    private final long androidDownloadId;
    private final long id; // id for type (content type is the contentItemId)
    private final ItemMediaType type;

    public DownloadRequestedEvent(long androidDownloadId, ItemMediaType type, long id) {
        this.androidDownloadId = androidDownloadId;
        this.id = id;
        this.type = type;
    }

    public long getAndroidDownloadId() {
        return androidDownloadId;
    }

    public long getId() {
        return id;
    }

    public ItemMediaType getType() {
        return type;
    }
}

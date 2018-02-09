package org.lds.ldssa.ui.web;

public class ContentData {
    private long contentItemId;
    private long subItemId;
    private String title;
    private String titleUri;
    private String baseUrl;
    private String content;

    public ContentData(long contentItemId, long subItemId, String baseUrl, String content) {
        this.contentItemId = contentItemId;
        this.subItemId = subItemId;
        this.baseUrl = baseUrl;
        this.content = content;
    }

    public ContentData(long contentItemId, long subItemId) {
        this.contentItemId = contentItemId;
        this.subItemId = subItemId;
        this.content = "";
    }

    public ContentData(String content) {
        this.content = content;
    }

    public long getContentItemId() {
        return contentItemId;
    }

    public void setContentItemId(long contentItemId) {
        this.contentItemId = contentItemId;
    }

    public long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(long subItemId) {
        this.subItemId = subItemId;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleUri() {
        return titleUri;
    }

    public void setTitleUri(String titleUri) {
        this.titleUri = titleUri;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getContent() {
        return content;
    }
}

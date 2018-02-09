package org.lds.ldssa.util;

import android.app.Application;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lds.ldssa.R;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager;
import org.lds.ldssa.model.database.content.subitem.SubItem;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.content.subitemcontent.SubItemContentManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.search.FtsOffsetItem;
import org.lds.ldssa.ui.web.ContentData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import timber.log.Timber;

@Singleton
public class ContentRenderer {
    private static final int MAX_EXPORT_TITLE_CHARS = 5;
    public static final String ASSETS_BASE_URL = "file:///android_asset";
    private static final String TEMPLATE_FILENAME = "contentTemplate.html";
    private static final String EXTRAHEAD_FILENAME = "extrahead.txt";
    private static final String ORIGIN_PATH = "__ORIGIN_PATH__";
    private static final String VIDEO_CONTROLS_PATTERN = "<video controls(=\"[a-zA-Z]+\")*";
    private static final String VIDEO_CONTROLS_REPLACE = "<video ";
    private static final String SOURCE_PATTERN = "<source ";
    public static final String SOURCE_REPLACE = "<source-no-preload ";

    public static final String VIDEO_WRAPPER_TOP_REPLACE;
    public static final String VIDEO_WRAPPER_BOTTOM_REPLACE;

    static {
        VIDEO_WRAPPER_TOP_REPLACE = "<div class=\"video_container\"> \n" +
                "    <video ";

        VIDEO_WRAPPER_BOTTOM_REPLACE = "</video> \n" +
                "      \n" +
                "    <div name=\"videoControls\"> \n" +
                "        <img class=\"video_button_play\" />\n" +
                "        <img class=\"video_button_download\"/>\n" +
                "    </div> \n" +
                "</div>";
    }

    private final Application application;
    private final AssetManager assetManager;
    private final Prefs prefs;
    private final ImageUtil imageUtil;
    private final SubItemManager subItemManager;
    private final SubItemContentManager subItemContentManager;
    private final GLFileUtil glFileUtil;
    private final ContentMetaDataManager contentMetaDataManager;
    private final UriUtil uriUtil;
    private final ContentItemUtil contentItemUtil;
    private final ItemManager itemManager;
    private final LdsMusicUtil ldsMusicUtil;
    private final RelatedVideoUtil relatedVideoUtil;
    private final LanguageManager languageManager;

    private int markCount = 0;

    @Inject
    public ContentRenderer(Application application, AssetManager assetManager, Prefs prefs, ImageUtil imageUtil, SubItemManager subItemManager,
                           SubItemContentManager subItemContentManager, GLFileUtil glFileUtil, ContentMetaDataManager contentMetaDataManager,
                           UriUtil uriUtil, ContentItemUtil contentItemUtil, ItemManager itemManager,
                           LdsMusicUtil ldsMusicUtil, RelatedVideoUtil relatedVideoUtil, LanguageManager languageManager) {
        this.application = application;
        this.assetManager = assetManager;
        this.prefs = prefs;
        this.imageUtil = imageUtil;
        this.subItemManager = subItemManager;
        this.subItemContentManager = subItemContentManager;
        this.glFileUtil = glFileUtil;
        this.contentMetaDataManager = contentMetaDataManager;
        this.uriUtil = uriUtil;
        this.contentItemUtil = contentItemUtil;
        this.itemManager = itemManager;
        this.ldsMusicUtil = ldsMusicUtil;
        this.relatedVideoUtil = relatedVideoUtil;
        this.languageManager = languageManager;
    }

    public Single<ContentData> generateDefaultHtmlTextRx(final long contentItemId, final long subItemId, @Nullable final String markTextSqliteOffsets, boolean markTextExactPhrase) {
        return Single.fromCallable(() -> generateDefaultHtmlText(contentItemId, subItemId, markTextSqliteOffsets, markTextExactPhrase));
    }

    public ContentData generateDefaultHtmlText(long contentItemId, long subItemId, @Nullable String markTextSqliteOffsets, boolean markTextExactPhrase) {
        SubItem subItem = subItemManager.findByRowId(contentItemId, subItemId);

        if (subItem != null) {
            String contentHtml = subItemContentManager.findContentById(contentItemId, subItemId);

            // Marks MUST be done prior to ANY other adjustments on content
            if (markTextSqliteOffsets != null && !markTextSqliteOffsets.isEmpty()) {
                // get sqlite byte offsets
                List<FtsOffsetItem> offsetItemList = SqliteUtils.INSTANCE.parseOffsetResultDataOffsetOrder(markTextSqliteOffsets, markTextExactPhrase);

                // only mark the content if there are items to mark
                if (!offsetItemList.isEmpty()) {
                    markCount = offsetItemList.size();
                    contentHtml = addMarkTextElements(contentHtml, offsetItemList);
                }
            }

            boolean createMusicLink = ldsMusicUtil.isContentMusic(contentItemId, subItemId) && ldsMusicUtil.isLdsMusicInstalled();
            String musicLinkUri = "";
            if (createMusicLink) {
                // determine language
                long languageId = itemManager.findLanguageIdById(contentItemId);
                String lang = languageManager.findLocaleById(languageId);
                musicLinkUri = ldsMusicUtil.generateExternalLinkUri(contentHtml, subItem.getUri());
                musicLinkUri += "?lang=" + lang;
            }

            return generateDefaultHtmlText(contentItemId, subItemId, contentHtml, musicLinkUri);
        } else {
            return new ContentData(contentItemId, subItemId);
        }
    }

    @NonNull
    private String addMarkTextElements(@NonNull String contentHtml, List<FtsOffsetItem> offsetItemList) {
        // create an output stream to write the content with "<mark>"
        ByteArrayOutputStream markedContentOutputStream = new ByteArrayOutputStream();

        try {
            // sqlite offsets are based on bytes... convert content to bytes and rebuild content with marks embedded at specific byte locations
            byte[] contentHtmlBytes = contentHtml.getBytes("UTF-8");

            // create index variables
            int currentBytePosition = 0;
            int totalContentBytes = contentHtmlBytes.length;

            // cycle through mark offsets
            for (FtsOffsetItem ftsOffsetItem : offsetItemList) {
                // append content prior to mark
                int lengthOfPreContent = ftsOffsetItem.getTermByteOffsetInContent() - currentBytePosition;
                markedContentOutputStream.write(contentHtmlBytes, currentBytePosition, lengthOfPreContent);

                // update the position
                currentBytePosition = ftsOffsetItem.getTermByteOffsetInContent();

                // append closing mark
                markedContentOutputStream.write("<mark>".getBytes());

                // append content within mark
                markedContentOutputStream.write(contentHtmlBytes, currentBytePosition, ftsOffsetItem.getTermSize());

                // update the position
                currentBytePosition = ftsOffsetItem.getTermByteOffsetInContent() + ftsOffsetItem.getTermSize();

                // append closing mark
                markedContentOutputStream.write("</mark>".getBytes());
            }

            // add all remaining content after the last mark
            int lengthFromLastMarkToEnd = totalContentBytes - currentBytePosition;
            markedContentOutputStream.write(contentHtmlBytes, currentBytePosition, lengthFromLastMarkToEnd);


            // create a string from the byte array
            return new String(markedContentOutputStream.toByteArray());
        } catch (Exception e) {
            Timber.e(e, "addMarkTextElements failed");

            // if something bad happened... just don't mark anything (return the original document)
            return contentHtml;
        } finally {
            // close the stream
            IOUtils.closeQuietly(markedContentOutputStream);
        }
    }

    private ContentData generateDefaultHtmlText(long contentItemId, long subItemId, String htmlContent, String musicLinkUri) {
        String dataScheme = prefs.getGeneralDisplayTheme().getHtmlScheme(); // used to help update colors defined in css and other
        String dataTextSizeCode = prefs.getContentTextSize().getSizeCode();
        boolean showFootnotes = !prefs.getContentHideFootnotes();

        return generateHtmlText(contentItemId, subItemId, htmlContent, dataScheme, dataTextSizeCode, showFootnotes, musicLinkUri);
    }

    private ContentData generateSidebarHtmlText(long contentItemId, long subItemId, String htmlContent) {
        String dataScheme = prefs.getGeneralDisplayTheme().getHtmlScheme(); // used to help update colors defined in css and other
        String dataTextSizeCode =  prefs.getContentTextSize().reduceBy(1).getSizeCode();

        ContentData contentData = generateHtmlText(contentItemId, subItemId, htmlContent, dataScheme, dataTextSizeCode, false, "");
        contentData.setContentItemId(contentItemId);
        contentData.setSubItemId(subItemId);
        return contentData;
    }

    private ContentData generateHtmlText(long contentItemId, long subItemId, String htmlContent,
                                         String dataScheme,
                                         String dataTextSizeCode,
                                         boolean showFootnotes,
                                         String musicLinkUri) {
        String templateContent;
        String extraHeadContent;

        try {
            templateContent = IOUtils.toString(assetManager.open(TEMPLATE_FILENAME), Charset.defaultCharset());
            File extraHeadContentFile = new File(glFileUtil.getContentItemDir(contentItemId), EXTRAHEAD_FILENAME);
            extraHeadContent = extraHeadContentFile.exists() ? FileUtils.readFileToString(extraHeadContentFile, Charset.defaultCharset()) : "";
        } catch (IOException e) {
            throw new IllegalStateException("Could not read template file: (" + e.getMessage() + ")", e);
        }

        String dataFootnotes = showFootnotes ? "visible" : "hidden";

        if (!StringUtils.isEmpty(htmlContent)) {
            // fix up the image src values
            htmlContent = htmlContent.replaceAll(ORIGIN_PATH, imageUtil.getBaseImageUrl());

            // we want to use our own poster and we want to disable controls
            htmlContent = htmlContent.replaceAll(VIDEO_CONTROLS_PATTERN, VIDEO_CONTROLS_REPLACE);

            // we want to wrap the <video> tags in a container to have our custom play and download buttons
            htmlContent = htmlContent.replaceAll("<video ", VIDEO_WRAPPER_TOP_REPLACE);
            htmlContent = htmlContent.replaceAll("</video>", VIDEO_WRAPPER_BOTTOM_REPLACE);

            // we want to NOT fetch video metadata on page load (it slows the page load time down significantly)
            htmlContent = htmlContent.replaceAll(SOURCE_PATTERN, SOURCE_REPLACE);
        }

        String languageId = contentMetaDataManager.findLanguageCode(contentItemId);

        String externalLinkHtmlText = StringUtils.isEmpty(musicLinkUri) ? "" : ldsMusicUtil.generateExternalLinkHtmlText(musicLinkUri);

        String relatedVideoItemHtmlText = relatedVideoUtil.generateRelatedVideoHtmlText(contentItemId, subItemId);

        String content = String.format(templateContent,
                languageId, // 1
                ASSETS_BASE_URL, // 2
                extraHeadContent, // 3
                dataScheme, // 4
                dataTextSizeCode, // 5
                dataFootnotes, // 6
                htmlContent, // 7
                externalLinkHtmlText, // 8
                relatedVideoItemHtmlText // 9
        );

        File contentItemDir = new File(glFileUtil.getContentItemDir(contentItemId), "index.html"); // index.html is fake (only used to establish base url)
        String baseUrl = Uri.fromFile(contentItemDir).toString();
        Timber.i("Webview baseUrl: [%s]", baseUrl);

        // Debug
        // exportHtml(contentItemId, "test", content); // NOSONAR - OK to have commented code here

        return new ContentData(contentItemId, subItemId, baseUrl, content);
    }

    @SuppressWarnings("unused") // used for debugging
    private void exportHtml(long contentItemId, String title, String content) {
        File tempFile = new File(application.getCacheDir(), "content-" + contentItemId + "-" + StringUtils.substring(title, 0, MAX_EXPORT_TITLE_CHARS) + ".html");
        try {
            FileUtils.writeStringToFile(tempFile, content, Charset.defaultCharset());
        } catch (IOException e) {
            Timber.e(e, "Failed to write debug content to file");
        }
    }

    public ContentData generateHtmlTextForUriOnPartialContent(long languageId, String uriText) {
        URI uri;
        try {
            uri = new URI(uriText);
        } catch (URISyntaxException e) {
            Timber.e(e, "Failed to parse uri");
            return new ContentData("");
        }

        if (uriUtil.isFullContentReference(uri)) {
            long contentItemId = uriUtil.findContentItemIdByUri(languageId, uri);

            if (contentItemId <= 0) {
                return new ContentData("");
            }

            long subItemId = 0;
            if (contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
                // Check the subitemId
                subItemId = subItemManager.findIdByUri(contentItemId, uri.getPath());
            }
            return new ContentData(contentItemId, subItemId);
        } else {
            return generateHtmlTextForUri(languageId, uri);
        }
    }

    private ContentData generateHtmlTextForUri(long languageId, URI uri) {
        Timber.d("generateHtmlTextForUri: %s", uri);
        long contentItemId = uriUtil.findContentItemIdByUri(languageId, uri);

        if (contentItemId <= 0) {
            return new ContentData("");
        }

        if (contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            // Check the subitemId
            long subItemId = subItemManager.findIdByUri(contentItemId, uri.getPath());

            if (subItemId <= 0) {
                return new ContentData(application.getString(R.string.content_no_content));
            }

            // Check the paragraph ids
            Set<String> paragraphAidSet = uriUtil.findAidsByUri(contentItemId, subItemId, uri, false, true);

            return generateHtmlTextForSubitemAndParagraphs(contentItemId, subItemId, paragraphAidSet);
        } else {
            String contentItemTitle = itemManager.findTitleById(contentItemId);
            return new ContentData(application.getString(R.string.content_no_content, contentItemTitle));
        }
    }

    public ContentData generateHtmlTextForSubitemAndParagraphs(long contentItemId, long subItemId, @NonNull Set<String> paragraphAidSet) {
        if (contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            return this.generateSidebarHtmlText(contentItemId, subItemId, generateFilteredHtmlText(contentItemId, subItemId, paragraphAidSet));
        } else {
            String contentItemTitle = itemManager.findTitleById(contentItemId);
            ContentData contentData = new ContentData(application.getString(R.string.content_no_content, contentItemTitle));
            contentData.setContentItemId(contentItemId);
            return contentData;
        }
    }

    private String generateFilteredHtmlText(long contentItemId, long subItemId, @NonNull Set<String> paragraphAidSet) {
        String contentHtml = subItemContentManager.findContentById(contentItemId, subItemId);

        Document document = Jsoup.parse(contentHtml);

        // remove the padding
        Element body = document.body();
        body.attr("style", "margin:0px;padding:0px");

        // headers and paragraphs may add extra spacing
        body.prepend("<style>p{margin: 0px;padding: 0px} header{margin: 0px;padding: 0px}</style>");

        // remove paragraph elements that are NOT in paragraphAidSet
        if (!paragraphAidSet.isEmpty()) {
            // remove page numbers
            body.getElementsByClass("page-break").remove();

            // remove all elements that are difficult to preview
            body.getElementsByTag("ol").remove();
            body.getElementsByTag("ul").remove();
            body.getElementsByTag("table").remove();
            body.getElementsByTag("figure").remove();
            body.getElementsByTag("aside").remove();
            body.getElementsByTag("img").remove();
            body.getElementsByTag("video").remove();
            body.getElementsByTag("section").remove();

            // remove any data aid element that are not included in paragraphAidSet
            Elements dataAidElements = body.select("[data-aid]");
            for (Element dataAidElement : dataAidElements) {
                if (!paragraphAidSet.contains(dataAidElement.attr("data-aid"))) {
                    dataAidElement.remove();
                }
            }

            // remove any left-over empty body-blocks
            Elements divBodyBlockElements = body.getElementsByClass("body-block");
            for (Element divBodyBlockElement : divBodyBlockElements) {
                if (divBodyBlockElement.children().size() == 0) {
                    divBodyBlockElement.remove();
                }
            }
        }

        return document.html();
    }

    public int getMarkCount() {
        return markCount;
    }
}

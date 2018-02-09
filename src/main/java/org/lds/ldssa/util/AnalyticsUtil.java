package org.lds.ldssa.util;

import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.itemcategory.ItemCategoryManager;
import org.lds.ldssa.model.database.catalog.languagename.LanguageNameManager;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadata;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.tips.tip.Tip;
import org.lds.ldssa.model.database.tips.tip.TipManager;
import org.lds.ldssa.model.database.userdata.annotation.Annotation;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;
import org.lds.ldssa.model.database.userdata.note.NoteManager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import timber.log.Timber;

public class AnalyticsUtil {

    private final Analytics analytics;
    private final ContentMetaDataManager contentMetaDataManager;
    private final ContentItemUtil contentItemUtil;
    private final ItemManager itemManager;
    private final ItemCategoryManager itemCategoryManager;
    private final LanguageNameManager languageNameManager;
    private final SubItemManager subItemManager;
    private final SubItemMetadataManager subItemMetadataManager;
    private final AnnotationManager annotationManager;
    private final ScreenUtil screenUtil;
    private final BookmarkManager bookmarkManager;
    private final NoteManager noteManager;
    private final TipManager tipManager;

    @Inject
    public AnalyticsUtil(Analytics analytics, ContentMetaDataManager contentMetaDataManager, ContentItemUtil contentItemUtil, ItemManager itemManager, ItemCategoryManager itemCategoryManager,
                         LanguageNameManager languageNameManager, SubItemManager subItemManager, SubItemMetadataManager subItemMetadataManager,
                         AnnotationManager annotationManager, ScreenUtil screenUtil, BookmarkManager bookmarkManager, NoteManager noteManager, TipManager tipManager) {
        this.analytics = analytics;
        this.contentMetaDataManager = contentMetaDataManager;
        this.contentItemUtil = contentItemUtil;
        this.itemManager = itemManager;
        this.itemCategoryManager = itemCategoryManager;
        this.languageNameManager = languageNameManager;
        this.subItemManager = subItemManager;
        this.subItemMetadataManager = subItemMetadataManager;
        this.annotationManager = annotationManager;
        this.screenUtil = screenUtil;
        this.bookmarkManager = bookmarkManager;
        this.noteManager = noteManager;
        this.tipManager = tipManager;
    }

    public String findContentGroupByContentItemId(long contentItemId) {
        return itemCategoryManager.findNameByItemId(contentItemId);
    }

    public String findContentLanguageByContentItemId(long contentItemId) {
        return findContentLanguageByLanguageId(itemManager.findLanguageIdById(contentItemId));
    }

    public String findContentLanguageByLanguageId(long languageId) {
        return languageNameManager.findLanguageName(languageId);
    }

    public String findContentVersionByContentItemId(long contentItemId) {
        return "v" + contentMetaDataManager.findSchemaVersion(contentItemId) + "." + contentMetaDataManager.findItemPackageVersion(contentItemId);
    }

    public String findItemUriByContentItemId(long contentItemId) {
        return itemManager.findUriById(contentItemId);
    }

    public String findSubItemUriBySubItemId(long contentItemId, long subItemId) {
        return subItemManager.findUriById(contentItemId, subItemId);
    }

    public String findTitleBySubItemId(long contentItemId, long subItemId) {
        return subItemManager.findTitleById(contentItemId, subItemId);
    }

    public void logContentAnnotated(Annotation annotation, Analytics.ChangeType changeType) {
        if (bookmarkManager.findCountByAnnotationId(annotation.getId()) > 0) {
            logContentAnnotated(annotation, Analytics.AnnotationType.BOOKMARK, changeType);
        } else if (noteManager.findCountByAnnotationId(annotation.getId()) > 0){
            logContentAnnotated(annotation, Analytics.AnnotationType.NOTE, changeType);
        } else {
            logContentAnnotated(annotation, Analytics.AnnotationType.MARK, changeType);
        }
    }

    public void logContentAnnotated(long annotationId, Analytics.AnnotationType annotationType, Analytics.ChangeType changeType) {
        Annotation annotation = annotationManager.findByRowId(annotationId);
        if (annotation == null) {
            return;
        }
        logContentAnnotated(annotation, annotationType, changeType);
    }

    public void logContentAnnotated(@Nonnull Annotation annotation, @Nonnull Analytics.AnnotationType annotationType, @Nonnull Analytics.ChangeType changeType) {
        String docId = annotation.getDocId();
        if (docId == null) {
            return; // The annotation is not associated with content
        }

        SubItemMetadata metadata = subItemMetadataManager.findByDocId(docId);
        if (metadata == null) {
            return; // The annotation is not associated with content
        }

        String uri = "Not Installed";
        long contentItemId = metadata.getItemId();
        if (contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            uri = subItemManager.findUriById(contentItemId, metadata.getSubitemId());
        }

        Map<String, String> attributes = new HashMap<>();
        attributes.put(Analytics.Attribute.ANNOTATION_TYPE, annotationType.getValue());
        attributes.put(Analytics.Attribute.CHANGE_TYPE, changeType.getValue());
        attributes.put(Analytics.Attribute.CONTENT_GROUP, findContentGroupByContentItemId(metadata.getItemId()));
        attributes.put(Analytics.Attribute.CONTENT_LANGUAGE, findContentLanguageByLanguageId(screenUtil.getLanguageIdForScreen(screenUtil.getLastVisibleScreenId())));
        attributes.put(Analytics.Attribute.URI, uri);
        analytics.postEvent(Analytics.Event.CONTENT_ANNOTATED, attributes);
    }

    public void logTipViewed(long tipId) {
        Tip tip = tipManager.findByRowId(tipId);
        if (tip == null) {
            return;
        }

        Map<String, String> attributes = new HashMap<>();
        attributes.put(Analytics.Attribute.TITLE, tip.getTitle());
        attributes.put(Analytics.Attribute.CONTENT_LANGUAGE, tip.getIso6393());
        attributes.put(Analytics.Attribute.CONTENT_VERSION, String.valueOf(tip.getVersionId()));
        analytics.postEvent(Analytics.Event.TIP_VIEWED, attributes);
    }

    /**
     * Outputs the analytic event to logs instead of posting it through Analytics.
     * @param eventId - the id of the event being logged
     * @param attributes the attributes of the event being logged
     */
    public void logDebugEvent(@Nonnull String eventId, @Nonnull Map<String, String> attributes) {
        StringBuilder attributeString = new StringBuilder();
        for (String key : attributes.keySet()) {
            attributeString.append("  ").append(key).append(": ").append(attributes.get(key)).append("\n");
        }

        Timber.d("Debug Analytic Event Logged: [%s] Attributes: \n%s", eventId, attributeString.toString());
    }
}

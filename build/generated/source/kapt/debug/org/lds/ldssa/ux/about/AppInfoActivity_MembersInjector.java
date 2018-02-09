package org.lds.ldssa.ux.about;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager;
import org.lds.ldssa.model.database.userdata.link.LinkManager;
import org.lds.ldssa.model.database.userdata.note.NoteManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager;
import org.lds.ldssa.model.database.userdata.tag.TagManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.DeviceUtil;
import org.lds.ldssa.util.FeedbackUtil;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.mobile.util.LdsDeviceUtil;
import org.lds.mobile.util.LdsUiUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppInfoActivity_MembersInjector implements MembersInjector<AppInfoActivity> {
  private final Provider<CatalogMetaDataManager> catalogMetaDataManagerProvider;

  private final Provider<DownloadedItemManager> downloadedItemManagerProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<NotebookManager> notebookManagerProvider;

  private final Provider<NotebookAnnotationManager> notebookAnnotationManagerProvider;

  private final Provider<Analytics> analyticsProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<DeviceUtil> deviceUtilProvider;

  private final Provider<FeedbackUtil> feedbackUtilProvider;

  private final Provider<GLFileUtil> fileUtilProvider;

  private final Provider<LdsUiUtil> uiUtilProvider;

  private final Provider<LdsDeviceUtil> ldsDeviceUtilProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<ContentMetaDataManager> contentMetaDataManagerProvider;

  private final Provider<HighlightManager> highlightManagerProvider;

  private final Provider<NoteManager> noteManagerProvider;

  private final Provider<LinkManager> linkManagerProvider;

  private final Provider<BookmarkManager> bookmarkManagerProvider;

  private final Provider<TagManager> tagManagerProvider;

  public AppInfoActivity_MembersInjector(
      Provider<CatalogMetaDataManager> catalogMetaDataManagerProvider,
      Provider<DownloadedItemManager> downloadedItemManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<NotebookManager> notebookManagerProvider,
      Provider<NotebookAnnotationManager> notebookAnnotationManagerProvider,
      Provider<Analytics> analyticsProvider,
      Provider<Prefs> prefsProvider,
      Provider<DeviceUtil> deviceUtilProvider,
      Provider<FeedbackUtil> feedbackUtilProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<LdsUiUtil> uiUtilProvider,
      Provider<LdsDeviceUtil> ldsDeviceUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<ContentMetaDataManager> contentMetaDataManagerProvider,
      Provider<HighlightManager> highlightManagerProvider,
      Provider<NoteManager> noteManagerProvider,
      Provider<LinkManager> linkManagerProvider,
      Provider<BookmarkManager> bookmarkManagerProvider,
      Provider<TagManager> tagManagerProvider) {
    this.catalogMetaDataManagerProvider = catalogMetaDataManagerProvider;
    this.downloadedItemManagerProvider = downloadedItemManagerProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.notebookManagerProvider = notebookManagerProvider;
    this.notebookAnnotationManagerProvider = notebookAnnotationManagerProvider;
    this.analyticsProvider = analyticsProvider;
    this.prefsProvider = prefsProvider;
    this.deviceUtilProvider = deviceUtilProvider;
    this.feedbackUtilProvider = feedbackUtilProvider;
    this.fileUtilProvider = fileUtilProvider;
    this.uiUtilProvider = uiUtilProvider;
    this.ldsDeviceUtilProvider = ldsDeviceUtilProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.contentMetaDataManagerProvider = contentMetaDataManagerProvider;
    this.highlightManagerProvider = highlightManagerProvider;
    this.noteManagerProvider = noteManagerProvider;
    this.linkManagerProvider = linkManagerProvider;
    this.bookmarkManagerProvider = bookmarkManagerProvider;
    this.tagManagerProvider = tagManagerProvider;
  }

  public static MembersInjector<AppInfoActivity> create(
      Provider<CatalogMetaDataManager> catalogMetaDataManagerProvider,
      Provider<DownloadedItemManager> downloadedItemManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<NotebookManager> notebookManagerProvider,
      Provider<NotebookAnnotationManager> notebookAnnotationManagerProvider,
      Provider<Analytics> analyticsProvider,
      Provider<Prefs> prefsProvider,
      Provider<DeviceUtil> deviceUtilProvider,
      Provider<FeedbackUtil> feedbackUtilProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<LdsUiUtil> uiUtilProvider,
      Provider<LdsDeviceUtil> ldsDeviceUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<ContentMetaDataManager> contentMetaDataManagerProvider,
      Provider<HighlightManager> highlightManagerProvider,
      Provider<NoteManager> noteManagerProvider,
      Provider<LinkManager> linkManagerProvider,
      Provider<BookmarkManager> bookmarkManagerProvider,
      Provider<TagManager> tagManagerProvider) {
    return new AppInfoActivity_MembersInjector(
        catalogMetaDataManagerProvider,
        downloadedItemManagerProvider,
        annotationManagerProvider,
        notebookManagerProvider,
        notebookAnnotationManagerProvider,
        analyticsProvider,
        prefsProvider,
        deviceUtilProvider,
        feedbackUtilProvider,
        fileUtilProvider,
        uiUtilProvider,
        ldsDeviceUtilProvider,
        itemManagerProvider,
        contentMetaDataManagerProvider,
        highlightManagerProvider,
        noteManagerProvider,
        linkManagerProvider,
        bookmarkManagerProvider,
        tagManagerProvider);
  }

  @Override
  public void injectMembers(AppInfoActivity instance) {
    injectCatalogMetaDataManager(instance, catalogMetaDataManagerProvider.get());
    injectDownloadedItemManager(instance, downloadedItemManagerProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectNotebookManager(instance, notebookManagerProvider.get());
    injectNotebookAnnotationManager(instance, notebookAnnotationManagerProvider.get());
    injectAnalytics(instance, analyticsProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectDeviceUtil(instance, deviceUtilProvider.get());
    injectFeedbackUtil(instance, feedbackUtilProvider.get());
    injectFileUtil(instance, fileUtilProvider.get());
    injectUiUtil(instance, uiUtilProvider.get());
    injectLdsDeviceUtil(instance, ldsDeviceUtilProvider.get());
    injectItemManager(instance, itemManagerProvider.get());
    injectContentMetaDataManager(instance, contentMetaDataManagerProvider.get());
    injectHighlightManager(instance, highlightManagerProvider.get());
    injectNoteManager(instance, noteManagerProvider.get());
    injectLinkManager(instance, linkManagerProvider.get());
    injectBookmarkManager(instance, bookmarkManagerProvider.get());
    injectTagManager(instance, tagManagerProvider.get());
  }

  public static void injectCatalogMetaDataManager(
      AppInfoActivity instance, CatalogMetaDataManager catalogMetaDataManager) {
    instance.catalogMetaDataManager = catalogMetaDataManager;
  }

  public static void injectDownloadedItemManager(
      AppInfoActivity instance, DownloadedItemManager downloadedItemManager) {
    instance.downloadedItemManager = downloadedItemManager;
  }

  public static void injectAnnotationManager(
      AppInfoActivity instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectNotebookManager(
      AppInfoActivity instance, NotebookManager notebookManager) {
    instance.notebookManager = notebookManager;
  }

  public static void injectNotebookAnnotationManager(
      AppInfoActivity instance, NotebookAnnotationManager notebookAnnotationManager) {
    instance.notebookAnnotationManager = notebookAnnotationManager;
  }

  public static void injectAnalytics(AppInfoActivity instance, Analytics analytics) {
    instance.analytics = analytics;
  }

  public static void injectPrefs(AppInfoActivity instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectDeviceUtil(AppInfoActivity instance, DeviceUtil deviceUtil) {
    instance.deviceUtil = deviceUtil;
  }

  public static void injectFeedbackUtil(AppInfoActivity instance, FeedbackUtil feedbackUtil) {
    instance.feedbackUtil = feedbackUtil;
  }

  public static void injectFileUtil(AppInfoActivity instance, GLFileUtil fileUtil) {
    instance.fileUtil = fileUtil;
  }

  public static void injectUiUtil(AppInfoActivity instance, LdsUiUtil uiUtil) {
    instance.uiUtil = uiUtil;
  }

  public static void injectLdsDeviceUtil(AppInfoActivity instance, LdsDeviceUtil ldsDeviceUtil) {
    instance.ldsDeviceUtil = ldsDeviceUtil;
  }

  public static void injectItemManager(AppInfoActivity instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }

  public static void injectContentMetaDataManager(
      AppInfoActivity instance, ContentMetaDataManager contentMetaDataManager) {
    instance.contentMetaDataManager = contentMetaDataManager;
  }

  public static void injectHighlightManager(
      AppInfoActivity instance, HighlightManager highlightManager) {
    instance.highlightManager = highlightManager;
  }

  public static void injectNoteManager(AppInfoActivity instance, NoteManager noteManager) {
    instance.noteManager = noteManager;
  }

  public static void injectLinkManager(AppInfoActivity instance, LinkManager linkManager) {
    instance.linkManager = linkManager;
  }

  public static void injectBookmarkManager(
      AppInfoActivity instance, BookmarkManager bookmarkManager) {
    instance.bookmarkManager = bookmarkManager;
  }

  public static void injectTagManager(AppInfoActivity instance, TagManager tagManager) {
    instance.tagManager = tagManager;
  }
}

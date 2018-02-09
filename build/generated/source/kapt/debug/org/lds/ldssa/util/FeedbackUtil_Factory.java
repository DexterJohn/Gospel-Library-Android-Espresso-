package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.LDSAccountPrefs;
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager;
import org.lds.ldssa.model.database.catalog.languagename.LanguageNameManager;
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
import org.lds.mobile.util.LdsFeedbackUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class FeedbackUtil_Factory implements Factory<FeedbackUtil> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<LDSAccountPrefs> ldsAccountPrefsProvider;

  private final Provider<Application> applicationProvider;

  private final Provider<LdsFeedbackUtil> feedbackUtilProvider;

  private final Provider<LanguageNameManager> languageNameManagerProvider;

  private final Provider<GLFileUtil> fileUtilProvider;

  private final Provider<CatalogMetaDataManager> catalogMetaDataManagerProvider;

  private final Provider<DownloadedItemManager> downloadedItemManagerProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<NotebookManager> notebookManagerProvider;

  private final Provider<NotebookAnnotationManager> notebookAnnotationManagerProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<DeviceUtil> deviceUtilProvider;

  private final Provider<HighlightManager> highlightManagerProvider;

  private final Provider<NoteManager> noteManagerProvider;

  private final Provider<LinkManager> linkManagerProvider;

  private final Provider<BookmarkManager> bookmarkManagerProvider;

  private final Provider<TagManager> tagManagerProvider;

  public FeedbackUtil_Factory(
      Provider<Prefs> prefsProvider,
      Provider<LDSAccountPrefs> ldsAccountPrefsProvider,
      Provider<Application> applicationProvider,
      Provider<LdsFeedbackUtil> feedbackUtilProvider,
      Provider<LanguageNameManager> languageNameManagerProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<CatalogMetaDataManager> catalogMetaDataManagerProvider,
      Provider<DownloadedItemManager> downloadedItemManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<NotebookManager> notebookManagerProvider,
      Provider<NotebookAnnotationManager> notebookAnnotationManagerProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<DeviceUtil> deviceUtilProvider,
      Provider<HighlightManager> highlightManagerProvider,
      Provider<NoteManager> noteManagerProvider,
      Provider<LinkManager> linkManagerProvider,
      Provider<BookmarkManager> bookmarkManagerProvider,
      Provider<TagManager> tagManagerProvider) {
    this.prefsProvider = prefsProvider;
    this.ldsAccountPrefsProvider = ldsAccountPrefsProvider;
    this.applicationProvider = applicationProvider;
    this.feedbackUtilProvider = feedbackUtilProvider;
    this.languageNameManagerProvider = languageNameManagerProvider;
    this.fileUtilProvider = fileUtilProvider;
    this.catalogMetaDataManagerProvider = catalogMetaDataManagerProvider;
    this.downloadedItemManagerProvider = downloadedItemManagerProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.notebookManagerProvider = notebookManagerProvider;
    this.notebookAnnotationManagerProvider = notebookAnnotationManagerProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.deviceUtilProvider = deviceUtilProvider;
    this.highlightManagerProvider = highlightManagerProvider;
    this.noteManagerProvider = noteManagerProvider;
    this.linkManagerProvider = linkManagerProvider;
    this.bookmarkManagerProvider = bookmarkManagerProvider;
    this.tagManagerProvider = tagManagerProvider;
  }

  @Override
  public FeedbackUtil get() {
    return new FeedbackUtil(
        prefsProvider.get(),
        ldsAccountPrefsProvider.get(),
        applicationProvider.get(),
        feedbackUtilProvider.get(),
        languageNameManagerProvider.get(),
        fileUtilProvider.get(),
        catalogMetaDataManagerProvider.get(),
        downloadedItemManagerProvider.get(),
        annotationManagerProvider.get(),
        notebookManagerProvider.get(),
        notebookAnnotationManagerProvider.get(),
        screenUtilProvider.get(),
        deviceUtilProvider.get(),
        highlightManagerProvider.get(),
        noteManagerProvider.get(),
        linkManagerProvider.get(),
        bookmarkManagerProvider.get(),
        tagManagerProvider.get());
  }

  public static Factory<FeedbackUtil> create(
      Provider<Prefs> prefsProvider,
      Provider<LDSAccountPrefs> ldsAccountPrefsProvider,
      Provider<Application> applicationProvider,
      Provider<LdsFeedbackUtil> feedbackUtilProvider,
      Provider<LanguageNameManager> languageNameManagerProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<CatalogMetaDataManager> catalogMetaDataManagerProvider,
      Provider<DownloadedItemManager> downloadedItemManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<NotebookManager> notebookManagerProvider,
      Provider<NotebookAnnotationManager> notebookAnnotationManagerProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<DeviceUtil> deviceUtilProvider,
      Provider<HighlightManager> highlightManagerProvider,
      Provider<NoteManager> noteManagerProvider,
      Provider<LinkManager> linkManagerProvider,
      Provider<BookmarkManager> bookmarkManagerProvider,
      Provider<TagManager> tagManagerProvider) {
    return new FeedbackUtil_Factory(
        prefsProvider,
        ldsAccountPrefsProvider,
        applicationProvider,
        feedbackUtilProvider,
        languageNameManagerProvider,
        fileUtilProvider,
        catalogMetaDataManagerProvider,
        downloadedItemManagerProvider,
        annotationManagerProvider,
        notebookManagerProvider,
        notebookAnnotationManagerProvider,
        screenUtilProvider,
        deviceUtilProvider,
        highlightManagerProvider,
        noteManagerProvider,
        linkManagerProvider,
        bookmarkManagerProvider,
        tagManagerProvider);
  }
}

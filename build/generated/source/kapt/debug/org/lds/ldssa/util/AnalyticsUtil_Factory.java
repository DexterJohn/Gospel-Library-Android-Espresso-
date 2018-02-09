package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.itemcategory.ItemCategoryManager;
import org.lds.ldssa.model.database.catalog.languagename.LanguageNameManager;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.tips.tip.TipManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;
import org.lds.ldssa.model.database.userdata.note.NoteManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnalyticsUtil_Factory implements Factory<AnalyticsUtil> {
  private final Provider<Analytics> analyticsProvider;

  private final Provider<ContentMetaDataManager> contentMetaDataManagerProvider;

  private final Provider<ContentItemUtil> contentItemUtilProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<ItemCategoryManager> itemCategoryManagerProvider;

  private final Provider<LanguageNameManager> languageNameManagerProvider;

  private final Provider<SubItemManager> subItemManagerProvider;

  private final Provider<SubItemMetadataManager> subItemMetadataManagerProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<BookmarkManager> bookmarkManagerProvider;

  private final Provider<NoteManager> noteManagerProvider;

  private final Provider<TipManager> tipManagerProvider;

  public AnalyticsUtil_Factory(
      Provider<Analytics> analyticsProvider,
      Provider<ContentMetaDataManager> contentMetaDataManagerProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<ItemCategoryManager> itemCategoryManagerProvider,
      Provider<LanguageNameManager> languageNameManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<SubItemMetadataManager> subItemMetadataManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<BookmarkManager> bookmarkManagerProvider,
      Provider<NoteManager> noteManagerProvider,
      Provider<TipManager> tipManagerProvider) {
    this.analyticsProvider = analyticsProvider;
    this.contentMetaDataManagerProvider = contentMetaDataManagerProvider;
    this.contentItemUtilProvider = contentItemUtilProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.itemCategoryManagerProvider = itemCategoryManagerProvider;
    this.languageNameManagerProvider = languageNameManagerProvider;
    this.subItemManagerProvider = subItemManagerProvider;
    this.subItemMetadataManagerProvider = subItemMetadataManagerProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.bookmarkManagerProvider = bookmarkManagerProvider;
    this.noteManagerProvider = noteManagerProvider;
    this.tipManagerProvider = tipManagerProvider;
  }

  @Override
  public AnalyticsUtil get() {
    return new AnalyticsUtil(
        analyticsProvider.get(),
        contentMetaDataManagerProvider.get(),
        contentItemUtilProvider.get(),
        itemManagerProvider.get(),
        itemCategoryManagerProvider.get(),
        languageNameManagerProvider.get(),
        subItemManagerProvider.get(),
        subItemMetadataManagerProvider.get(),
        annotationManagerProvider.get(),
        screenUtilProvider.get(),
        bookmarkManagerProvider.get(),
        noteManagerProvider.get(),
        tipManagerProvider.get());
  }

  public static Factory<AnalyticsUtil> create(
      Provider<Analytics> analyticsProvider,
      Provider<ContentMetaDataManager> contentMetaDataManagerProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<ItemCategoryManager> itemCategoryManagerProvider,
      Provider<LanguageNameManager> languageNameManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<SubItemMetadataManager> subItemMetadataManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<BookmarkManager> bookmarkManagerProvider,
      Provider<NoteManager> noteManagerProvider,
      Provider<TipManager> tipManagerProvider) {
    return new AnalyticsUtil_Factory(
        analyticsProvider,
        contentMetaDataManagerProvider,
        contentItemUtilProvider,
        itemManagerProvider,
        itemCategoryManagerProvider,
        languageNameManagerProvider,
        subItemManagerProvider,
        subItemMetadataManagerProvider,
        annotationManagerProvider,
        screenUtilProvider,
        bookmarkManagerProvider,
        noteManagerProvider,
        tipManagerProvider);
  }
}

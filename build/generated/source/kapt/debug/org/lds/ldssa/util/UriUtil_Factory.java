package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UriUtil_Factory implements Factory<UriUtil> {
  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<SubItemManager> subItemManagerProvider;

  private final Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider;

  private final Provider<SubItemMetadataManager> subItemMetadataManagerProvider;

  private final Provider<LanguageManager> languageManagerProvider;

  private final Provider<CitationUtil> citationUtilProvider;

  private final Provider<ContentItemUtil> contentItemUtilProvider;

  public UriUtil_Factory(
      Provider<ItemManager> itemManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider,
      Provider<SubItemMetadataManager> subItemMetadataManagerProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<CitationUtil> citationUtilProvider,
      Provider<ContentItemUtil> contentItemUtilProvider) {
    this.itemManagerProvider = itemManagerProvider;
    this.subItemManagerProvider = subItemManagerProvider;
    this.paragraphMetadataManagerProvider = paragraphMetadataManagerProvider;
    this.subItemMetadataManagerProvider = subItemMetadataManagerProvider;
    this.languageManagerProvider = languageManagerProvider;
    this.citationUtilProvider = citationUtilProvider;
    this.contentItemUtilProvider = contentItemUtilProvider;
  }

  @Override
  public UriUtil get() {
    return new UriUtil(
        itemManagerProvider.get(),
        subItemManagerProvider.get(),
        paragraphMetadataManagerProvider.get(),
        subItemMetadataManagerProvider.get(),
        languageManagerProvider.get(),
        citationUtilProvider.get(),
        contentItemUtilProvider.get());
  }

  public static Factory<UriUtil> create(
      Provider<ItemManager> itemManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider,
      Provider<SubItemMetadataManager> subItemMetadataManagerProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<CitationUtil> citationUtilProvider,
      Provider<ContentItemUtil> contentItemUtilProvider) {
    return new UriUtil_Factory(
        itemManagerProvider,
        subItemManagerProvider,
        paragraphMetadataManagerProvider,
        subItemMetadataManagerProvider,
        languageManagerProvider,
        citationUtilProvider,
        contentItemUtilProvider);
  }
}

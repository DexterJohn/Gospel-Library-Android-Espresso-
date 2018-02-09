package org.lds.ldssa.util;

import android.app.Application;
import android.content.res.AssetManager;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.content.subitemcontent.SubItemContentManager;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentRenderer_Factory implements Factory<ContentRenderer> {
  private final Provider<Application> applicationProvider;

  private final Provider<AssetManager> assetManagerProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<ImageUtil> imageUtilProvider;

  private final Provider<SubItemManager> subItemManagerProvider;

  private final Provider<SubItemContentManager> subItemContentManagerProvider;

  private final Provider<GLFileUtil> glFileUtilProvider;

  private final Provider<ContentMetaDataManager> contentMetaDataManagerProvider;

  private final Provider<UriUtil> uriUtilProvider;

  private final Provider<ContentItemUtil> contentItemUtilProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<LdsMusicUtil> ldsMusicUtilProvider;

  private final Provider<RelatedVideoUtil> relatedVideoUtilProvider;

  private final Provider<LanguageManager> languageManagerProvider;

  public ContentRenderer_Factory(
      Provider<Application> applicationProvider,
      Provider<AssetManager> assetManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<ImageUtil> imageUtilProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<SubItemContentManager> subItemContentManagerProvider,
      Provider<GLFileUtil> glFileUtilProvider,
      Provider<ContentMetaDataManager> contentMetaDataManagerProvider,
      Provider<UriUtil> uriUtilProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<LdsMusicUtil> ldsMusicUtilProvider,
      Provider<RelatedVideoUtil> relatedVideoUtilProvider,
      Provider<LanguageManager> languageManagerProvider) {
    this.applicationProvider = applicationProvider;
    this.assetManagerProvider = assetManagerProvider;
    this.prefsProvider = prefsProvider;
    this.imageUtilProvider = imageUtilProvider;
    this.subItemManagerProvider = subItemManagerProvider;
    this.subItemContentManagerProvider = subItemContentManagerProvider;
    this.glFileUtilProvider = glFileUtilProvider;
    this.contentMetaDataManagerProvider = contentMetaDataManagerProvider;
    this.uriUtilProvider = uriUtilProvider;
    this.contentItemUtilProvider = contentItemUtilProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.ldsMusicUtilProvider = ldsMusicUtilProvider;
    this.relatedVideoUtilProvider = relatedVideoUtilProvider;
    this.languageManagerProvider = languageManagerProvider;
  }

  @Override
  public ContentRenderer get() {
    return new ContentRenderer(
        applicationProvider.get(),
        assetManagerProvider.get(),
        prefsProvider.get(),
        imageUtilProvider.get(),
        subItemManagerProvider.get(),
        subItemContentManagerProvider.get(),
        glFileUtilProvider.get(),
        contentMetaDataManagerProvider.get(),
        uriUtilProvider.get(),
        contentItemUtilProvider.get(),
        itemManagerProvider.get(),
        ldsMusicUtilProvider.get(),
        relatedVideoUtilProvider.get(),
        languageManagerProvider.get());
  }

  public static Factory<ContentRenderer> create(
      Provider<Application> applicationProvider,
      Provider<AssetManager> assetManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<ImageUtil> imageUtilProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<SubItemContentManager> subItemContentManagerProvider,
      Provider<GLFileUtil> glFileUtilProvider,
      Provider<ContentMetaDataManager> contentMetaDataManagerProvider,
      Provider<UriUtil> uriUtilProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<LdsMusicUtil> ldsMusicUtilProvider,
      Provider<RelatedVideoUtil> relatedVideoUtilProvider,
      Provider<LanguageManager> languageManagerProvider) {
    return new ContentRenderer_Factory(
        applicationProvider,
        assetManagerProvider,
        prefsProvider,
        imageUtilProvider,
        subItemManagerProvider,
        subItemContentManagerProvider,
        glFileUtilProvider,
        contentMetaDataManagerProvider,
        uriUtilProvider,
        contentItemUtilProvider,
        itemManagerProvider,
        ldsMusicUtilProvider,
        relatedVideoUtilProvider,
        languageManagerProvider);
  }
}

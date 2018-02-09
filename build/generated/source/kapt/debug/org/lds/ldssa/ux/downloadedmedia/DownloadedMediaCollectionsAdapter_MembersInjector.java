package org.lds.ldssa.ux.downloadedmedia;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedMediaCollectionsAdapter_MembersInjector
    implements MembersInjector<DownloadedMediaCollectionsAdapter> {
  private final Provider<ItemManager> itemManagerProvider;

  public DownloadedMediaCollectionsAdapter_MembersInjector(
      Provider<ItemManager> itemManagerProvider) {
    this.itemManagerProvider = itemManagerProvider;
  }

  public static MembersInjector<DownloadedMediaCollectionsAdapter> create(
      Provider<ItemManager> itemManagerProvider) {
    return new DownloadedMediaCollectionsAdapter_MembersInjector(itemManagerProvider);
  }

  @Override
  public void injectMembers(DownloadedMediaCollectionsAdapter instance) {
    injectItemManager(instance, itemManagerProvider.get());
  }

  public static void injectItemManager(
      DownloadedMediaCollectionsAdapter instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }
}

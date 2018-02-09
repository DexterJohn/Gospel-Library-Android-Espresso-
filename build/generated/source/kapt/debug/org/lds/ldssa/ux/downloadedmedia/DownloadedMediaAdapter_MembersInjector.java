package org.lds.ldssa.ux.downloadedmedia;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedMediaAdapter_MembersInjector
    implements MembersInjector<DownloadedMediaAdapter> {
  private final Provider<ItemManager> itemManagerProvider;

  public DownloadedMediaAdapter_MembersInjector(Provider<ItemManager> itemManagerProvider) {
    this.itemManagerProvider = itemManagerProvider;
  }

  public static MembersInjector<DownloadedMediaAdapter> create(
      Provider<ItemManager> itemManagerProvider) {
    return new DownloadedMediaAdapter_MembersInjector(itemManagerProvider);
  }

  @Override
  public void injectMembers(DownloadedMediaAdapter instance) {
    injectItemManager(instance, itemManagerProvider.get());
  }

  public static void injectItemManager(DownloadedMediaAdapter instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }
}

package org.lds.ldssa.model.database.gl.downloadeditem;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedItemManager_Factory implements Factory<DownloadedItemManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<ItemManager> arg1Provider;

  public DownloadedItemManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<ItemManager> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public DownloadedItemManager get() {
    return new DownloadedItemManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<DownloadedItemManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<ItemManager> arg1Provider) {
    return new DownloadedItemManager_Factory(arg0Provider, arg1Provider);
  }
}

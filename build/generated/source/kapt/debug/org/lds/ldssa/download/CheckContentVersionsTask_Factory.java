package org.lds.ldssa.download;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.mobile.util.LdsTimeUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CheckContentVersionsTask_Factory implements Factory<CheckContentVersionsTask> {
  private final Provider<GLDownloadManager> arg0Provider;

  private final Provider<DownloadedItemManager> arg1Provider;

  private final Provider<ItemManager> arg2Provider;

  private final Provider<LdsTimeUtil> arg3Provider;

  public CheckContentVersionsTask_Factory(
      Provider<GLDownloadManager> arg0Provider,
      Provider<DownloadedItemManager> arg1Provider,
      Provider<ItemManager> arg2Provider,
      Provider<LdsTimeUtil> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public CheckContentVersionsTask get() {
    return new CheckContentVersionsTask(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<CheckContentVersionsTask> create(
      Provider<GLDownloadManager> arg0Provider,
      Provider<DownloadedItemManager> arg1Provider,
      Provider<ItemManager> arg2Provider,
      Provider<LdsTimeUtil> arg3Provider) {
    return new CheckContentVersionsTask_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}

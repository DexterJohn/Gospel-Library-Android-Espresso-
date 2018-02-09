package org.lds.ldssa.model.database.gl.downloadqueueitem;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadQueueItemManager_Factory implements Factory<DownloadQueueItemManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<CoroutineContextProvider> arg1Provider;

  public DownloadQueueItemManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<CoroutineContextProvider> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public DownloadQueueItemManager get() {
    return new DownloadQueueItemManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<DownloadQueueItemManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<CoroutineContextProvider> arg1Provider) {
    return new DownloadQueueItemManager_Factory(arg0Provider, arg1Provider);
  }
}

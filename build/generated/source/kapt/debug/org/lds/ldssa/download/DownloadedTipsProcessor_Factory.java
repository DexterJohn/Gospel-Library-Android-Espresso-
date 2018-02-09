package org.lds.ldssa.download;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedTipsProcessor_Factory implements Factory<DownloadedTipsProcessor> {
  private final Provider<DownloadQueueItemManager> arg0Provider;

  public DownloadedTipsProcessor_Factory(Provider<DownloadQueueItemManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public DownloadedTipsProcessor get() {
    return new DownloadedTipsProcessor(arg0Provider.get());
  }

  public static Factory<DownloadedTipsProcessor> create(
      Provider<DownloadQueueItemManager> arg0Provider) {
    return new DownloadedTipsProcessor_Factory(arg0Provider);
  }
}

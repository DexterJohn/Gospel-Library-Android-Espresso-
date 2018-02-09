package org.lds.ldssa.download;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.mobile.download.DownloadManagerHelper;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CancelDownloadsTask_Factory implements Factory<CancelDownloadsTask> {
  private final Provider<DownloadQueueItemManager> arg0Provider;

  private final Provider<DownloadManagerHelper> arg1Provider;

  public CancelDownloadsTask_Factory(
      Provider<DownloadQueueItemManager> arg0Provider,
      Provider<DownloadManagerHelper> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public CancelDownloadsTask get() {
    return new CancelDownloadsTask(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<CancelDownloadsTask> create(
      Provider<DownloadQueueItemManager> arg0Provider,
      Provider<DownloadManagerHelper> arg1Provider) {
    return new CancelDownloadsTask_Factory(arg0Provider, arg1Provider);
  }
}

package org.lds.ldssa.download;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.util.CatalogUpdateUtil;
import org.lds.mobile.download.DownloadManagerHelper;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CheckDownloadsTask_Factory implements Factory<CheckDownloadsTask> {
  private final Provider<DownloadQueueItemManager> arg0Provider;

  private final Provider<DownloadManagerHelper> arg1Provider;

  private final Provider<GLDownloadManager> arg2Provider;

  private final Provider<CatalogUpdateUtil> arg3Provider;

  public CheckDownloadsTask_Factory(
      Provider<DownloadQueueItemManager> arg0Provider,
      Provider<DownloadManagerHelper> arg1Provider,
      Provider<GLDownloadManager> arg2Provider,
      Provider<CatalogUpdateUtil> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public CheckDownloadsTask get() {
    return new CheckDownloadsTask(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<CheckDownloadsTask> create(
      Provider<DownloadQueueItemManager> arg0Provider,
      Provider<DownloadManagerHelper> arg1Provider,
      Provider<GLDownloadManager> arg2Provider,
      Provider<CatalogUpdateUtil> arg3Provider) {
    return new CheckDownloadsTask_Factory(arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}

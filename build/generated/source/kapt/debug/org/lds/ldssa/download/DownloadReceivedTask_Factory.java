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
public final class DownloadReceivedTask_Factory implements Factory<DownloadReceivedTask> {
  private final Provider<GLDownloadManager> arg0Provider;

  private final Provider<DownloadManagerHelper> arg1Provider;

  private final Provider<DownloadQueueItemManager> arg2Provider;

  public DownloadReceivedTask_Factory(
      Provider<GLDownloadManager> arg0Provider,
      Provider<DownloadManagerHelper> arg1Provider,
      Provider<DownloadQueueItemManager> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public DownloadReceivedTask get() {
    return new DownloadReceivedTask(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<DownloadReceivedTask> create(
      Provider<GLDownloadManager> arg0Provider,
      Provider<DownloadManagerHelper> arg1Provider,
      Provider<DownloadQueueItemManager> arg2Provider) {
    return new DownloadReceivedTask_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

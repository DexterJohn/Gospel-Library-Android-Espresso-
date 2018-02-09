package org.lds.ldssa.download;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.mobile.download.DownloadManagerHelper;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedMediaProcessor_Factory implements Factory<DownloadedMediaProcessor> {
  private final Provider<Bus> arg0Provider;

  private final Provider<Analytics> arg1Provider;

  private final Provider<DownloadQueueItemManager> arg2Provider;

  private final Provider<DownloadManagerHelper> arg3Provider;

  private final Provider<DownloadedMediaManager> arg4Provider;

  private final Provider<GLFileUtil> arg5Provider;

  public DownloadedMediaProcessor_Factory(
      Provider<Bus> arg0Provider,
      Provider<Analytics> arg1Provider,
      Provider<DownloadQueueItemManager> arg2Provider,
      Provider<DownloadManagerHelper> arg3Provider,
      Provider<DownloadedMediaManager> arg4Provider,
      Provider<GLFileUtil> arg5Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
  }

  @Override
  public DownloadedMediaProcessor get() {
    return new DownloadedMediaProcessor(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get());
  }

  public static Factory<DownloadedMediaProcessor> create(
      Provider<Bus> arg0Provider,
      Provider<Analytics> arg1Provider,
      Provider<DownloadQueueItemManager> arg2Provider,
      Provider<DownloadManagerHelper> arg3Provider,
      Provider<DownloadedMediaManager> arg4Provider,
      Provider<GLFileUtil> arg5Provider) {
    return new DownloadedMediaProcessor_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider, arg4Provider, arg5Provider);
  }
}

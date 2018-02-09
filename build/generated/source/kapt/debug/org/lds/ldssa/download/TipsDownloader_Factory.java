package org.lds.ldssa.download;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.ldssa.util.TipsUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipsDownloader_Factory implements Factory<TipsDownloader> {
  private final Provider<Application> arg0Provider;

  private final Provider<TipsUtil> arg1Provider;

  private final Provider<GLFileUtil> arg2Provider;

  private final Provider<DownloadQueueItemManager> arg3Provider;

  private final Provider<GLDownloadManager> arg4Provider;

  public TipsDownloader_Factory(
      Provider<Application> arg0Provider,
      Provider<TipsUtil> arg1Provider,
      Provider<GLFileUtil> arg2Provider,
      Provider<DownloadQueueItemManager> arg3Provider,
      Provider<GLDownloadManager> arg4Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
  }

  @Override
  public TipsDownloader get() {
    return new TipsDownloader(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get());
  }

  public static Factory<TipsDownloader> create(
      Provider<Application> arg0Provider,
      Provider<TipsUtil> arg1Provider,
      Provider<GLFileUtil> arg2Provider,
      Provider<DownloadQueueItemManager> arg3Provider,
      Provider<GLDownloadManager> arg4Provider) {
    return new TipsDownloader_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider, arg4Provider);
  }
}

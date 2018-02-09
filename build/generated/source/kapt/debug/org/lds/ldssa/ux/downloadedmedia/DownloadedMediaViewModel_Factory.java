package org.lds.ldssa.ux.downloadedmedia;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.database.gl.downloadedmediacollection.DownloadedMediaCollectionManager;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedMediaViewModel_Factory implements Factory<DownloadedMediaViewModel> {
  private final Provider<DownloadedMediaCollectionManager> arg0Provider;

  private final Provider<DownloadedMediaManager> arg1Provider;

  private final Provider<CoroutineContextProvider> arg2Provider;

  public DownloadedMediaViewModel_Factory(
      Provider<DownloadedMediaCollectionManager> arg0Provider,
      Provider<DownloadedMediaManager> arg1Provider,
      Provider<CoroutineContextProvider> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public DownloadedMediaViewModel get() {
    return new DownloadedMediaViewModel(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<DownloadedMediaViewModel> create(
      Provider<DownloadedMediaCollectionManager> arg0Provider,
      Provider<DownloadedMediaManager> arg1Provider,
      Provider<CoroutineContextProvider> arg2Provider) {
    return new DownloadedMediaViewModel_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

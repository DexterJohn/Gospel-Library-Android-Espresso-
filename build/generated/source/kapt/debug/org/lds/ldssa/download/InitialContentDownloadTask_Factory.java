package org.lds.ldssa.download;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.mobile.util.LdsNetworkUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class InitialContentDownloadTask_Factory
    implements Factory<InitialContentDownloadTask> {
  private final Provider<Application> arg0Provider;

  private final Provider<ItemManager> arg1Provider;

  private final Provider<GLDownloadManager> arg2Provider;

  private final Provider<LdsNetworkUtil> arg3Provider;

  public InitialContentDownloadTask_Factory(
      Provider<Application> arg0Provider,
      Provider<ItemManager> arg1Provider,
      Provider<GLDownloadManager> arg2Provider,
      Provider<LdsNetworkUtil> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public InitialContentDownloadTask get() {
    return new InitialContentDownloadTask(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<InitialContentDownloadTask> create(
      Provider<Application> arg0Provider,
      Provider<ItemManager> arg1Provider,
      Provider<GLDownloadManager> arg2Provider,
      Provider<LdsNetworkUtil> arg3Provider) {
    return new InitialContentDownloadTask_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}

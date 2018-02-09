package org.lds.ldssa.download;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.CatalogUpdateUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedCatalogProcessor_Factory
    implements Factory<DownloadedCatalogProcessor> {
  private final Provider<InternalIntents> arg0Provider;

  private final Provider<CatalogUpdateUtil> arg1Provider;

  private final Provider<DownloadQueueItemManager> arg2Provider;

  private final Provider<Prefs> arg3Provider;

  public DownloadedCatalogProcessor_Factory(
      Provider<InternalIntents> arg0Provider,
      Provider<CatalogUpdateUtil> arg1Provider,
      Provider<DownloadQueueItemManager> arg2Provider,
      Provider<Prefs> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public DownloadedCatalogProcessor get() {
    return new DownloadedCatalogProcessor(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<DownloadedCatalogProcessor> create(
      Provider<InternalIntents> arg0Provider,
      Provider<CatalogUpdateUtil> arg1Provider,
      Provider<DownloadQueueItemManager> arg2Provider,
      Provider<Prefs> arg3Provider) {
    return new DownloadedCatalogProcessor_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}

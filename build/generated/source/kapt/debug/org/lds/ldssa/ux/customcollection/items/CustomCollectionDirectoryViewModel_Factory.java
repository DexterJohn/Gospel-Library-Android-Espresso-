package org.lds.ldssa.ux.customcollection.items;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQueryManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemManager;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CustomCollectionDirectoryViewModel_Factory
    implements Factory<CustomCollectionDirectoryViewModel> {
  private final Provider<CatalogItemQueryManager> arg0Provider;

  private final Provider<CustomCollectionItemManager> arg1Provider;

  private final Provider<DownloadedItemManager> arg2Provider;

  private final Provider<CoroutineContextProvider> arg3Provider;

  public CustomCollectionDirectoryViewModel_Factory(
      Provider<CatalogItemQueryManager> arg0Provider,
      Provider<CustomCollectionItemManager> arg1Provider,
      Provider<DownloadedItemManager> arg2Provider,
      Provider<CoroutineContextProvider> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public CustomCollectionDirectoryViewModel get() {
    return new CustomCollectionDirectoryViewModel(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<CustomCollectionDirectoryViewModel> create(
      Provider<CatalogItemQueryManager> arg0Provider,
      Provider<CustomCollectionItemManager> arg1Provider,
      Provider<DownloadedItemManager> arg2Provider,
      Provider<CoroutineContextProvider> arg3Provider) {
    return new CustomCollectionDirectoryViewModel_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}

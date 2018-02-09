package org.lds.ldssa.ux.customcollection.collections;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQueryManager;
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionManager;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CustomCollectionsViewModel_Factory
    implements Factory<CustomCollectionsViewModel> {
  private final Provider<CatalogItemQueryManager> arg0Provider;

  private final Provider<CustomCollectionManager> arg1Provider;

  private final Provider<CoroutineContextProvider> arg2Provider;

  public CustomCollectionsViewModel_Factory(
      Provider<CatalogItemQueryManager> arg0Provider,
      Provider<CustomCollectionManager> arg1Provider,
      Provider<CoroutineContextProvider> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public CustomCollectionsViewModel get() {
    return new CustomCollectionsViewModel(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<CustomCollectionsViewModel> create(
      Provider<CatalogItemQueryManager> arg0Provider,
      Provider<CustomCollectionManager> arg1Provider,
      Provider<CoroutineContextProvider> arg2Provider) {
    return new CustomCollectionsViewModel_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

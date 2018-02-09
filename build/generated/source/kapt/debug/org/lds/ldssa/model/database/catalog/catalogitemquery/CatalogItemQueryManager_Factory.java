package org.lds.ldssa.model.database.catalog.catalogitemquery;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CatalogItemQueryManager_Factory implements Factory<CatalogItemQueryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<ItemManager> arg1Provider;

  private final Provider<UserdataDbUtil> arg2Provider;

  public CatalogItemQueryManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<ItemManager> arg1Provider,
      Provider<UserdataDbUtil> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public CatalogItemQueryManager get() {
    return new CatalogItemQueryManager(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<CatalogItemQueryManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<ItemManager> arg1Provider,
      Provider<UserdataDbUtil> arg2Provider) {
    return new CatalogItemQueryManager_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

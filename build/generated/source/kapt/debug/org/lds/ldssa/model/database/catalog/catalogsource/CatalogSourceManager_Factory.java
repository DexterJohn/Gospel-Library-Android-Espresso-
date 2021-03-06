package org.lds.ldssa.model.database.catalog.catalogsource;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CatalogSourceManager_Factory implements Factory<CatalogSourceManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public CatalogSourceManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public CatalogSourceManager get() {
    return new CatalogSourceManager(arg0Provider.get());
  }

  public static Factory<CatalogSourceManager> create(Provider<DatabaseManager> arg0Provider) {
    return new CatalogSourceManager_Factory(arg0Provider);
  }
}

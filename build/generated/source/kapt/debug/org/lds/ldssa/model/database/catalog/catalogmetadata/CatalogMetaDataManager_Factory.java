package org.lds.ldssa.model.database.catalog.catalogmetadata;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CatalogMetaDataManager_Factory implements Factory<CatalogMetaDataManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public CatalogMetaDataManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public CatalogMetaDataManager get() {
    return new CatalogMetaDataManager(arg0Provider.get());
  }

  public static Factory<CatalogMetaDataManager> create(Provider<DatabaseManager> arg0Provider) {
    return new CatalogMetaDataManager_Factory(arg0Provider);
  }
}

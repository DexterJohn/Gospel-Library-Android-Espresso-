package org.lds.ldssa.model.database.catalog.allitemsincollectionquery;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AllItemsInCollectionQueryManager_Factory
    implements Factory<AllItemsInCollectionQueryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public AllItemsInCollectionQueryManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public AllItemsInCollectionQueryManager get() {
    return new AllItemsInCollectionQueryManager(arg0Provider.get());
  }

  public static Factory<AllItemsInCollectionQueryManager> create(
      Provider<DatabaseManager> arg0Provider) {
    return new AllItemsInCollectionQueryManager_Factory(arg0Provider);
  }
}

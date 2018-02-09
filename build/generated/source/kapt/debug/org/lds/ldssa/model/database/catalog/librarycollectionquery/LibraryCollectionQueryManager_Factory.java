package org.lds.ldssa.model.database.catalog.librarycollectionquery;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LibraryCollectionQueryManager_Factory
    implements Factory<LibraryCollectionQueryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public LibraryCollectionQueryManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public LibraryCollectionQueryManager get() {
    return new LibraryCollectionQueryManager(arg0Provider.get());
  }

  public static Factory<LibraryCollectionQueryManager> create(
      Provider<DatabaseManager> arg0Provider) {
    return new LibraryCollectionQueryManager_Factory(arg0Provider);
  }
}

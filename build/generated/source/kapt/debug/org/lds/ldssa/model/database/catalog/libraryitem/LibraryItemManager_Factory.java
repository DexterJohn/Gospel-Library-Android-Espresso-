package org.lds.ldssa.model.database.catalog.libraryitem;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LibraryItemManager_Factory implements Factory<LibraryItemManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public LibraryItemManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public LibraryItemManager get() {
    return new LibraryItemManager(arg0Provider.get());
  }

  public static Factory<LibraryItemManager> create(Provider<DatabaseManager> arg0Provider) {
    return new LibraryItemManager_Factory(arg0Provider);
  }
}

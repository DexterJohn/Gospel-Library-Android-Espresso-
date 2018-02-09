package org.lds.ldssa.model.database.catalog.librarycollection;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LibraryCollectionManager_Factory implements Factory<LibraryCollectionManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<LanguageManager> arg1Provider;

  public LibraryCollectionManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<LanguageManager> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public LibraryCollectionManager get() {
    return new LibraryCollectionManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<LibraryCollectionManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<LanguageManager> arg1Provider) {
    return new LibraryCollectionManager_Factory(arg0Provider, arg1Provider);
  }
}

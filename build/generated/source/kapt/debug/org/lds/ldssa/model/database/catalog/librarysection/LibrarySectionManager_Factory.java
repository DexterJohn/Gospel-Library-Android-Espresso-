package org.lds.ldssa.model.database.catalog.librarysection;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LibrarySectionManager_Factory implements Factory<LibrarySectionManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public LibrarySectionManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public LibrarySectionManager get() {
    return new LibrarySectionManager(arg0Provider.get());
  }

  public static Factory<LibrarySectionManager> create(Provider<DatabaseManager> arg0Provider) {
    return new LibrarySectionManager_Factory(arg0Provider);
  }
}

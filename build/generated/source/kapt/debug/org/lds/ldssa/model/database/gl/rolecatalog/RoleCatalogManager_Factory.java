package org.lds.ldssa.model.database.gl.rolecatalog;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RoleCatalogManager_Factory implements Factory<RoleCatalogManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public RoleCatalogManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public RoleCatalogManager get() {
    return new RoleCatalogManager(arg0Provider.get());
  }

  public static Factory<RoleCatalogManager> create(Provider<DatabaseManager> arg0Provider) {
    return new RoleCatalogManager_Factory(arg0Provider);
  }
}

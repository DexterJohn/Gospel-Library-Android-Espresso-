package org.lds.ldssa.model.database.content.role;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RoleManager_Factory implements Factory<RoleManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public RoleManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public RoleManager get() {
    return new RoleManager(arg0Provider.get());
  }

  public static Factory<RoleManager> create(Provider<DatabaseManager> arg0Provider) {
    return new RoleManager_Factory(arg0Provider);
  }
}

package org.lds.ldssa.model.database;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.dbtools.android.domain.config.DatabaseConfig;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DatabaseManager_Factory implements Factory<DatabaseManager> {
  private final Provider<DatabaseConfig> arg0Provider;

  public DatabaseManager_Factory(Provider<DatabaseConfig> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public DatabaseManager get() {
    return new DatabaseManager(arg0Provider.get());
  }

  public static Factory<DatabaseManager> create(Provider<DatabaseConfig> arg0Provider) {
    return new DatabaseManager_Factory(arg0Provider);
  }
}

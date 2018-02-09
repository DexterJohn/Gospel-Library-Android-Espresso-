package org.lds.ldssa.model.database.catalog.stopword;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StopWordManager_Factory implements Factory<StopWordManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public StopWordManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public StopWordManager get() {
    return new StopWordManager(arg0Provider.get());
  }

  public static Factory<StopWordManager> create(Provider<DatabaseManager> arg0Provider) {
    return new StopWordManager_Factory(arg0Provider);
  }
}

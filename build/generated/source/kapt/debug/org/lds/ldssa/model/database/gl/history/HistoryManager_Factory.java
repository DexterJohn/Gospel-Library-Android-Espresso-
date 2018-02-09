package org.lds.ldssa.model.database.gl.history;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HistoryManager_Factory implements Factory<HistoryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public HistoryManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public HistoryManager get() {
    return new HistoryManager(arg0Provider.get());
  }

  public static Factory<HistoryManager> create(Provider<DatabaseManager> arg0Provider) {
    return new HistoryManager_Factory(arg0Provider);
  }
}

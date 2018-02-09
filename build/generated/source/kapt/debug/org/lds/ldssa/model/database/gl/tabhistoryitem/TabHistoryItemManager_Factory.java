package org.lds.ldssa.model.database.gl.tabhistoryitem;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TabHistoryItemManager_Factory implements Factory<TabHistoryItemManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public TabHistoryItemManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public TabHistoryItemManager get() {
    return new TabHistoryItemManager(arg0Provider.get());
  }

  public static Factory<TabHistoryItemManager> create(Provider<DatabaseManager> arg0Provider) {
    return new TabHistoryItemManager_Factory(arg0Provider);
  }
}

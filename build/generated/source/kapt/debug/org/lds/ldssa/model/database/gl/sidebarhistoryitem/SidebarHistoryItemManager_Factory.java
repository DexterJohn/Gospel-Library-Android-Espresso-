package org.lds.ldssa.model.database.gl.sidebarhistoryitem;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SidebarHistoryItemManager_Factory implements Factory<SidebarHistoryItemManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SidebarHistoryItemManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SidebarHistoryItemManager get() {
    return new SidebarHistoryItemManager(arg0Provider.get());
  }

  public static Factory<SidebarHistoryItemManager> create(Provider<DatabaseManager> arg0Provider) {
    return new SidebarHistoryItemManager_Factory(arg0Provider);
  }
}

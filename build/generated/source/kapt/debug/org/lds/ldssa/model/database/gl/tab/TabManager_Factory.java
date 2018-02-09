package org.lds.ldssa.model.database.gl.tab;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.gl.tabhistoryitem.TabHistoryItemManager;
import org.lds.ldssa.util.GLFileUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TabManager_Factory implements Factory<TabManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<GLFileUtil> arg1Provider;

  private final Provider<TabHistoryItemManager> arg2Provider;

  public TabManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<GLFileUtil> arg1Provider,
      Provider<TabHistoryItemManager> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public TabManager get() {
    return new TabManager(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<TabManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<GLFileUtil> arg1Provider,
      Provider<TabHistoryItemManager> arg2Provider) {
    return new TabManager_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

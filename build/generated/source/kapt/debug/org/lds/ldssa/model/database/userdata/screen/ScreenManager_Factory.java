package org.lds.ldssa.model.database.userdata.screen;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemManager;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ScreenManager_Factory implements Factory<ScreenManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<GLFileUtil> arg1Provider;

  private final Provider<ScreenHistoryItemManager> arg2Provider;

  private final Provider<UserdataDbUtil> arg3Provider;

  public ScreenManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<GLFileUtil> arg1Provider,
      Provider<ScreenHistoryItemManager> arg2Provider,
      Provider<UserdataDbUtil> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public ScreenManager get() {
    return new ScreenManager(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<ScreenManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<GLFileUtil> arg1Provider,
      Provider<ScreenHistoryItemManager> arg2Provider,
      Provider<UserdataDbUtil> arg3Provider) {
    return new ScreenManager_Factory(arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}

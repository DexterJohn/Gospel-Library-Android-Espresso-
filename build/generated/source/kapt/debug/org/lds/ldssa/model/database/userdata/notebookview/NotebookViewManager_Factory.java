package org.lds.ldssa.model.database.userdata.notebookview;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NotebookViewManager_Factory implements Factory<NotebookViewManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<UserdataDbUtil> arg1Provider;

  public NotebookViewManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public NotebookViewManager get() {
    return new NotebookViewManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<NotebookViewManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    return new NotebookViewManager_Factory(arg0Provider, arg1Provider);
  }
}

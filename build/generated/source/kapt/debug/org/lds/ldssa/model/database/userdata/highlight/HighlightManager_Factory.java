package org.lds.ldssa.model.database.userdata.highlight;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HighlightManager_Factory implements Factory<HighlightManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<UserdataDbUtil> arg1Provider;

  public HighlightManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public HighlightManager get() {
    return new HighlightManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<HighlightManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    return new HighlightManager_Factory(arg0Provider, arg1Provider);
  }
}

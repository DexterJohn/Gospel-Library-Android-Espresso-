package org.lds.ldssa.model.database.gl.tipviewed;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipViewedManager_Factory implements Factory<TipViewedManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public TipViewedManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public TipViewedManager get() {
    return new TipViewedManager(arg0Provider.get());
  }

  public static Factory<TipViewedManager> create(Provider<DatabaseManager> arg0Provider) {
    return new TipViewedManager_Factory(arg0Provider);
  }
}

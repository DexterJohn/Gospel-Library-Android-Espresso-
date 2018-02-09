package org.lds.ldssa.model.database.tips.tipsappversion;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipsAppVersionManager_Factory implements Factory<TipsAppVersionManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public TipsAppVersionManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public TipsAppVersionManager get() {
    return new TipsAppVersionManager(arg0Provider.get());
  }

  public static Factory<TipsAppVersionManager> create(Provider<DatabaseManager> arg0Provider) {
    return new TipsAppVersionManager_Factory(arg0Provider);
  }
}

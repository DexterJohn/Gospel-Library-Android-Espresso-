package org.lds.ldssa.model.database.gl.recentlanguage;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RecentLanguageManager_Factory implements Factory<RecentLanguageManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public RecentLanguageManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public RecentLanguageManager get() {
    return new RecentLanguageManager(arg0Provider.get());
  }

  public static Factory<RecentLanguageManager> create(Provider<DatabaseManager> arg0Provider) {
    return new RecentLanguageManager_Factory(arg0Provider);
  }
}

package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UserdataDbUtil_Factory implements Factory<UserdataDbUtil> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<GLFileUtil> fileUtilProvider;

  private final Provider<DatabaseManager> databaseManagerProvider;

  public UserdataDbUtil_Factory(
      Provider<Prefs> prefsProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<DatabaseManager> databaseManagerProvider) {
    this.prefsProvider = prefsProvider;
    this.fileUtilProvider = fileUtilProvider;
    this.databaseManagerProvider = databaseManagerProvider;
  }

  @Override
  public UserdataDbUtil get() {
    return new UserdataDbUtil(
        prefsProvider.get(), fileUtilProvider.get(), databaseManagerProvider.get());
  }

  public static Factory<UserdataDbUtil> create(
      Provider<Prefs> prefsProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<DatabaseManager> databaseManagerProvider) {
    return new UserdataDbUtil_Factory(prefsProvider, fileUtilProvider, databaseManagerProvider);
  }
}

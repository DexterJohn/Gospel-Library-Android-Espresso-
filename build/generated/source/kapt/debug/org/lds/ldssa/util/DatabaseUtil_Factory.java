package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.mobile.util.LdsTimeUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DatabaseUtil_Factory implements Factory<DatabaseUtil> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<Prefs> arg1Provider;

  private final Provider<CatalogMetaDataManager> arg2Provider;

  private final Provider<LdsTimeUtil> arg3Provider;

  public DatabaseUtil_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<CatalogMetaDataManager> arg2Provider,
      Provider<LdsTimeUtil> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public DatabaseUtil get() {
    return new DatabaseUtil(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<DatabaseUtil> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<CatalogMetaDataManager> arg2Provider,
      Provider<LdsTimeUtil> arg3Provider) {
    return new DatabaseUtil_Factory(arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}

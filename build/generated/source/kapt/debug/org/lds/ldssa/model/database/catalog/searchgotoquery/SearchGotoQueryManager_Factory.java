package org.lds.ldssa.model.database.catalog.searchgotoquery;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchGotoQueryManager_Factory implements Factory<SearchGotoQueryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SearchGotoQueryManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SearchGotoQueryManager get() {
    return new SearchGotoQueryManager(arg0Provider.get());
  }

  public static Factory<SearchGotoQueryManager> create(Provider<DatabaseManager> arg0Provider) {
    return new SearchGotoQueryManager_Factory(arg0Provider);
  }
}

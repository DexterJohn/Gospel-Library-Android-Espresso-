package org.lds.ldssa.model.database.catalog.searchgoto;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchGotoManager_Factory implements Factory<SearchGotoManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SearchGotoManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SearchGotoManager get() {
    return new SearchGotoManager(arg0Provider.get());
  }

  public static Factory<SearchGotoManager> create(Provider<DatabaseManager> arg0Provider) {
    return new SearchGotoManager_Factory(arg0Provider);
  }
}

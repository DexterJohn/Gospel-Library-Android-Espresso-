package org.lds.ldssa.model.database.search.searchcountcontent;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchCountContentManager_Factory implements Factory<SearchCountContentManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SearchCountContentManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SearchCountContentManager get() {
    return new SearchCountContentManager(arg0Provider.get());
  }

  public static Factory<SearchCountContentManager> create(Provider<DatabaseManager> arg0Provider) {
    return new SearchCountContentManager_Factory(arg0Provider);
  }
}

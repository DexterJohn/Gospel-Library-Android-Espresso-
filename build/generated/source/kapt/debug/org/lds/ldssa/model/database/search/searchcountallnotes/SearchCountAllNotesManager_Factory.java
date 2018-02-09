package org.lds.ldssa.model.database.search.searchcountallnotes;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchCountAllNotesManager_Factory
    implements Factory<SearchCountAllNotesManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SearchCountAllNotesManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SearchCountAllNotesManager get() {
    return new SearchCountAllNotesManager(arg0Provider.get());
  }

  public static Factory<SearchCountAllNotesManager> create(Provider<DatabaseManager> arg0Provider) {
    return new SearchCountAllNotesManager_Factory(arg0Provider);
  }
}

package org.lds.ldssa.model.database.userdata.notesearchquery;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.search.SearchUtil;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NoteSearchQueryManager_Factory implements Factory<NoteSearchQueryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<UserdataDbUtil> arg1Provider;

  private final Provider<SearchUtil> arg2Provider;

  public NoteSearchQueryManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<UserdataDbUtil> arg1Provider,
      Provider<SearchUtil> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public NoteSearchQueryManager get() {
    return new NoteSearchQueryManager(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<NoteSearchQueryManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<UserdataDbUtil> arg1Provider,
      Provider<SearchUtil> arg2Provider) {
    return new NoteSearchQueryManager_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

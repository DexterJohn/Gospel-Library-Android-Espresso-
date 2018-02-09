package org.lds.ldssa.model.database.search.searchpreviewnote;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchPreviewNoteManager_Factory implements Factory<SearchPreviewNoteManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SearchPreviewNoteManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SearchPreviewNoteManager get() {
    return new SearchPreviewNoteManager(arg0Provider.get());
  }

  public static Factory<SearchPreviewNoteManager> create(Provider<DatabaseManager> arg0Provider) {
    return new SearchPreviewNoteManager_Factory(arg0Provider);
  }
}

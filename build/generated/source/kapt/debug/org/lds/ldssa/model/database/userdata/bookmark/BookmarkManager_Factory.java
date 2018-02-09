package org.lds.ldssa.model.database.userdata.bookmark;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BookmarkManager_Factory implements Factory<BookmarkManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<UserdataDbUtil> arg1Provider;

  public BookmarkManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public BookmarkManager get() {
    return new BookmarkManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<BookmarkManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    return new BookmarkManager_Factory(arg0Provider, arg1Provider);
  }
}

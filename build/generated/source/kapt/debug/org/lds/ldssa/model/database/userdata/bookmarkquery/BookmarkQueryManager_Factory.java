package org.lds.ldssa.model.database.userdata.bookmarkquery;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BookmarkQueryManager_Factory implements Factory<BookmarkQueryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<UserdataDbUtil> arg1Provider;

  public BookmarkQueryManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public BookmarkQueryManager get() {
    return new BookmarkQueryManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<BookmarkQueryManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    return new BookmarkQueryManager_Factory(arg0Provider, arg1Provider);
  }
}

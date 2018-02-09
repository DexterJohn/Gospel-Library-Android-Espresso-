package org.lds.ldssa.model.database.content.author;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AuthorManager_Factory implements Factory<AuthorManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<ContentItemUtil> arg1Provider;

  public AuthorManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public AuthorManager get() {
    return new AuthorManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<AuthorManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    return new AuthorManager_Factory(arg0Provider, arg1Provider);
  }
}

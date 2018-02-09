package org.lds.ldssa.model.database.content.authorrole;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AuthorRoleManager_Factory implements Factory<AuthorRoleManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<ContentItemUtil> arg1Provider;

  public AuthorRoleManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public AuthorRoleManager get() {
    return new AuthorRoleManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<AuthorRoleManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    return new AuthorRoleManager_Factory(arg0Provider, arg1Provider);
  }
}

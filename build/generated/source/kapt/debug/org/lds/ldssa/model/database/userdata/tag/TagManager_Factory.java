package org.lds.ldssa.model.database.userdata.tag;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TagManager_Factory implements Factory<TagManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<UserdataDbUtil> arg1Provider;

  public TagManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public TagManager get() {
    return new TagManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<TagManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    return new TagManager_Factory(arg0Provider, arg1Provider);
  }
}

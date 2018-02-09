package org.lds.ldssa.model.database.userdata.note;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NoteManager_Factory implements Factory<NoteManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<UserdataDbUtil> arg1Provider;

  public NoteManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public NoteManager get() {
    return new NoteManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<NoteManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    return new NoteManager_Factory(arg0Provider, arg1Provider);
  }
}

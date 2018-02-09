package org.lds.ldssa.model.database.userdata.notebookannotation;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NotebookAnnotationManager_Factory implements Factory<NotebookAnnotationManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<UserdataDbUtil> arg1Provider;

  public NotebookAnnotationManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public NotebookAnnotationManager get() {
    return new NotebookAnnotationManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<NotebookAnnotationManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    return new NotebookAnnotationManager_Factory(arg0Provider, arg1Provider);
  }
}

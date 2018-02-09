package org.lds.ldssa.model.database.userdata.notebook;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager;
import org.lds.ldssa.sync.AnnotationSyncScheduler;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NotebookManager_Factory implements Factory<NotebookManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<UserdataDbUtil> arg1Provider;

  private final Provider<NotebookAnnotationManager> arg2Provider;

  private final Provider<AnnotationSyncScheduler> arg3Provider;

  public NotebookManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<UserdataDbUtil> arg1Provider,
      Provider<NotebookAnnotationManager> arg2Provider,
      Provider<AnnotationSyncScheduler> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public NotebookManager get() {
    return new NotebookManager(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<NotebookManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<UserdataDbUtil> arg1Provider,
      Provider<NotebookAnnotationManager> arg2Provider,
      Provider<AnnotationSyncScheduler> arg3Provider) {
    return new NotebookManager_Factory(arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}

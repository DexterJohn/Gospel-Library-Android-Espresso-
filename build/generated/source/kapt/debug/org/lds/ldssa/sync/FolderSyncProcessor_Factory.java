package org.lds.ldssa.sync;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager;
import org.lds.mobile.util.LdsTimeUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class FolderSyncProcessor_Factory implements Factory<FolderSyncProcessor> {
  private final Provider<LdsTimeUtil> arg0Provider;

  private final Provider<NotebookManager> arg1Provider;

  private final Provider<NotebookAnnotationManager> arg2Provider;

  private final Provider<Gson> arg3Provider;

  public FolderSyncProcessor_Factory(
      Provider<LdsTimeUtil> arg0Provider,
      Provider<NotebookManager> arg1Provider,
      Provider<NotebookAnnotationManager> arg2Provider,
      Provider<Gson> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public FolderSyncProcessor get() {
    return new FolderSyncProcessor(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<FolderSyncProcessor> create(
      Provider<LdsTimeUtil> arg0Provider,
      Provider<NotebookManager> arg1Provider,
      Provider<NotebookAnnotationManager> arg2Provider,
      Provider<Gson> arg3Provider) {
    return new FolderSyncProcessor_Factory(arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}

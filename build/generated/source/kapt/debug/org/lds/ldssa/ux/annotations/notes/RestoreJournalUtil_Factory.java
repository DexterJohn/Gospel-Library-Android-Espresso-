package org.lds.ldssa.ux.annotations.notes;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RestoreJournalUtil_Factory implements Factory<RestoreJournalUtil> {
  private final Provider<Application> arg0Provider;

  private final Provider<AnnotationManager> arg1Provider;

  private final Provider<NotebookManager> arg2Provider;

  public RestoreJournalUtil_Factory(
      Provider<Application> arg0Provider,
      Provider<AnnotationManager> arg1Provider,
      Provider<NotebookManager> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public RestoreJournalUtil get() {
    return new RestoreJournalUtil(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<RestoreJournalUtil> create(
      Provider<Application> arg0Provider,
      Provider<AnnotationManager> arg1Provider,
      Provider<NotebookManager> arg2Provider) {
    return new RestoreJournalUtil_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NotebookUtil_Factory implements Factory<NotebookUtil> {
  private final Provider<AnnotationManager> arg0Provider;

  private final Provider<NotebookManager> arg1Provider;

  public NotebookUtil_Factory(
      Provider<AnnotationManager> arg0Provider, Provider<NotebookManager> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public NotebookUtil get() {
    return new NotebookUtil(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<NotebookUtil> create(
      Provider<AnnotationManager> arg0Provider, Provider<NotebookManager> arg1Provider) {
    return new NotebookUtil_Factory(arg0Provider, arg1Provider);
  }
}

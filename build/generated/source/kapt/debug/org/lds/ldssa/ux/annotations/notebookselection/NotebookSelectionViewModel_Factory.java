package org.lds.ldssa.ux.annotations.notebookselection;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager;
import org.lds.ldssa.model.database.userdata.notebookview.NotebookViewManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NotebookSelectionViewModel_Factory
    implements Factory<NotebookSelectionViewModel> {
  private final Provider<NotebookViewManager> arg0Provider;

  private final Provider<AnnotationManager> arg1Provider;

  private final Provider<NotebookManager> arg2Provider;

  private final Provider<NotebookAnnotationManager> arg3Provider;

  private final Provider<Prefs> arg4Provider;

  private final Provider<CoroutineContextProvider> arg5Provider;

  public NotebookSelectionViewModel_Factory(
      Provider<NotebookViewManager> arg0Provider,
      Provider<AnnotationManager> arg1Provider,
      Provider<NotebookManager> arg2Provider,
      Provider<NotebookAnnotationManager> arg3Provider,
      Provider<Prefs> arg4Provider,
      Provider<CoroutineContextProvider> arg5Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
  }

  @Override
  public NotebookSelectionViewModel get() {
    return new NotebookSelectionViewModel(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get());
  }

  public static Factory<NotebookSelectionViewModel> create(
      Provider<NotebookViewManager> arg0Provider,
      Provider<AnnotationManager> arg1Provider,
      Provider<NotebookManager> arg2Provider,
      Provider<NotebookAnnotationManager> arg3Provider,
      Provider<Prefs> arg4Provider,
      Provider<CoroutineContextProvider> arg5Provider) {
    return new NotebookSelectionViewModel_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider, arg4Provider, arg5Provider);
  }
}

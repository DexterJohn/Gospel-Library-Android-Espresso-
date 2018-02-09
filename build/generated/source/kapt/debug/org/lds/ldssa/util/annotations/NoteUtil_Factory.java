package org.lds.ldssa.util.annotations;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.note.NoteManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NoteUtil_Factory implements Factory<NoteUtil> {
  private final Provider<NoteManager> arg0Provider;

  private final Provider<AnnotationManager> arg1Provider;

  private final Provider<NotebookManager> arg2Provider;

  private final Provider<NotebookAnnotationManager> arg3Provider;

  public NoteUtil_Factory(
      Provider<NoteManager> arg0Provider,
      Provider<AnnotationManager> arg1Provider,
      Provider<NotebookManager> arg2Provider,
      Provider<NotebookAnnotationManager> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public NoteUtil get() {
    return new NoteUtil(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<NoteUtil> create(
      Provider<NoteManager> arg0Provider,
      Provider<AnnotationManager> arg1Provider,
      Provider<NotebookManager> arg2Provider,
      Provider<NotebookAnnotationManager> arg3Provider) {
    return new NoteUtil_Factory(arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}

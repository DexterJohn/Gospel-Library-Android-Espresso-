package org.lds.ldssa.model.database.userdata.annotation;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager;
import org.lds.ldssa.model.database.userdata.link.LinkManager;
import org.lds.ldssa.model.database.userdata.note.NoteManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager;
import org.lds.ldssa.model.database.userdata.tag.TagManager;
import org.lds.ldssa.sync.AnnotationSyncScheduler;
import org.lds.ldssa.util.UserdataDbUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationManager_Factory implements Factory<AnnotationManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<Bus> arg1Provider;

  private final Provider<Gson> arg2Provider;

  private final Provider<AnnotationSyncScheduler> arg3Provider;

  private final Provider<HighlightManager> arg4Provider;

  private final Provider<BookmarkManager> arg5Provider;

  private final Provider<NoteManager> arg6Provider;

  private final Provider<LinkManager> arg7Provider;

  private final Provider<TagManager> arg8Provider;

  private final Provider<UserdataDbUtil> arg9Provider;

  private final Provider<NotebookAnnotationManager> arg10Provider;

  private final Provider<NotebookManager> arg11Provider;

  public AnnotationManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<Bus> arg1Provider,
      Provider<Gson> arg2Provider,
      Provider<AnnotationSyncScheduler> arg3Provider,
      Provider<HighlightManager> arg4Provider,
      Provider<BookmarkManager> arg5Provider,
      Provider<NoteManager> arg6Provider,
      Provider<LinkManager> arg7Provider,
      Provider<TagManager> arg8Provider,
      Provider<UserdataDbUtil> arg9Provider,
      Provider<NotebookAnnotationManager> arg10Provider,
      Provider<NotebookManager> arg11Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
    this.arg7Provider = arg7Provider;
    this.arg8Provider = arg8Provider;
    this.arg9Provider = arg9Provider;
    this.arg10Provider = arg10Provider;
    this.arg11Provider = arg11Provider;
  }

  @Override
  public AnnotationManager get() {
    return new AnnotationManager(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get(),
        arg7Provider.get(),
        arg8Provider.get(),
        arg9Provider.get(),
        arg10Provider.get(),
        arg11Provider.get());
  }

  public static Factory<AnnotationManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<Bus> arg1Provider,
      Provider<Gson> arg2Provider,
      Provider<AnnotationSyncScheduler> arg3Provider,
      Provider<HighlightManager> arg4Provider,
      Provider<BookmarkManager> arg5Provider,
      Provider<NoteManager> arg6Provider,
      Provider<LinkManager> arg7Provider,
      Provider<TagManager> arg8Provider,
      Provider<UserdataDbUtil> arg9Provider,
      Provider<NotebookAnnotationManager> arg10Provider,
      Provider<NotebookManager> arg11Provider) {
    return new AnnotationManager_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider,
        arg7Provider,
        arg8Provider,
        arg9Provider,
        arg10Provider,
        arg11Provider);
  }
}

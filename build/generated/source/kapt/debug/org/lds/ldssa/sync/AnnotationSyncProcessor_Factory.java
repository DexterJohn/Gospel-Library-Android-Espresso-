package org.lds.ldssa.sync;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager;
import org.lds.ldssa.model.database.userdata.link.LinkManager;
import org.lds.ldssa.model.database.userdata.note.NoteManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager;
import org.lds.ldssa.model.database.userdata.tag.TagManager;
import org.lds.ldssa.ui.notification.AnnotationSyncNotification;
import org.lds.ldssa.util.CitationUtil;
import org.lds.mobile.util.LdsTimeUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationSyncProcessor_Factory implements Factory<AnnotationSyncProcessor> {
  private final Provider<AnnotationManager> arg0Provider;

  private final Provider<BookmarkManager> arg1Provider;

  private final Provider<HighlightManager> arg2Provider;

  private final Provider<TagManager> arg3Provider;

  private final Provider<NotebookManager> arg4Provider;

  private final Provider<NotebookAnnotationManager> arg5Provider;

  private final Provider<LinkManager> arg6Provider;

  private final Provider<NoteManager> arg7Provider;

  private final Provider<LdsTimeUtil> arg8Provider;

  private final Provider<CitationUtil> arg9Provider;

  private final Provider<AnnotationSyncNotification> arg10Provider;

  private final Provider<Gson> arg11Provider;

  public AnnotationSyncProcessor_Factory(
      Provider<AnnotationManager> arg0Provider,
      Provider<BookmarkManager> arg1Provider,
      Provider<HighlightManager> arg2Provider,
      Provider<TagManager> arg3Provider,
      Provider<NotebookManager> arg4Provider,
      Provider<NotebookAnnotationManager> arg5Provider,
      Provider<LinkManager> arg6Provider,
      Provider<NoteManager> arg7Provider,
      Provider<LdsTimeUtil> arg8Provider,
      Provider<CitationUtil> arg9Provider,
      Provider<AnnotationSyncNotification> arg10Provider,
      Provider<Gson> arg11Provider) {
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
  public AnnotationSyncProcessor get() {
    return new AnnotationSyncProcessor(
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

  public static Factory<AnnotationSyncProcessor> create(
      Provider<AnnotationManager> arg0Provider,
      Provider<BookmarkManager> arg1Provider,
      Provider<HighlightManager> arg2Provider,
      Provider<TagManager> arg3Provider,
      Provider<NotebookManager> arg4Provider,
      Provider<NotebookAnnotationManager> arg5Provider,
      Provider<LinkManager> arg6Provider,
      Provider<NoteManager> arg7Provider,
      Provider<LdsTimeUtil> arg8Provider,
      Provider<CitationUtil> arg9Provider,
      Provider<AnnotationSyncNotification> arg10Provider,
      Provider<Gson> arg11Provider) {
    return new AnnotationSyncProcessor_Factory(
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

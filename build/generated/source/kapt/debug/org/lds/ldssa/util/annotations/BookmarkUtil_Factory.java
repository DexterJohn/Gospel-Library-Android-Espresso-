package org.lds.ldssa.util.annotations;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;
import org.lds.ldssa.model.database.userdata.bookmarkquery.BookmarkQueryManager;
import org.lds.ldssa.sync.AnnotationSyncScheduler;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.CitationUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BookmarkUtil_Factory implements Factory<BookmarkUtil> {
  private final Provider<AnnotationManager> arg0Provider;

  private final Provider<SubItemManager> arg1Provider;

  private final Provider<ItemManager> arg2Provider;

  private final Provider<BookmarkManager> arg3Provider;

  private final Provider<CitationUtil> arg4Provider;

  private final Provider<AnalyticsUtil> arg5Provider;

  private final Provider<BookmarkQueryManager> arg6Provider;

  private final Provider<AnnotationSyncScheduler> arg7Provider;

  public BookmarkUtil_Factory(
      Provider<AnnotationManager> arg0Provider,
      Provider<SubItemManager> arg1Provider,
      Provider<ItemManager> arg2Provider,
      Provider<BookmarkManager> arg3Provider,
      Provider<CitationUtil> arg4Provider,
      Provider<AnalyticsUtil> arg5Provider,
      Provider<BookmarkQueryManager> arg6Provider,
      Provider<AnnotationSyncScheduler> arg7Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
    this.arg7Provider = arg7Provider;
  }

  @Override
  public BookmarkUtil get() {
    return new BookmarkUtil(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get(),
        arg7Provider.get());
  }

  public static Factory<BookmarkUtil> create(
      Provider<AnnotationManager> arg0Provider,
      Provider<SubItemManager> arg1Provider,
      Provider<ItemManager> arg2Provider,
      Provider<BookmarkManager> arg3Provider,
      Provider<CitationUtil> arg4Provider,
      Provider<AnalyticsUtil> arg5Provider,
      Provider<BookmarkQueryManager> arg6Provider,
      Provider<AnnotationSyncScheduler> arg7Provider) {
    return new BookmarkUtil_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider,
        arg7Provider);
  }
}

package org.lds.ldssa.ux.locations.bookmarks;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;
import org.lds.ldssa.util.annotations.BookmarkUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BookmarksViewModel_Factory implements Factory<BookmarksViewModel> {
  private final Provider<CoroutineContextProvider> arg0Provider;

  private final Provider<BookmarkManager> arg1Provider;

  private final Provider<BookmarkUtil> arg2Provider;

  public BookmarksViewModel_Factory(
      Provider<CoroutineContextProvider> arg0Provider,
      Provider<BookmarkManager> arg1Provider,
      Provider<BookmarkUtil> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public BookmarksViewModel get() {
    return new BookmarksViewModel(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<BookmarksViewModel> create(
      Provider<CoroutineContextProvider> arg0Provider,
      Provider<BookmarkManager> arg1Provider,
      Provider<BookmarkUtil> arg2Provider) {
    return new BookmarksViewModel_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

package org.lds.ldssa.ux.locations.bookmarks;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.util.annotations.BookmarkUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BookmarksAdapter_MembersInjector implements MembersInjector<BookmarksAdapter> {
  private final Provider<BookmarkUtil> bookmarkUtilProvider;

  public BookmarksAdapter_MembersInjector(Provider<BookmarkUtil> bookmarkUtilProvider) {
    this.bookmarkUtilProvider = bookmarkUtilProvider;
  }

  public static MembersInjector<BookmarksAdapter> create(
      Provider<BookmarkUtil> bookmarkUtilProvider) {
    return new BookmarksAdapter_MembersInjector(bookmarkUtilProvider);
  }

  @Override
  public void injectMembers(BookmarksAdapter instance) {
    injectBookmarkUtil(instance, bookmarkUtilProvider.get());
  }

  public static void injectBookmarkUtil(BookmarksAdapter instance, BookmarkUtil bookmarkUtil) {
    instance.bookmarkUtil = bookmarkUtil;
  }
}

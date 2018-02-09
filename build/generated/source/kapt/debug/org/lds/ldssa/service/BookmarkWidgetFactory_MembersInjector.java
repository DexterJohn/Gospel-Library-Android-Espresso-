package org.lds.ldssa.service;

import android.app.Application;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BookmarkWidgetFactory_MembersInjector
    implements MembersInjector<BookmarkWidgetFactory> {
  private final Provider<BookmarkManager> bookmarkManagerProvider;

  private final Provider<Application> applicationProvider;

  public BookmarkWidgetFactory_MembersInjector(
      Provider<BookmarkManager> bookmarkManagerProvider,
      Provider<Application> applicationProvider) {
    this.bookmarkManagerProvider = bookmarkManagerProvider;
    this.applicationProvider = applicationProvider;
  }

  public static MembersInjector<BookmarkWidgetFactory> create(
      Provider<BookmarkManager> bookmarkManagerProvider,
      Provider<Application> applicationProvider) {
    return new BookmarkWidgetFactory_MembersInjector(bookmarkManagerProvider, applicationProvider);
  }

  @Override
  public void injectMembers(BookmarkWidgetFactory instance) {
    injectBookmarkManager(instance, bookmarkManagerProvider.get());
    injectApplication(instance, applicationProvider.get());
  }

  public static void injectBookmarkManager(
      BookmarkWidgetFactory instance, BookmarkManager bookmarkManager) {
    instance.bookmarkManager = bookmarkManager;
  }

  public static void injectApplication(BookmarkWidgetFactory instance, Application application) {
    instance.application = application;
  }
}

package org.lds.ldssa.ux.locations.bookmarks;

import android.arch.lifecycle.ViewModelProvider;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.ExternalIntents;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;
import org.lds.ldssa.model.database.userdata.bookmarkquery.BookmarkQueryManager;
import org.lds.ldssa.sync.AnnotationSync;
import org.lds.ldssa.ui.fragment.BaseFragment_MembersInjector;
import org.lds.ldssa.util.CitationUtil;
import org.lds.ldssa.util.ToastUtil;
import org.lds.ldssa.util.annotations.BookmarkUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BookmarksFragment_MembersInjector implements MembersInjector<BookmarksFragment> {
  private final Provider<Bus> busProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<BookmarkManager> bookmarkManagerProvider;

  private final Provider<BookmarkQueryManager> bookmarkQueryManagerProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<BookmarkUtil> bookmarkUtilProvider;

  private final Provider<ExternalIntents> externalIntentsProvider;

  private final Provider<CitationUtil> citationUtilProvider;

  private final Provider<AnnotationSync> annotationSyncProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  private final Provider<ToastUtil> toastUtilProvider;

  public BookmarksFragment_MembersInjector(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<BookmarkManager> bookmarkManagerProvider,
      Provider<BookmarkQueryManager> bookmarkQueryManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<BookmarkUtil> bookmarkUtilProvider,
      Provider<ExternalIntents> externalIntentsProvider,
      Provider<CitationUtil> citationUtilProvider,
      Provider<AnnotationSync> annotationSyncProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider,
      Provider<ToastUtil> toastUtilProvider) {
    this.busProvider = busProvider;
    this.ccProvider = ccProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.bookmarkManagerProvider = bookmarkManagerProvider;
    this.bookmarkQueryManagerProvider = bookmarkQueryManagerProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.bookmarkUtilProvider = bookmarkUtilProvider;
    this.externalIntentsProvider = externalIntentsProvider;
    this.citationUtilProvider = citationUtilProvider;
    this.annotationSyncProvider = annotationSyncProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
    this.toastUtilProvider = toastUtilProvider;
  }

  public static MembersInjector<BookmarksFragment> create(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<BookmarkManager> bookmarkManagerProvider,
      Provider<BookmarkQueryManager> bookmarkQueryManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<BookmarkUtil> bookmarkUtilProvider,
      Provider<ExternalIntents> externalIntentsProvider,
      Provider<CitationUtil> citationUtilProvider,
      Provider<AnnotationSync> annotationSyncProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider,
      Provider<ToastUtil> toastUtilProvider) {
    return new BookmarksFragment_MembersInjector(
        busProvider,
        ccProvider,
        internalIntentsProvider,
        bookmarkManagerProvider,
        bookmarkQueryManagerProvider,
        annotationManagerProvider,
        bookmarkUtilProvider,
        externalIntentsProvider,
        citationUtilProvider,
        annotationSyncProvider,
        viewModelFactoryProvider,
        toastUtilProvider);
  }

  @Override
  public void injectMembers(BookmarksFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, busProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, ccProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectBookmarkManager(instance, bookmarkManagerProvider.get());
    injectBookmarkQueryManager(instance, bookmarkQueryManagerProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectBookmarkUtil(instance, bookmarkUtilProvider.get());
    injectExternalIntents(instance, externalIntentsProvider.get());
    injectCitationUtil(instance, citationUtilProvider.get());
    injectAnnotationSync(instance, annotationSyncProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
    injectToastUtil(instance, toastUtilProvider.get());
  }

  public static void injectInternalIntents(
      BookmarksFragment instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectBookmarkManager(
      BookmarksFragment instance, BookmarkManager bookmarkManager) {
    instance.bookmarkManager = bookmarkManager;
  }

  public static void injectBookmarkQueryManager(
      BookmarksFragment instance, BookmarkQueryManager bookmarkQueryManager) {
    instance.bookmarkQueryManager = bookmarkQueryManager;
  }

  public static void injectAnnotationManager(
      BookmarksFragment instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectBookmarkUtil(BookmarksFragment instance, BookmarkUtil bookmarkUtil) {
    instance.bookmarkUtil = bookmarkUtil;
  }

  public static void injectExternalIntents(
      BookmarksFragment instance, ExternalIntents externalIntents) {
    instance.externalIntents = externalIntents;
  }

  public static void injectCitationUtil(BookmarksFragment instance, CitationUtil citationUtil) {
    instance.citationUtil = citationUtil;
  }

  public static void injectAnnotationSync(
      BookmarksFragment instance, AnnotationSync annotationSync) {
    instance.annotationSync = annotationSync;
  }

  public static void injectViewModelFactory(
      BookmarksFragment instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }

  public static void injectToastUtil(BookmarksFragment instance, ToastUtil toastUtil) {
    instance.toastUtil = toastUtil;
  }
}

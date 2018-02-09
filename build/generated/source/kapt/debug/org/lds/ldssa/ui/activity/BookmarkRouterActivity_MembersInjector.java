package org.lds.ldssa.ui.activity;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BookmarkRouterActivity_MembersInjector
    implements MembersInjector<BookmarkRouterActivity> {
  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<ScreenLauncherUtil> screenLauncherUtilProvider;

  public BookmarkRouterActivity_MembersInjector(
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider) {
    this.internalIntentsProvider = internalIntentsProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.screenLauncherUtilProvider = screenLauncherUtilProvider;
  }

  public static MembersInjector<BookmarkRouterActivity> create(
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider) {
    return new BookmarkRouterActivity_MembersInjector(
        internalIntentsProvider, screenUtilProvider, screenLauncherUtilProvider);
  }

  @Override
  public void injectMembers(BookmarkRouterActivity instance) {
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectScreenUtil(instance, screenUtilProvider.get());
    injectScreenLauncherUtil(instance, screenLauncherUtilProvider.get());
  }

  public static void injectInternalIntents(
      BookmarkRouterActivity instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectScreenUtil(BookmarkRouterActivity instance, ScreenUtil screenUtil) {
    instance.screenUtil = screenUtil;
  }

  public static void injectScreenLauncherUtil(
      BookmarkRouterActivity instance, ScreenLauncherUtil screenLauncherUtil) {
    instance.screenLauncherUtil = screenLauncherUtil;
  }
}

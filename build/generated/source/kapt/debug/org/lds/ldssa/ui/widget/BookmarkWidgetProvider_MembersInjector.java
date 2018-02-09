package org.lds.ldssa.ui.widget;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BookmarkWidgetProvider_MembersInjector
    implements MembersInjector<BookmarkWidgetProvider> {
  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<DatabaseManager> databaseManagerProvider;

  private final Provider<UserdataDbUtil> userdataDbUtilProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<Prefs> prefsProvider;

  public BookmarkWidgetProvider_MembersInjector(
      Provider<InternalIntents> internalIntentsProvider,
      Provider<DatabaseManager> databaseManagerProvider,
      Provider<UserdataDbUtil> userdataDbUtilProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<Prefs> prefsProvider) {
    this.internalIntentsProvider = internalIntentsProvider;
    this.databaseManagerProvider = databaseManagerProvider;
    this.userdataDbUtilProvider = userdataDbUtilProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.prefsProvider = prefsProvider;
  }

  public static MembersInjector<BookmarkWidgetProvider> create(
      Provider<InternalIntents> internalIntentsProvider,
      Provider<DatabaseManager> databaseManagerProvider,
      Provider<UserdataDbUtil> userdataDbUtilProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<Prefs> prefsProvider) {
    return new BookmarkWidgetProvider_MembersInjector(
        internalIntentsProvider,
        databaseManagerProvider,
        userdataDbUtilProvider,
        screenUtilProvider,
        prefsProvider);
  }

  @Override
  public void injectMembers(BookmarkWidgetProvider instance) {
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectDatabaseManager(instance, databaseManagerProvider.get());
    injectUserdataDbUtil(instance, userdataDbUtilProvider.get());
    injectScreenUtil(instance, screenUtilProvider.get());
    injectPrefs(instance, prefsProvider.get());
  }

  public static void injectInternalIntents(
      BookmarkWidgetProvider instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectDatabaseManager(
      BookmarkWidgetProvider instance, DatabaseManager databaseManager) {
    instance.databaseManager = databaseManager;
  }

  public static void injectUserdataDbUtil(
      BookmarkWidgetProvider instance, UserdataDbUtil userdataDbUtil) {
    instance.userdataDbUtil = userdataDbUtil;
  }

  public static void injectScreenUtil(BookmarkWidgetProvider instance, ScreenUtil screenUtil) {
    instance.screenUtil = screenUtil;
  }

  public static void injectPrefs(BookmarkWidgetProvider instance, Prefs prefs) {
    instance.prefs = prefs;
  }
}

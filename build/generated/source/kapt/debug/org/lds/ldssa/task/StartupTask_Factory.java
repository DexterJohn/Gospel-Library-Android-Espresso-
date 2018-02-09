package org.lds.ldssa.task;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.download.CheckDownloadsTask;
import org.lds.ldssa.download.InitialContentDownloadTask;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.gl.history.HistoryManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.CatalogUpdateUtil;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.TipsUpdateUtil;
import org.lds.ldssa.util.TipsUtil;
import org.lds.ldssa.util.UserdataDbUtil;
import org.lds.mobile.util.LdsTimeUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StartupTask_Factory implements Factory<StartupTask> {
  private final Provider<Bus> busProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<DatabaseManager> databaseManagerProvider;

  private final Provider<CatalogUpdateUtil> catalogUpdateUtilProvider;

  private final Provider<ScreenLauncherUtil> screenLauncherUtilProvider;

  private final Provider<LdsTimeUtil> timeUtilProvider;

  private final Provider<UserdataDbUtil> userdataDbUtilProvider;

  private final Provider<HistoryManager> historyManagerProvider;

  private final Provider<TipsUtil> tipsUtilProvider;

  private final Provider<TipsUpdateUtil> tipsUpdateUtilProvider;

  private final Provider<LanguageUtil> languageUtilProvider;

  private final Provider<CheckDownloadsTask> checkDownloadsTaskProvider;

  private final Provider<InitialContentDownloadTask> initialContentDownloadTaskProvider;

  public StartupTask_Factory(
      Provider<Bus> busProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<Prefs> prefsProvider,
      Provider<DatabaseManager> databaseManagerProvider,
      Provider<CatalogUpdateUtil> catalogUpdateUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<LdsTimeUtil> timeUtilProvider,
      Provider<UserdataDbUtil> userdataDbUtilProvider,
      Provider<HistoryManager> historyManagerProvider,
      Provider<TipsUtil> tipsUtilProvider,
      Provider<TipsUpdateUtil> tipsUpdateUtilProvider,
      Provider<LanguageUtil> languageUtilProvider,
      Provider<CheckDownloadsTask> checkDownloadsTaskProvider,
      Provider<InitialContentDownloadTask> initialContentDownloadTaskProvider) {
    this.busProvider = busProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.prefsProvider = prefsProvider;
    this.databaseManagerProvider = databaseManagerProvider;
    this.catalogUpdateUtilProvider = catalogUpdateUtilProvider;
    this.screenLauncherUtilProvider = screenLauncherUtilProvider;
    this.timeUtilProvider = timeUtilProvider;
    this.userdataDbUtilProvider = userdataDbUtilProvider;
    this.historyManagerProvider = historyManagerProvider;
    this.tipsUtilProvider = tipsUtilProvider;
    this.tipsUpdateUtilProvider = tipsUpdateUtilProvider;
    this.languageUtilProvider = languageUtilProvider;
    this.checkDownloadsTaskProvider = checkDownloadsTaskProvider;
    this.initialContentDownloadTaskProvider = initialContentDownloadTaskProvider;
  }

  @Override
  public StartupTask get() {
    return new StartupTask(
        busProvider.get(),
        internalIntentsProvider.get(),
        prefsProvider.get(),
        databaseManagerProvider.get(),
        catalogUpdateUtilProvider.get(),
        screenLauncherUtilProvider.get(),
        timeUtilProvider.get(),
        userdataDbUtilProvider.get(),
        historyManagerProvider.get(),
        tipsUtilProvider.get(),
        tipsUpdateUtilProvider.get(),
        languageUtilProvider.get(),
        checkDownloadsTaskProvider,
        initialContentDownloadTaskProvider);
  }

  public static Factory<StartupTask> create(
      Provider<Bus> busProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<Prefs> prefsProvider,
      Provider<DatabaseManager> databaseManagerProvider,
      Provider<CatalogUpdateUtil> catalogUpdateUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<LdsTimeUtil> timeUtilProvider,
      Provider<UserdataDbUtil> userdataDbUtilProvider,
      Provider<HistoryManager> historyManagerProvider,
      Provider<TipsUtil> tipsUtilProvider,
      Provider<TipsUpdateUtil> tipsUpdateUtilProvider,
      Provider<LanguageUtil> languageUtilProvider,
      Provider<CheckDownloadsTask> checkDownloadsTaskProvider,
      Provider<InitialContentDownloadTask> initialContentDownloadTaskProvider) {
    return new StartupTask_Factory(
        busProvider,
        internalIntentsProvider,
        prefsProvider,
        databaseManagerProvider,
        catalogUpdateUtilProvider,
        screenLauncherUtilProvider,
        timeUtilProvider,
        userdataDbUtilProvider,
        historyManagerProvider,
        tipsUtilProvider,
        tipsUpdateUtilProvider,
        languageUtilProvider,
        checkDownloadsTaskProvider,
        initialContentDownloadTaskProvider);
  }
}

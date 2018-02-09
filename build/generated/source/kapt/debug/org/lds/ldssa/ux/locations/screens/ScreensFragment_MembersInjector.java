package org.lds.ldssa.ux.locations.screens;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.userdata.screen.ScreenManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.fragment.BaseFragment_MembersInjector;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ScreensFragment_MembersInjector implements MembersInjector<ScreensFragment> {
  private final Provider<Bus> busProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<ScreenLauncherUtil> screenLauncherUtilProvider;

  private final Provider<ScreenManager> screenManagerProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  public ScreensFragment_MembersInjector(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<ScreenManager> screenManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<InternalIntents> internalIntentsProvider) {
    this.busProvider = busProvider;
    this.ccProvider = ccProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.screenLauncherUtilProvider = screenLauncherUtilProvider;
    this.screenManagerProvider = screenManagerProvider;
    this.prefsProvider = prefsProvider;
    this.internalIntentsProvider = internalIntentsProvider;
  }

  public static MembersInjector<ScreensFragment> create(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<ScreenManager> screenManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<InternalIntents> internalIntentsProvider) {
    return new ScreensFragment_MembersInjector(
        busProvider,
        ccProvider,
        screenUtilProvider,
        screenLauncherUtilProvider,
        screenManagerProvider,
        prefsProvider,
        internalIntentsProvider);
  }

  @Override
  public void injectMembers(ScreensFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, busProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, ccProvider.get());
    injectScreenUtil(instance, screenUtilProvider.get());
    injectScreenLauncherUtil(instance, screenLauncherUtilProvider.get());
    injectScreenManager(instance, screenManagerProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
  }

  public static void injectScreenUtil(ScreensFragment instance, ScreenUtil screenUtil) {
    instance.screenUtil = screenUtil;
  }

  public static void injectScreenLauncherUtil(
      ScreensFragment instance, ScreenLauncherUtil screenLauncherUtil) {
    instance.screenLauncherUtil = screenLauncherUtil;
  }

  public static void injectScreenManager(ScreensFragment instance, ScreenManager screenManager) {
    instance.screenManager = screenManager;
  }

  public static void injectPrefs(ScreensFragment instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectInternalIntents(
      ScreensFragment instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }
}

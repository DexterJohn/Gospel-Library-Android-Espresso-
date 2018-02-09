package org.lds.ldssa.ux.welcome;

import android.arch.lifecycle.ViewModelProvider;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.LDSAccountUtil;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class WelcomeActivity_MembersInjector implements MembersInjector<WelcomeActivity> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<LDSAccountUtil> ldsAccountUtilProvider;

  private final Provider<ScreenLauncherUtil> screenLauncherUtilProvider;

  private final Provider<AccountUtil> accountUtilProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public WelcomeActivity_MembersInjector(
      Provider<Prefs> prefsProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<LDSAccountUtil> ldsAccountUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<AccountUtil> accountUtilProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    this.prefsProvider = prefsProvider;
    this.ccProvider = ccProvider;
    this.ldsAccountUtilProvider = ldsAccountUtilProvider;
    this.screenLauncherUtilProvider = screenLauncherUtilProvider;
    this.accountUtilProvider = accountUtilProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<WelcomeActivity> create(
      Provider<Prefs> prefsProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<LDSAccountUtil> ldsAccountUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<AccountUtil> accountUtilProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new WelcomeActivity_MembersInjector(
        prefsProvider,
        ccProvider,
        ldsAccountUtilProvider,
        screenLauncherUtilProvider,
        accountUtilProvider,
        viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(WelcomeActivity instance) {
    injectPrefs(instance, prefsProvider.get());
    injectCc(instance, ccProvider.get());
    injectLdsAccountUtil(instance, ldsAccountUtilProvider.get());
    injectScreenLauncherUtil(instance, screenLauncherUtilProvider.get());
    injectAccountUtil(instance, accountUtilProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  public static void injectPrefs(WelcomeActivity instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectCc(WelcomeActivity instance, CoroutineContextProvider cc) {
    instance.cc = cc;
  }

  public static void injectLdsAccountUtil(WelcomeActivity instance, LDSAccountUtil ldsAccountUtil) {
    instance.ldsAccountUtil = ldsAccountUtil;
  }

  public static void injectScreenLauncherUtil(
      WelcomeActivity instance, ScreenLauncherUtil screenLauncherUtil) {
    instance.screenLauncherUtil = screenLauncherUtil;
  }

  public static void injectAccountUtil(WelcomeActivity instance, AccountUtil accountUtil) {
    instance.accountUtil = accountUtil;
  }

  public static void injectViewModelFactory(
      WelcomeActivity instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}

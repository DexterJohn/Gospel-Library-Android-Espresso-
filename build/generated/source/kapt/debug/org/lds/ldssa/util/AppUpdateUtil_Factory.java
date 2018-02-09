package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.webservice.app.AppConfigService;
import org.lds.ldssa.ui.notification.AppUpdateNotification;
import org.lds.mobile.util.LdsNetworkUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppUpdateUtil_Factory implements Factory<AppUpdateUtil> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<LdsNetworkUtil> networkUtilProvider;

  private final Provider<AppConfigService> appConfigServiceProvider;

  private final Provider<AppUpdateNotification> appUpdateNotificationProvider;

  public AppUpdateUtil_Factory(
      Provider<Prefs> prefsProvider,
      Provider<LdsNetworkUtil> networkUtilProvider,
      Provider<AppConfigService> appConfigServiceProvider,
      Provider<AppUpdateNotification> appUpdateNotificationProvider) {
    this.prefsProvider = prefsProvider;
    this.networkUtilProvider = networkUtilProvider;
    this.appConfigServiceProvider = appConfigServiceProvider;
    this.appUpdateNotificationProvider = appUpdateNotificationProvider;
  }

  @Override
  public AppUpdateUtil get() {
    return new AppUpdateUtil(
        prefsProvider.get(),
        networkUtilProvider.get(),
        appConfigServiceProvider.get(),
        appUpdateNotificationProvider.get());
  }

  public static Factory<AppUpdateUtil> create(
      Provider<Prefs> prefsProvider,
      Provider<LdsNetworkUtil> networkUtilProvider,
      Provider<AppConfigService> appConfigServiceProvider,
      Provider<AppUpdateNotification> appUpdateNotificationProvider) {
    return new AppUpdateUtil_Factory(
        prefsProvider,
        networkUtilProvider,
        appConfigServiceProvider,
        appUpdateNotificationProvider);
  }
}

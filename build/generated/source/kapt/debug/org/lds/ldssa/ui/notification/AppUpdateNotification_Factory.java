package org.lds.ldssa.ui.notification;

import android.app.Application;
import android.app.NotificationManager;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppUpdateNotification_Factory implements Factory<AppUpdateNotification> {
  private final Provider<Application> arg0Provider;

  private final Provider<NotificationManager> arg1Provider;

  public AppUpdateNotification_Factory(
      Provider<Application> arg0Provider, Provider<NotificationManager> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public AppUpdateNotification get() {
    return new AppUpdateNotification(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<AppUpdateNotification> create(
      Provider<Application> arg0Provider, Provider<NotificationManager> arg1Provider) {
    return new AppUpdateNotification_Factory(arg0Provider, arg1Provider);
  }
}

package org.lds.ldssa.ui.notification;

import android.app.Application;
import android.app.NotificationManager;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationSyncNotification_Factory
    implements Factory<AnnotationSyncNotification> {
  private final Provider<Application> arg0Provider;

  private final Provider<NotificationManager> arg1Provider;

  private final Provider<Bus> arg2Provider;

  public AnnotationSyncNotification_Factory(
      Provider<Application> arg0Provider,
      Provider<NotificationManager> arg1Provider,
      Provider<Bus> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public AnnotationSyncNotification get() {
    return new AnnotationSyncNotification(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<AnnotationSyncNotification> create(
      Provider<Application> arg0Provider,
      Provider<NotificationManager> arg1Provider,
      Provider<Bus> arg2Provider) {
    return new AnnotationSyncNotification_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

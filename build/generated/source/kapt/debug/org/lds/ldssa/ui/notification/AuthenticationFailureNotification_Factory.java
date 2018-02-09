package org.lds.ldssa.ui.notification;

import android.app.Application;
import android.app.NotificationManager;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AuthenticationFailureNotification_Factory
    implements Factory<AuthenticationFailureNotification> {
  private final Provider<Application> arg0Provider;

  private final Provider<Prefs> arg1Provider;

  private final Provider<NotificationManager> arg2Provider;

  public AuthenticationFailureNotification_Factory(
      Provider<Application> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<NotificationManager> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public AuthenticationFailureNotification get() {
    return new AuthenticationFailureNotification(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<AuthenticationFailureNotification> create(
      Provider<Application> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<NotificationManager> arg2Provider) {
    return new AuthenticationFailureNotification_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

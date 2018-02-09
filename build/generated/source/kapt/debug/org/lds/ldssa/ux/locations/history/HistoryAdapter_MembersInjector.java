package org.lds.ldssa.ux.locations.history;

import android.app.Application;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HistoryAdapter_MembersInjector implements MembersInjector<HistoryAdapter> {
  private final Provider<Application> applicationProvider;

  public HistoryAdapter_MembersInjector(Provider<Application> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  public static MembersInjector<HistoryAdapter> create(Provider<Application> applicationProvider) {
    return new HistoryAdapter_MembersInjector(applicationProvider);
  }

  @Override
  public void injectMembers(HistoryAdapter instance) {
    injectApplication(instance, applicationProvider.get());
  }

  public static void injectApplication(HistoryAdapter instance, Application application) {
    instance.application = application;
  }
}

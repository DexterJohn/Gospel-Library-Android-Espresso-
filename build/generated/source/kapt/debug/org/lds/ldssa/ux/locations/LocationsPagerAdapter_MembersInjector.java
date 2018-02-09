package org.lds.ldssa.ux.locations;

import android.app.Application;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LocationsPagerAdapter_MembersInjector
    implements MembersInjector<LocationsPagerAdapter> {
  private final Provider<Application> applicationProvider;

  public LocationsPagerAdapter_MembersInjector(Provider<Application> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  public static MembersInjector<LocationsPagerAdapter> create(
      Provider<Application> applicationProvider) {
    return new LocationsPagerAdapter_MembersInjector(applicationProvider);
  }

  @Override
  public void injectMembers(LocationsPagerAdapter instance) {
    injectApplication(instance, applicationProvider.get());
  }

  public static void injectApplication(LocationsPagerAdapter instance, Application application) {
    instance.application = application;
  }
}

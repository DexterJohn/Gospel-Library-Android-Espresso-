package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DeviceUtil_Factory implements Factory<DeviceUtil> {
  private final Provider<Application> applicationProvider;

  public DeviceUtil_Factory(Provider<Application> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  @Override
  public DeviceUtil get() {
    return new DeviceUtil(applicationProvider.get());
  }

  public static Factory<DeviceUtil> create(Provider<Application> applicationProvider) {
    return new DeviceUtil_Factory(applicationProvider);
  }
}

package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class VideoUtil_Factory implements Factory<VideoUtil> {
  private final Provider<Application> applicationProvider;

  public VideoUtil_Factory(Provider<Application> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  @Override
  public VideoUtil get() {
    return new VideoUtil(applicationProvider.get());
  }

  public static Factory<VideoUtil> create(Provider<Application> applicationProvider) {
    return new VideoUtil_Factory(applicationProvider);
  }
}

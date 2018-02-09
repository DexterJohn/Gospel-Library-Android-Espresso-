package org.lds.ldssa.job;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppJobCreator_Factory implements Factory<AppJobCreator> {
  private final Provider<AnnotationSyncJob> arg0Provider;

  public AppJobCreator_Factory(Provider<AnnotationSyncJob> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public AppJobCreator get() {
    return new AppJobCreator(arg0Provider);
  }

  public static Factory<AppJobCreator> create(Provider<AnnotationSyncJob> arg0Provider) {
    return new AppJobCreator_Factory(arg0Provider);
  }
}

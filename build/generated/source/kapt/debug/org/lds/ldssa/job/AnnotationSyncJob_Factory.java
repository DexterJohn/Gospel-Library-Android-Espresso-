package org.lds.ldssa.job;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.sync.AnnotationSync;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationSyncJob_Factory implements Factory<AnnotationSyncJob> {
  private final Provider<AnnotationSync> arg0Provider;

  public AnnotationSyncJob_Factory(Provider<AnnotationSync> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public AnnotationSyncJob get() {
    return new AnnotationSyncJob(arg0Provider.get());
  }

  public static Factory<AnnotationSyncJob> create(Provider<AnnotationSync> arg0Provider) {
    return new AnnotationSyncJob_Factory(arg0Provider);
  }
}

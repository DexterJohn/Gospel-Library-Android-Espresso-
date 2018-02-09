package org.lds.ldssa.sync;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationSyncScheduler_Factory implements Factory<AnnotationSyncScheduler> {
  private static final AnnotationSyncScheduler_Factory INSTANCE =
      new AnnotationSyncScheduler_Factory();

  @Override
  public AnnotationSyncScheduler get() {
    return new AnnotationSyncScheduler();
  }

  public static Factory<AnnotationSyncScheduler> create() {
    return INSTANCE;
  }
}

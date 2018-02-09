package org.lds.ldssa.download;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DirectDownloadTask_Factory implements Factory<DirectDownloadTask> {
  private static final DirectDownloadTask_Factory INSTANCE = new DirectDownloadTask_Factory();

  @Override
  public DirectDownloadTask get() {
    return new DirectDownloadTask();
  }

  public static Factory<DirectDownloadTask> create() {
    return INSTANCE;
  }
}

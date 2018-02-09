package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MarkdownUtil_Factory implements Factory<MarkdownUtil> {
  private final Provider<Application> applicationProvider;

  public MarkdownUtil_Factory(Provider<Application> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  @Override
  public MarkdownUtil get() {
    return new MarkdownUtil(applicationProvider.get());
  }

  public static Factory<MarkdownUtil> create(Provider<Application> applicationProvider) {
    return new MarkdownUtil_Factory(applicationProvider);
  }
}

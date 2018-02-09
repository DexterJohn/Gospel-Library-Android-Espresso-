package org.lds.ldssa.ux.welcome;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.util.AnalyticsUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class WelcomeViewModel_Factory implements Factory<WelcomeViewModel> {
  private final Provider<AnalyticsUtil> arg0Provider;

  public WelcomeViewModel_Factory(Provider<AnalyticsUtil> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public WelcomeViewModel get() {
    return new WelcomeViewModel(arg0Provider.get());
  }

  public static Factory<WelcomeViewModel> create(Provider<AnalyticsUtil> arg0Provider) {
    return new WelcomeViewModel_Factory(arg0Provider);
  }
}

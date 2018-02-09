package org.lds.ldssa.ux.tips.pages;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.util.AnalyticsUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipsPagerViewModel_Factory implements Factory<TipsPagerViewModel> {
  private final Provider<AnalyticsUtil> arg0Provider;

  public TipsPagerViewModel_Factory(Provider<AnalyticsUtil> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public TipsPagerViewModel get() {
    return new TipsPagerViewModel(arg0Provider.get());
  }

  public static Factory<TipsPagerViewModel> create(Provider<AnalyticsUtil> arg0Provider) {
    return new TipsPagerViewModel_Factory(arg0Provider);
  }
}

package org.lds.ldssa.ux.tips.pages.tip;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.gl.tipviewed.TipViewedManager;
import org.lds.ldssa.model.database.tips.tip.TipManager;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipViewModel_Factory implements Factory<TipViewModel> {
  private final Provider<TipManager> arg0Provider;

  private final Provider<TipViewedManager> arg1Provider;

  private final Provider<CoroutineContextProvider> arg2Provider;

  public TipViewModel_Factory(
      Provider<TipManager> arg0Provider,
      Provider<TipViewedManager> arg1Provider,
      Provider<CoroutineContextProvider> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public TipViewModel get() {
    return new TipViewModel(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<TipViewModel> create(
      Provider<TipManager> arg0Provider,
      Provider<TipViewedManager> arg1Provider,
      Provider<CoroutineContextProvider> arg2Provider) {
    return new TipViewModel_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.tips.tip.TipManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.webservice.tips.TipsService;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipsUtil_Factory implements Factory<TipsUtil> {
  private final Provider<Prefs> arg0Provider;

  private final Provider<TipsService> arg1Provider;

  private final Provider<TipManager> arg2Provider;

  public TipsUtil_Factory(
      Provider<Prefs> arg0Provider,
      Provider<TipsService> arg1Provider,
      Provider<TipManager> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public TipsUtil get() {
    return new TipsUtil(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<TipsUtil> create(
      Provider<Prefs> arg0Provider,
      Provider<TipsService> arg1Provider,
      Provider<TipManager> arg2Provider) {
    return new TipsUtil_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

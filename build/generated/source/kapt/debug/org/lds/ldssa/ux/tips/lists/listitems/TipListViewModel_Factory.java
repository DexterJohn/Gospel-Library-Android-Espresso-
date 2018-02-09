package org.lds.ldssa.ux.tips.lists.listitems;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.tips.tipquery.TipQueryManager;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipListViewModel_Factory implements Factory<TipListViewModel> {
  private final Provider<CoroutineContextProvider> arg0Provider;

  private final Provider<LanguageUtil> arg1Provider;

  private final Provider<TipQueryManager> arg2Provider;

  public TipListViewModel_Factory(
      Provider<CoroutineContextProvider> arg0Provider,
      Provider<LanguageUtil> arg1Provider,
      Provider<TipQueryManager> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public TipListViewModel get() {
    return new TipListViewModel(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<TipListViewModel> create(
      Provider<CoroutineContextProvider> arg0Provider,
      Provider<LanguageUtil> arg1Provider,
      Provider<TipQueryManager> arg2Provider) {
    return new TipListViewModel_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

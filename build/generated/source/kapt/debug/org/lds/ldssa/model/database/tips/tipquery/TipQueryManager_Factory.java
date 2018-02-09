package org.lds.ldssa.model.database.tips.tipquery;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.gl.tipviewed.TipViewedManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipQueryManager_Factory implements Factory<TipQueryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<LanguageManager> arg1Provider;

  private final Provider<TipViewedManager> arg2Provider;

  public TipQueryManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<LanguageManager> arg1Provider,
      Provider<TipViewedManager> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public TipQueryManager get() {
    return new TipQueryManager(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<TipQueryManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<LanguageManager> arg1Provider,
      Provider<TipViewedManager> arg2Provider) {
    return new TipQueryManager_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

package org.lds.ldssa.inject;

import android.accounts.AccountManager;
import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideAccountManagerFactory implements Factory<AccountManager> {
  private final AppModule module;

  private final Provider<Application> arg0Provider;

  public AppModule_ProvideAccountManagerFactory(
      AppModule module, Provider<Application> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public AccountManager get() {
    return Preconditions.checkNotNull(
        module.provideAccountManager(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<AccountManager> create(
      AppModule module, Provider<Application> arg0Provider) {
    return new AppModule_ProvideAccountManagerFactory(module, arg0Provider);
  }
}

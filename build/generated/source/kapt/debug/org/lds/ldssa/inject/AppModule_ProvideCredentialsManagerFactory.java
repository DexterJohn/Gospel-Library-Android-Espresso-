package org.lds.ldssa.inject;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.CredentialsManager;
import org.lds.ldsaccount.LDSAccountPrefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideCredentialsManagerFactory
    implements Factory<CredentialsManager> {
  private final AppModule module;

  private final Provider<LDSAccountPrefs> arg0Provider;

  public AppModule_ProvideCredentialsManagerFactory(
      AppModule module, Provider<LDSAccountPrefs> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public CredentialsManager get() {
    return Preconditions.checkNotNull(
        module.provideCredentialsManager(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<CredentialsManager> create(
      AppModule module, Provider<LDSAccountPrefs> arg0Provider) {
    return new AppModule_ProvideCredentialsManagerFactory(module, arg0Provider);
  }
}

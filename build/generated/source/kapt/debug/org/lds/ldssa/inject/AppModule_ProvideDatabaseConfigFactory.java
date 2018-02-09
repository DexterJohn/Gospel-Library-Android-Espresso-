package org.lds.ldssa.inject;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.dbtools.android.domain.config.DatabaseConfig;
import org.lds.ldssa.util.GLFileUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideDatabaseConfigFactory implements Factory<DatabaseConfig> {
  private final AppModule module;

  private final Provider<GLFileUtil> arg0Provider;

  public AppModule_ProvideDatabaseConfigFactory(
      AppModule module, Provider<GLFileUtil> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public DatabaseConfig get() {
    return Preconditions.checkNotNull(
        module.provideDatabaseConfig(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<DatabaseConfig> create(
      AppModule module, Provider<GLFileUtil> arg0Provider) {
    return new AppModule_ProvideDatabaseConfigFactory(module, arg0Provider);
  }
}

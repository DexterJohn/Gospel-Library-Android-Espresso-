package org.lds.ldssa.inject;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideEventBusFactory implements Factory<Bus> {
  private final AppModule module;

  public AppModule_ProvideEventBusFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public Bus get() {
    return Preconditions.checkNotNull(
        module.provideEventBus(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Bus> create(AppModule module) {
    return new AppModule_ProvideEventBusFactory(module);
  }
}

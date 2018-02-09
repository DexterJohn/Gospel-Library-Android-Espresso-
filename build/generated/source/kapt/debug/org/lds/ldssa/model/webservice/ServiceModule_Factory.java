package org.lds.ldssa.model.webservice;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ServiceModule_Factory implements Factory<ServiceModule> {
  private static final ServiceModule_Factory INSTANCE = new ServiceModule_Factory();

  @Override
  public ServiceModule get() {
    return new ServiceModule();
  }

  public static Factory<ServiceModule> create() {
    return INSTANCE;
  }
}

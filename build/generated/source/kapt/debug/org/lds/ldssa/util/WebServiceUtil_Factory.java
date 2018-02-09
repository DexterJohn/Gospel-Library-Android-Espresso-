package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class WebServiceUtil_Factory implements Factory<WebServiceUtil> {
  private static final WebServiceUtil_Factory INSTANCE = new WebServiceUtil_Factory();

  @Override
  public WebServiceUtil get() {
    return new WebServiceUtil();
  }

  public static Factory<WebServiceUtil> create() {
    return INSTANCE;
  }
}

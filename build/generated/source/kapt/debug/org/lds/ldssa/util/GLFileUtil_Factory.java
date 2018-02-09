package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.mobile.util.LdsZipUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GLFileUtil_Factory implements Factory<GLFileUtil> {
  private final Provider<Application> arg0Provider;

  private final Provider<LdsZipUtil> arg1Provider;

  private final Provider<ToastUtil> arg2Provider;

  public GLFileUtil_Factory(
      Provider<Application> arg0Provider,
      Provider<LdsZipUtil> arg1Provider,
      Provider<ToastUtil> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public GLFileUtil get() {
    return new GLFileUtil(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<GLFileUtil> create(
      Provider<Application> arg0Provider,
      Provider<LdsZipUtil> arg1Provider,
      Provider<ToastUtil> arg2Provider) {
    return new GLFileUtil_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

package org.lds.ldssa.media.exomedia.helper;

import android.content.Context;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.mobile.media.cast.CastManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GLCastApi_Factory implements Factory<GLCastApi> {
  private final Provider<Context> arg0Provider;

  private final Provider<CastManager> arg1Provider;

  public GLCastApi_Factory(Provider<Context> arg0Provider, Provider<CastManager> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public GLCastApi get() {
    return new GLCastApi(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<GLCastApi> create(
      Provider<Context> arg0Provider, Provider<CastManager> arg1Provider) {
    return new GLCastApi_Factory(arg0Provider, arg1Provider);
  }
}

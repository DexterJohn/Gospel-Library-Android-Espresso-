package org.lds.ldssa.ui.web;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.mobile.util.LdsUiUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentJsInterface_Factory implements Factory<ContentJsInterface> {
  private final Provider<LdsUiUtil> arg0Provider;

  private final Provider<Gson> arg1Provider;

  public ContentJsInterface_Factory(Provider<LdsUiUtil> arg0Provider, Provider<Gson> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public ContentJsInterface get() {
    return new ContentJsInterface(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<ContentJsInterface> create(
      Provider<LdsUiUtil> arg0Provider, Provider<Gson> arg1Provider) {
    return new ContentJsInterface_Factory(arg0Provider, arg1Provider);
  }
}

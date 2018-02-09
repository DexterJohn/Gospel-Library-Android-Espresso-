package org.lds.ldssa.inject;

import android.app.Application;
import android.view.inputmethod.InputMethodManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideInputMethodManagerFactory
    implements Factory<InputMethodManager> {
  private final AppModule module;

  private final Provider<Application> arg0Provider;

  public AppModule_ProvideInputMethodManagerFactory(
      AppModule module, Provider<Application> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public InputMethodManager get() {
    return Preconditions.checkNotNull(
        module.provideInputMethodManager(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<InputMethodManager> create(
      AppModule module, Provider<Application> arg0Provider) {
    return new AppModule_ProvideInputMethodManagerFactory(module, arg0Provider);
  }
}

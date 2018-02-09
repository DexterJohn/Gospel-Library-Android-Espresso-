package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ThemeUtil_Factory implements Factory<ThemeUtil> {
  private final Provider<Prefs> arg0Provider;

  public ThemeUtil_Factory(Provider<Prefs> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public ThemeUtil get() {
    return new ThemeUtil(arg0Provider.get());
  }

  public static Factory<ThemeUtil> create(Provider<Prefs> arg0Provider) {
    return new ThemeUtil_Factory(arg0Provider);
  }
}

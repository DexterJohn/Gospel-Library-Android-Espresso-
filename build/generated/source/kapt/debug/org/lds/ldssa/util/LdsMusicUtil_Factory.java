package org.lds.ldssa.util;

import android.app.Application;
import android.content.pm.PackageManager;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LdsMusicUtil_Factory implements Factory<LdsMusicUtil> {
  private final Provider<Application> arg0Provider;

  private final Provider<PackageManager> arg1Provider;

  private final Provider<SubItemManager> arg2Provider;

  public LdsMusicUtil_Factory(
      Provider<Application> arg0Provider,
      Provider<PackageManager> arg1Provider,
      Provider<SubItemManager> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public LdsMusicUtil get() {
    return new LdsMusicUtil(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<LdsMusicUtil> create(
      Provider<Application> arg0Provider,
      Provider<PackageManager> arg1Provider,
      Provider<SubItemManager> arg2Provider) {
    return new LdsMusicUtil_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}

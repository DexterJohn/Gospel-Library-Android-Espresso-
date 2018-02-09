package org.lds.ldssa.intent;

import android.content.pm.PackageManager;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.util.LdsMusicUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ExternalIntents_Factory implements Factory<ExternalIntents> {
  private final Provider<PackageManager> arg0Provider;

  private final Provider<LdsMusicUtil> arg1Provider;

  public ExternalIntents_Factory(
      Provider<PackageManager> arg0Provider, Provider<LdsMusicUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public ExternalIntents get() {
    return new ExternalIntents(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<ExternalIntents> create(
      Provider<PackageManager> arg0Provider, Provider<LdsMusicUtil> arg1Provider) {
    return new ExternalIntents_Factory(arg0Provider, arg1Provider);
  }
}

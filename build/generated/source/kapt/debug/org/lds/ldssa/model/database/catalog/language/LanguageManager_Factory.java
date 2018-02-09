package org.lds.ldssa.model.database.catalog.language;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LanguageManager_Factory implements Factory<LanguageManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public LanguageManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public LanguageManager get() {
    return new LanguageManager(arg0Provider.get());
  }

  public static Factory<LanguageManager> create(Provider<DatabaseManager> arg0Provider) {
    return new LanguageManager_Factory(arg0Provider);
  }
}

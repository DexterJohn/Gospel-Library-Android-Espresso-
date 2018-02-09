package org.lds.ldssa.model.database.catalog.languagename;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LanguageNameManager_Factory implements Factory<LanguageNameManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public LanguageNameManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public LanguageNameManager get() {
    return new LanguageNameManager(arg0Provider.get());
  }

  public static Factory<LanguageNameManager> create(Provider<DatabaseManager> arg0Provider) {
    return new LanguageNameManager_Factory(arg0Provider);
  }
}

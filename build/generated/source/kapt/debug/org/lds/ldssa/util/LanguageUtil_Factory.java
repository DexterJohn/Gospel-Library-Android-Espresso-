package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LanguageUtil_Factory implements Factory<LanguageUtil> {
  private final Provider<LanguageManager> languageManagerProvider;

  public LanguageUtil_Factory(Provider<LanguageManager> languageManagerProvider) {
    this.languageManagerProvider = languageManagerProvider;
  }

  @Override
  public LanguageUtil get() {
    return new LanguageUtil(languageManagerProvider.get());
  }

  public static Factory<LanguageUtil> create(Provider<LanguageManager> languageManagerProvider) {
    return new LanguageUtil_Factory(languageManagerProvider);
  }
}

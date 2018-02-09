package org.lds.ldssa.ui.menu;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CatalogDirectoryMenu_Factory implements Factory<CatalogDirectoryMenu> {
  private final Provider<CommonMenu> commonMenuProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<LanguageManager> languageManagerProvider;

  public CatalogDirectoryMenu_Factory(
      Provider<CommonMenu> commonMenuProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<LanguageManager> languageManagerProvider) {
    this.commonMenuProvider = commonMenuProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.languageManagerProvider = languageManagerProvider;
  }

  @Override
  public CatalogDirectoryMenu get() {
    return new CatalogDirectoryMenu(
        commonMenuProvider.get(), internalIntentsProvider.get(), languageManagerProvider.get());
  }

  public static Factory<CatalogDirectoryMenu> create(
      Provider<CommonMenu> commonMenuProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<LanguageManager> languageManagerProvider) {
    return new CatalogDirectoryMenu_Factory(
        commonMenuProvider, internalIntentsProvider, languageManagerProvider);
  }
}

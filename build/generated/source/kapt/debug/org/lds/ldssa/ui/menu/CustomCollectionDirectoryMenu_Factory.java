package org.lds.ldssa.ui.menu;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.util.CustomCollectionUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CustomCollectionDirectoryMenu_Factory
    implements Factory<CustomCollectionDirectoryMenu> {
  private final Provider<CommonMenu> commonMenuProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<CustomCollectionUtil> customCollectionUtilProvider;

  public CustomCollectionDirectoryMenu_Factory(
      Provider<CommonMenu> commonMenuProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<CustomCollectionUtil> customCollectionUtilProvider) {
    this.commonMenuProvider = commonMenuProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.customCollectionUtilProvider = customCollectionUtilProvider;
  }

  @Override
  public CustomCollectionDirectoryMenu get() {
    return new CustomCollectionDirectoryMenu(
        commonMenuProvider.get(),
        internalIntentsProvider.get(),
        customCollectionUtilProvider.get());
  }

  public static Factory<CustomCollectionDirectoryMenu> create(
      Provider<CommonMenu> commonMenuProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<CustomCollectionUtil> customCollectionUtilProvider) {
    return new CustomCollectionDirectoryMenu_Factory(
        commonMenuProvider, internalIntentsProvider, customCollectionUtilProvider);
  }
}

package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionManager;
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CustomCollectionUtil_Factory implements Factory<CustomCollectionUtil> {
  private final Provider<Application> applicationProvider;

  private final Provider<CustomCollectionManager> customCollectionManagerProvider;

  private final Provider<CustomCollectionItemManager> customCollectionItemManagerProvider;

  public CustomCollectionUtil_Factory(
      Provider<Application> applicationProvider,
      Provider<CustomCollectionManager> customCollectionManagerProvider,
      Provider<CustomCollectionItemManager> customCollectionItemManagerProvider) {
    this.applicationProvider = applicationProvider;
    this.customCollectionManagerProvider = customCollectionManagerProvider;
    this.customCollectionItemManagerProvider = customCollectionItemManagerProvider;
  }

  @Override
  public CustomCollectionUtil get() {
    return new CustomCollectionUtil(
        applicationProvider.get(),
        customCollectionManagerProvider.get(),
        customCollectionItemManagerProvider.get());
  }

  public static Factory<CustomCollectionUtil> create(
      Provider<Application> applicationProvider,
      Provider<CustomCollectionManager> customCollectionManagerProvider,
      Provider<CustomCollectionItemManager> customCollectionItemManagerProvider) {
    return new CustomCollectionUtil_Factory(
        applicationProvider, customCollectionManagerProvider, customCollectionItemManagerProvider);
  }
}

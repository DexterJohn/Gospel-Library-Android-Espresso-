package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.webservice.catalog.CatalogService;
import org.lds.ldssa.model.webservice.catalog.RoleCatalogService;
import org.lds.ldssa.model.webservice.rolecontent.RoleBasedContentService;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CatalogUtil_Factory implements Factory<CatalogUtil> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<CatalogService> catalogServiceProvider;

  private final Provider<RoleCatalogService> roleCatalogServiceProvider;

  private final Provider<RoleBasedContentService> roleBasedContentServiceProvider;

  public CatalogUtil_Factory(
      Provider<Prefs> prefsProvider,
      Provider<CatalogService> catalogServiceProvider,
      Provider<RoleCatalogService> roleCatalogServiceProvider,
      Provider<RoleBasedContentService> roleBasedContentServiceProvider) {
    this.prefsProvider = prefsProvider;
    this.catalogServiceProvider = catalogServiceProvider;
    this.roleCatalogServiceProvider = roleCatalogServiceProvider;
    this.roleBasedContentServiceProvider = roleBasedContentServiceProvider;
  }

  @Override
  public CatalogUtil get() {
    return new CatalogUtil(
        prefsProvider.get(),
        catalogServiceProvider.get(),
        roleCatalogServiceProvider.get(),
        roleBasedContentServiceProvider.get());
  }

  public static Factory<CatalogUtil> create(
      Provider<Prefs> prefsProvider,
      Provider<CatalogService> catalogServiceProvider,
      Provider<RoleCatalogService> roleCatalogServiceProvider,
      Provider<RoleBasedContentService> roleBasedContentServiceProvider) {
    return new CatalogUtil_Factory(
        prefsProvider,
        catalogServiceProvider,
        roleCatalogServiceProvider,
        roleBasedContentServiceProvider);
  }
}

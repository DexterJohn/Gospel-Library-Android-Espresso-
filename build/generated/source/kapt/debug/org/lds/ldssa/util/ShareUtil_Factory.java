package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ShareUtil_Factory implements Factory<ShareUtil> {
  private final Provider<SubItemManager> arg0Provider;

  private final Provider<SubItemMetadataManager> arg1Provider;

  private final Provider<Analytics> arg2Provider;

  private final Provider<AnalyticsUtil> arg3Provider;

  private final Provider<UriUtil> arg4Provider;

  private final Provider<AnnotationUiUtil> arg5Provider;

  private final Provider<ContentItemUtil> arg6Provider;

  public ShareUtil_Factory(
      Provider<SubItemManager> arg0Provider,
      Provider<SubItemMetadataManager> arg1Provider,
      Provider<Analytics> arg2Provider,
      Provider<AnalyticsUtil> arg3Provider,
      Provider<UriUtil> arg4Provider,
      Provider<AnnotationUiUtil> arg5Provider,
      Provider<ContentItemUtil> arg6Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
  }

  @Override
  public ShareUtil get() {
    return new ShareUtil(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get());
  }

  public static Factory<ShareUtil> create(
      Provider<SubItemManager> arg0Provider,
      Provider<SubItemMetadataManager> arg1Provider,
      Provider<Analytics> arg2Provider,
      Provider<AnalyticsUtil> arg3Provider,
      Provider<UriUtil> arg4Provider,
      Provider<AnnotationUiUtil> arg5Provider,
      Provider<ContentItemUtil> arg6Provider) {
    return new ShareUtil_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider);
  }
}

package org.lds.ldssa.util.annotations;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.link.LinkManager;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.CitationUtil;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LinkUtil_Factory implements Factory<LinkUtil> {
  private final Provider<LinkManager> arg0Provider;

  private final Provider<AnnotationManager> arg1Provider;

  private final Provider<ItemManager> arg2Provider;

  private final Provider<SubItemMetadataManager> arg3Provider;

  private final Provider<AnalyticsUtil> arg4Provider;

  private final Provider<CitationUtil> arg5Provider;

  private final Provider<ContentItemUtil> arg6Provider;

  public LinkUtil_Factory(
      Provider<LinkManager> arg0Provider,
      Provider<AnnotationManager> arg1Provider,
      Provider<ItemManager> arg2Provider,
      Provider<SubItemMetadataManager> arg3Provider,
      Provider<AnalyticsUtil> arg4Provider,
      Provider<CitationUtil> arg5Provider,
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
  public LinkUtil get() {
    return new LinkUtil(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get());
  }

  public static Factory<LinkUtil> create(
      Provider<LinkManager> arg0Provider,
      Provider<AnnotationManager> arg1Provider,
      Provider<ItemManager> arg2Provider,
      Provider<SubItemMetadataManager> arg3Provider,
      Provider<AnalyticsUtil> arg4Provider,
      Provider<CitationUtil> arg5Provider,
      Provider<ContentItemUtil> arg6Provider) {
    return new LinkUtil_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider);
  }
}

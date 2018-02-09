package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.util.annotations.HighlightUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationUiUtil_Factory implements Factory<AnnotationUiUtil> {
  private final Provider<CoroutineContextProvider> arg0Provider;

  private final Provider<HighlightUtil> arg1Provider;

  private final Provider<ItemManager> arg2Provider;

  private final Provider<CitationUtil> arg3Provider;

  private final Provider<InternalIntents> arg4Provider;

  private final Provider<SubItemManager> arg5Provider;

  private final Provider<SubItemMetadataManager> arg6Provider;

  private final Provider<MarkdownUtil> arg7Provider;

  private final Provider<Application> arg8Provider;

  private final Provider<ContentItemUtil> arg9Provider;

  public AnnotationUiUtil_Factory(
      Provider<CoroutineContextProvider> arg0Provider,
      Provider<HighlightUtil> arg1Provider,
      Provider<ItemManager> arg2Provider,
      Provider<CitationUtil> arg3Provider,
      Provider<InternalIntents> arg4Provider,
      Provider<SubItemManager> arg5Provider,
      Provider<SubItemMetadataManager> arg6Provider,
      Provider<MarkdownUtil> arg7Provider,
      Provider<Application> arg8Provider,
      Provider<ContentItemUtil> arg9Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
    this.arg7Provider = arg7Provider;
    this.arg8Provider = arg8Provider;
    this.arg9Provider = arg9Provider;
  }

  @Override
  public AnnotationUiUtil get() {
    return new AnnotationUiUtil(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get(),
        arg7Provider.get(),
        arg8Provider.get(),
        arg9Provider.get());
  }

  public static Factory<AnnotationUiUtil> create(
      Provider<CoroutineContextProvider> arg0Provider,
      Provider<HighlightUtil> arg1Provider,
      Provider<ItemManager> arg2Provider,
      Provider<CitationUtil> arg3Provider,
      Provider<InternalIntents> arg4Provider,
      Provider<SubItemManager> arg5Provider,
      Provider<SubItemMetadataManager> arg6Provider,
      Provider<MarkdownUtil> arg7Provider,
      Provider<Application> arg8Provider,
      Provider<ContentItemUtil> arg9Provider) {
    return new AnnotationUiUtil_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider,
        arg7Provider,
        arg8Provider,
        arg9Provider);
  }
}

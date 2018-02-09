package org.lds.ldssa.intent;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.download.CheckContentVersionsTask;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionManager;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager;
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.ContentItemUtil;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ToastUtil;
import org.lds.ldssa.util.UriUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class InternalIntents_Factory implements Factory<InternalIntents> {
  private final Provider<Application> arg0Provider;

  private final Provider<ScreenUtil> arg1Provider;

  private final Provider<Prefs> arg2Provider;

  private final Provider<LanguageManager> arg3Provider;

  private final Provider<LanguageUtil> arg4Provider;

  private final Provider<AnnotationManager> arg5Provider;

  private final Provider<BookmarkManager> arg6Provider;

  private final Provider<SubItemMetadataManager> arg7Provider;

  private final Provider<NavCollectionManager> arg8Provider;

  private final Provider<DownloadQueueItemManager> arg9Provider;

  private final Provider<ContentItemUtil> arg10Provider;

  private final Provider<GLDownloadManager> arg11Provider;

  private final Provider<ItemManager> arg12Provider;

  private final Provider<SubItemManager> arg13Provider;

  private final Provider<UriUtil> arg14Provider;

  private final Provider<ContentMetaDataManager> arg15Provider;

  private final Provider<CheckContentVersionsTask> arg16Provider;

  private final Provider<LibraryCollectionManager> arg17Provider;

  private final Provider<ToastUtil> arg18Provider;

  public InternalIntents_Factory(
      Provider<Application> arg0Provider,
      Provider<ScreenUtil> arg1Provider,
      Provider<Prefs> arg2Provider,
      Provider<LanguageManager> arg3Provider,
      Provider<LanguageUtil> arg4Provider,
      Provider<AnnotationManager> arg5Provider,
      Provider<BookmarkManager> arg6Provider,
      Provider<SubItemMetadataManager> arg7Provider,
      Provider<NavCollectionManager> arg8Provider,
      Provider<DownloadQueueItemManager> arg9Provider,
      Provider<ContentItemUtil> arg10Provider,
      Provider<GLDownloadManager> arg11Provider,
      Provider<ItemManager> arg12Provider,
      Provider<SubItemManager> arg13Provider,
      Provider<UriUtil> arg14Provider,
      Provider<ContentMetaDataManager> arg15Provider,
      Provider<CheckContentVersionsTask> arg16Provider,
      Provider<LibraryCollectionManager> arg17Provider,
      Provider<ToastUtil> arg18Provider) {
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
    this.arg10Provider = arg10Provider;
    this.arg11Provider = arg11Provider;
    this.arg12Provider = arg12Provider;
    this.arg13Provider = arg13Provider;
    this.arg14Provider = arg14Provider;
    this.arg15Provider = arg15Provider;
    this.arg16Provider = arg16Provider;
    this.arg17Provider = arg17Provider;
    this.arg18Provider = arg18Provider;
  }

  @Override
  public InternalIntents get() {
    return new InternalIntents(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get(),
        arg7Provider.get(),
        arg8Provider.get(),
        arg9Provider.get(),
        arg10Provider.get(),
        arg11Provider.get(),
        arg12Provider.get(),
        arg13Provider.get(),
        arg14Provider.get(),
        arg15Provider.get(),
        arg16Provider,
        arg17Provider.get(),
        arg18Provider.get());
  }

  public static Factory<InternalIntents> create(
      Provider<Application> arg0Provider,
      Provider<ScreenUtil> arg1Provider,
      Provider<Prefs> arg2Provider,
      Provider<LanguageManager> arg3Provider,
      Provider<LanguageUtil> arg4Provider,
      Provider<AnnotationManager> arg5Provider,
      Provider<BookmarkManager> arg6Provider,
      Provider<SubItemMetadataManager> arg7Provider,
      Provider<NavCollectionManager> arg8Provider,
      Provider<DownloadQueueItemManager> arg9Provider,
      Provider<ContentItemUtil> arg10Provider,
      Provider<GLDownloadManager> arg11Provider,
      Provider<ItemManager> arg12Provider,
      Provider<SubItemManager> arg13Provider,
      Provider<UriUtil> arg14Provider,
      Provider<ContentMetaDataManager> arg15Provider,
      Provider<CheckContentVersionsTask> arg16Provider,
      Provider<LibraryCollectionManager> arg17Provider,
      Provider<ToastUtil> arg18Provider) {
    return new InternalIntents_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider,
        arg7Provider,
        arg8Provider,
        arg9Provider,
        arg10Provider,
        arg11Provider,
        arg12Provider,
        arg13Provider,
        arg14Provider,
        arg15Provider,
        arg16Provider,
        arg17Provider,
        arg18Provider);
  }
}

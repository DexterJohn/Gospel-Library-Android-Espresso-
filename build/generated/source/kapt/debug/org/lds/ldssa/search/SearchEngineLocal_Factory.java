package org.lds.ldssa.search;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.allitemsincollectionquery.AllItemsInCollectionQueryManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.itemcollectionview.ItemCollectionViewManager;
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionManager;
import org.lds.ldssa.model.database.content.subitemsearchquery.SubItemSearchQueryManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.search.searchcollection.SearchCollectionManager;
import org.lds.ldssa.model.database.search.searchcontentcollectionmap.SearchContentCollectionMapManager;
import org.lds.ldssa.model.database.search.searchcountallnotes.SearchCountAllNotesManager;
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContentManager;
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNoteManager;
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitemManager;
import org.lds.ldssa.model.database.userdata.notesearchquery.NoteSearchQueryManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.mobile.util.LdsTimeUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchEngineLocal_Factory implements Factory<SearchEngineLocal> {
  private final Provider<Bus> arg0Provider;

  private final Provider<Prefs> arg1Provider;

  private final Provider<LdsTimeUtil> arg2Provider;

  private final Provider<DownloadedItemManager> arg3Provider;

  private final Provider<ItemCollectionViewManager> arg4Provider;

  private final Provider<NoteSearchQueryManager> arg5Provider;

  private final Provider<SubItemSearchQueryManager> arg6Provider;

  private final Provider<SearchCountContentManager> arg7Provider;

  private final Provider<SearchCollectionManager> arg8Provider;

  private final Provider<LibraryCollectionManager> arg9Provider;

  private final Provider<SearchContentCollectionMapManager> arg10Provider;

  private final Provider<SearchPreviewSubitemManager> arg11Provider;

  private final Provider<SearchCountAllNotesManager> arg12Provider;

  private final Provider<SearchPreviewNoteManager> arg13Provider;

  private final Provider<AllItemsInCollectionQueryManager> arg14Provider;

  private final Provider<ItemManager> arg15Provider;

  private final Provider<SearchUtil> arg16Provider;

  public SearchEngineLocal_Factory(
      Provider<Bus> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<LdsTimeUtil> arg2Provider,
      Provider<DownloadedItemManager> arg3Provider,
      Provider<ItemCollectionViewManager> arg4Provider,
      Provider<NoteSearchQueryManager> arg5Provider,
      Provider<SubItemSearchQueryManager> arg6Provider,
      Provider<SearchCountContentManager> arg7Provider,
      Provider<SearchCollectionManager> arg8Provider,
      Provider<LibraryCollectionManager> arg9Provider,
      Provider<SearchContentCollectionMapManager> arg10Provider,
      Provider<SearchPreviewSubitemManager> arg11Provider,
      Provider<SearchCountAllNotesManager> arg12Provider,
      Provider<SearchPreviewNoteManager> arg13Provider,
      Provider<AllItemsInCollectionQueryManager> arg14Provider,
      Provider<ItemManager> arg15Provider,
      Provider<SearchUtil> arg16Provider) {
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
  }

  @Override
  public SearchEngineLocal get() {
    return new SearchEngineLocal(
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
        arg16Provider.get());
  }

  public static Factory<SearchEngineLocal> create(
      Provider<Bus> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<LdsTimeUtil> arg2Provider,
      Provider<DownloadedItemManager> arg3Provider,
      Provider<ItemCollectionViewManager> arg4Provider,
      Provider<NoteSearchQueryManager> arg5Provider,
      Provider<SubItemSearchQueryManager> arg6Provider,
      Provider<SearchCountContentManager> arg7Provider,
      Provider<SearchCollectionManager> arg8Provider,
      Provider<LibraryCollectionManager> arg9Provider,
      Provider<SearchContentCollectionMapManager> arg10Provider,
      Provider<SearchPreviewSubitemManager> arg11Provider,
      Provider<SearchCountAllNotesManager> arg12Provider,
      Provider<SearchPreviewNoteManager> arg13Provider,
      Provider<AllItemsInCollectionQueryManager> arg14Provider,
      Provider<ItemManager> arg15Provider,
      Provider<SearchUtil> arg16Provider) {
    return new SearchEngineLocal_Factory(
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
        arg16Provider);
  }
}

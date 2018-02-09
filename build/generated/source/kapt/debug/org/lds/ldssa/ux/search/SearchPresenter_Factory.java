package org.lds.ldssa.ux.search;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.content.navitem.NavItemManager;
import org.lds.ldssa.model.database.content.navsection.NavSectionManager;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.database.content.subitemsearchquery.SubItemSearchQueryManager;
import org.lds.ldssa.model.database.search.searchallcount.SearchAllCountManager;
import org.lds.ldssa.model.database.search.searchcollection.SearchCollectionManager;
import org.lds.ldssa.model.database.search.searchcountallnotes.SearchCountAllNotesManager;
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContentManager;
import org.lds.ldssa.model.database.search.searchhistory.SearchHistoryManager;
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNoteManager;
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitemManager;
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestionManager;
import org.lds.ldssa.search.SearchUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchPresenter_Factory implements Factory<SearchPresenter> {
  private final Provider<Bus> arg0Provider;

  private final Provider<Analytics> arg1Provider;

  private final Provider<CoroutineContextProvider> arg2Provider;

  private final Provider<SearchUtil> arg3Provider;

  private final Provider<ItemManager> arg4Provider;

  private final Provider<SearchHistoryManager> arg5Provider;

  private final Provider<NavSectionManager> arg6Provider;

  private final Provider<NavItemManager> arg7Provider;

  private final Provider<ParagraphMetadataManager> arg8Provider;

  private final Provider<SearchPreviewSubitemManager> arg9Provider;

  private final Provider<SubItemSearchQueryManager> arg10Provider;

  private final Provider<SearchPreviewNoteManager> arg11Provider;

  private final Provider<SearchSuggestionManager> arg12Provider;

  private final Provider<SearchAllCountManager> arg13Provider;

  private final Provider<SearchCountContentManager> arg14Provider;

  private final Provider<SearchCollectionManager> arg15Provider;

  private final Provider<SearchCountAllNotesManager> arg16Provider;

  public SearchPresenter_Factory(
      Provider<Bus> arg0Provider,
      Provider<Analytics> arg1Provider,
      Provider<CoroutineContextProvider> arg2Provider,
      Provider<SearchUtil> arg3Provider,
      Provider<ItemManager> arg4Provider,
      Provider<SearchHistoryManager> arg5Provider,
      Provider<NavSectionManager> arg6Provider,
      Provider<NavItemManager> arg7Provider,
      Provider<ParagraphMetadataManager> arg8Provider,
      Provider<SearchPreviewSubitemManager> arg9Provider,
      Provider<SubItemSearchQueryManager> arg10Provider,
      Provider<SearchPreviewNoteManager> arg11Provider,
      Provider<SearchSuggestionManager> arg12Provider,
      Provider<SearchAllCountManager> arg13Provider,
      Provider<SearchCountContentManager> arg14Provider,
      Provider<SearchCollectionManager> arg15Provider,
      Provider<SearchCountAllNotesManager> arg16Provider) {
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
  public SearchPresenter get() {
    return new SearchPresenter(
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

  public static Factory<SearchPresenter> create(
      Provider<Bus> arg0Provider,
      Provider<Analytics> arg1Provider,
      Provider<CoroutineContextProvider> arg2Provider,
      Provider<SearchUtil> arg3Provider,
      Provider<ItemManager> arg4Provider,
      Provider<SearchHistoryManager> arg5Provider,
      Provider<NavSectionManager> arg6Provider,
      Provider<NavItemManager> arg7Provider,
      Provider<ParagraphMetadataManager> arg8Provider,
      Provider<SearchPreviewSubitemManager> arg9Provider,
      Provider<SubItemSearchQueryManager> arg10Provider,
      Provider<SearchPreviewNoteManager> arg11Provider,
      Provider<SearchSuggestionManager> arg12Provider,
      Provider<SearchAllCountManager> arg13Provider,
      Provider<SearchCountContentManager> arg14Provider,
      Provider<SearchCollectionManager> arg15Provider,
      Provider<SearchCountAllNotesManager> arg16Provider) {
    return new SearchPresenter_Factory(
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

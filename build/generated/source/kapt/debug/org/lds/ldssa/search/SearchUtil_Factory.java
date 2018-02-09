package org.lds.ldssa.search;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.stopword.StopWordManager;
import org.lds.ldssa.model.database.search.searchcollection.SearchCollectionManager;
import org.lds.ldssa.model.database.search.searchcontentcollectionmap.SearchContentCollectionMapManager;
import org.lds.ldssa.model.database.search.searchcountallnotes.SearchCountAllNotesManager;
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContentManager;
import org.lds.ldssa.model.database.search.searchpreviewnote.SearchPreviewNoteManager;
import org.lds.ldssa.model.database.search.searchpreviewsubitem.SearchPreviewSubitemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.LanguageUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchUtil_Factory implements Factory<SearchUtil> {
  private final Provider<Prefs> arg0Provider;

  private final Provider<LanguageUtil> arg1Provider;

  private final Provider<SearchCollectionManager> arg2Provider;

  private final Provider<SearchCountAllNotesManager> arg3Provider;

  private final Provider<SearchCountContentManager> arg4Provider;

  private final Provider<SearchContentCollectionMapManager> arg5Provider;

  private final Provider<SearchPreviewNoteManager> arg6Provider;

  private final Provider<SearchPreviewSubitemManager> arg7Provider;

  private final Provider<StopWordManager> arg8Provider;

  public SearchUtil_Factory(
      Provider<Prefs> arg0Provider,
      Provider<LanguageUtil> arg1Provider,
      Provider<SearchCollectionManager> arg2Provider,
      Provider<SearchCountAllNotesManager> arg3Provider,
      Provider<SearchCountContentManager> arg4Provider,
      Provider<SearchContentCollectionMapManager> arg5Provider,
      Provider<SearchPreviewNoteManager> arg6Provider,
      Provider<SearchPreviewSubitemManager> arg7Provider,
      Provider<StopWordManager> arg8Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
    this.arg7Provider = arg7Provider;
    this.arg8Provider = arg8Provider;
  }

  @Override
  public SearchUtil get() {
    return new SearchUtil(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get(),
        arg7Provider.get(),
        arg8Provider.get());
  }

  public static Factory<SearchUtil> create(
      Provider<Prefs> arg0Provider,
      Provider<LanguageUtil> arg1Provider,
      Provider<SearchCollectionManager> arg2Provider,
      Provider<SearchCountAllNotesManager> arg3Provider,
      Provider<SearchCountContentManager> arg4Provider,
      Provider<SearchContentCollectionMapManager> arg5Provider,
      Provider<SearchPreviewNoteManager> arg6Provider,
      Provider<SearchPreviewSubitemManager> arg7Provider,
      Provider<StopWordManager> arg8Provider) {
    return new SearchUtil_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider,
        arg7Provider,
        arg8Provider);
  }
}

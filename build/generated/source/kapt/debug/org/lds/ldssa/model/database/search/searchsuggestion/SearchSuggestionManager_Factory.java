package org.lds.ldssa.model.database.search.searchsuggestion;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionManager;
import org.lds.ldssa.model.database.catalog.searchgotoquery.SearchGotoQueryManager;
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager;
import org.lds.ldssa.model.database.userdata.note.NoteManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.search.SearchUtil;
import org.lds.ldssa.util.ContentItemUtil;
import org.lds.mobile.util.LdsTimeUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchSuggestionManager_Factory implements Factory<SearchSuggestionManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<Application> arg1Provider;

  private final Provider<SearchUtil> arg2Provider;

  private final Provider<LdsTimeUtil> arg3Provider;

  private final Provider<NotebookManager> arg4Provider;

  private final Provider<SearchGotoQueryManager> arg5Provider;

  private final Provider<NoteManager> arg6Provider;

  private final Provider<ItemManager> arg7Provider;

  private final Provider<LibraryCollectionManager> arg8Provider;

  private final Provider<LanguageManager> arg9Provider;

  private final Provider<NavCollectionManager> arg10Provider;

  private final Provider<ContentItemUtil> arg11Provider;

  public SearchSuggestionManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<Application> arg1Provider,
      Provider<SearchUtil> arg2Provider,
      Provider<LdsTimeUtil> arg3Provider,
      Provider<NotebookManager> arg4Provider,
      Provider<SearchGotoQueryManager> arg5Provider,
      Provider<NoteManager> arg6Provider,
      Provider<ItemManager> arg7Provider,
      Provider<LibraryCollectionManager> arg8Provider,
      Provider<LanguageManager> arg9Provider,
      Provider<NavCollectionManager> arg10Provider,
      Provider<ContentItemUtil> arg11Provider) {
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
  }

  @Override
  public SearchSuggestionManager get() {
    return new SearchSuggestionManager(
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
        arg11Provider.get());
  }

  public static Factory<SearchSuggestionManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<Application> arg1Provider,
      Provider<SearchUtil> arg2Provider,
      Provider<LdsTimeUtil> arg3Provider,
      Provider<NotebookManager> arg4Provider,
      Provider<SearchGotoQueryManager> arg5Provider,
      Provider<NoteManager> arg6Provider,
      Provider<ItemManager> arg7Provider,
      Provider<LibraryCollectionManager> arg8Provider,
      Provider<LanguageManager> arg9Provider,
      Provider<NavCollectionManager> arg10Provider,
      Provider<ContentItemUtil> arg11Provider) {
    return new SearchSuggestionManager_Factory(
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
        arg11Provider);
  }
}

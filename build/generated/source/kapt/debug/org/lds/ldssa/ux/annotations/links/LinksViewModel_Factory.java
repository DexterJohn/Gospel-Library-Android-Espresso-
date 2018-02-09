package org.lds.ldssa.ux.annotations.links;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.content.navsection.NavSectionManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestionManager;
import org.lds.ldssa.model.database.userdata.link.LinkManager;
import org.lds.ldssa.util.annotations.LinkUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LinksViewModel_Factory implements Factory<LinksViewModel> {
  private final Provider<LinkManager> arg0Provider;

  private final Provider<LinkUtil> arg1Provider;

  private final Provider<NavSectionManager> arg2Provider;

  private final Provider<SearchSuggestionManager> arg3Provider;

  private final Provider<SubItemManager> arg4Provider;

  private final Provider<SubItemMetadataManager> arg5Provider;

  private final Provider<CoroutineContextProvider> arg6Provider;

  public LinksViewModel_Factory(
      Provider<LinkManager> arg0Provider,
      Provider<LinkUtil> arg1Provider,
      Provider<NavSectionManager> arg2Provider,
      Provider<SearchSuggestionManager> arg3Provider,
      Provider<SubItemManager> arg4Provider,
      Provider<SubItemMetadataManager> arg5Provider,
      Provider<CoroutineContextProvider> arg6Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
  }

  @Override
  public LinksViewModel get() {
    return new LinksViewModel(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get());
  }

  public static Factory<LinksViewModel> create(
      Provider<LinkManager> arg0Provider,
      Provider<LinkUtil> arg1Provider,
      Provider<NavSectionManager> arg2Provider,
      Provider<SearchSuggestionManager> arg3Provider,
      Provider<SubItemManager> arg4Provider,
      Provider<SubItemMetadataManager> arg5Provider,
      Provider<CoroutineContextProvider> arg6Provider) {
    return new LinksViewModel_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider);
  }
}

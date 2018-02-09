package org.lds.ldssa.search;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchService_MembersInjector implements MembersInjector<SearchService> {
  private final Provider<SearchUtil> searchUtilProvider;

  private final Provider<SearchEngineLocal> searchEngineLocalProvider;

  public SearchService_MembersInjector(
      Provider<SearchUtil> searchUtilProvider,
      Provider<SearchEngineLocal> searchEngineLocalProvider) {
    this.searchUtilProvider = searchUtilProvider;
    this.searchEngineLocalProvider = searchEngineLocalProvider;
  }

  public static MembersInjector<SearchService> create(
      Provider<SearchUtil> searchUtilProvider,
      Provider<SearchEngineLocal> searchEngineLocalProvider) {
    return new SearchService_MembersInjector(searchUtilProvider, searchEngineLocalProvider);
  }

  @Override
  public void injectMembers(SearchService instance) {
    injectSearchUtil(instance, searchUtilProvider.get());
    injectSearchEngineLocal(instance, searchEngineLocalProvider.get());
  }

  public static void injectSearchUtil(SearchService instance, SearchUtil searchUtil) {
    instance.searchUtil = searchUtil;
  }

  public static void injectSearchEngineLocal(
      SearchService instance, SearchEngineLocal searchEngineLocal) {
    instance.searchEngineLocal = searchEngineLocal;
  }
}

package org.lds.ldssa.ux.annotations.tagselection;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.tag.TagManager;
import org.lds.ldssa.model.database.userdata.tagview.TagViewManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.annotations.TagUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TagSelectionViewModel_Factory implements Factory<TagSelectionViewModel> {
  private final Provider<TagManager> arg0Provider;

  private final Provider<TagViewManager> arg1Provider;

  private final Provider<TagUtil> arg2Provider;

  private final Provider<Prefs> arg3Provider;

  private final Provider<CoroutineContextProvider> arg4Provider;

  public TagSelectionViewModel_Factory(
      Provider<TagManager> arg0Provider,
      Provider<TagViewManager> arg1Provider,
      Provider<TagUtil> arg2Provider,
      Provider<Prefs> arg3Provider,
      Provider<CoroutineContextProvider> arg4Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
  }

  @Override
  public TagSelectionViewModel get() {
    return new TagSelectionViewModel(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get());
  }

  public static Factory<TagSelectionViewModel> create(
      Provider<TagManager> arg0Provider,
      Provider<TagViewManager> arg1Provider,
      Provider<TagUtil> arg2Provider,
      Provider<Prefs> arg3Provider,
      Provider<CoroutineContextProvider> arg4Provider) {
    return new TagSelectionViewModel_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider, arg4Provider);
  }
}

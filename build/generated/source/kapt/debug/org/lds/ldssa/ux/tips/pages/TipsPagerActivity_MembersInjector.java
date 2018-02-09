package org.lds.ldssa.ux.tips.pages;

import android.arch.lifecycle.ViewModelProvider;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipsPagerActivity_MembersInjector implements MembersInjector<TipsPagerActivity> {
  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public TipsPagerActivity_MembersInjector(
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<TipsPagerActivity> create(
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new TipsPagerActivity_MembersInjector(viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(TipsPagerActivity instance) {
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  public static void injectViewModelFactory(
      TipsPagerActivity instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}

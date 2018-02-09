package org.lds.ldssa.ux.study.items;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StudyItemsViewModel_Factory implements Factory<StudyItemsViewModel> {
  private final Provider<Application> arg0Provider;

  private final Provider<CoroutineContextProvider> arg1Provider;

  public StudyItemsViewModel_Factory(
      Provider<Application> arg0Provider, Provider<CoroutineContextProvider> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public StudyItemsViewModel get() {
    return new StudyItemsViewModel(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<StudyItemsViewModel> create(
      Provider<Application> arg0Provider, Provider<CoroutineContextProvider> arg1Provider) {
    return new StudyItemsViewModel_Factory(arg0Provider, arg1Provider);
  }
}

package org.lds.ldssa.ux.annotations;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationsViewModel_Factory implements Factory<AnnotationsViewModel> {
  private final Provider<AnnotationManager> arg0Provider;

  private final Provider<CoroutineContextProvider> arg1Provider;

  public AnnotationsViewModel_Factory(
      Provider<AnnotationManager> arg0Provider, Provider<CoroutineContextProvider> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public AnnotationsViewModel get() {
    return new AnnotationsViewModel(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<AnnotationsViewModel> create(
      Provider<AnnotationManager> arg0Provider, Provider<CoroutineContextProvider> arg1Provider) {
    return new AnnotationsViewModel_Factory(arg0Provider, arg1Provider);
  }
}

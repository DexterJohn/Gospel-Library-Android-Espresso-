package org.lds.ldssa.ux.annotations.allannotations;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AllAnnotationsViewModel_Factory implements Factory<AllAnnotationsViewModel> {
  private final Provider<AnnotationManager> arg0Provider;

  public AllAnnotationsViewModel_Factory(Provider<AnnotationManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public AllAnnotationsViewModel get() {
    return new AllAnnotationsViewModel(arg0Provider.get());
  }

  public static Factory<AllAnnotationsViewModel> create(Provider<AnnotationManager> arg0Provider) {
    return new AllAnnotationsViewModel_Factory(arg0Provider);
  }
}

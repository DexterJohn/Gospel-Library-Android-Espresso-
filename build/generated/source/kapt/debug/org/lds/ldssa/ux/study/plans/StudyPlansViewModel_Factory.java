package org.lds.ldssa.ux.study.plans;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StudyPlansViewModel_Factory implements Factory<StudyPlansViewModel> {
  private final Provider<Application> arg0Provider;

  public StudyPlansViewModel_Factory(Provider<Application> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public StudyPlansViewModel get() {
    return new StudyPlansViewModel(arg0Provider.get());
  }

  public static Factory<StudyPlansViewModel> create(Provider<Application> arg0Provider) {
    return new StudyPlansViewModel_Factory(arg0Provider);
  }
}

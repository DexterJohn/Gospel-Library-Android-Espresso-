package org.lds.ldssa.ux.study.plans;

import android.app.Application;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StudyPlansAdapter_MembersInjector implements MembersInjector<StudyPlansAdapter> {
  private final Provider<Application> applicationProvider;

  public StudyPlansAdapter_MembersInjector(Provider<Application> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  public static MembersInjector<StudyPlansAdapter> create(
      Provider<Application> applicationProvider) {
    return new StudyPlansAdapter_MembersInjector(applicationProvider);
  }

  @Override
  public void injectMembers(StudyPlansAdapter instance) {
    injectApplication(instance, applicationProvider.get());
  }

  public static void injectApplication(StudyPlansAdapter instance, Application application) {
    instance.application = application;
  }
}

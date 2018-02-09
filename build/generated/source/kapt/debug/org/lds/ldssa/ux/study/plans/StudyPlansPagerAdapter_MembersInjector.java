package org.lds.ldssa.ux.study.plans;

import android.app.Application;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StudyPlansPagerAdapter_MembersInjector
    implements MembersInjector<StudyPlansPagerAdapter> {
  private final Provider<Application> applicationProvider;

  public StudyPlansPagerAdapter_MembersInjector(Provider<Application> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  public static MembersInjector<StudyPlansPagerAdapter> create(
      Provider<Application> applicationProvider) {
    return new StudyPlansPagerAdapter_MembersInjector(applicationProvider);
  }

  @Override
  public void injectMembers(StudyPlansPagerAdapter instance) {
    injectApplication(instance, applicationProvider.get());
  }

  public static void injectApplication(StudyPlansPagerAdapter instance, Application application) {
    instance.application = application;
  }
}

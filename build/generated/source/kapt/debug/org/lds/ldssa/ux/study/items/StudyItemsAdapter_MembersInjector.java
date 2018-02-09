package org.lds.ldssa.ux.study.items;

import android.app.Application;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StudyItemsAdapter_MembersInjector implements MembersInjector<StudyItemsAdapter> {
  private final Provider<Application> applicationProvider;

  public StudyItemsAdapter_MembersInjector(Provider<Application> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  public static MembersInjector<StudyItemsAdapter> create(
      Provider<Application> applicationProvider) {
    return new StudyItemsAdapter_MembersInjector(applicationProvider);
  }

  @Override
  public void injectMembers(StudyItemsAdapter instance) {
    injectApplication(instance, applicationProvider.get());
  }

  public static void injectApplication(StudyItemsAdapter instance, Application application) {
    instance.application = application;
  }
}

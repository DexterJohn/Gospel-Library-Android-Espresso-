package org.lds.ldssa;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.job.AppJobCreator;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.AppUpgradeUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class App_MembersInjector implements MembersInjector<App> {
  private final Provider<Analytics> analyticsProvider;

  private final Provider<AppJobCreator> appJobCreatorProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<AppUpgradeUtil> appUpgradeUtilProvider;

  private final Provider<GLUpdateLifecycle> glUpdateLifecycleProvider;

  public App_MembersInjector(
      Provider<Analytics> analyticsProvider,
      Provider<AppJobCreator> appJobCreatorProvider,
      Provider<Prefs> prefsProvider,
      Provider<AppUpgradeUtil> appUpgradeUtilProvider,
      Provider<GLUpdateLifecycle> glUpdateLifecycleProvider) {
    this.analyticsProvider = analyticsProvider;
    this.appJobCreatorProvider = appJobCreatorProvider;
    this.prefsProvider = prefsProvider;
    this.appUpgradeUtilProvider = appUpgradeUtilProvider;
    this.glUpdateLifecycleProvider = glUpdateLifecycleProvider;
  }

  public static MembersInjector<App> create(
      Provider<Analytics> analyticsProvider,
      Provider<AppJobCreator> appJobCreatorProvider,
      Provider<Prefs> prefsProvider,
      Provider<AppUpgradeUtil> appUpgradeUtilProvider,
      Provider<GLUpdateLifecycle> glUpdateLifecycleProvider) {
    return new App_MembersInjector(
        analyticsProvider,
        appJobCreatorProvider,
        prefsProvider,
        appUpgradeUtilProvider,
        glUpdateLifecycleProvider);
  }

  @Override
  public void injectMembers(App instance) {
    injectAnalytics(instance, analyticsProvider.get());
    injectAppJobCreator(instance, appJobCreatorProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectAppUpgradeUtil(instance, appUpgradeUtilProvider.get());
    injectGlUpdateLifecycle(instance, glUpdateLifecycleProvider.get());
  }

  public static void injectAnalytics(App instance, Analytics analytics) {
    instance.analytics = analytics;
  }

  public static void injectAppJobCreator(App instance, AppJobCreator appJobCreator) {
    instance.appJobCreator = appJobCreator;
  }

  public static void injectPrefs(App instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectAppUpgradeUtil(App instance, AppUpgradeUtil appUpgradeUtil) {
    instance.appUpgradeUtil = appUpgradeUtil;
  }

  public static void injectGlUpdateLifecycle(App instance, GLUpdateLifecycle glUpdateLifecycle) {
    instance.glUpdateLifecycle = glUpdateLifecycle;
  }
}

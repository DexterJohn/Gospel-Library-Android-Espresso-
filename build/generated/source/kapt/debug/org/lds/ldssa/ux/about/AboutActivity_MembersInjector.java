package org.lds.ldssa.ux.about;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.ThemeUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AboutActivity_MembersInjector implements MembersInjector<AboutActivity> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<Analytics> analyticsProvider;

  private final Provider<ThemeUtil> themeUtilProvider;

  public AboutActivity_MembersInjector(
      Provider<Prefs> prefsProvider,
      Provider<Analytics> analyticsProvider,
      Provider<ThemeUtil> themeUtilProvider) {
    this.prefsProvider = prefsProvider;
    this.analyticsProvider = analyticsProvider;
    this.themeUtilProvider = themeUtilProvider;
  }

  public static MembersInjector<AboutActivity> create(
      Provider<Prefs> prefsProvider,
      Provider<Analytics> analyticsProvider,
      Provider<ThemeUtil> themeUtilProvider) {
    return new AboutActivity_MembersInjector(prefsProvider, analyticsProvider, themeUtilProvider);
  }

  @Override
  public void injectMembers(AboutActivity instance) {
    injectPrefs(instance, prefsProvider.get());
    injectAnalytics(instance, analyticsProvider.get());
    injectThemeUtil(instance, themeUtilProvider.get());
  }

  public static void injectPrefs(AboutActivity instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectAnalytics(AboutActivity instance, Analytics analytics) {
    instance.analytics = analytics;
  }

  public static void injectThemeUtil(AboutActivity instance, ThemeUtil themeUtil) {
    instance.themeUtil = themeUtil;
  }
}

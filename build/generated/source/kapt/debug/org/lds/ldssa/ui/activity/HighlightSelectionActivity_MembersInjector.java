package org.lds.ldssa.ui.activity;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.ldssa.util.annotations.HighlightUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HighlightSelectionActivity_MembersInjector
    implements MembersInjector<HighlightSelectionActivity> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<HighlightUtil> highlightUtilProvider;

  private final Provider<Analytics> analyticsProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<ThemeUtil> themeUtilProvider;

  public HighlightSelectionActivity_MembersInjector(
      Provider<Prefs> prefsProvider,
      Provider<HighlightUtil> highlightUtilProvider,
      Provider<Analytics> analyticsProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<ThemeUtil> themeUtilProvider) {
    this.prefsProvider = prefsProvider;
    this.highlightUtilProvider = highlightUtilProvider;
    this.analyticsProvider = analyticsProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.themeUtilProvider = themeUtilProvider;
  }

  public static MembersInjector<HighlightSelectionActivity> create(
      Provider<Prefs> prefsProvider,
      Provider<HighlightUtil> highlightUtilProvider,
      Provider<Analytics> analyticsProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<ThemeUtil> themeUtilProvider) {
    return new HighlightSelectionActivity_MembersInjector(
        prefsProvider,
        highlightUtilProvider,
        analyticsProvider,
        annotationManagerProvider,
        themeUtilProvider);
  }

  @Override
  public void injectMembers(HighlightSelectionActivity instance) {
    injectPrefs(instance, prefsProvider.get());
    injectHighlightUtil(instance, highlightUtilProvider.get());
    injectAnalytics(instance, analyticsProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectThemeUtil(instance, themeUtilProvider.get());
  }

  public static void injectPrefs(HighlightSelectionActivity instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectHighlightUtil(
      HighlightSelectionActivity instance, HighlightUtil highlightUtil) {
    instance.highlightUtil = highlightUtil;
  }

  public static void injectAnalytics(HighlightSelectionActivity instance, Analytics analytics) {
    instance.analytics = analytics;
  }

  public static void injectAnnotationManager(
      HighlightSelectionActivity instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectThemeUtil(HighlightSelectionActivity instance, ThemeUtil themeUtil) {
    instance.themeUtil = themeUtil;
  }
}

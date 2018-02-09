package org.lds.ldssa.ui.activity;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.model.database.content.subitemcontent.SubItemContentManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentSourceActivity_MembersInjector
    implements MembersInjector<ContentSourceActivity> {
  private final Provider<Analytics> analyticsProvider;

  private final Provider<SubItemContentManager> subItemContentManagerProvider;

  public ContentSourceActivity_MembersInjector(
      Provider<Analytics> analyticsProvider,
      Provider<SubItemContentManager> subItemContentManagerProvider) {
    this.analyticsProvider = analyticsProvider;
    this.subItemContentManagerProvider = subItemContentManagerProvider;
  }

  public static MembersInjector<ContentSourceActivity> create(
      Provider<Analytics> analyticsProvider,
      Provider<SubItemContentManager> subItemContentManagerProvider) {
    return new ContentSourceActivity_MembersInjector(
        analyticsProvider, subItemContentManagerProvider);
  }

  @Override
  public void injectMembers(ContentSourceActivity instance) {
    injectAnalytics(instance, analyticsProvider.get());
    injectSubItemContentManager(instance, subItemContentManagerProvider.get());
  }

  public static void injectAnalytics(ContentSourceActivity instance, Analytics analytics) {
    instance.analytics = analytics;
  }

  public static void injectSubItemContentManager(
      ContentSourceActivity instance, SubItemContentManager subItemContentManager) {
    instance.subItemContentManager = subItemContentManager;
  }
}

package org.lds.ldssa.ux.locations.screens;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemManager;
import org.lds.ldssa.util.GLFileUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ScreensAdapter_MembersInjector implements MembersInjector<ScreensAdapter> {
  private final Provider<GLFileUtil> fileUtilProvider;

  private final Provider<ScreenHistoryItemManager> screenHistoryItemManagerProvider;

  public ScreensAdapter_MembersInjector(
      Provider<GLFileUtil> fileUtilProvider,
      Provider<ScreenHistoryItemManager> screenHistoryItemManagerProvider) {
    this.fileUtilProvider = fileUtilProvider;
    this.screenHistoryItemManagerProvider = screenHistoryItemManagerProvider;
  }

  public static MembersInjector<ScreensAdapter> create(
      Provider<GLFileUtil> fileUtilProvider,
      Provider<ScreenHistoryItemManager> screenHistoryItemManagerProvider) {
    return new ScreensAdapter_MembersInjector(fileUtilProvider, screenHistoryItemManagerProvider);
  }

  @Override
  public void injectMembers(ScreensAdapter instance) {
    injectFileUtil(instance, fileUtilProvider.get());
    injectScreenHistoryItemManager(instance, screenHistoryItemManagerProvider.get());
  }

  public static void injectFileUtil(ScreensAdapter instance, GLFileUtil fileUtil) {
    instance.fileUtil = fileUtil;
  }

  public static void injectScreenHistoryItemManager(
      ScreensAdapter instance, ScreenHistoryItemManager screenHistoryItemManager) {
    instance.screenHistoryItemManager = screenHistoryItemManager;
  }
}

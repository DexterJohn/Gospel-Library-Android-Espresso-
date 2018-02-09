package org.lds.ldssa.task;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.content.subitemcontent.SubItemContentManager;
import org.lds.ldssa.model.database.userdata.link.LinkManager;
import org.lds.ldssa.util.CitationUtil;
import org.lds.ldssa.util.annotations.LinkUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LinkSubItemTask_Factory implements Factory<LinkSubItemTask> {
  private final Provider<SubItemContentManager> subItemContentManagerProvider;

  private final Provider<LinkManager> linkManagerProvider;

  private final Provider<SubItemManager> subItemManagerProvider;

  private final Provider<CitationUtil> citationUtilProvider;

  private final Provider<Bus> busProvider;

  private final Provider<LinkUtil> linkUtilProvider;

  public LinkSubItemTask_Factory(
      Provider<SubItemContentManager> subItemContentManagerProvider,
      Provider<LinkManager> linkManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<CitationUtil> citationUtilProvider,
      Provider<Bus> busProvider,
      Provider<LinkUtil> linkUtilProvider) {
    this.subItemContentManagerProvider = subItemContentManagerProvider;
    this.linkManagerProvider = linkManagerProvider;
    this.subItemManagerProvider = subItemManagerProvider;
    this.citationUtilProvider = citationUtilProvider;
    this.busProvider = busProvider;
    this.linkUtilProvider = linkUtilProvider;
  }

  @Override
  public LinkSubItemTask get() {
    return new LinkSubItemTask(
        subItemContentManagerProvider.get(),
        linkManagerProvider.get(),
        subItemManagerProvider.get(),
        citationUtilProvider.get(),
        busProvider.get(),
        linkUtilProvider.get());
  }

  public static Factory<LinkSubItemTask> create(
      Provider<SubItemContentManager> subItemContentManagerProvider,
      Provider<LinkManager> linkManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<CitationUtil> citationUtilProvider,
      Provider<Bus> busProvider,
      Provider<LinkUtil> linkUtilProvider) {
    return new LinkSubItemTask_Factory(
        subItemContentManagerProvider,
        linkManagerProvider,
        subItemManagerProvider,
        citationUtilProvider,
        busProvider,
        linkUtilProvider);
  }
}

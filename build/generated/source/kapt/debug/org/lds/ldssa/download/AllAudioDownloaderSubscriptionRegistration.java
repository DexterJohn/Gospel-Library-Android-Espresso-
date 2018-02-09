package org.lds.ldssa.download;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lds.ldssa.event.download.DownloadCancelAllEvent;
import pocketbus.Subscription;
import pocketbus.SubscriptionRegistration;
import pocketbus.ThreadMode;

public class AllAudioDownloaderSubscriptionRegistration implements SubscriptionRegistration {
  private final WeakReference<AllAudioDownloader> targetRef;

  private final List<Subscription<?>> subscriptions;

  private Subscription<DownloadCancelAllEvent> subscription1 = new Subscription<DownloadCancelAllEvent>() {
    @Override
    public boolean handle(DownloadCancelAllEvent event) {
      AllAudioDownloader target = targetRef.get();
      if (target != null) {
        target.handle(event);
      }
      return target != null;
    }

    @Override
    public Class<DownloadCancelAllEvent> getEventClass() {
      return DownloadCancelAllEvent.class;
    }

    @Override
    public ThreadMode getThreadMode() {
      return ThreadMode.CURRENT;
    }

    @Override
    public AllAudioDownloader getTarget() {
      return targetRef.get();
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Subscription<?>) {
        return this.getTarget() != null && this.getTarget().equals(((Subscription<?>)o).getTarget());
      }
      return false;
    }
  };

  public AllAudioDownloaderSubscriptionRegistration(AllAudioDownloader target) {
    this.targetRef = new WeakReference<AllAudioDownloader>(target);
    List<Subscription<?>> subscriptions = new ArrayList<Subscription<?>>();
    subscriptions.add(subscription1);
    this.subscriptions = Collections.unmodifiableList(subscriptions);
  }

  @Override
  public List<Subscription<?>> getSubscriptions() {
    return subscriptions;
  }
}

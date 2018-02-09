package org.lds.ldssa.ui.activity;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lds.ldssa.event.download.DownloadCompletedEvent;
import pocketbus.Subscription;
import pocketbus.ThreadMode;

public class VideoPlayerActivitySubscriptionRegistration extends BaseActivitySubscriptionRegistration {
  private final WeakReference<VideoPlayerActivity> targetRef;

  private final List<Subscription<?>> subscriptions;

  private Subscription<DownloadCompletedEvent> subscription1 = new Subscription<DownloadCompletedEvent>() {
    @Override
    public boolean handle(DownloadCompletedEvent event) {
      VideoPlayerActivity target = targetRef.get();
      if (target != null) {
        target.handle(event);
      }
      return target != null;
    }

    @Override
    public Class<DownloadCompletedEvent> getEventClass() {
      return DownloadCompletedEvent.class;
    }

    @Override
    public ThreadMode getThreadMode() {
      return ThreadMode.MAIN;
    }

    @Override
    public VideoPlayerActivity getTarget() {
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

  public VideoPlayerActivitySubscriptionRegistration(VideoPlayerActivity target) {
    super(target);
    this.targetRef = new WeakReference<VideoPlayerActivity>(target);
    List<Subscription<?>> subscriptions = new ArrayList<Subscription<?>>();
    subscriptions.addAll(super.getSubscriptions());
    subscriptions.add(subscription1);
    this.subscriptions = Collections.unmodifiableList(subscriptions);
  }

  @Override
  public List<Subscription<?>> getSubscriptions() {
    return subscriptions;
  }
}

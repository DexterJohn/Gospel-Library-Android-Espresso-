package org.lds.ldssa.ux.annotations;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lds.ldssa.event.download.DownloadCompletedEvent;
import pocketbus.Subscription;
import pocketbus.SubscriptionRegistration;
import pocketbus.ThreadMode;

public class AnnotationsFragmentSubscriptionRegistration implements SubscriptionRegistration {
  private final WeakReference<AnnotationsFragment> targetRef;

  private final List<Subscription<?>> subscriptions;

  private Subscription<DownloadCompletedEvent> subscription1 = new Subscription<DownloadCompletedEvent>() {
    @Override
    public boolean handle(DownloadCompletedEvent event) {
      AnnotationsFragment target = targetRef.get();
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
    public AnnotationsFragment getTarget() {
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

  public AnnotationsFragmentSubscriptionRegistration(AnnotationsFragment target) {
    this.targetRef = new WeakReference<AnnotationsFragment>(target);
    List<Subscription<?>> subscriptions = new ArrayList<Subscription<?>>();
    subscriptions.add(subscription1);
    this.subscriptions = Collections.unmodifiableList(subscriptions);
  }

  @Override
  public List<Subscription<?>> getSubscriptions() {
    return subscriptions;
  }
}

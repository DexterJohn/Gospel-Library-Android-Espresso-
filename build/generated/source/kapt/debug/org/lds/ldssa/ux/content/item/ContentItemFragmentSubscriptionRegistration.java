package org.lds.ldssa.ux.content.item;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lds.ldssa.event.sync.AnnotationSyncFinishedEvent;
import pocketbus.Subscription;
import pocketbus.SubscriptionRegistration;
import pocketbus.ThreadMode;

public class ContentItemFragmentSubscriptionRegistration implements SubscriptionRegistration {
  private final WeakReference<ContentItemFragment> targetRef;

  private final List<Subscription<?>> subscriptions;

  private Subscription<AnnotationSyncFinishedEvent> subscription1 = new Subscription<AnnotationSyncFinishedEvent>() {
    @Override
    public boolean handle(AnnotationSyncFinishedEvent event) {
      ContentItemFragment target = targetRef.get();
      if (target != null) {
        target.handle(event);
      }
      return target != null;
    }

    @Override
    public Class<AnnotationSyncFinishedEvent> getEventClass() {
      return AnnotationSyncFinishedEvent.class;
    }

    @Override
    public ThreadMode getThreadMode() {
      return ThreadMode.MAIN;
    }

    @Override
    public ContentItemFragment getTarget() {
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

  public ContentItemFragmentSubscriptionRegistration(ContentItemFragment target) {
    this.targetRef = new WeakReference<ContentItemFragment>(target);
    List<Subscription<?>> subscriptions = new ArrayList<Subscription<?>>();
    subscriptions.add(subscription1);
    this.subscriptions = Collections.unmodifiableList(subscriptions);
  }

  @Override
  public List<Subscription<?>> getSubscriptions() {
    return subscriptions;
  }
}

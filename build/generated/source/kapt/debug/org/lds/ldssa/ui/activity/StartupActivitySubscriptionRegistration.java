package org.lds.ldssa.ui.activity;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lds.ldssa.event.StartupProgressEvent;
import pocketbus.Subscription;
import pocketbus.SubscriptionRegistration;
import pocketbus.ThreadMode;

public class StartupActivitySubscriptionRegistration implements SubscriptionRegistration {
  private final WeakReference<StartupActivity> targetRef;

  private final List<Subscription<?>> subscriptions;

  private Subscription<StartupProgressEvent> subscription1 = new Subscription<StartupProgressEvent>() {
    @Override
    public boolean handle(StartupProgressEvent event) {
      StartupActivity target = targetRef.get();
      if (target != null) {
        target.handle(event);
      }
      return target != null;
    }

    @Override
    public Class<StartupProgressEvent> getEventClass() {
      return StartupProgressEvent.class;
    }

    @Override
    public ThreadMode getThreadMode() {
      return ThreadMode.MAIN;
    }

    @Override
    public StartupActivity getTarget() {
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

  public StartupActivitySubscriptionRegistration(StartupActivity target) {
    this.targetRef = new WeakReference<StartupActivity>(target);
    List<Subscription<?>> subscriptions = new ArrayList<Subscription<?>>();
    subscriptions.add(subscription1);
    this.subscriptions = Collections.unmodifiableList(subscriptions);
  }

  @Override
  public List<Subscription<?>> getSubscriptions() {
    return subscriptions;
  }
}

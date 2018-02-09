package org.lds.ldssa.ux.catalog;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lds.ldssa.event.catalog.CatalogReloadEvent;
import org.lds.ldssa.event.catalog.CatalogUpdateCheckCompletedEvent;
import org.lds.ldssa.ui.activity.BaseActivitySubscriptionRegistration;
import pocketbus.Subscription;
import pocketbus.ThreadMode;

public class CatalogDirectoryActivitySubscriptionRegistration extends BaseActivitySubscriptionRegistration {
  private final WeakReference<CatalogDirectoryActivity> targetRef;

  private final List<Subscription<?>> subscriptions;

  private Subscription<CatalogReloadEvent> subscription1 = new Subscription<CatalogReloadEvent>() {
    @Override
    public boolean handle(CatalogReloadEvent event) {
      CatalogDirectoryActivity target = targetRef.get();
      if (target != null) {
        target.handle(event);
      }
      return target != null;
    }

    @Override
    public Class<CatalogReloadEvent> getEventClass() {
      return CatalogReloadEvent.class;
    }

    @Override
    public ThreadMode getThreadMode() {
      return ThreadMode.MAIN;
    }

    @Override
    public CatalogDirectoryActivity getTarget() {
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

  private Subscription<CatalogUpdateCheckCompletedEvent> subscription2 = new Subscription<CatalogUpdateCheckCompletedEvent>() {
    @Override
    public boolean handle(CatalogUpdateCheckCompletedEvent event) {
      CatalogDirectoryActivity target = targetRef.get();
      if (target != null) {
        target.handle(event);
      }
      return target != null;
    }

    @Override
    public Class<CatalogUpdateCheckCompletedEvent> getEventClass() {
      return CatalogUpdateCheckCompletedEvent.class;
    }

    @Override
    public ThreadMode getThreadMode() {
      return ThreadMode.MAIN;
    }

    @Override
    public CatalogDirectoryActivity getTarget() {
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

  public CatalogDirectoryActivitySubscriptionRegistration(CatalogDirectoryActivity target) {
    super(target);
    this.targetRef = new WeakReference<CatalogDirectoryActivity>(target);
    List<Subscription<?>> subscriptions = new ArrayList<Subscription<?>>();
    subscriptions.addAll(super.getSubscriptions());
    subscriptions.add(subscription1);
    subscriptions.add(subscription2);
    this.subscriptions = Collections.unmodifiableList(subscriptions);
  }

  @Override
  public List<Subscription<?>> getSubscriptions() {
    return subscriptions;
  }
}

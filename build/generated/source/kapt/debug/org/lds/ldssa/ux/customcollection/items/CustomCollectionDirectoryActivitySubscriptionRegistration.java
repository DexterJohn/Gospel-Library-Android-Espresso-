package org.lds.ldssa.ux.customcollection.items;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lds.ldssa.event.catalog.CatalogReloadEvent;
import org.lds.ldssa.ui.activity.BaseActivitySubscriptionRegistration;
import pocketbus.Subscription;
import pocketbus.ThreadMode;

public class CustomCollectionDirectoryActivitySubscriptionRegistration extends BaseActivitySubscriptionRegistration {
  private final WeakReference<CustomCollectionDirectoryActivity> targetRef;

  private final List<Subscription<?>> subscriptions;

  private Subscription<CatalogReloadEvent> subscription1 = new Subscription<CatalogReloadEvent>() {
    @Override
    public boolean handle(CatalogReloadEvent event) {
      CustomCollectionDirectoryActivity target = targetRef.get();
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
    public CustomCollectionDirectoryActivity getTarget() {
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

  public CustomCollectionDirectoryActivitySubscriptionRegistration(CustomCollectionDirectoryActivity target) {
    super(target);
    this.targetRef = new WeakReference<CustomCollectionDirectoryActivity>(target);
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

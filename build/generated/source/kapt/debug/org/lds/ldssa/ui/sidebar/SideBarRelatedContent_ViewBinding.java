// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.sidebar;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;
import org.lds.mobile.ui.widget.EmptyStateIndicator;

public class SideBarRelatedContent_ViewBinding implements Unbinder {
  private SideBarRelatedContent target;

  @UiThread
  public SideBarRelatedContent_ViewBinding(SideBarRelatedContent target) {
    this(target, target);
  }

  @UiThread
  public SideBarRelatedContent_ViewBinding(SideBarRelatedContent target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.emptyStateIndicator = Utils.findRequiredViewAsType(source, R.id.emptyStateIndicator, "field 'emptyStateIndicator'", EmptyStateIndicator.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SideBarRelatedContent target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.emptyStateIndicator = null;
  }
}

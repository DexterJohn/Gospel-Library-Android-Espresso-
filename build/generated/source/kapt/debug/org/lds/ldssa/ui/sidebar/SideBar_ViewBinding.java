// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.sidebar;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;
import org.lds.mobile.ui.widget.EmptyStateIndicator;

public class SideBar_ViewBinding implements Unbinder {
  private SideBar target;

  @UiThread
  public SideBar_ViewBinding(SideBar target) {
    this(target, target);
  }

  @UiThread
  public SideBar_ViewBinding(SideBar target, View source) {
    this.target = target;

    target.toolbarTitleTextView = Utils.findRequiredViewAsType(source, R.id.contentDrawerToolbarTitleTextView, "field 'toolbarTitleTextView'", TextView.class);
    target.sideBarContainer = Utils.findRequiredViewAsType(source, R.id.sideBarContainer, "field 'sideBarContainer'", FrameLayout.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.contentDrawerToolbar, "field 'toolbar'", Toolbar.class);
    target.emptyStateIndicator = Utils.findRequiredViewAsType(source, R.id.emptyStateIndicator, "field 'emptyStateIndicator'", EmptyStateIndicator.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SideBar target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbarTitleTextView = null;
    target.sideBarContainer = null;
    target.toolbar = null;
    target.emptyStateIndicator = null;
  }
}

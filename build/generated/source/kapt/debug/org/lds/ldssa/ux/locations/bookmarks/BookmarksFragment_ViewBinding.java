// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ux.locations.bookmarks;

import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.mobile.ui.widget.EmptyStateIndicator;

public final class BookmarksFragment_ViewBinding implements Unbinder {
  private BookmarksFragment target;

  @UiThread
  public BookmarksFragment_ViewBinding(BookmarksFragment target, View source) {
    this.target = target;

    target.emptyStateIndicator = Utils.findRequiredViewAsType(source, 2131427553, "field 'emptyStateIndicator'", EmptyStateIndicator.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, 2131427412, "field 'recyclerView'", RecyclerView.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, 2131428067, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
  }

  @Override
  public void unbind() {
    BookmarksFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.emptyStateIndicator = null;
    target.recyclerView = null;
    target.swipeRefreshLayout = null;
  }
}

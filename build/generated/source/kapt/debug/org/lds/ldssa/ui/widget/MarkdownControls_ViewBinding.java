// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.widget;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;

public class MarkdownControls_ViewBinding implements Unbinder {
  private MarkdownControls target;

  @UiThread
  public MarkdownControls_ViewBinding(MarkdownControls target) {
    this(target, target);
  }

  @UiThread
  public MarkdownControls_ViewBinding(MarkdownControls target, View source) {
    this.target = target;

    target.boldView = Utils.findRequiredViewAsType(source, R.id.markdown_controls_bold, "field 'boldView'", ImageView.class);
    target.orderedListView = Utils.findRequiredViewAsType(source, R.id.markdown_controls_ordered_list, "field 'orderedListView'", ImageView.class);
    target.italicView = Utils.findRequiredViewAsType(source, R.id.markdown_controls_italic, "field 'italicView'", ImageView.class);
    target.unorderedListView = Utils.findRequiredViewAsType(source, R.id.markdown_controls_unordered_list, "field 'unorderedListView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MarkdownControls target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.boldView = null;
    target.orderedListView = null;
    target.italicView = null;
    target.unorderedListView = null;
  }
}

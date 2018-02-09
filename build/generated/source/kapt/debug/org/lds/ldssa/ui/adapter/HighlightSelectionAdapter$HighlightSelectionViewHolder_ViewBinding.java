// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;

public class HighlightSelectionAdapter$HighlightSelectionViewHolder_ViewBinding implements Unbinder {
  private HighlightSelectionAdapter.HighlightSelectionViewHolder target;

  @UiThread
  public HighlightSelectionAdapter$HighlightSelectionViewHolder_ViewBinding(HighlightSelectionAdapter.HighlightSelectionViewHolder target,
      View source) {
    this.target = target;

    target.textView = Utils.findRequiredViewAsType(source, R.id.listItemTextView, "field 'textView'", TextView.class);
    target.stickyIcon = Utils.findRequiredViewAsType(source, R.id.stickyIcon, "field 'stickyIcon'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HighlightSelectionAdapter.HighlightSelectionViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textView = null;
    target.stickyIcon = null;
  }
}

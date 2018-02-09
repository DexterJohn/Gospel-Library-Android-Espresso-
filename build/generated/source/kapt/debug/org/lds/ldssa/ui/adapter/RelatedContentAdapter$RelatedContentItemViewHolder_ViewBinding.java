// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;

public class RelatedContentAdapter$RelatedContentItemViewHolder_ViewBinding implements Unbinder {
  private RelatedContentAdapter.RelatedContentItemViewHolder target;

  @UiThread
  public RelatedContentAdapter$RelatedContentItemViewHolder_ViewBinding(RelatedContentAdapter.RelatedContentItemViewHolder target,
      View source) {
    this.target = target;

    target.referenceTextView = Utils.findRequiredViewAsType(source, R.id.referenceTextView, "field 'referenceTextView'", TextView.class);
    target.contentTextView = Utils.findRequiredViewAsType(source, R.id.contentTextView, "field 'contentTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RelatedContentAdapter.RelatedContentItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.referenceTextView = null;
    target.contentTextView = null;
  }
}

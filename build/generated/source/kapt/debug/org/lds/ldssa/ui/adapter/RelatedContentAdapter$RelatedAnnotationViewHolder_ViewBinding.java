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
import org.lds.ldssa.ui.widget.AnnotationView;

public class RelatedContentAdapter$RelatedAnnotationViewHolder_ViewBinding implements Unbinder {
  private RelatedContentAdapter.RelatedAnnotationViewHolder target;

  @UiThread
  public RelatedContentAdapter$RelatedAnnotationViewHolder_ViewBinding(RelatedContentAdapter.RelatedAnnotationViewHolder target,
      View source) {
    this.target = target;

    target.annotationMenuImageButton = Utils.findRequiredViewAsType(source, R.id.annotationMenuImageButton, "field 'annotationMenuImageButton'", ImageView.class);
    target.annotationView = Utils.findRequiredViewAsType(source, R.id.annotationView, "field 'annotationView'", AnnotationView.class);
    target.referenceTextView = Utils.findRequiredViewAsType(source, R.id.referenceTextView, "field 'referenceTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RelatedContentAdapter.RelatedAnnotationViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.annotationMenuImageButton = null;
    target.annotationView = null;
    target.referenceTextView = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.adapter.viewholder;

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

public class ImageActionTwoRowViewHolder_ViewBinding implements Unbinder {
  private ImageActionTwoRowViewHolder target;

  @UiThread
  public ImageActionTwoRowViewHolder_ViewBinding(ImageActionTwoRowViewHolder target, View source) {
    this.target = target;

    target.titleView = Utils.findRequiredViewAsType(source, R.id.titleTextView, "field 'titleView'", TextView.class);
    target.imageView = Utils.findRequiredViewAsType(source, R.id.imageView, "field 'imageView'", ImageView.class);
    target.subTitleView = Utils.findRequiredViewAsType(source, R.id.subTitleTextView, "field 'subTitleView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ImageActionTwoRowViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleView = null;
    target.imageView = null;
    target.subTitleView = null;
  }
}

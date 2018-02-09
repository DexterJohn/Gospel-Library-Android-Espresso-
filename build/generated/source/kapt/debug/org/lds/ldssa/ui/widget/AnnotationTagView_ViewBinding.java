// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.widget;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;

public class AnnotationTagView_ViewBinding implements Unbinder {
  private AnnotationTagView target;

  private View view2131428078;

  private View view2131427971;

  @UiThread
  public AnnotationTagView_ViewBinding(AnnotationTagView target) {
    this(target, target);
  }

  @UiThread
  public AnnotationTagView_ViewBinding(final AnnotationTagView target, View source) {
    this.target = target;

    View view;
    target.textView = Utils.findRequiredViewAsType(source, R.id.tag_text, "field 'textView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tag_delete, "field 'deleteView' and method 'onDeleteClick'");
    target.deleteView = Utils.castView(view, R.id.tag_delete, "field 'deleteView'", ImageView.class);
    view2131428078 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDeleteClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.rootLayout, "field 'root' and method 'onRootClick'");
    target.root = Utils.castView(view, R.id.rootLayout, "field 'root'", LinearLayout.class);
    view2131427971 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRootClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AnnotationTagView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textView = null;
    target.deleteView = null;
    target.root = null;

    view2131428078.setOnClickListener(null);
    view2131428078 = null;
    view2131427971.setOnClickListener(null);
    view2131427971 = null;
  }
}

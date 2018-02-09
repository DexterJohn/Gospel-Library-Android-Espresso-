// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.sidebar;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;
import org.lds.ldssa.ui.widget.AnnotationView;

public class SideBarAnnotation_ViewBinding implements Unbinder {
  private SideBarAnnotation target;

  @UiThread
  public SideBarAnnotation_ViewBinding(SideBarAnnotation target) {
    this(target, target);
  }

  @UiThread
  public SideBarAnnotation_ViewBinding(SideBarAnnotation target, View source) {
    this.target = target;

    target.annotationView = Utils.findRequiredViewAsType(source, R.id.annotationView, "field 'annotationView'", AnnotationView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SideBarAnnotation target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.annotationView = null;
  }
}

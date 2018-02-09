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

public class DownloadMediaViewHolder_ViewBinding implements Unbinder {
  private DownloadMediaViewHolder target;

  @UiThread
  public DownloadMediaViewHolder_ViewBinding(DownloadMediaViewHolder target, View source) {
    this.target = target;

    target.detailView = Utils.findRequiredViewAsType(source, R.id.detailView, "field 'detailView'", TextView.class);
    target.titleView = Utils.findRequiredViewAsType(source, R.id.titleView, "field 'titleView'", TextView.class);
    target.typeIcon = Utils.findRequiredViewAsType(source, R.id.typeIcon, "field 'typeIcon'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DownloadMediaViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.detailView = null;
    target.titleView = null;
    target.typeIcon = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;
import org.lds.ldssa.ui.widget.LoadingView;

public class DownloadMediaDialogFragment_ViewBinding implements Unbinder {
  private DownloadMediaDialogFragment target;

  @UiThread
  public DownloadMediaDialogFragment_ViewBinding(DownloadMediaDialogFragment target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.download_media_dialog_recycler, "field 'recyclerView'", RecyclerView.class);
    target.loadingView = Utils.findRequiredViewAsType(source, R.id.download_media_dialog_progress, "field 'loadingView'", LoadingView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DownloadMediaDialogFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.loadingView = null;
  }
}

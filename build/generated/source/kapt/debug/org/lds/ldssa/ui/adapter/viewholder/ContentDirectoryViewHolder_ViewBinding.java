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

public class ContentDirectoryViewHolder_ViewBinding implements Unbinder {
  private ContentDirectoryViewHolder target;

  @UiThread
  public ContentDirectoryViewHolder_ViewBinding(ContentDirectoryViewHolder target, View source) {
    this.target = target;

    target.listSubTitleTextView = Utils.findOptionalViewAsType(source, R.id.content_item_sub_title, "field 'listSubTitleTextView'", TextView.class);
    target.listIndentationView = source.findViewById(R.id.content_item_indentation_spacing);
    target.contentImage = Utils.findOptionalViewAsType(source, R.id.content_item_image, "field 'contentImage'", ImageView.class);
    target.gridPreviewTextView = Utils.findOptionalViewAsType(source, R.id.content_item_preview, "field 'gridPreviewTextView'", TextView.class);
    target.gridChapterTextView = Utils.findOptionalViewAsType(source, R.id.content_item_chapter, "field 'gridChapterTextView'", TextView.class);
    target.titleTextView = Utils.findRequiredViewAsType(source, R.id.content_item_title, "field 'titleTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ContentDirectoryViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.listSubTitleTextView = null;
    target.listIndentationView = null;
    target.contentImage = null;
    target.gridPreviewTextView = null;
    target.gridChapterTextView = null;
    target.titleTextView = null;
  }
}

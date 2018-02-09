// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.widget;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.flexbox.FlexboxLayout;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;
import org.lds.mobile.markdown.widget.MarkdownTextView;

public final class AnnotationView_ViewBinding implements Unbinder {
  private AnnotationView target;

  @UiThread
  public AnnotationView_ViewBinding(AnnotationView target) {
    this(target, target);
  }

  @UiThread
  public AnnotationView_ViewBinding(AnnotationView target, View source) {
    this.target = target;

    target.tagsLayout = Utils.findRequiredViewAsType(source, R.id.tagsLayout, "field 'tagsLayout'", FlexboxLayout.class);
    target.noteMarkdownTextView = Utils.findRequiredViewAsType(source, R.id.noteMarkdownTextView, "field 'noteMarkdownTextView'", MarkdownTextView.class);
    target.highlightContentTextView = Utils.findRequiredViewAsType(source, R.id.highlightContentTextView, "field 'highlightContentTextView'", TextView.class);
    target.highlightTitleTextView = Utils.findRequiredViewAsType(source, R.id.highlightTitleTextView, "field 'highlightTitleTextView'", TextView.class);
    target.linksLayout = Utils.findRequiredViewAsType(source, R.id.linksLayout, "field 'linksLayout'", LinearLayout.class);
    target.highlightLayout = Utils.findRequiredViewAsType(source, R.id.highlightLayout, "field 'highlightLayout'", LinearLayout.class);
    target.lastModifiedTextView = Utils.findRequiredViewAsType(source, R.id.lastModifiedTextView, "field 'lastModifiedTextView'", TextView.class);
    target.notebooksLayout = Utils.findRequiredViewAsType(source, R.id.notebooksLayout, "field 'notebooksLayout'", LinearLayout.class);
    target.noteTitleTextView = Utils.findRequiredViewAsType(source, R.id.noteTitleTextView, "field 'noteTitleTextView'", MarkdownTextView.class);
    target.highlightSubTitleTextView = Utils.findRequiredViewAsType(source, R.id.highlightSubTitleTextView, "field 'highlightSubTitleTextView'", TextView.class);
  }

  @Override
  public void unbind() {
    AnnotationView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tagsLayout = null;
    target.noteMarkdownTextView = null;
    target.highlightContentTextView = null;
    target.highlightTitleTextView = null;
    target.linksLayout = null;
    target.highlightLayout = null;
    target.lastModifiedTextView = null;
    target.notebooksLayout = null;
    target.noteTitleTextView = null;
    target.highlightSubTitleTextView = null;
  }
}

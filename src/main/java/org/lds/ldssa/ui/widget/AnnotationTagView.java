package org.lds.ldssa.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.lds.ldssa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnnotationTagView extends FrameLayout {

    public interface OnTagClickListener {
        void onDeleteTag(String name, long tagId);
        void onClickTag(String name, long tagId);
    }

    @BindView(R.id.tag_text)
    TextView textView;
    @BindView(R.id.tag_delete)
    ImageView deleteView;
    @BindView(R.id.rootLayout)
    LinearLayout root;

    //The tag id in the database
    private long tagId = -1;

    private int additionalTopMargin = 0;

    @Nullable
    private OnTagClickListener clickListener;

    public AnnotationTagView(Context context) {
        super(context);
        init(context);
    }

    public AnnotationTagView(Context context, String text) {
        super(context);
        init(context);
        setText(text);
    }

    public AnnotationTagView(Context context, String text, long tagId, int additionalTopMargin) {
        super(context);
        this.tagId = tagId;
        this.additionalTopMargin = additionalTopMargin;
        init(context);
        setText(text);
    }

    public AnnotationTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnnotationTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnnotationTagView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.annotation_item_tag, this, true);
        ButterKnife.bind(this);
        addTopMargin();
    }

    @OnClick(R.id.tag_delete)
    public void onDeleteClick() {
        if (clickListener != null) {
            clickListener.onDeleteTag(getText(), tagId);
        }
    }

    @OnClick(R.id.rootLayout)
    public void onRootClick() {
        if (clickListener != null) {
            clickListener.onClickTag(getText(), tagId);
        }
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }

    public String getText() {
        return textView.getText().toString();
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public void setDeleteVisible(boolean visible) {
        deleteView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setTagClickListener(@Nullable OnTagClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private void addTopMargin() {
        if (additionalTopMargin == 0) {
            return;
        }
        final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) root.getLayoutParams();
        params.setMargins(params.leftMargin, params.topMargin + additionalTopMargin, params.rightMargin, params.bottomMargin);
        root.setLayoutParams(params);
    }
}

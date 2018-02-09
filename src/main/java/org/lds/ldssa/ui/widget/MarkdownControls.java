package org.lds.ldssa.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.lds.ldssa.R;
import org.lds.ldssa.inject.Injector;
import org.lds.mobile.markdown.widget.MarkdownEditText;
import org.lds.mobile.ui.util.LdsDrawableUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MarkdownControls extends FrameLayout implements MarkdownEditText.MarkdownControlsCallbacks {

    public interface Callback {
        boolean onBoldClick();
        boolean onItalicClick();
        boolean onUnorderedListClick();
        boolean onOrderedListClick();
    }

    @BindView(R.id.markdown_controls_bold)
    ImageView boldView;
    @BindView(R.id.markdown_controls_italic)
    ImageView italicView;
    @BindView(R.id.markdown_controls_unordered_list)
    ImageView unorderedListView;
    @BindView(R.id.markdown_controls_ordered_list)
    ImageView orderedListView;

    @Nullable
    private Callback callback;
    @Nullable
    private MarkdownEditText markdownEditText;

    private Context context;

    public MarkdownControls(Context context) {
        super(context);
        init(context, null);
    }

    public MarkdownControls(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MarkdownControls(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MarkdownControls(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    public void boldToggled(boolean on) {
        if (on) {
            boldView.setBackgroundColor(LdsDrawableUtil.INSTANCE.resolvedColorIntResourceId(context, R.attr.colorAccentDim));
        } else {
            boldView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        }
    }

    @Override
    public void italicToggled(boolean on) {
        if (on) {
            italicView.setBackgroundColor(LdsDrawableUtil.INSTANCE.resolvedColorIntResourceId(context, R.attr.colorAccentDim));
        } else {
            italicView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        }
    }

    @Override
    public void orderedListToggled(boolean on) {
        if (on) {
            orderedListView.setBackgroundColor(LdsDrawableUtil.INSTANCE.resolvedColorIntResourceId(context, R.attr.colorAccentDim));
        } else {
            orderedListView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        }
    }

    @Override
    public void unOrderedListToggled(boolean on) {
        if (on) {
            unorderedListView.setBackgroundColor(LdsDrawableUtil.INSTANCE.resolvedColorIntResourceId(context, R.attr.colorAccentDim));
        } else {
            unorderedListView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        }
    }

    public void setCallback(@Nullable Callback callback) {
        this.callback = callback;
    }

    public void setMarkdownEditText(@Nullable MarkdownEditText markdownEditText) {
        if (this.markdownEditText != null) {
            this.markdownEditText.markdownControlsCallbacks = null;
        }
        this.markdownEditText = markdownEditText;
        if (this.markdownEditText != null) {
            this.markdownEditText.markdownControlsCallbacks = this;
        }
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        this.context = context;
        View.inflate(context, R.layout.widget_markdown_controls, this);

        //Done to support the xml layout preview
        if (isInEditMode()) {
            return;
        }

        Injector.INSTANCE.get().inject(this);
        ButterKnife.bind(this);
        ButterKnife.bind(new ClickListeners(), this);
        updateIconColors();
    }

    private void updateIconColors() {
        int accentColorInt = LdsDrawableUtil.INSTANCE.resolvedColorIntResourceId(getContext(), R.attr.colorAccent);

        boldView.setImageDrawable(LdsDrawableUtil.INSTANCE.tintDrawable(boldView.getDrawable(), accentColorInt));
        italicView.setImageDrawable(LdsDrawableUtil.INSTANCE.tintDrawable(italicView.getDrawable(), accentColorInt));
        unorderedListView.setImageDrawable(LdsDrawableUtil.INSTANCE.tintDrawable(unorderedListView.getDrawable(), accentColorInt));
        orderedListView.setImageDrawable(LdsDrawableUtil.INSTANCE.tintDrawable(orderedListView.getDrawable(), accentColorInt));
    }

    public class ClickListeners {

        @OnClick(R.id.markdown_controls_bold)
        public void onBoldClick() {
            if ((callback == null || !callback.onBoldClick()) && markdownEditText != null) {
                markdownEditText.toggleBold();
            }
        }

        @OnClick(R.id.markdown_controls_italic)
        public void onItalicClick() {
            if ((callback == null || !callback.onItalicClick()) && markdownEditText != null) {
                markdownEditText.toggleItalics();
            }
        }

        @OnClick(R.id.markdown_controls_unordered_list)
        public void onUnorderedListClick() {
            if ((callback == null || !callback.onUnorderedListClick()) && markdownEditText != null) {
                markdownEditText.toggleUnOrderedList();
            }
        }

        @OnClick(R.id.markdown_controls_ordered_list)
        public void onOrderedListClick() {
            if ((callback == null || !callback.onOrderedListClick()) && markdownEditText != null) {
                markdownEditText.toggleOrderedList();
            }
        }
    }
}

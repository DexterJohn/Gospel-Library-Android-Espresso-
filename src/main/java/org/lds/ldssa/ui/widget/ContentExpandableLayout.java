package org.lds.ldssa.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import org.lds.ldssa.R;

public class ContentExpandableLayout extends LinearLayout {

    private static final int ANIMATION_DURATION = 500;
    private int collapseExpandHandlerId;
    private int contentViewId;
    private int collapseExpandHandlerViewGroupId;

    private View collapseExpandHandlerView;
    private View contentView;
    private ViewGroup collapseExpandHandlerViewGroup;

    private boolean expanded = false;
    private boolean collapsible = false;
    private int maxCollapsedHeight = 0;
    private int contentHeight = 0;
    private int animationDuration = 0;

    // saved width for this (used with expand/collapse)
    private int widthMeasureSpec = 0;

    private OnExpandListener listener;


    public ContentExpandableLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ContentExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ContentExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ContentExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        listener = new DefaultOnExpandListener();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ContentExpandableLayout, 0, 0);

        // How high the content should be in "collapsed" state
        maxCollapsedHeight = (int) typedArray.getDimension(R.styleable.ContentExpandableLayout_maxCollapsedHeight, 0.0f);

        // How long the animation should take
        animationDuration = typedArray.getInteger(R.styleable.ContentExpandableLayout_animationDuration, ANIMATION_DURATION);

        int handleId = typedArray.getResourceId(R.styleable.ContentExpandableLayout_handle, 0);
        if (handleId == 0) {
            throw new IllegalArgumentException(
                    "The handle attribute is required and must refer to a valid child.");
        }

        int contentId = typedArray.getResourceId(R.styleable.ContentExpandableLayout_content, 0);
        if (contentId == 0) {
            throw new IllegalArgumentException("The content attribute is required and must refer to a valid child.");
        }

        int viewGroupId = typedArray.getResourceId(R.styleable.ContentExpandableLayout_viewgroup, 0);
        boolean isViewGroup = typedArray.getBoolean(R.styleable.ContentExpandableLayout_isviewgroup, false);
        if (isViewGroup) {
            collapseExpandHandlerViewGroupId = viewGroupId;
        } else {
            collapseExpandHandlerViewGroupId = 0;
        }

        collapsible = typedArray.getBoolean(R.styleable.ContentExpandableLayout_collapsible, false);

        collapseExpandHandlerId = handleId;
        contentViewId = contentId;

        typedArray.recycle();
    }

    public void setOnExpandListener(OnExpandListener listener) {
        this.listener = listener;
    }

    public void setMaxCollapsedHeight(int maxCollapsedHeight) {
        this.maxCollapsedHeight = maxCollapsedHeight;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        collapseExpandHandlerView = findViewById(collapseExpandHandlerId);
        if (collapseExpandHandlerView == null) {
            throw new IllegalArgumentException("The handle attribute is must refer to an existing child.");
        }
        if (collapseExpandHandlerViewGroupId != 0) {
            collapseExpandHandlerViewGroup = findViewById(collapseExpandHandlerViewGroupId);
        }


        contentView = findViewById(contentViewId);
        if (contentView == null) {
            throw new IllegalArgumentException("The content attribute must refer to an existing child.");
        }

        android.view.ViewGroup.LayoutParams contentViewLayoutParams = contentView.getLayoutParams();
        contentView.setLayoutParams(contentViewLayoutParams);

        collapseExpandHandlerView.setOnClickListener(new ExpandCollapseToggler());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void updateContentViewSize() {
        // First, measure how high content wants to be
        contentView.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
        contentHeight = contentView.getMeasuredHeight();

        if (contentHeight <= maxCollapsedHeight) {
            if (collapseExpandHandlerViewGroup != null) {
                collapseExpandHandlerViewGroup.setVisibility(View.GONE);
            } else {
                collapseExpandHandlerView.setVisibility(GONE);
            }

            // set the content view to just wrap its content.... allow it to be smaller
            android.view.ViewGroup.LayoutParams contentViewLayoutParams = contentView.getLayoutParams();
            contentViewLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            contentView.setLayoutParams(contentViewLayoutParams);
        } else {
            if (collapseExpandHandlerViewGroup != null) {
                collapseExpandHandlerViewGroup.setVisibility(View.VISIBLE);
            } else {
                collapseExpandHandlerView.setVisibility(VISIBLE);
            }

            android.view.ViewGroup.LayoutParams contentViewLayoutParams = contentView.getLayoutParams();
            contentViewLayoutParams.height = maxCollapsedHeight;
            contentView.setLayoutParams(contentViewLayoutParams);
        }
    }

    private class ExpandCollapseToggler implements OnClickListener {
        public void onClick(View v) {
            Animation animation;
            if (expanded) {
                animation = new ExpandAnimation(contentHeight, maxCollapsedHeight);
                listener.onCollapse(collapseExpandHandlerView, contentView);
            } else {
                contentView.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
                contentHeight = contentView.getMeasuredHeight();

                animation = new ExpandAnimation(maxCollapsedHeight, contentHeight);
                listener.onExpand(collapseExpandHandlerView, contentView);
            }
            animation.setDuration(animationDuration);
            if (contentView.getLayoutParams().height == 0) {//Need to do this or else the animation will not play if the height is 0
                android.view.ViewGroup.LayoutParams lp = contentView.getLayoutParams();
                lp.height = 1;
                contentView.setLayoutParams(lp);
                contentView.requestLayout();
            }
            contentView.startAnimation(animation);
            expanded = !expanded;

            if (!collapsible) {
                collapseExpandHandlerView.setVisibility(GONE);
            }
        }
    }

    private class ExpandAnimation extends Animation {
        private final int startHeight;
        private final int deltaHeight;

        public ExpandAnimation(int startHeight, int endHeight) {
            this.startHeight = startHeight;
            deltaHeight = endHeight - startHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            android.view.ViewGroup.LayoutParams contentViewLayoutParams = contentView.getLayoutParams();
            contentViewLayoutParams.height = (int) (startHeight + deltaHeight * interpolatedTime);
            contentView.setLayoutParams(contentViewLayoutParams);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    public interface OnExpandListener {
        void onExpand(View handle, View content);
        void onCollapse(View handle, View content);
    }

    private class DefaultOnExpandListener implements OnExpandListener {
        public void onCollapse(View handle, View content) {
        }

        public void onExpand(View handle, View content) {
        }
    }
}
package org.lds.ldssa.ui.adapter.viewholder;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ImageActionTwoRowViewHolder extends ClickableViewHolder {
    public static final long ANIMATION_DURATION = 250;

    public interface OnActionImageClickListener {
        void onActionImageClick(ImageActionTwoRowViewHolder viewHolder);
    }

    @BindView(R.id.titleTextView)
    TextView titleView;
    @BindView(R.id.subTitleTextView)
    TextView subTitleView;
    @BindView(R.id.imageView)
    ImageView imageView;

    @DrawableRes
    private int unSelectedImageId;
    private Drawable unSelectedImage;
    @Nullable
    private OnActionImageClickListener actionImageClickListener;

    public static ImageActionTwoRowViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image_action_two_row, parent, false);
        return new ImageActionTwoRowViewHolder(view);
    }

    public ImageActionTwoRowViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        imageView.setOnClickListener(new ActionImageClickListener());
    }

    public void setOnActionImageClickListener(@Nullable OnActionImageClickListener listener) {
        this.actionImageClickListener = listener;
    }

    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    public void setSubTitle(CharSequence subTitle) {
        subTitleView.setVisibility(StringUtils.isEmpty(subTitle) ? View.GONE : View.VISIBLE);
        subTitleView.setText(subTitle);
    }

    public void setUnSelectedImage(@DrawableRes int drawableId) {
        unSelectedImageId = drawableId;
        imageView.setImageResource(drawableId);
    }

    public void setUnSelectedImage(Drawable drawable) {
        unSelectedImageId = 0;
        unSelectedImage = drawable;
        imageView.setImageDrawable(drawable);
    }

    public void setSelected(boolean selected) {
        if (unSelectedImageId == 0 && unSelectedImage == null) {
            return;
        }

        if (selected) {
            imageView.setImageResource(R.drawable.checkmark_in_circle);
        } else {
            if (unSelectedImageId != 0) {
                imageView.setImageResource(unSelectedImageId);
            } else {
                imageView.setImageDrawable(unSelectedImage);
            }
        }
    }

    private class ActionImageClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (actionImageClickListener != null) {
                actionImageClickListener.onActionImageClick(ImageActionTwoRowViewHolder.this);
            }
        }
    }
}

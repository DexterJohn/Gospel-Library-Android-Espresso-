package org.lds.ldssa.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import org.lds.ldssa.R;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.model.webview.content.dto.DtoImage;
import org.lds.ldssa.model.webview.content.dto.DtoImageSource;
import org.lds.ldssa.ui.widget.LoadingView;
import org.lds.mobile.ui.widget.media.TouchImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pocketknife.PocketKnife;

public class ImageViewerActivity extends BaseActivity {
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";

    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.touchImageView)
    TouchImageView touchImageView;

    private DtoImage image;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_image_viewer;
    }

    @NonNull
    @Override
    protected String getAnalyticsScreenName() {
        return Analytics.Screen.IMAGE_VIEW;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        PocketKnife.bindExtras(this);

        image = (DtoImage) getIntent().getSerializableExtra(EXTRA_IMAGE);

        setTitle(image.getTitle());
        Glide.with(this).load(getLargestImageUrl()).into(new ImageTarget(touchImageView));
    }

    private String getLargestImageUrl() {
        int currentWidth = 0;
        String url = null;

        for (DtoImageSource source : image.getSources()) {
            int width = Integer.valueOf(source.getWidth());
            if (width > currentWidth) {
                currentWidth = width;
                url = source.getSrc();
            }
        }

        return url;
    }

    /**
     * Used to keep track of when the image is actually loaded, allowing us
     * to hide the loading view
     */
    private class ImageTarget extends DrawableImageViewTarget {

        public ImageTarget(ImageView view) {
            super(view);
        }

        @Override
        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
            super.onResourceReady(resource, transition);
            loadingView.setVisibility(View.GONE);
        }

        @Override
        protected void setResource(Drawable resource) {
            super.setResource(resource);
            loadingView.setVisibility(View.GONE);
        }
    }
}

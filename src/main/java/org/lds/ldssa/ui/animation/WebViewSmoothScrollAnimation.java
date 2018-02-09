package org.lds.ldssa.ui.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.webkit.WebView;

/**
 * An animation to smoothly scroll to the specified x
 */
public class WebViewSmoothScrollAnimation extends Animation {
    public static final int DEFAULT_DURATION = 150;

    private final WebView webView;
    private final float endY;
    private final float endX;

    public WebViewSmoothScrollAnimation(int endX, int endY, WebView webView) {
        this.endX = (float)endX;
        this.endY = (float)endY;
        this.webView = webView;

        setDuration(DEFAULT_DURATION);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int currentX = (int)(endX * interpolatedTime);
        int currentY = (int)(endY * interpolatedTime);
        webView.scrollTo(currentX, currentY);
    }
}
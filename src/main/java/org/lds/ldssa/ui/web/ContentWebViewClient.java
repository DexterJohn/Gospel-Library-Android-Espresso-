package org.lds.ldssa.ui.web;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * This class is used to intercept link taps by the user and handle them ourselves instead of allowing the webview to handle them.
 */
public class ContentWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
        return true;
    }
}

package org.lds.ldssa.ui.web;

import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import timber.log.Timber;

public class ContentWebChromeClient extends WebChromeClient {
    @Inject
    public ContentWebChromeClient() {
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Timber.d("JsAlert: %s", message);
        return false;
    }

    @Override
    public boolean onConsoleMessage(@Nonnull ConsoleMessage consoleMessage) {
        Timber.d("JsConsole (%s:%d) - %s",  consoleMessage.sourceId(), consoleMessage.lineNumber(), consoleMessage.message());
        return true;
    }

}

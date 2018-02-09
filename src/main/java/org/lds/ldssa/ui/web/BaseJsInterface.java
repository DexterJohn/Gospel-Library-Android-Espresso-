package org.lds.ldssa.ui.web;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import timber.log.Timber;

public class BaseJsInterface {
    @JavascriptInterface
    public void jsShowToast(String toast) {
        jsShowToast(toast, Toast.LENGTH_SHORT);
    }

    @JavascriptInterface
    public void jsShowToast(String toast, int length) {
        Timber.d("JsToast: %s", toast);
    }

    @JavascriptInterface
    public void jsConsoleLog(String msg) {
        Timber.d("JsLog %s", msg);
    }
}

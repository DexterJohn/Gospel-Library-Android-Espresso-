package org.lds.ldssa.util;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import javax.inject.Inject;

public class DeviceUtil {
    public static final String ANDROID_SYSTEM_WEBVIEW_PACKAGE = "com.google.android.webview";
    public static final String CHROME_APP_PACKAGE = "com.android.chrome";

    private final Application application;

    @Inject
    public DeviceUtil(Application application) {
        this.application = application;
    }

    public boolean isPackageInstalled(String packageName) {
        PackageManager pm = application.getPackageManager();
        try {
            pm.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public String getPackageVersionName(String packageName) {
        PackageManager pm = application.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return packageName + " not found";
        }
    }
}
